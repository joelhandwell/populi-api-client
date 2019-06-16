package com.github.joelhandwell.populi

import java.io.StringWriter
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
fun assertMarshaled(expectedXml: String, actualObject: Any) {
    assertEquals(XML_HEADER + expectedXml.trim(), StringWriter().apply { JAXB.marshal(actualObject, this) }.toString().trim())
}
/**
 * Convenience function to reduce repeated code
 */
fun assertUnmarshaled(expectedObject: Any, actualXml: String) {
    assertEquals(expectedObject, JAXB.unmarshal(actualXml.reader(), expectedObject::class.java))
}
