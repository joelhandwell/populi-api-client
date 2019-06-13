package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object CourseGroupSpec : Spek({
    describe("CourseGroup") {
        it("marshal to xml") {
            val r = CourseGroupResponse(
                mutableListOf(CourseGroup(1, "CORE", "Core Curriculum"), CourseGroup(2, "ENG", "English"))
            )
            val sw = StringWriter()
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + getCourseGroupsXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml") {
            val r = JAXB.unmarshal(getCourseGroupsXml.reader(), CourseGroupResponse::class.java)
            assertCourseGroups(r.course_group)
        }
    }
})

const val getCourseGroupsXml = """
<response>
    <course_groups>
        <course_group>
            <abbreviation>CORE</abbreviation>
            <id>1</id>
            <name>Core Curriculum</name>
        </course_group>
        <course_group>
            <abbreviation>ENG</abbreviation>
            <id>2</id>
            <name>English</name>
        </course_group>
    </course_groups>
</response>
"""

fun assertCourseGroups(courseGroups: MutableList<CourseGroup>) {
    assertEquals(2, courseGroups.size)
    val g = courseGroups.firstOrNull { it.id == 1 }
    assertNotNull(g)
    assertEquals("CORE", g.abbreviation)
    assertEquals("Core Curriculum", g.name)
}
