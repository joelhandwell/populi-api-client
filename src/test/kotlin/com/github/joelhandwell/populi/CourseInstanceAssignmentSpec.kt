package com.github.joelhandwell.populi

import io.github.threetenjaxb.core.LocalDateTimeXmlAdapter
import io.github.threetenjaxb.core.OffsetDateTimeXmlAdapter
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.xml.bind.annotation.XmlRootElement
import kotlin.test.assertEquals

@XmlRootElement(name = "student_info")
data class StudentInfo(
    var person_id: Int,
    var student_id: Int,
    var first_name: String,
    var last_name: String,
    var preferred_name: String,
    var submitted_assignment_data: Int,
    var assignment_submitted_at: OffsetDateTime? = null,
    var grade: Int? = null,
    var letter_grade: String? = null,
    var grade_added_at: LocalDateTime? = null,
    var grade_added_by_id: Int? = null,
    var grade_updated_at: LocalDateTime? = null,
    var grade_updated_by_id: Int? = null
)

@XmlRootElement(name = "assignment")
data class Assignment(
    var assignmentid: Int,
    var name: String,
    var points: Int,
    var type: String,
    var percent_of_course: Double,
    var groupid: Int,
    var group_name: String,
    var extra_credit: Double,
    var description: String,
    var discussion_id: Int,
    var visible_to_students_before_due: Int,
    var time_due: OffsetDateTime,
    var start_window: OffsetDateTime,
    var end_window: OffsetDateTime,
    var time_limit: Int,
    var retake_policy: String,
    var retakes: Int,
    var proctored: Int,
    var test_submit_feedback: String,
    var test_end_feedback: String,
    var student_info: MutableList<StudentInfo> = mutableListOf()
)

@XmlRootElement(name = "response")
data class AssignmentResponse(
    var assignment: MutableList<Assignment> = mutableListOf()
)

private val studentInfo1 = StudentInfo(
    3093,
    2010000043,
    "James",
    "McMcintosh",
    "Jimmy",
    1,
    OffsetDateTime.parse("2016-06-16T15:28:57-04:00"),
    95,
    "A",
    LocalDateTime.parse("2017-07-22T13:35:49"),
    468721,
    LocalDateTime.parse("2017-07-22T13:35:49"),
    468721
)

private val studentInfo2 = StudentInfo(
    3094,
    2010000044,
    "Joseph",
    "White",
    "Joe",
    1,
    OffsetDateTime.parse("2016-06-16T15:28:57-04:00"),
    96,
    "B",
    LocalDateTime.parse("2017-07-22T13:35:49"),
    468722,
    LocalDateTime.parse("2017-07-22T13:35:49"),
    468722
)

private val test = Assignment(
    111,
    "Quiz #1",
    10,
    "TEST",
    5.00000000,
    7777,
    "Quizzes",
    0.0,
    "Quiz #1 Description",
    8888,
    1,
    OffsetDateTime.parse("2017-07-22T00:00:00-07:00"),
    OffsetDateTime.parse("2017-07-20T00:00:00-07:00"),
    OffsetDateTime.parse("2017-07-21T00:00:00-07:00"),
    60,
    "KEEP_HIGHEST",
    1,
    1,
    "FEEDBACK",
    "ANSWERS",
    mutableListOf(studentInfo1, studentInfo2)
)

/*private val assignment2 = Assignment(
    222,
    "Peer Review",
    10,
    "PEER_REVIEW_FILE_UPLOAD",
    5.00000000,
    7777,
    "Quizzes",
    0.0,
    "Peer review description",
    9999,
    1,
    OffsetDateTime.parse("2017-07-20T00:00:00-07:00"),
    OffsetDateTime.parse("2017-07-20T00:00:00-07:00"),
    OffsetDateTime.parse("2017-07-20T00:00:00-07:00"),
    100,
    1,

    100,
    0,
    "NEVER",
    "allow_review_comments",

    studentInfo1
)*/

