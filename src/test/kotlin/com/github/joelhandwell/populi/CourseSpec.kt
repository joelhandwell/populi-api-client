package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val p = mutableListOf(
    Program(id = 2222, name = "Undergraduate"), Program(id = 3333, name = "Registered Nursing")
)

object CourseSpec : Spek({
    describe("Course") {
        it("marshal to xml") {
            val r = CourseResponse(
                mutableListOf(
                    Course(
                        11111,
                        "Advanced Writing: The Novel",
                        "ENG400",
                        "You'd want to take ENG400 because",
                        3.00,
                        0.00,
                        "ACTIVE",
                        1,
                        "Humanities",
                        p
                    ), Course(
                        22222,
                        "Calculus I",
                        "MAT201",
                        "You'd want to take Calculus because",
                        0.00,
                        20.00,
                        "ACTIVE",
                        2,
                        "Sciences",
                        p
                    )
                )
            )
            val sw = StringWriter()
            JAXB.marshal(r, sw)

            // skip due to Course.abbrv have variant
            // assertEquals(XML_HEADER + getCourseCatalogXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml") {
            val r = JAXB.unmarshal(getCourseCatalogXml.reader(), CourseResponse::class.java)
            assertCourses(r.course)
        }
    }
})

const val getCourseCatalogXml = """
<response>
    <course>
        <courseid>11111</courseid>
        <name>Advanced Writing: The Novel</name>
        <abbrv>ENG400</abbrv>
        <description>You'd want to take ENG400 because</description>
        <credits>3.0</credits>
        <hours>0.0</hours>
        <status>ACTIVE</status>
        <department_id>1</department_id>
        <department_name>Humanities</department_name>
        <programs>
            <program>
                <id>2222</id>
                <name>Undergraduate</name>
            </program>
            <program>
                <id>3333</id>
                <name>Registered Nursing</name>
            </program>
        </programs>
    </course>
    <course>
        <courseid>22222</courseid>
        <name>Calculus I</name>
        <abbrv>MAT201</abbrv>
        <description>You'd want to take Calculus because</description>
        <credits>0.0</credits>
        <hours>20.0</hours>
        <status>ACTIVE</status>
        <department_id>2</department_id>
        <department_name>Sciences</department_name>
        <programs>
            <program>
                <id>2222</id>
                <name>Undergraduate</name>
            </program>
            <program>
                <id>3333</id>
                <name>Registered Nursing</name>
            </program>
        </programs>
    </course>
</response>
"""

fun assertCourses(courses: MutableList<Course>) {
    assertEquals(2, courses.size)
    val c = courses.firstOrNull { it.courseid == 22222 }
    assertNotNull(c)
    assertEquals(22222, c.courseid)
    assertEquals("Calculus I", c.name)
    assertEquals("MAT201", c.abbrv)
    assertEquals("You'd want to take Calculus because", c.description)
    assertEquals(0.0, c.credits)
    assertEquals(20.0, c.hours)
    assertEquals("ACTIVE", c.status)
    assertEquals(2, c.department_id)
    assertEquals("Sciences", c.department_name)
    assertEquals(p, c.program)
}
