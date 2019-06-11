package com.github.joelhandwell.populi


import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import ch.qos.logback.classic.Level
import org.slf4j.LoggerFactory

fun muteJettyLogger() {
    (LoggerFactory.getLogger("org.eclipse.jetty") as ch.qos.logback.classic.Logger).level = Level.ERROR
}

object DegreeSpec : Spek({
    describe("Degree") {

        it("unmarshal xml to Specialization") {
            val xml = """
            <specialization>
                <id>1</id>
                <type>MAJOR</type>
                <name>Test Specialization Name</name>
                <description>Test specialization description</description>
                <abbrv>TSD</abbrv>
                <status>ACTIVE</status>
                <cip_code>52.0201: Business Administration and Management, General</cip_code>
            </specialization>
            """.trimIndent()

            val s = JAXB.unmarshal(xml.reader(), Specialization::class.java)
            assertSpecialization(s)
        }

        it("marshal Degree with multiple specializations") {
            val s1 = Specialization(1, "", "", "", "", "", "")
            val s2 = Specialization(2, "", "", "", "", "", "")
            val s3 = Specialization(3, "", "", "", "", "", "")
            val d = Degree(1, "", "", 1, "", 1, "", "", 1, 1, "")
            d.specialization.addAll(mutableListOf(s1, s2, s3))
            val writer = StringWriter()
            JAXB.marshal(d, writer)

            val xml = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <degree>
                <id>1</id>
                <name></name>
                <abbrv></abbrv>
                <program_id>1</program_id>
                <program_name></program_name>
                <department_id>1</department_id>
                <department_name></department_name>
                <status></status>
                <graduate>1</graduate>
                <length>1</length>
                <length_unit></length_unit>
                <specializations>
                    <specialization>
                        <abbrv></abbrv>
                        <cip_code></cip_code>
                        <description></description>
                        <id>1</id>
                        <name></name>
                        <status></status>
                        <type></type>
                    </specialization>
                    <specialization>
                        <abbrv></abbrv>
                        <cip_code></cip_code>
                        <description></description>
                        <id>2</id>
                        <name></name>
                        <status></status>
                        <type></type>
                    </specialization>
                    <specialization>
                        <abbrv></abbrv>
                        <cip_code></cip_code>
                        <description></description>
                        <id>3</id>
                        <name></name>
                        <status></status>
                        <type></type>
                    </specialization>
                </specializations>
            </degree>
            """.trimIndent()
            assertEquals(xml.trim(), writer.toString().trim())
        }

        it("unmarshal xml to Degree") {
            val xml = """
            <degree>
                <id>1</id>
                <name>Test Degree</name>
                <abbrv>TD</abbrv>
                <program_id>11</program_id>
                <program_name>Test Program Name</program_name>
                <department_id>111</department_id>
                <department_name>Test Department</department_name>
                <status>ACTIVE</status>
                <graduate>1</graduate>
                <length>15</length>
                <length_unit>MONTHS</length_unit>
                <specializations>
                    <specialization>
                        <id>1</id>
                        <type>MAJOR</type>
                        <name>Test Specialization Name</name>
                        <description>Test specialization description</description>
                        <abbrv>TSD</abbrv>
                        <status>ACTIVE</status>
                        <cip_code>52.0201: Business Administration and Management, General</cip_code>
                    </specialization>
                </specializations>
            </degree>
            """.trimIndent()

            val d = JAXB.unmarshal(xml.reader(), Degree::class.java)
            assertDegree(d)
        }

        it("unmarshal to DegreeRequest") {
            val xml = """
            <request>
                <degree>
                    <id>1</id>
                    <name>Test Degree</name>
                    <abbrv>TD</abbrv>
                    <program_id>11</program_id>
                    <program_name>Test Program Name</program_name>
                    <department_id>111</department_id>
                    <department_name>Test Department</department_name>
                    <status>ACTIVE</status>
                    <graduate>1</graduate>
                    <length>15</length>
                    <length_unit>MONTHS</length_unit>
                    <specializations>
                        <specialization>
                            <id>1</id>
                            <type>MAJOR</type>
                            <name>Test Specialization Name</name>
                            <description>Test specialization description</description>
                            <abbrv>TSD</abbrv>
                            <status>ACTIVE</status>
                            <cip_code>52.0201: Business Administration and Management, General</cip_code>
                        </specialization>
                    </specializations>
                </degree>
                <degree>
                    <id>2</id>
                    <specializations>
                        <specialization>
                            <id>2</id>
                        </specialization>
                        <specialization>
                            <id>3</id>
                        </specialization>
                    </specializations>
                </degree>
            </request>
            """.trimIndent()

            val dr = JAXB.unmarshal(xml.reader(), DegreeRequest::class.java)
            assertDegreeRequest(dr)
        }
    }
})

const val wireMockPort = 8080

const val apiAccessKey =
    "694f1f255a606e89bd6acc9cdc215c277e7bfd5a326fe057d99903606562cd7f1d37bbf4631c42773321e41ed0f7241decfff04eb8bdac6dfa6b14fc9171086626a22091dd35b7a99db5d24a3df4daa094c16e0b74f5a9ecd8bfc584c13d6f2e5c2719692416cce27d49638ac88a4a8921c7c437c40d4f4ab219fb7fcd5903a8533873c40bb7cda456ff6fd71992403450b3096d5898b40ed2b5983ef970c5e68f5ace7507517283dac8472d4e2d"

object ClientSpec : Spek({
    describe("Client") {
        muteJettyLogger()
        val wireMockServer = WireMockServer()
        beforeGroup { wireMockServer.start() }

        it("send request, receive response and parse it into object") {
            stubFor(
                post("/api/").withRequestBody(containing("access_key=$apiAccessKey&task=getDegrees"))
                    .willReturn(aResponse().withBodyFile("degree.xml"))
            )
            val populi = populiClientOf("http://localhost:$wireMockPort/")
            val dr = populi.degreeRequest(accessKey = apiAccessKey).execute().body()
            assertNotNull(dr)
            assertDegreeRequest(dr)
        }

        afterGroup { wireMockServer.stop() }
    }
})

fun assertSpecialization(s: Specialization) {
    assertEquals(1, s.id)
    assertEquals("MAJOR", s.type)
    assertEquals("Test Specialization Name", s.name)
    assertEquals("TSD", s.abbrv)
    assertEquals("ACTIVE", s.status)
    assertEquals("52.0201: Business Administration and Management, General", s.cip_code)
}

fun assertDegree(d: Degree) {
    assertEquals(1, d.id)
    assertEquals("Test Degree", d.name)
    assertEquals("TD", d.abbrv)
    assertEquals(11, d.program_id)
    assertEquals("Test Program Name", d.program_name)
    assertEquals(111, d.department_id)
    assertEquals("Test Department", d.department_name)
    assertEquals("ACTIVE", d.status)
    assertEquals(1, d.graduate)
    assertEquals(15, d.length)
    assertEquals("MONTHS", d.length_unit)
    assertSpecialization(d.specialization.first())
}

fun assertDegreeRequest(dr: DegreeRequest) {
    assertDegree(dr.degree.first())
    assertEquals(2, dr.degree.size)
    assertEquals(3, dr.degree.flatMap { it.specialization }.size)
}