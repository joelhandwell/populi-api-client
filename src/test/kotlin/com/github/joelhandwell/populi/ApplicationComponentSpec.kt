package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val c1 = ApplicationComponent(
    id = 1111,
    name = "Educational History",
    description = "",
    received_date = LocalDate.parse("2008-04-15"),
    status = "ACCEPTED"
)

private val c2 = ApplicationComponent(
    id = 1112,
    name = "Please write us a 5-10 page essay on the topic of your choice.",
    description = "",
    received_date = LocalDate.parse("2008-04-15"),
    status = "ACCEPTED"
)

private val applicationComponentResponse = ApplicationComponentResponse(mutableListOf(c1, c2))

object ApplicationComponentSpec : Spek({

    describe("ApplicationComponent") {

        it("marshal to xml") { JAXB.marshal(applicationComponentResponse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(applicationComponentResponse, getApplicationComponentsXml) }
    }
})

fun assertApplicationComponents(applicationComponents: MutableList<ApplicationComponent>) {
    assertEquals(mutableListOf(c1, c2), applicationComponents)
}

const val getApplicationComponentsXml = """
<response>
    <component>
        <id>1111</id>
        <name>Educational History</name>
        <description></description>
        <received_date>2008-04-15</received_date>
        <status>ACCEPTED</status>
    </component>
    <component>
        <id>1112</id>
        <name>Please write us a 5-10 page essay on the topic of your choice.</name>
        <description></description>
        <received_date>2008-04-15</received_date>
        <status>ACCEPTED</status>
    </component>
</response>"""
