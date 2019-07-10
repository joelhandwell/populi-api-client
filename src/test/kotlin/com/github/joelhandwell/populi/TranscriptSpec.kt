package com.github.joelhandwell.populi

import com.joelhandwell.populi.jaxb.UsaLocalDateAdapter
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import java.time.Year
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val date = LocalDate.of(1985, 1, 5)

private val address = TranscriptAddress(
    street = "P.O. Box 1994",
    city = "Moscow",
    state = "AR",
    zip = "88887"
)

private val student = TranscriptStudent(
    first_name = "Joe",
    last_name = "Bob",
    middle_name = "",
    preferred_name = "",
    birth_date = date, //Jan. 5 1985
    student_id = 85494038,
    enrolled_date = null,
    address = address
)

private val honor = TranscriptHonor(19748, "Cum Laude")

private val chcg = ClinicalHourCourseGroup(
    course_group_id = 846,
    name = "English",
    clinical_hours = 0.0,
    required_clinical_hours = 10.00
)

private val ahcg = AttendanceHourCourseGroup(
    course_group_id = 846,
    name = "English",
    attendance_hours = 0.0,
    required_attendance_hours = 10.00
)

private val specialization = TranscriptSpecialization(
    specialization_id = 513,
    name = "English",
    type = "MAJOR",
    status = "ACTIVE",
    granted_date = null,
    clinical_hour_course_group = mutableListOf(chcg),
    attendance_hour_course_group = mutableListOf(ahcg)
)

private val transcriptDegree = TranscriptDegree(
    degree_id = 1,
    degree_student_id = 10228,
    abbrv = "B.A.",
    name = "Bachelor of Arts",
    status = "ACTIVE",
    active_date = LocalDate.parse("2000-02-07"),
    inactive_date = null,
    graduation_date = null,
    graduate_degree = 0,
    catalog_year_id = 48,
    catalog_start_year = Year.of(2010),
    catalog_end_year = Year.of(2011),
    clinical_hour_course_group = mutableListOf(chcg),
    attendance_hour_course_group = mutableListOf(ahcg),
    specialization = mutableListOf(specialization)
)

private val transferCreditCourse = TransferCreditCourse(
    abbrv = "ENG101",
    name = "English 101",
    attempted_units = 3.00,
    earned_units = 3.00,
    earned_standing_units = 3.00,
    quality_points = 4.00,
    grade_abbrv = "A",
    letter_grade = "A",
    attendance_hours = 20.50,
    clinical_hours = 18.25
)

private val transferCreditTotal = TransferCreditTotal(
    gpa = 4.00,
    quality_points = 48.00,
    gpa_units = 12.00,
    attempted_units = 12.00,
    earned_units = 12.00,
    earned_standing_units = 12.00,
    attendance_hours = 42.50,
    clinical_hours = 30.25
)

private val transferCreditInstitution = TransferCreditInstitution(
    course = mutableListOf(transferCreditCourse),
    totals = transferCreditTotal
)

private val transcriptTermCourse = TranscriptTermCourse(
    instance_id = 84632,
    course_id = 98435,
    abbrv = "ENG102",
    name = "English 102",
    start_date = LocalDate.parse("2013-01-15"),
    end_date = LocalDate.parse("2013-04-15"),
    attempted_units = 3.00,
    earned_units = 3.00,
    `attempted_contra-units` = 3.00,
    `earned_contra-units` = 3.00,
    earned_standing_units = 3.00,
    status = "ENROLLED",
    grade_abbrv = "A",
    letter_grade = "A",
    numeric_grade = 100.00,
    quality_points = 12.00,
    gpa_units = 3.00,
    course_evaluation_grades_locked = 0,
    teacher_person_id = 468721,
    teacher_first_name = "Bob",
    teacher_last_name = "Teacher",
    teacher_preferred_name = "",
    teacher_display_name = "Dr. Bob Teacher",
    attempted_attendance_hours = 20.50,
    earned_attendance_hours = 20.50,
    attempted_clinical_hours = 18.25,
    earned_clinical_hours = 18.25
)

