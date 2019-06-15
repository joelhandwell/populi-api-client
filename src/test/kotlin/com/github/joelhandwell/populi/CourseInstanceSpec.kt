package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.LocalTime
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val mt = MeetingTime(LocalTime.of(8, 0), LocalTime.of(9, 0), "Room TBA", "Smith Building", mutableListOf(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY))

private val f1 = Faculty(1, 1001, "Robert", "McProfessor", "Bob", "Bob McProfessor", 0)

private val f2 = Faculty(0, 554, "Jimmy", "McTeachingAssistant", "Jim", "Jimmy McTeachingAssistant", 1)

private val s = Supply(227835, "Computer wi3th Internet Access", 1)

private val a = Author(325, "Ernest Hemingway")

private val b = Book(
    227835,
    "The Old Man and The Sea",
    1,
    "0684801221",
    "9780684801223",
    "Scribner",
    "1995-05-05",
    "2nd",
    "Paperback",
    "The Old Man and the Sea is one of Hemingway's most enduring works.",
    "https://www.amazon.com/dp/0684801221",
    "https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL.jpg",
    500,
    328,
    "https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL160_.jpg",
    160,
    105,
    "https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL75_.jpg",
    75,
    49,
    mutableListOf(a)
)

private val c = CourseInstance(
    instanceid = 44444,
    name = "Calculus I",
    abbrv = "MAT201",
    section = 3,
    credits = 3.00,
    hours = 9.00,
    affects_earned_credits = 1,
    description = "Calculus I is the stepping stone into the world of higher mathematics",
    pass_fail = 0,
    finalized = 0,
    termid = 777,
    term_name = "Spring Term 2009-2010",
    allow_auditor_assignments = 0,
    allow_auditor_attendance = 0,
    start_date = LocalDate.parse("2004-10-18"),
    end_date = LocalDate.parse("2004-12-18"),
    open_to_students_date = LocalDate.parse("2004-10-18"),
    closed_to_students_date = LocalDate.parse("2004-12-18"),
    max_enrolled = 25,
    max_auditors = 5,
    published = 1,
    meeting_time = mutableListOf(mt),
    person = mutableListOf(f1, f2),
    supply = mutableListOf(s),
    book = mutableListOf(b)
)


