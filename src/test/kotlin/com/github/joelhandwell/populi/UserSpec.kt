package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

object UserSpec : Spek({
    describe("Person") {
        it("marshal to xml") {
            val userResponse = UserResponse()
            userResponse.person.addAll(
                mutableListOf(Person(40913, "Frank", "Klemgaard", "frankk", 0), Person(40914, "John", "White", "johnw", 0))
            )
            val sw = StringWriter()
            JAXB.marshal(userResponse, sw)
            assertEquals(
                XML_HEADER + getUsersXml.trim(),
                sw.toString().trim()
            )
        }

        it("unmarshal from xml") {
            val r = JAXB.unmarshal(getUsersXml.reader(), UserResponse::class.java)
            val users = r.person
            assertUsers(users)
        }
    }
})

const val getUsersXml = """
<response>
    <person>
        <blocked>0</blocked>
        <first>Frank</first>
        <last>Klemgaard</last>
        <person_id>40913</person_id>
        <username>frankk</username>
    </person>
    <person>
        <blocked>0</blocked>
        <first>John</first>
        <last>White</last>
        <person_id>40914</person_id>
        <username>johnw</username>
    </person>
</response>
"""

fun assertUsers(users: MutableList<Person>) {
    assertEquals(2, users.size)
    val john = users.firstOrNull { it.person_id == 40914 } ?: throw AssertionError("john not found")
    assertEquals("John", john.first)
    assertEquals("White", john.last)
    assertEquals("johnw", john.username)
    assertEquals(0, john.blocked)
}