private val transcriptTermTotal = TranscriptTermTotal(
    gpa = 4.00,
    gpa_including_transfer = 4.00,
    quality_points = 48.00,
    gpa_units = 12.00,
    attempted_units = 12.00,
    `attempted_contra-units` = 12.00,
    earned_units = 12.00,
    `earned_contra-units` = 12.00,
    earned_standing_units = 12.00,
    cumulative_gpa = 4.00,
    cumulative_gpa_including_transfer = 4.00,
    cum_quality_points = 48.00,
    cum_gpa_units = 48.00,
    cum_attempted_units = 12.00,
    `cum_attempted_contra-units` = 12.00,
    cum_earned_units = 12.00,
    `cum_earned_contra-units` = 12.00,
    attempted_attendance_hours = 20.50,
    earned_attendance_hours = 20.50,
    cum_attempted_attendance_hours = 55.50,
    cum_earned_attendance_hours = 55.50,
    attempted_clinical_hours = 18.25,
    earned_clinical_hours = 18.25,
    cum_attempted_clinical_hours = 53.25,
    cum_earned_clinical_hours = 53.25
)

private val transcriptTerm = TranscriptTerm(
    term_id = 1354,
    name = "Spring Term 2012-2013",
    start_date = LocalDate.parse("2013-01-15"),
    end_date = LocalDate.parse("2013-04-15"),
    honor = mutableListOf(honor),
    discipline = TranscriptTermDiscipline(name = "Academic Suspension"),
    course = mutableListOf(transcriptTermCourse),
    totals = transcriptTermTotal
)

private val transcriptProgramTotal = TranscriptProgramTotal(
    attempted_units = 24.00,
    `attempted_contra-units` = 24.00,
    earned_units = 24.00,
    `earned_contra-units` = 24.00,
    earned_standing_units = 24.00,
    transfer_attempted_units = 12.00,
    `transfer_attempted_contra-units` = 12.00,
    transfer_earned_units = 12.00,
    `transfer_earned_contra-units` = 12.00,
    transfer_earned_standing_units = 12.00,
    cumulative_gpa = 4.00,
    quality_points = 192.00,
    gpa_units = 48.00,
    transfer_gpa = 4.00,
    transfer_quality_points = 16.00,
    transfer_gpa_units = 12.00,
    attempted_attendance_hours = 90.75,
    earned_attendance_hours = 90.75,
    transfer_attendance_hours = 38.00,
    attempted_clinical_hours = 95.00,
    earned_clinical_hours = 95.00,
    transfer_clinical_hours = 28.20
)

private val transcriptProgram = TranscriptProgram(
    honor = mutableListOf(honor),
    degree = mutableListOf(transcriptDegree),
    institution = mutableListOf(transferCreditInstitution),
    term = mutableListOf(transcriptTerm),
    totals = transcriptProgramTotal
)

private val transcriptCourseDescription = TranscriptCourseDescription(
    instance_id = 84632,
    name = "English 102",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
)

val transcript = Transcript(
    student = student,
    honor = mutableListOf(honor),
    program = mutableListOf(transcriptProgram),
    course_description = mutableListOf(transcriptCourseDescription)
)