object CourseInstanceSpec : Spek({
    describe("ClockLocalTimeAdapter") {

        val adapter = ClockLocalTimeAdapter()

        it("marshal LocalTime to xml value with format 8:00am") {
            assertEquals("8:00am", adapter.marshal(LocalTime.of(8, 0)))
            assertEquals("11:59pm", adapter.marshal(LocalTime.of(23, 59)))
        }

        it("unmarshal xml value 8:00am to LocalDate") {
            assertEquals(adapter.unmarshal("8:00am"), LocalTime.of(8, 0))
            assertEquals(adapter.unmarshal("11:59pm"), LocalTime.of(23, 59))
        }
    }

    describe("DayOfWeekAdapter") {

        val adapter = DayOfWeekAdapter()

        it("marshal DayOfWeek to xml value with format MO") {
            assertEquals("MO", adapter.marshal(MONDAY))
        }

        it("unmarshal xml value MO to DayOfWeek") {
            assertEquals(MONDAY, adapter.unmarshal("MO"))
        }
    }

    describe("MeetingTime") {
        val xml = """
            <meeting_time>
                <start_time>8:00am</start_time>
                <end_time>9:00am</end_time>
                <room>Room TBA</room>
                <building>Smith Building</building>
                <weekdays>
                    <day>MO</day>
                    <day>TU</day>
                    <day>WE</day>
                    <day>TH</day>
                    <day>FR</day>
                </weekdays>
            </meeting_time>
            """.trimIndent()

        it("marshal to xml") { assertMarshaled(xml, mt) }

        it("unmarshal from xml") { assertUnmarshaled(mt, xml) }
    }

    describe("Faculty") {

        val xml = """
        <person>
            <primary>1</primary>
            <personid>1001</personid>
            <first>Robert</first>
            <last>McProfessor</last>
            <preferred>Bob</preferred>
            <displayname>Bob McProfessor</displayname>
            <is_teaching_assistant>0</is_teaching_assistant>
        </person>
        """.trimIndent()

        it("marshal to xml") { assertMarshaled(xml, f1) }

        it("unmarshal from xml") { assertUnmarshaled(f1, xml) }
    }

    describe("Supply") {
        val xml = """
        <supply>
            <id>227835</id>
            <name>Computer wi3th Internet Access</name>
            <required>1</required>
        </supply>
        """.trimIndent()

        it("marshal to xml") { assertMarshaled(xml, s) }

        it("unmarshal from xml") { assertUnmarshaled(s, xml) }
    }

    describe("Author") {
        val xml = """
        <author>
            <id>325</id>
            <name>Ernest Hemingway</name>
        </author>
        """.trimIndent()

        it("marshal to xml") { assertMarshaled(xml, a) }

        it("unmarshal from xml") { assertUnmarshaled(a, xml) }
    }

    describe("Book") {
        val xml = """
        <book>
            <id>227835</id>
            <title>The Old Man and The Sea</title>
            <required>1</required>
            <isbn>0684801221</isbn>
            <ean>9780684801223</ean>
            <publisher>Scribner</publisher>
            <publish_date>1995-05-05</publish_date>
            <edition>2nd</edition>
            <binding>Paperback</binding>
            <description>The Old Man and the Sea is one of Hemingway's most enduring works.</description>
            <amazon_url>https://www.amazon.com/dp/0684801221</amazon_url>
            <image_url_large>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL.jpg</image_url_large>
            <image_height_large>500</image_height_large>
            <image_width_large>328</image_width_large>
            <image_url_medium>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL160_.jpg</image_url_medium>
            <image_height_medium>160</image_height_medium>
            <image_width_medium>105</image_width_medium>
            <image_url_small>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL75_.jpg</image_url_small>
            <image_height_small>75</image_height_small>
            <image_width_small>49</image_width_small>
            <authors>
                <author>
                    <id>325</id>
                    <name>Ernest Hemingway</name>
                </author>
            </authors>
        </book>
        """.trimIndent()

        it("marshal to xml") { assertMarshaled(xml, b) }
        it("unmarshal from xml") { assertUnmarshaled(b, xml) }
    }

    describe("CourseInstance") {
        it("marshal to xml") {
            val xml = """
            <response>
                <instanceid>44444</instanceid>
                <name>Calculus I</name>
                <abbrv>MAT201</abbrv>
                <section>3</section>
                <credits>3.0</credits>
                <hours>9.0</hours>
                <description>Calculus I is the stepping stone into the world of higher mathematics</description>
                <start_date>2004-10-18</start_date>
                <end_date>2004-12-18</end_date>
                <open_to_students_date>2004-10-18</open_to_students_date>
                <closed_to_students_date>2004-12-18</closed_to_students_date>
                <max_enrolled>25</max_enrolled>
                <max_auditors>5</max_auditors>
                <published>1</published>
                <programs/>
                <affects_earned_credits>1</affects_earned_credits>
                <pass_fail>0</pass_fail>
                <finalized>0</finalized>
                <termid>777</termid>
                <term_name>Spring Term 2009-2010</term_name>
                <allow_auditor_assignments>0</allow_auditor_assignments>
                <allow_auditor_attendance>0</allow_auditor_attendance>
                <schedule>
                    <meeting_time>
                        <start_time>8:00am</start_time>
                        <end_time>9:00am</end_time>
                        <room>Room TBA</room>
                        <building>Smith Building</building>
                        <weekdays>
                            <day>MO</day>
                            <day>TU</day>
                            <day>WE</day>
                            <day>TH</day>
                            <day>FR</day>
                        </weekdays>
                    </meeting_time>
                </schedule>
                <faculty>
                    <person>
                        <primary>1</primary>
                        <personid>1001</personid>
                        <first>Robert</first>
                        <last>McProfessor</last>
                        <preferred>Bob</preferred>
                        <displayname>Bob McProfessor</displayname>
                        <is_teaching_assistant>0</is_teaching_assistant>
                    </person>
                    <person>
                        <primary>0</primary>
                        <personid>554</personid>
                        <first>Jimmy</first>
                        <last>McTeachingAssistant</last>
                        <preferred>Jim</preferred>
                        <displayname>Jimmy McTeachingAssistant</displayname>
                        <is_teaching_assistant>1</is_teaching_assistant>
                    </person>
                </faculty>
                <supplies>
                    <supply>
                        <id>227835</id>
                        <name>Computer wi3th Internet Access</name>
                        <required>1</required>
                    </supply>
                </supplies>
                <books>
                    <book>
                        <id>227835</id>
                        <title>The Old Man and The Sea</title>
                        <required>1</required>
                        <isbn>0684801221</isbn>
                        <ean>9780684801223</ean>
                        <publisher>Scribner</publisher>
                        <publish_date>1995-05-05</publish_date>
                        <edition>2nd</edition>
                        <binding>Paperback</binding>
                        <description>The Old Man and the Sea is one of Hemingway's most enduring works.</description>
                        <amazon_url>https://www.amazon.com/dp/0684801221</amazon_url>
                        <image_url_large>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL.jpg</image_url_large>
                        <image_height_large>500</image_height_large>
                        <image_width_large>328</image_width_large>
                        <image_url_medium>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL160_.jpg</image_url_medium>
                        <image_height_medium>160</image_height_medium>
                        <image_width_medium>105</image_width_medium>
                        <image_url_small>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL75_.jpg</image_url_small>
                        <image_height_small>75</image_height_small>
                        <image_width_small>49</image_width_small>
                        <authors>
                            <author>
                                <id>325</id>
                                <name>Ernest Hemingway</name>
                            </author>
                        </authors>
                    </book>
                </books>
            </response>
            """
            val actual = StringWriter().apply { JAXB.marshal(c, this) }
            assertEquals(XML_HEADER + xml.trimIndent().trim(), actual.toString().trim())
        }

        it("unmarshal from xml"){
            assertCourseInstanceResponse(JAXB.unmarshal(getCourseInstanceXml.reader(), CourseInstance::class.java))
        }
    }
})