object CourseInstanceAssignmentSpec : Spek({
    describe("OffsetDateTimeXmlAdapter") {

        val offsetDateTime = OffsetDateTime.of(2017, 7, 22, 0, 0, 0, 0, ZoneOffset.ofHours(-7))
        val adapter = OffsetDateTimeXmlAdapter()

        it("marshal OffsetDateTime to xml") {
            assertEquals("2017-07-22T00:00:00-07:00", adapter.marshal(offsetDateTime))
        }

        it("unmarshal xml to OffsetDateTime") {
            assertEquals(offsetDateTime, adapter.unmarshal("2017-07-22T00:00:00-07:00"))
        }
    }

    describe("LocalDateTimeXmlAdapter") {

        val localDateTime = LocalDateTime.of(2017, 7, 22, 13, 35, 49)
        val adapter = LocalDateTimeXmlAdapter()

        it("marshal LocalDateTime to xml") {
            assertEquals("2017-07-22T13:35:49", adapter.marshal(localDateTime))
        }

        it("unmarshal xml to LocalDateTime") {
            assertEquals(localDateTime, adapter.unmarshal("2017-07-22T13:35:49"))
        }
    }

    describe("StudentInfo") {

        val s1Xml = """
        <student_info>
            <assignment_submitted_at>2016-06-16T15:28:57-04:00</assignment_submitted_at>
            <first_name>James</first_name>
            <grade>95</grade>
            <grade_added_at>2017-07-22T13:35:49</grade_added_at>
            <grade_added_by_id>468721</grade_added_by_id>
            <grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
            <grade_updated_by_id>468721</grade_updated_by_id>
            <last_name>McMcintosh</last_name>
            <letter_grade>A</letter_grade>
            <person_id>3093</person_id>
            <preferred_name>Jimmy</preferred_name>
            <student_id>2010000043</student_id>
            <submitted_assignment_data>1</submitted_assignment_data>
        </student_info>
        """.trimIndent()

        it("marshal to xml") { assertMarshals(s1Xml, studentInfo1) }
        it("unmarshal from xml") { assertUnmarshals(studentInfo1, s1Xml) }
    }

    describe("StudentInfo without grading") {

        val xml = """
            <student_info>
                <first_name>James</first_name>
                <last_name>McMcintosh</last_name>
                <person_id>3093</person_id>
                <preferred_name>Jimmy</preferred_name>
                <student_id>2010000043</student_id>
                <submitted_assignment_data>0</submitted_assignment_data>
            </student_info>
            """.trimIndent()

        val s = StudentInfo(
            first_name = "James",
            last_name = "McMcintosh",
            person_id = 3093,
            preferred_name = "Jimmy",
            student_id = 2010000043,
            submitted_assignment_data = 0
        )

        it("marshal to xml") { assertMarshals(xml, s) }
        it("unmarshal from xml") { assertUnmarshals(s, xml) }
    }

/*  Assignment types
    Grade-only: Just give the student a grade. It's good for things like readings and other simple assignments that don't require a lot of interaction.
    File: Requires the student to submit a file to you for evaluation; good for essays, papers, etc. that benefit from feedback, revisions, and other interaction.
    Test: Creates a corresponding online test.
    Attendance: Calculates an assignment grade based on the student's attendance of course meeting times.
    Discussion: Creates a corresponding discussion.
    Essay: These provide students with a WYSIWYG editor that they can use to compose and format an essay-length composition right in Populi.
    Peer Review: Peer-review files and essays let other students in the course section view, comment on, and even grade the student's work. Read more about peer-review assignments.
    https://support.populiweb.com/hc/en-us/articles/223797467-Introduction-to-assignments-and-assignment-groups
    */
    describe("Assignment TEST") {

        val xml = """
        <assignment>
            <assignmentid>111</assignmentid>
            <description>Quiz #1 Description</description>
            <discussion_id>8888</discussion_id>
            <end_window>2017-07-21T00:00:00-07:00</end_window>
            <extra_credit>0.0</extra_credit>
            <group_name>Quizzes</group_name>
            <groupid>7777</groupid>
            <name>Quiz #1</name>
            <percent_of_course>5.0</percent_of_course>
            <points>10</points>
            <proctored>1</proctored>
            <retake_policy>KEEP_HIGHEST</retake_policy>
            <retakes>1</retakes>
            <start_window>2017-07-20T00:00:00-07:00</start_window>
            <student_info>
                <assignment_submitted_at>2016-06-16T15:28:57-04:00</assignment_submitted_at>
                <first_name>James</first_name>
                <grade>95</grade>
                <grade_added_at>2017-07-22T13:35:49</grade_added_at>
                <grade_added_by_id>468721</grade_added_by_id>
                <grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
                <grade_updated_by_id>468721</grade_updated_by_id>
                <last_name>McMcintosh</last_name>
                <letter_grade>A</letter_grade>
                <person_id>3093</person_id>
                <preferred_name>Jimmy</preferred_name>
                <student_id>2010000043</student_id>
                <submitted_assignment_data>1</submitted_assignment_data>
            </student_info>
            <student_info>
                <assignment_submitted_at>2016-06-16T15:28:57-04:00</assignment_submitted_at>
                <first_name>Joseph</first_name>
                <grade>96</grade>
                <grade_added_at>2017-07-22T13:35:49</grade_added_at>
                <grade_added_by_id>468722</grade_added_by_id>
                <grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
                <grade_updated_by_id>468722</grade_updated_by_id>
                <last_name>White</last_name>
                <letter_grade>B</letter_grade>
                <person_id>3094</person_id>
                <preferred_name>Joe</preferred_name>
                <student_id>2010000044</student_id>
                <submitted_assignment_data>1</submitted_assignment_data>
            </student_info>
            <test_end_feedback>ANSWERS</test_end_feedback>
            <test_submit_feedback>FEEDBACK</test_submit_feedback>
            <time_due>2017-07-22T00:00:00-07:00</time_due>
            <time_limit>60</time_limit>
            <type>TEST</type>
            <visible_to_students_before_due>1</visible_to_students_before_due>
        </assignment>
        """.trimIndent()

        it("marshal to xml") { assertMarshals(xml, test) }
        it("unmarshal from xml") { assertUnmarshals(test, xml) }
    }

    describe("Assignment GRADE_ONLY") {
        val xml = """
        <assignment>
            <assignmentid>110</assignmentid>
            <name>Live Chat</name>
            <points>80</points>
            <type>GRADE_ONLY</type>
            <percent_of_course>0</percent_of_course>
            <groupid>2298505</groupid>
            <group_name>I. Extra Credit</group_name>
            <extra_credit>0</extra_credit>
            <description>Live chat description</description>
            <discussion_id></discussion_id>
            <visible_to_students_before_due>1</visible_to_students_before_due>
            <time_due>2016-06-16T02:59:59-04:00</time_due>
            <student_info>
                <first_name>James</first_name>
                <last_name>McMcintosh</last_name>
                <letter_grade>A</letter_grade>
                <person_id>3093</person_id>
                <preferred_name>Jimmy</preferred_name>
                <student_id>2010000043</student_id>
                <submitted_assignment_data>0</submitted_assignment_data>
                <assignment_submitted_at></assignment_submitted_at>
            </student_info>
            <student_info>
                <first_name>3094</first_name>
                <grade>2010000044</grade>
                <grade_added_at>Joseph</grade_added_at>
                <grade_added_by_id>White</grade_added_by_id>
                <grade_updated_at>Joe</grade_updated_at>
                <grade_updated_by_id>2</grade_updated_by_id>
                <last_name>96</last_name>
                <letter_grade>B</letter_grade>
                <person_id>2017-07-22T13:35:49</person_id>
                <preferred_name>468722</preferred_name>
                <student_id>2017-07-22T13:35:49</student_id>
                <submitted_assignment_data>468722</submitted_assignment_data>
            </student_info>
        </assignment>
        """.trimIndent()
    }
})