object TranscriptSpec : Spek({

    describe("TranscriptAddress") {
        val xml = """
        <address>
            <city>Moscow</city>
            <state>AR</state>
            <street>P.O. Box 1994</street>
            <zip>88887</zip>
        </address>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(xml, address) }

        it("unmarshal from xml") { assertUnmarshals(address, xml) }
    }

    describe("UsaLocalDateAdapter") {
        it("unmarshal xml to LocalDate") {
            assertEquals(date, UsaLocalDateAdapter().unmarshal("Jan. 5 1985"))
        }
    }

    describe("TranscriptStudent") {
        val xml = """
        <student>
            <first_name>Joe</first_name>
            <last_name>Bob</last_name>
            <middle_name></middle_name>
            <preferred_name></preferred_name>
            <birth_date>Jan. 5 1985</birth_date>
            <student_id>85494038</student_id>
            <enrolled_date></enrolled_date>
            <address>
                <street>P.O. Box 1994</street>
                <city>Moscow</city>
                <state>AR</state>
                <zip>88887</zip>
            </address>
        </student>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(student, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(student, xml) }
    }

    describe("TranscriptHonor") {
        val xml = """
        <honor>
            <honor_id>19748</honor_id>
            <name>Cum Laude</name>
        </honor>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(xml, honor) }

        it("unmarshal from xml") { assertUnmarshals(honor, xml) }
    }

    describe("ClinicalHourCourseGroup") {
        val xml = """
        <clinical_hour_course_group>
            <course_group_id>846</course_group_id>
            <name>English</name>
            <clinical_hours>0</clinical_hours>
            <required_clinical_hours>10.00</required_clinical_hours>
        </clinical_hour_course_group>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(chcg, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(chcg, xml) }
    }

    describe("AttendanceHourCourseGroup") {
        val xml = """
        <attendance_hour_course_group>
            <course_group_id>846</course_group_id>
            <name>English</name>
            <attendance_hours>0</attendance_hours>
            <required_attendance_hours>10.00</required_attendance_hours>
        </attendance_hour_course_group>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(ahcg, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(ahcg, xml) }
    }

    describe("TranscriptSpecialization") {
        val xml = """ 
        <specialization>
            <specialization_id>513</specialization_id>
            <name>English</name>
            <type>MAJOR</type>
            <status>ACTIVE</status>
            <granted_date></granted_date>
            <clinical_hour_course_groups>
                <clinical_hour_course_group>
                    <course_group_id>846</course_group_id>
                    <name>English</name>
                    <clinical_hours>0</clinical_hours>
                    <required_clinical_hours>10.00</required_clinical_hours>
                </clinical_hour_course_group>
            </clinical_hour_course_groups>
            <attendance_hour_course_groups>
                <attendance_hour_course_group>
                    <course_group_id>846</course_group_id>
                    <name>English</name>
                    <attendance_hours>0</attendance_hours>
                    <required_attendance_hours>10.00</required_attendance_hours>
                </attendance_hour_course_group>
            </attendance_hour_course_groups>
        </specialization>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(specialization, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(specialization, xml) }
    }

    describe("TranscriptDegree") {
        val xml = """
        <degree>
            <degree_id>1</degree_id>
            <degree_student_id>10228</degree_student_id>
            <abbrv>B.A.</abbrv>
            <name>Bachelor of Arts</name>
            <status>ACTIVE</status>
            <active_date>2000-02-07</active_date>
            <inactive_date></inactive_date>
            <graduation_date></graduation_date>
            <graduate_degree>0</graduate_degree>
            <catalog_year_id>48</catalog_year_id>
            <catalog_start_year>2010</catalog_start_year>
            <catalog_end_year>2011</catalog_end_year>
            <honors>
                <honor>
                    <honor_id>19748</honor_id>
                    <name>Cum Laude</name>
                </honor>
            </honors>
            <clinical_hour_course_groups>
                <clinical_hour_course_group>
                    <course_group_id>846</course_group_id>
                    <name>English</name>
                    <clinical_hours>0</clinical_hours>
                    <required_clinical_hours>10.00</required_clinical_hours>
                </clinical_hour_course_group>
            </clinical_hour_course_groups>
            <attendance_hour_course_groups>
                <attendance_hour_course_group>
                    <course_group_id>846</course_group_id>
                    <name>English</name>
                    <attendance_hours>0</attendance_hours>
                    <required_attendance_hours>10.00</required_attendance_hours>
                </attendance_hour_course_group>
            </attendance_hour_course_groups>
            <specializations>
                <specialization>
                    <specialization_id>513</specialization_id>
                    <name>English</name>
                    <type>MAJOR</type>
                    <status>ACTIVE</status>
                    <granted_date></granted_date>
                    <clinical_hour_course_groups>
                        <clinical_hour_course_group>
                            <course_group_id>846</course_group_id>
                            <name>English</name>
                            <clinical_hours>0</clinical_hours>
                            <required_clinical_hours>10.00</required_clinical_hours>
                        </clinical_hour_course_group>
                    </clinical_hour_course_groups>
                    <attendance_hour_course_groups>
                        <attendance_hour_course_group>
                            <course_group_id>846</course_group_id>
                            <name>English</name>
                            <attendance_hours>0</attendance_hours>
                            <required_attendance_hours>10.00</required_attendance_hours>
                        </attendance_hour_course_group>
                    </attendance_hour_course_groups>
                </specialization>
            </specializations>
        </degree>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transcriptDegree, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcriptDegree, xml) }
    }

    describe("TransferCreditCourse") {
        val xml = """
        <course>
            <abbrv>ENG101</abbrv>
            <name>English 101</name>
            <attempted_units>3.00</attempted_units>
            <earned_units>3.00</earned_units>
            <earned_standing_units>3.00</earned_standing_units>
            <quality_points>4.00</quality_points>
            <grade_abbrv>A</grade_abbrv>
            <letter_grade>A</letter_grade>
            <attendance_hours>20.50</attendance_hours>
            <clinical_hours>18.25</clinical_hours>
        </course>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transferCreditCourse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transferCreditCourse, xml) }
    }

    describe("TransferCreditTotal") {
        val xml = """
        <totals>
            <gpa>4.00</gpa>
            <quality_points>48.00</quality_points>
            <gpa_units>12.00</gpa_units>
            <attempted_units>12.00</attempted_units>
            <earned_units>12.00</earned_units>
            <earned_standing_units>12.00</earned_standing_units>
            <attendance_hours>42.50</attendance_hours>
            <clinical_hours>30.25</clinical_hours>
        </totals>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transferCreditTotal, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transferCreditTotal, xml) }
    }

    describe("TransferCreditInstitution") {
        val xml = """
        <institution>
            <courses>
                <course>
                    <abbrv>ENG101</abbrv>
                    <name>English 101</name>
                    <attempted_units>3.00</attempted_units>
                    <earned_units>3.00</earned_units>
                    <earned_standing_units>3.00</earned_standing_units>
                    <quality_points>4.00</quality_points>
                    <grade_abbrv>A</grade_abbrv>
                    <letter_grade>A</letter_grade>
                    <attendance_hours>20.50</attendance_hours>
                    <clinical_hours>18.25</clinical_hours>
                </course>
            </courses>
            <totals>
                <gpa>4.00</gpa>
                <quality_points>48.00</quality_points>
                <gpa_units>12.00</gpa_units>
                <attempted_units>12.00</attempted_units>
                <earned_units>12.00</earned_units>
                <earned_standing_units>12.00</earned_standing_units>
                <attendance_hours>42.50</attendance_hours>
                <clinical_hours>30.25</clinical_hours>
            </totals>
        </institution>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transferCreditInstitution, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transferCreditInstitution, xml) }
    }

    describe("TranscriptTermCourse") {
        val xml = """
        <course>
            <instance_id>84632</instance_id>
            <course_id>98435</course_id>
            <abbrv>ENG102</abbrv>
            <name>English 102</name>
            <start_date>2013-01-15</start_date>
            <end_date>2013-04-15</end_date>
            <attempted_units>3.00</attempted_units>
            <earned_units>3.00</earned_units>
            <attempted_contra-units>3.00</attempted_contra-units>
            <earned_contra-units>3.00</earned_contra-units>
            <earned_standing_units>3.00</earned_standing_units>
            <status>ENROLLED</status>
            <grade_abbrv>A</grade_abbrv>
            <letter_grade>A</letter_grade>
            <numeric_grade>100.00</numeric_grade>
            <quality_points>12.00</quality_points>
            <gpa_units>3.00</gpa_units>
            <course_evaluation_grades_locked>0</course_evaluation_grades_locked>
            <teacher_person_id>468721</teacher_person_id>
            <teacher_first_name>Bob</teacher_first_name>
            <teacher_last_name>Teacher</teacher_last_name>
            <teacher_preferred_name></teacher_preferred_name>
            <teacher_display_name>Dr. Bob Teacher</teacher_display_name>
            <attempted_attendance_hours>20.50</attempted_attendance_hours>
            <earned_attendance_hours>20.50</earned_attendance_hours>
            <attempted_clinical_hours>18.25</attempted_clinical_hours>
            <earned_clinical_hours>18.25</earned_clinical_hours>
        </course>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transcriptTermCourse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcriptTermCourse, xml) }
    }

    describe("TranscriptTermTotal") {
        val xml = """
        <totals>
            <gpa>4.00</gpa>
            <gpa_including_transfer>4.00</gpa_including_transfer>
            <quality_points>48.00</quality_points>
            <gpa_units>12.00</gpa_units>
            <attempted_units>12.00</attempted_units>
            <attempted_contra-units>12.00</attempted_contra-units>
            <earned_units>12.00</earned_units>
            <earned_contra-units>12.00</earned_contra-units>
            <earned_standing_units>12.00</earned_standing_units>
            <cumulative_gpa>4.00</cumulative_gpa>
            <cumulative_gpa_including_transfer>4.00</cumulative_gpa_including_transfer>
            <cum_quality_points>48.00</cum_quality_points>
            <cum_gpa_units>48.00</cum_gpa_units>
            <cum_attempted_units>12.00</cum_attempted_units>
            <cum_attempted_contra-units>12.00</cum_attempted_contra-units>
            <cum_earned_units>12.00</cum_earned_units>
            <cum_earned_contra-units>12.00</cum_earned_contra-units>
            <attempted_attendance_hours>20.50</attempted_attendance_hours>
            <earned_attendance_hours>20.50</earned_attendance_hours>
            <cum_attempted_attendance_hours>55.50</cum_attempted_attendance_hours>
            <cum_earned_attendance_hours>55.50</cum_earned_attendance_hours>
            <attempted_clinical_hours>18.25</attempted_clinical_hours>
            <earned_clinical_hours>18.25</earned_clinical_hours>
            <cum_attempted_clinical_hours>53.25</cum_attempted_clinical_hours>
            <cum_earned_clinical_hours>53.25</cum_earned_clinical_hours>
        </totals>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transcriptTermTotal, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcriptTermTotal, xml) }
    }

    describe("TranscriptTerm") {
        val xml = """
        <term>
            <term_id>1354</term_id>
            <name>Spring Term 2012-2013</name>
            <start_date>2013-01-15</start_date>
            <end_date>2013-04-15</end_date>
            <honors>
                <honor>
                    <honor_id>19748</honor_id>
                    <name>Cum Laude</name>
                </honor>
            </honors>
            <discipline>
                <name>Academic Suspension</name>
            </discipline>
            <courses>
                <course>
                    <instance_id>84632</instance_id>
                    <course_id>98435</course_id>
                    <abbrv>ENG102</abbrv>
                    <name>English 102</name>
                    <start_date>2013-01-15</start_date>
                    <end_date>2013-04-15</end_date>
                    <attempted_units>3.00</attempted_units>
                    <earned_units>3.00</earned_units>
                    <attempted_contra-units>3.00</attempted_contra-units>
                    <earned_contra-units>3.00</earned_contra-units>
                    <earned_standing_units>3.00</earned_standing_units>
                    <status>ENROLLED</status>
                    <grade_abbrv>A</grade_abbrv>
                    <letter_grade>A</letter_grade>
                    <numeric_grade>100.00</numeric_grade>
                    <quality_points>12.00</quality_points>
                    <gpa_units>3.00</gpa_units>
                    <course_evaluation_grades_locked>0</course_evaluation_grades_locked>
                    <teacher_person_id>468721</teacher_person_id>
                    <teacher_first_name>Bob</teacher_first_name>
                    <teacher_last_name>Teacher</teacher_last_name>
                    <teacher_preferred_name></teacher_preferred_name>
                    <teacher_display_name>Dr. Bob Teacher</teacher_display_name>
                    <attempted_attendance_hours>20.50</attempted_attendance_hours>
                    <earned_attendance_hours>20.50</earned_attendance_hours>
                    <attempted_clinical_hours>18.25</attempted_clinical_hours>
                    <earned_clinical_hours>18.25</earned_clinical_hours>
                </course>
            </courses>
            <totals>
                <gpa>4.00</gpa>
                <gpa_including_transfer>4.00</gpa_including_transfer>
                <quality_points>48.00</quality_points>
                <gpa_units>12.00</gpa_units>
                <attempted_units>12.00</attempted_units>
                <attempted_contra-units>12.00</attempted_contra-units>
                <earned_units>12.00</earned_units>
                <earned_contra-units>12.00</earned_contra-units>
                <earned_standing_units>12.00</earned_standing_units>
                <cumulative_gpa>4.00</cumulative_gpa>
                <cumulative_gpa_including_transfer>4.00</cumulative_gpa_including_transfer>
                <cum_quality_points>48.00</cum_quality_points>
                <cum_gpa_units>48.00</cum_gpa_units>
                <cum_attempted_units>12.00</cum_attempted_units>
                <cum_attempted_contra-units>12.00</cum_attempted_contra-units>
                <cum_earned_units>12.00</cum_earned_units>
                <cum_earned_contra-units>12.00</cum_earned_contra-units>
                <attempted_attendance_hours>20.50</attempted_attendance_hours>
                <earned_attendance_hours>20.50</earned_attendance_hours>
                <cum_attempted_attendance_hours>55.50</cum_attempted_attendance_hours>
                <cum_earned_attendance_hours>55.50</cum_earned_attendance_hours>
                <attempted_clinical_hours>18.25</attempted_clinical_hours>
                <earned_clinical_hours>18.25</earned_clinical_hours>
                <cum_attempted_clinical_hours>53.25</cum_attempted_clinical_hours>
                <cum_earned_clinical_hours>53.25</cum_earned_clinical_hours>
            </totals>
        </term>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transcriptTerm, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcriptTerm, xml) }
    }

    describe("TranscriptProgramTotal") {
        val xml = """
        <totals>
            <attempted_units>24.00</attempted_units>
            <attempted_contra-units>24.00</attempted_contra-units>
            <earned_units>24.00</earned_units>
            <earned_contra-units>24.00</earned_contra-units>
            <earned_standing_units>24.00</earned_standing_units>
            <transfer_attempted_units>12.00</transfer_attempted_units>
            <transfer_attempted_contra-units>12.00</transfer_attempted_contra-units>
            <transfer_earned_units>12.00</transfer_earned_units>
            <transfer_earned_contra-units>12.00</transfer_earned_contra-units>
            <transfer_earned_standing_units>12.00</transfer_earned_standing_units>
            <cumulative_gpa>4.00</cumulative_gpa>
            <quality_points>192.00</quality_points>
            <gpa_units>48.00</gpa_units>
            <transfer_gpa>4.00</transfer_gpa>
            <transfer_quality_points>16.00</transfer_quality_points>
            <transfer_gpa_units>12.00</transfer_gpa_units>
            <attempted_attendance_hours>90.75</attempted_attendance_hours>
            <earned_attendance_hours>90.75</earned_attendance_hours>
            <transfer_attendance_hours>38.00</transfer_attendance_hours>
            <attempted_clinical_hours>95.00</attempted_clinical_hours>
            <earned_clinical_hours>95.00</earned_clinical_hours>
            <transfer_clinical_hours>28.20</transfer_clinical_hours>
        </totals>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(transcriptProgramTotal, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcriptProgramTotal, xml) }
    }

    describe("Transcript") {

        it("marshal to xml") { JAXB.marshal(transcript, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(transcript, getTranscriptXml) }
    }
})