fun assertCourseInstanceResponse(courseInstance: CourseInstance) {
    courseInstance.program = mutableListOf()
    assertEquals(c, courseInstance)
}

const val getCourseInstanceXml = """
<response>
    <instanceid>44444</instanceid>
    <name>Calculus I</name>
    <abbrv>MAT201</abbrv>
    <section>3</section>
    <credits>3.00</credits>
    <hours>9.00</hours>
    <affects_earned_credits>1</affects_earned_credits>
    <description>Calculus I is the stepping stone into the world of higher mathematics</description>
    <pass_fail>0</pass_fail>.
    <finalized>0</finalized>
    <termid>777</termid>
    <term_name>Spring Term 2009-2010</term_name>
    <allow_auditor_assignments>0</allow_auditor_assignments>
    <allow_auditor_attendance>0</allow_auditor_attendance>
    <start_date>2004-10-18</start_date>
    <end_date>2004-12-18</end_date>
    <open_to_students_date>2004-10-18</open_to_students_date>
    <closed_to_students_date>2004-12-18</closed_to_students_date>
    <max_enrolled>25</max_enrolled>
    <max_auditors>5</max_auditors>
    <published>1</published>
    <schedule>
        <meeting_time>
            <start_time>8:00am</start_time>
            <end_time>9:00am</end_time>
            <room>Room TBA</room>
            <building>Smith Building</building>
            <weekdays>
                <day>MO</day>
                <day>TU</day>
                <day>WE</day>
                <day>TH</day>
                <day>FR</day>
            </weekdays>
        </meeting_time>
    </schedule>
    <faculty>
        <person>
            <primary>1</primary>
            <personid>1001</personid>
            <first>Robert</first>
            <last>McProfessor</last>
            <preferred>Bob</preferred>
            <displayname>Bob McProfessor</displayname>
            <is_teaching_assistant>0</is_teaching_assistant>
        </person>
        <person>
            <primary>0</primary>
            <personid>554</personid>
            <first>Jimmy</first>
            <last>McTeachingAssistant</last>
            <preferred>Jim</preferred>
            <displayname>Jimmy McTeachingAssistant</displayname>
            <is_teaching_assistant>1</is_teaching_assistant>
        </person>
    </faculty>
    <supplies>
        <supply>
            <id>227835</id>
            <name>Computer wi3th Internet Access</name>
            <required>1</required>
        </supply>
    </supplies>
    <books>
        <book>
            <id>227835</id>
            <title>The Old Man and The Sea</title>
            <required>1</required>
            <isbn>0684801221</isbn>
            <ean>9780684801223</ean>
            <publisher>Scribner</publisher>
            <publish_date>1995-05-05</publish_date>
            <edition>2nd</edition>
            <binding>Paperback</binding>
            <description>The Old Man and the Sea is one of Hemingway's most enduring works.</description>
            <amazon_url>https://www.amazon.com/dp/0684801221</amazon_url>
            <image_url_large>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL.jpg</image_url_large>
            <image_height_large>500</image_height_large>
            <image_width_large>328</image_width_large>
            <image_url_medium>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL160_.jpg</image_url_medium>
            <image_height_medium>160</image_height_medium>
            <image_width_medium>105</image_width_medium>
            <image_url_small>https://images-na.ssl-images-amazon.com/images/I/411pakPjvdL._SL75_.jpg</image_url_small>
            <image_height_small>75</image_height_small>
            <image_width_small>49</image_width_small>
            <authors>
                <author>
                    <id>325</id>
                    <name>Ernest Hemingway</name>
                </author>
            </authors>
        </book>
    </books>
</response>
"""