const val getCourseInstanceAssignmentsXml = """
<response>
	<assignment>
		<assignmentid>111</assignmentid>
		<name>Quiz #1</name>
		<points>10</points>
		<type>TEST</type>
		<percent_of_course>5.00000000</percent_of_course>
		<groupid>7777</groupid>
		<group_name>Quizzes</group_name>
		<extra_credit>0</extra_credit>
		<description>Quiz #1 Description</description>
		<discussion_id>8888</discussion_id>
		<visible_to_students_before_due>1</visible_to_students_before_due>
		<time_due>2017-07-22T00:00:00-07:00</time_due>
		<start_window>2017-07-20T00:00:00-07:00</start_window>
		<end_window>2017-07-21T00:00:00-07:00</end_window>
		<time_limit>60</time_limit>
		<retake_policy>KEEP_HIGHEST</retake_policy>
		<retakes>1</retakes>
		<proctored>1</proctored>
		<test_submit_feedback>FEEDBACK</test_submit_feedback>
		<test_end_feedback>ANSWERS</test_end_feedback>
		<student_info>
			<person_id>3093</person_id>
			<student_id>2010000043</student_id>
			<first_name>James</first_name>
			<last_name>McMcintosh</last_name>
			<preferred_name>Jimmy</preferred_name>
			<submitted_assignment_data>1</submitted_assignment_data>
			<grade>95</grade>
			<letter_grade>A</letter_grade>
			<grade_added_at>2017-07-22T13:35:49</grade_added_at>
			<grade_added_by_id>468721</grade_added_by_id>
			<grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
			<grade_updated_by_id>468721</grade_updated_by_id>
		</student_info>
	</assignment>
	<assignment>
		<assignmentid>222</assignmentid>
		<name>Peer Review</name>
		<points>10</points>
		<type>PEER_REVIEW_FILE_UPLOAD</type>
		<percent_of_course>5.00000000</percent_of_course>
		<groupid>7777</groupid>
		<group_name>Quizzes</group_name>
		<extra_credit>0</extra_credit>
		<description>Peer review description</description>
		<discussion_id>9999</discussion_id>
		<visible_to_students_before_due>1</visible_to_students_before_due>
		<time_due>2017-07-20T00:00:00-07:00</time_due>
		<peer_grade>1</peer_grade>
		<grade_submissions>100</grade_submissions>
		<grade_reviews>100</grade_reviews>
		<anonymous_reviews>0</anonymous_reviews>
		<review_visibility>NEVER</review_visibility>
		<allow_review_comments>allow_review_comments</allow_review_comments>
		<reviews_due_time>2017-07-20T00:00:00-07:00</reviews_due_time>
		<reviews_closed_time>2017-07-20T00:00:00-07:00</reviews_closed_time>
		<student_info>
			<person_id>3093</person_id>
			<student_id>2010000043</student_id>
			<first_name>James</first_name>
			<last_name>McMcintosh</last_name>
			<preferred_name>Jimmy</preferred_name>
			<submitted_assignment_data>1</submitted_assignment_data>
			<grade>95</grade>
			<letter_grade>A</letter_grade>
			<grade_added_at>2017-07-22T13:35:49</grade_added_at>
			<grade_added_by_id>468721</grade_added_by_id>
			<grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
			<grade_updated_by_id>468721</grade_updated_by_id>
		</student_info>
	</assignment>
    <assignment>
        <assignmentid>8199698</assignmentid>
        <name>Non-RS students: Live Chat</name>
        <points>80</points>
        <type>GRADE_ONLY</type>
        <percent_of_course>0</percent_of_course>
        <groupid>2298506</groupid>
        <group_name>I. Extra Credit</group_name>
        <extra_credit>0</extra_credit>
        <description>- Online/hybrid students should join a total of 8 weekly chats on Fridays, 1pm to 2 pm, PST via
            Adobe Connect webinar system.
        </description>
        <discussion_id></discussion_id>
        <visible_to_students_before_due>1</visible_to_students_before_due>
        <time_due>2016-06-16T02:59:59-04:00</time_due>
		<student_info>
			<person_id>3093</person_id>
			<student_id>2010000043</student_id>
			<first_name>James</first_name>
			<last_name>McMcintosh</last_name>
			<preferred_name>Jimmy</preferred_name>
			<submitted_assignment_data>1</submitted_assignment_data>
			<grade>95</grade>
			<letter_grade>A</letter_grade>
			<grade_added_at>2017-07-22T13:35:49</grade_added_at>
			<grade_added_by_id>468721</grade_added_by_id>
			<grade_updated_at>2017-07-22T13:35:49</grade_updated_at>
			<grade_updated_by_id>468721</grade_updated_by_id>
		</student_info>
    </assignment>
</response>
"""