const val getTranscriptXml = """
<response>
    <student>
        <first_name>Joe</first_name>
        <last_name>Bob</last_name>
        <middle_name></middle_name>
        <preferred_name></preferred_name>
        <birth_date>Jan. 5 1985</birth_date>
        <student_id>85494038</student_id>
        <enrolled_date></enrolled_date>
        <address>
            <street>P.O. Box 1994</street>
            <city>Moscow</city>
            <state>AR</state>
            <zip>88887</zip>
        </address>
    </student>
    <honors>
        <honor>
            <honor_id>19748</honor_id>
            <name>Cum Laude</name>
        </honor>
    </honors>
    <programs>
        <program>
            <honors>
                <honor>
                    <honor_id>19748</honor_id>
                    <name>Cum Laude</name>
                </honor>
            </honors>
            <degrees>
                <degree>
                    <degree_id>1</degree_id>
                    <degree_student_id>10228</degree_student_id>
                    <abbrv>B.A.</abbrv>
                    <name>Bachelor of Arts</name>
                    <status>ACTIVE</status>
                    <active_date>2000-02-07</active_date>
                    <inactive_date></inactive_date>
                    <graduation_date></graduation_date>
                    <graduate_degree>0</graduate_degree>
                    <catalog_year_id>48</catalog_year_id>
                    <catalog_start_year>2010</catalog_start_year>
                    <catalog_end_year>2011</catalog_end_year>
                    <honors>
                        <honor>
                            <honor_id>19748</honor_id>
                            <name>Cum Laude</name>
                        </honor>
                    </honors>
                    <clinical_hour_course_groups>
                        <clinical_hour_course_group>
                            <course_group_id>846</course_group_id>
                            <name>English</name>
                            <clinical_hours>0</clinical_hours>
                            <required_clinical_hours>10.00</required_clinical_hours>
                        </clinical_hour_course_group>
                    </clinical_hour_course_groups>
                    <attendance_hour_course_groups>
                        <attendance_hour_course_group>
                            <course_group_id>846</course_group_id>
                            <name>English</name>
                            <attendance_hours>0</attendance_hours>
                            <required_attendance_hours>10.00</required_attendance_hours>
                        </attendance_hour_course_group>
                    </attendance_hour_course_groups>
                    <specializations>
                        <specialization>
                            <specialization_id>513</specialization_id>
                            <name>English</name>
                            <type>MAJOR</type>
                            <status>ACTIVE</status>
                            <granted_date></granted_date>
                            <clinical_hour_course_groups>
                                <clinical_hour_course_group>
                                    <course_group_id>846</course_group_id>
                                    <name>English</name>
                                    <clinical_hours>0</clinical_hours>
                                    <required_clinical_hours>10.00</required_clinical_hours>
                                </clinical_hour_course_group>
                            </clinical_hour_course_groups>
                            <attendance_hour_course_groups>
                                <attendance_hour_course_group>
                                    <course_group_id>846</course_group_id>
                                    <name>English</name>
                                    <attendance_hours>0</attendance_hours>
                                    <required_attendance_hours>10.00</required_attendance_hours>
                                </attendance_hour_course_group>
                            </attendance_hour_course_groups>
                        </specialization>
                    </specializations>
                </degree>
            </degrees>
            <transfer_credits>
                <institution>
                    <courses>
                        <course>
                            <abbrv>ENG101</abbrv>
                            <name>English 101</name>
                            <attempted_units>3.00</attempted_units>
                            <earned_units>3.00</earned_units>
                            <earned_standing_units>3.00</earned_standing_units>
                            <quality_points>4.00</quality_points>
                            <grade_abbrv>A</grade_abbrv>
                            <letter_grade>A</letter_grade>
                            <attendance_hours>20.50</attendance_hours>
                            <clinical_hours>18.25</clinical_hours>
                        </course>
                    </courses>
                    <totals>
                        <gpa>4.00</gpa>
                        <quality_points>48.00</quality_points>
                        <gpa_units>12.00</gpa_units>
                        <attempted_units>12.00</attempted_units>
                        <earned_units>12.00</earned_units>
                        <earned_standing_units>12.00</earned_standing_units>
                        <attendance_hours>42.50</attendance_hours>
                        <clinical_hours>30.25</clinical_hours>
                    </totals>
                </institution>
            </transfer_credits>
            <term_courses>
                <term>
                    <term_id>1354</term_id>
                    <name>Spring Term 2012-2013</name>
                    <start_date>2013-01-15</start_date>
                    <end_date>2013-04-15</end_date>
                    <honors>
                        <honor>
                            <honor_id>19748</honor_id>
                            <name>Cum Laude</name>
                        </honor>
                    </honors>
                    <discipline>
                        <name>Academic Suspension</name>
                    </discipline>
                    <courses>
                        <course>
                            <instance_id>84632</instance_id>
                            <course_id>98435</course_id>
                            <abbrv>ENG102</abbrv>
                            <name>English 102</name>
                            <start_date>2013-01-15</start_date>
                            <end_date>2013-04-15</end_date>
                            <attempted_units>3.00</attempted_units>
                            <earned_units>3.00</earned_units>
                            <attempted_contra-units>3.00</attempted_contra-units>
                            <earned_contra-units>3.00</earned_contra-units>
                            <earned_standing_units>3.00</earned_standing_units>
                            <status>ENROLLED</status>
                            <grade_abbrv>A</grade_abbrv>
                            <letter_grade>A</letter_grade>
                            <numeric_grade>100.00</numeric_grade>
                            <quality_points>12.00</quality_points>
                            <gpa_units>3.00</gpa_units>
                            <course_evaluation_grades_locked>0</course_evaluation_grades_locked>
                            <teacher_person_id>468721</teacher_person_id>
                            <teacher_first_name>Bob</teacher_first_name>
                            <teacher_last_name>Teacher</teacher_last_name>
                            <teacher_preferred_name></teacher_preferred_name>
                            <teacher_display_name>Dr. Bob Teacher</teacher_display_name>
                            <attempted_attendance_hours>20.50</attempted_attendance_hours>
                            <earned_attendance_hours>20.50</earned_attendance_hours>
                            <attempted_clinical_hours>18.25</attempted_clinical_hours>
                            <earned_clinical_hours>18.25</earned_clinical_hours>
                        </course>
                    </courses>
                    <totals>
                        <gpa>4.00</gpa>
                        <gpa_including_transfer>4.00</gpa_including_transfer>
                        <quality_points>48.00</quality_points>
                        <gpa_units>12.00</gpa_units>
                        <attempted_units>12.00</attempted_units>
                        <attempted_contra-units>12.00</attempted_contra-units>
                        <earned_units>12.00</earned_units>
                        <earned_contra-units>12.00</earned_contra-units>
                        <earned_standing_units>12.00</earned_standing_units>
                        <cumulative_gpa>4.00</cumulative_gpa>
                        <cumulative_gpa_including_transfer>4.00</cumulative_gpa_including_transfer>
                        <cum_quality_points>48.00</cum_quality_points>
                        <cum_gpa_units>48.00</cum_gpa_units>
                        <cum_attempted_units>12.00</cum_attempted_units>
                        <cum_attempted_contra-units>12.00</cum_attempted_contra-units>
                        <cum_earned_units>12.00</cum_earned_units>
                        <cum_earned_contra-units>12.00</cum_earned_contra-units>
                        <attempted_attendance_hours>20.50</attempted_attendance_hours>
                        <earned_attendance_hours>20.50</earned_attendance_hours>
                        <cum_attempted_attendance_hours>55.50</cum_attempted_attendance_hours>
                        <cum_earned_attendance_hours>55.50</cum_earned_attendance_hours>
                        <attempted_clinical_hours>18.25</attempted_clinical_hours>
                        <earned_clinical_hours>18.25</earned_clinical_hours>
                        <cum_attempted_clinical_hours>53.25</cum_attempted_clinical_hours>
                        <cum_earned_clinical_hours>53.25</cum_earned_clinical_hours>
                    </totals>
                </term>
            </term_courses>
            <totals>
                <attempted_units>24.00</attempted_units>
                <attempted_contra-units>24.00</attempted_contra-units>
                <earned_units>24.00</earned_units>
                <earned_contra-units>24.00</earned_contra-units>
                <earned_standing_units>24.00</earned_standing_units>
                <transfer_attempted_units>12.00</transfer_attempted_units>
                <transfer_attempted_contra-units>12.00</transfer_attempted_contra-units>
                <transfer_earned_units>12.00</transfer_earned_units>
                <transfer_earned_contra-units>12.00</transfer_earned_contra-units>
                <transfer_earned_standing_units>12.00</transfer_earned_standing_units>
                <cumulative_gpa>4.00</cumulative_gpa>
                <quality_points>192.00</quality_points>
                <gpa_units>48.00</gpa_units>
                <transfer_gpa>4.00</transfer_gpa>
                <transfer_quality_points>16.00</transfer_quality_points>
                <transfer_gpa_units>12.00</transfer_gpa_units>
                <attempted_attendance_hours>90.75</attempted_attendance_hours>
                <earned_attendance_hours>90.75</earned_attendance_hours>
                <transfer_attendance_hours>38.00</transfer_attendance_hours>
                <attempted_clinical_hours>95.00</attempted_clinical_hours>
                <earned_clinical_hours>95.00</earned_clinical_hours>
                <transfer_clinical_hours>28.20</transfer_clinical_hours>
            </totals>
        </program>
    </programs>
    <course_descriptions>
        <course_description>
            <instance_id>84632</instance_id>
            <name>English 102</name>
            <description>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</description>
        </course_description>
    </course_descriptions>
</response>
"""
