package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val p = mutableListOf(
    Program(id = 2222, name = "Undergraduate"), Program(id = 3333, name = "Registered Nursing")
)

private val c1 = CourseInstance(
    11111,
    123,
    "History of Philosophy and Biblical Wisdom",
    "CUL301",
    1,
    4444,
    "Jose Faculty",
    "This course teaches students about the many",
    1,
    "Humanities",
    2.0,
    0.0,
    777,
    "On Campus",
    444,
    "Portland",
    LocalDate.parse("2004-10-18"),
    LocalDate.parse("2004-12-18"),
    LocalDate.parse("2004-10-18"),
    LocalDate.parse("2004-12-18"),
    30,
    10,
    1,
    p
)

private val c2 = CourseInstance(
    22222,
    444,
    "Calculus I",
    "MAT201",
    3,
    888,
    "Sarah McFacuty",
    "This course teaches students about",
    2,
    "Sciences",
    0.0,
    10.0,
    5555,
    "Online",
    7777,
    "Main",
    LocalDate.parse("2004-10-18"),
    LocalDate.parse("2004-12-18"),
    LocalDate.parse("2004-10-18"),
    LocalDate.parse("2004-12-18"),
    45,
    0,
    1,
    p
)

object CourseInstanceSpec : Spek({
    describe("CourseInstance") {
        it("marshal to xml") {
            val r = TermCourseInstanceResponse(mutableListOf(c1, c2))
            val sw = StringWriter()
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + getTermCourseInstancesXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val r = JAXB.unmarshal(getTermCourseInstancesXml.reader(), TermCourseInstanceResponse::class.java)
            assertTermCourseInstanceResponses(r.course_instance)
        }
    }
})

const val getTermCourseInstancesXml = """
<response>
    <course_instance>
        <instanceid>11111</instanceid>
        <courseid>123</courseid>
        <name>History of Philosophy and Biblical Wisdom</name>
        <abbrv>CUL301</abbrv>
        <section>1</section>
        <primary_faculty_id>4444</primary_faculty_id>
        <primary_faculty_name>Jose Faculty</primary_faculty_name>
        <description>This course teaches students about the many</description>
        <department_id>1</department_id>
        <department_name>Humanities</department_name>
        <credits>2.0</credits>
        <hours>0.0</hours>
        <delivery_method_id>777</delivery_method_id>
        <delivery_method_name>On Campus</delivery_method_name>
        <campus_id>444</campus_id>
        <campus_name>Portland</campus_name>
        <start_date>2004-10-18</start_date>
        <end_date>2004-12-18</end_date>
        <open_to_students_date>2004-10-18</open_to_students_date>
        <closed_to_students_date>2004-12-18</closed_to_students_date>
        <max_enrolled>30</max_enrolled>
        <max_auditors>10</max_auditors>
        <published>1</published>
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
    </course_instance>
    <course_instance>
        <instanceid>22222</instanceid>
        <courseid>444</courseid>
        <name>Calculus I</name>
        <abbrv>MAT201</abbrv>
        <section>3</section>
        <primary_faculty_id>888</primary_faculty_id>
        <primary_faculty_name>Sarah McFacuty</primary_faculty_name>
        <description>This course teaches students about</description>
        <department_id>2</department_id>
        <department_name>Sciences</department_name>
        <credits>0.0</credits>
        <hours>10.0</hours>
        <delivery_method_id>5555</delivery_method_id>
        <delivery_method_name>Online</delivery_method_name>
        <campus_id>7777</campus_id>
        <campus_name>Main</campus_name>
        <start_date>2004-10-18</start_date>
        <end_date>2004-12-18</end_date>
        <open_to_students_date>2004-10-18</open_to_students_date>
        <closed_to_students_date>2004-12-18</closed_to_students_date>
        <max_enrolled>45</max_enrolled>
        <max_auditors>0</max_auditors>
        <published>1</published>
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
    </course_instance>
</response>
"""

fun assertTermCourseInstanceResponses(courseInstances: MutableList<CourseInstance>) {
    assertEquals(mutableListOf(c1, c2), courseInstances)
}
