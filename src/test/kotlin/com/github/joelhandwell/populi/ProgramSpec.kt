package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object ProgramSpec : Spek({
    describe("Program"){
        it("marshal to xml"){
            val programResponse = ProgramResponse()
            programResponse.program.addAll(
                mutableListOf(
                    Program(26, "Undergraduate", "CREDITS", 0, "ACTIVE", 1),
                    Program(27, "Graduate", "CREDITS", 1, "ACTIVE", 0),
                    Program(40, "Hourly Program", "HOURS", 0, "RETIRED", 0)
                )
            )
            val sw = StringWriter()
            JAXB.marshal(programResponse, sw)
            assertEquals(XML_HEADER + getProgramsXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val programResponse = JAXB.unmarshal(getProgramsXml.reader(), ProgramResponse::class.java)
            assertPrograms(programResponse.program)
        }
    }
})

const val getProgramsXml = """
<response>
    <program>
        <default>1</default>
        <graduate_level>0</graduate_level>
        <id>26</id>
        <name>Undergraduate</name>
        <status>ACTIVE</status>
        <units>CREDITS</units>
    </program>
    <program>
        <default>0</default>
        <graduate_level>1</graduate_level>
        <id>27</id>
        <name>Graduate</name>
        <status>ACTIVE</status>
        <units>CREDITS</units>
    </program>
    <program>
        <default>0</default>
        <graduate_level>0</graduate_level>
        <id>40</id>
        <name>Hourly Program</name>
        <status>RETIRED</status>
        <units>HOURS</units>
    </program>
</response>
"""

fun assertPrograms(programs: MutableList<Program>){
    assertEquals(3, programs.size)
    val grad = programs.firstOrNull { it.id == 27 }
    assertNotNull(grad)
    assertEquals(0, grad.default)
    assertEquals(1, grad.graduate_level)
    assertEquals("Graduate", grad.name)
    assertEquals("ACTIVE", grad.status)
    assertEquals("CREDITS", grad.units)
}
