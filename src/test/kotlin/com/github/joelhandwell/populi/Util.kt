package com.github.joelhandwell.populi

import com.github.tomakehurst.wiremock.client.WireMock
import java.io.StringWriter
import java.nio.file.Paths
import java.util.*
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

const val WIREMOCK_PORT = 8080
const val TEST_API_ACCESS_KEY =
    "694f1f255a606e89bd6acc9cdc215c277e7bfd5a326fe057d99903606562cd7f1d37bbf4631c42773321e41ed0f7241decfff04eb8bdac6dfa6b14fc9171086626a22091dd35b7a99db5d24a3df4daa094c16e0b74f5a9ecd8bfc584c13d6f2e5c2719692416cce27d49638ac88a4a8921c7c437c40d4f4ab219fb7fcd5903a8533873c40bb7cda456ff6fd71992403450b3096d5898b40ed2b5983ef970c5e68f5ace7507517283dac8472d4e2d"

const val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"

/**
 * Convenience function to reduce repeated code
 * @param expectedXml expect String.trimIndent() has been already applied
 */
fun assertMarshals(expectedXml: String, actualObject: Any) {
    assertEquals(XML_HEADER + sanitizeXml(expectedXml).trim(), StringWriter().apply { JAXB.marshal(actualObject, this) }.toString().trim())
}

/**
 * convenience function to check if the given object marshals without error
 */
fun assertMarshals(actualObject: Any) {
    JAXB.marshal(actualObject, StringWriter())
}

/**
 * Convenience function to reduce repeated code
 */
fun assertUnmarshals(expectedObject: Any, actualXml: String) {
    assertEquals(expectedObject, JAXB.unmarshal(sanitizeXml(actualXml).reader(), expectedObject::class.java))
}

/**
 * Convenience function to generate mock populi api client
 */
fun mockClient(): Populi = Populi.Builder()
    .withBaseUrl("http://localhost:$WIREMOCK_PORT/")
    .withAccessKey(TEST_API_ACCESS_KEY)
    .build()

object LocalProperty {

    val p = Properties()

    init {
        val input = Paths.get("${System.getProperty("user.dir")}\\local.properties")
            .toFile()
            .inputStream()

        p.load(input)
    }

    val baseUrl = p.getProperty("real.baseurl")!!
    val username = p.getProperty("real.username")!!
    val password = p.getProperty("real.password")!!
    val course_group_id = p.getProperty("real.course_group_id").toInt()
    val courseInstanceId = p.getProperty("real.course_instance_id").toInt()
    val courseInstanceAssignmentId = p.getProperty("real.course_instance_assignment_id").toInt()
    val yearId = p.getProperty("real.year_id").toInt()
    val termId = p.getProperty("real.term_id").toInt()
    val personId = p.getProperty("real.person_id").toInt()
    val customFieldId = p.getProperty("real.custom_field_id").toInt()
    val degreeId = p.getProperty("real.degree_id").toInt()
    val lessonId = p.getProperty("real.lesson_id").toInt()
    val applicationId = p.getProperty("real.application_id").toInt()
    val applicationFieldId = p.getProperty("real.application_field_id").toInt()
    val inquiryId = p.getProperty("real.inquiry_id").toInt()
    val fileId = p.getProperty("real.file_id").toInt()
    val tagId = p.getProperty("real.tag_id").toInt()

}

fun realClient(): Populi = Populi.Builder()
    .withBaseUrl(LocalProperty.baseUrl)
    .withUsername(LocalProperty.username)
    .withPassword(LocalProperty.password)
    .build()

/**
 * Convenience function to prepare WireMockServer to respond for a request with a xml
 */
fun stubForPopuli(task: String, xml: String) {
    WireMock.stubFor(
        WireMock.post("/api/").withRequestBody(
            WireMock.containing("access_key=$TEST_API_ACCESS_KEY&task=$task")
        ).willReturn(
            WireMock.aResponse().withBody(xml)
        )
    )
}
