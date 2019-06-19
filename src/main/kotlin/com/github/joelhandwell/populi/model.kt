package com.github.joelhandwell.populi

import org.javamoney.moneta.Money
import java.text.NumberFormat
import java.time.*
import java.time.format.DateTimeFormatter
import javax.money.MonetaryAmount
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlAdapter

@XmlRootElement(name = "account_id")
data class AccountId(var id: Int)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class AccessKeyResponse(
    var access_key: String, var account_id: AccountId, var account_type: String
)

@XmlRootElement(name = "specialization")
@XmlAccessorType(XmlAccessType.FIELD)
data class Specialization(
    var id: Int,
    var type: String? = null,
    var name: String,
    var description: String? = null,

    @XmlElements(XmlElement(name = "abbrv"), XmlElement(name = "abbreviation"))
    var abbrv: String,
    var status: String? = null,
    var cip_code: String? = null
)

@XmlRootElement(name = "degree")
@XmlAccessorType(XmlAccessType.FIELD)
data class Degree(
    var id: Int,
    var name: String,

    @XmlElements(XmlElement(name = "abbreviation"), XmlElement(name = "abbrv"))
    var abbrv: String,

    var program_id: Int? = null,
    var program_name: String? = null,
    var department_id: Int? = null,
    var department_name: String? = null,
    var status: String? = null,
    var graduate: Int? = null,
    var length: Int? = null,
    var length_unit: String? = null,

    @XmlElementWrapper(name = "specializations") @XmlElement var specialization: MutableList<Specialization> = mutableListOf()
)

@XmlRootElement(name = "response")
data class DegreeResponse(
    var degree: MutableList<Degree> = mutableListOf()
)

@XmlRootElement(name = "person")
data class User(
    var person_id: Int, var first: String, var last: String, var username: String, var blocked: Int
)

@XmlRootElement(name = "response")
data class UserResponse(
    var person: MutableList<User> = mutableListOf()
)

@XmlRootElement(name = "campus")
@XmlAccessorType(XmlAccessType.FIELD)
data class Campus(
    var id: Int,
    var name: String,
    var status: String,
    var city: String,
    var state: String,
    var zip: String,
    var country: String,

    @XmlElement(name = "is_primary") var is_primary: Int
)

@XmlRootElement(name = "response")
data class CampusResponse(
    var campus: MutableList<Campus> = mutableListOf()
)

@XmlRootElement(name = "program")
data class Program(
    var id: Int,
    var name: String,
    var units: String? = null,
    var graduate_level: Int? = null,
    var status: String? = null,
    var default: Int? = null
)

@XmlRootElement(name = "response")
data class ProgramResponse(
    var program: MutableList<Program> = mutableListOf()
)

@XmlRootElement(name = "academic_year")
data class AcademicYear(
    var yearid: Int, var start_year: Int, var end_year: Int
)

@XmlRootElement(name = "response")
data class AcademicYearResponse(
    var academic_year: MutableList<AcademicYear> = mutableListOf()
)

@XmlRootElement(name = "response")
data class AcademicTerm(
    var termid: Int,
    var name: String,
    var fullname: String,
    var start_date: LocalDate,
    var end_date: LocalDate,
    var type: String,
    var yearid: Int,
    var start_year: Year,
    var end_year: Year,
    var nonstandard: Int
)

@XmlRootElement(name = "response")
data class AcademicTermResponse(
    var academic_term: MutableList<AcademicTerm> = mutableListOf()
)

@XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.FIELD)
data class Course(

    @XmlElements(XmlElement(name = "id"), XmlElement(name = "courseid"))
    var courseid: Int,
    var name: String,

    @XmlElements(XmlElement(name = "abbrv"), XmlElement(name = "abbreviation"))
    var abbrv: String,

    var description: String? = null,
    var credits: Double,
    var hours: Double,
    var status: String? = null,
    var department_id: Int,
    var department_name: String,

    @XmlElementWrapper(name = "programs") var program: MutableList<Program> = mutableListOf()
)

@XmlRootElement(name = "response")
data class CourseResponse(
    var course: MutableList<Course> = mutableListOf()
)

@XmlRootElement(name = "course_group")
data class CourseGroup(
    var id: Int, var abbreviation: String, var name: String
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseGroupResponse(
    @XmlElementWrapper(name = "course_groups") var course_group: MutableList<CourseGroup> = mutableListOf()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseGroupInfoResponse(

    @XmlElementWrapper(name = "catalog_courses")
    var catalog_course: MutableList<Course> = mutableListOf(),

    @XmlElementWrapper(name = "degrees_requiring_course_group")
    var degree: MutableList<Degree> = mutableListOf(),

    @XmlElementWrapper(name = "specializations_requiring_course_group")
    var specialization: MutableList<Specialization> = mutableListOf()
)

@XmlRootElement(name = "meeting_time")
@XmlAccessorType(XmlAccessType.FIELD)
data class MeetingTime(
    var start_time: LocalTime,
    var end_time: LocalTime,
    var room: String,
    var building: String,

    @XmlElementWrapper(name = "weekdays")
    var day: MutableList<DayOfWeek>
)

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
data class Faculty(
    var primary: Int,
    var personid: Int,
    var first: String,
    var last: String,
    var preferred: String,
    var displayname: String,
    var is_teaching_assistant: Int
)

@XmlRootElement(name = "supply")
data class Supply(
    var id: Int,
    var name: String,
    var required: Int
)

@XmlRootElement(name = "author")
data class Author(
    var id: Int,
    var name: String
)

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
data class Book(
    var id: Int,
    var title: String,
    var required: Int,
    var isbn: String,
    var ean: String,
    var publisher: String,
    var publish_date: String,
    var edition: String,
    var binding: String,
    var description: String,
    var amazon_url: String,
    var image_url_large: String,
    var image_height_large: Int,
    var image_width_large: Int,
    var image_url_medium: String,
    var image_height_medium: Int,
    var image_width_medium: Int,
    var image_url_small: String,
    var image_height_small: Int,
    var image_width_small: Int,

    @XmlElementWrapper(name = "authors")
    var author: MutableList<Author> = mutableListOf()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseInstance(
    var instanceid: Int, //                                          appears in result of both tasks
    var name: String, //                                             appears in result of both tasks
    var abbrv: String, //                                            appears in result of both tasks
    var section: Int, //                                             appears in result of both tasks
    var credits: Double, //                                          appears in result of both tasks
    var hours: Double, //                                            appears in result of both tasks
    var description: String, //                                      appears in result of both tasks
    var start_date: LocalDate, //                                    appears in result of both tasks
    var end_date: LocalDate, //                                      appears in result of both tasks
    var open_to_students_date: LocalDate, //                         appears in result of both tasks
    var closed_to_students_date: LocalDate, //                       appears in result of both tasks
    var max_enrolled: Int, //                                        appears in result of both tasks
    var max_auditors: Int, //                                        appears in result of both tasks
    var published: Int, //                                           appears in result of both tasks

    var courseid: Int? = null, //                                    appears in result of getTermCourseInstance(term_id: Int)
    var primary_faculty_id: Int? = null, //                          appears in result of getTermCourseInstance(term_id: Int)
    var primary_faculty_name: String? = null, //                     appears in result of getTermCourseInstance(term_id: Int)
    var department_id: Int? = null, //                               appears in result of getTermCourseInstance(term_id: Int)
    var department_name: String? = null, //                          appears in result of getTermCourseInstance(term_id: Int)
    var delivery_method_id: Int? = null, //                          appears in result of getTermCourseInstance(term_id: Int)
    var delivery_method_name: String? = null, //                     appears in result of getTermCourseInstance(term_id: Int)
    var campus_id: Int? = null, //                                   appears in result of getTermCourseInstance(term_id: Int)
    var campus_name: String? = null, //                              appears in result of getTermCourseInstance(term_id: Int)

    @XmlElementWrapper(name = "programs")
    var program: MutableList<Program> = mutableListOf(), //          appears in result of getTermCourseInstance(term_id: Int)

    var affects_earned_credits: Int? = null, //                      appears in result of getCourseInstance(instance_id: Int)
    var pass_fail: Int? = null, //                                   appears in result of getCourseInstance(instance_id: Int)
    var finalized: Int? = null, //                                   appears in result of getCourseInstance(instance_id: Int)
    var termid: Int? = null, //                                      appears in result of getCourseInstance(instance_id: Int)
    var term_name: String? = null, //                                appears in result of getCourseInstance(instance_id: Int)
    var allow_auditor_assignments: Int? = null, //                   appears in result of getCourseInstance(instance_id: Int)
    var allow_auditor_attendance: Int? = null, //                    appears in result of getCourseInstance(instance_id: Int)

    @XmlElementWrapper(name = "schedule")
    var meeting_time: MutableList<MeetingTime> = mutableListOf(), // appears in result of getCourseInstance(instance_id: Int)

    @XmlElementWrapper(name = "faculty")
    var person: MutableList<Faculty> = mutableListOf(), //           appears in result of getCourseInstance(instance_id: Int)

    @XmlElementWrapper(name = "supplies")
    var supply: MutableList<Supply> = mutableListOf(), //            appears in result of getCourseInstance(instance_id: Int)

    @XmlElementWrapper(name = "books")
    var book: MutableList<Book> = mutableListOf() //                 appears in result of getCourseInstance(instance_id: Int)
)

@XmlRootElement(name = "response")
data class TermCourseInstanceResponse(
    var course_instance: MutableList<CourseInstance> = mutableListOf()
)

@XmlRootElement(name = "assignment_group")
data class AssignmentGroup(
    var groupid: Int,
    var name: String,
    var weight_percent: Int,
    var extra_credit: Int,
    var drop_lowest: Int
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class AssignmentGroupResponse(
    var assignment_group: MutableList<AssignmentGroup> = mutableListOf()
)

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
    var discussion_id: Int? = 0,
    var visible_to_students_before_due: Int,
    var time_due: OffsetDateTime,

    var start_window: OffsetDateTime? = null,        //TEST
    var end_window: OffsetDateTime? = null,          //TEST
    var time_limit: Int? = null,                     //TEST
    var retake_policy: String? = null,               //TEST
    var retakes: Int? = null,                        //TEST
    var proctored: Int? = null,                      //TEST
    var test_submit_feedback: String? = null,        //TEST
    var test_end_feedback: String? = null,           //TEST

    var peer_grade: Int? = null,                     //PEER_REVIEW_FILE_UPLOAD
    var grade_submissions: Int? = null,              //PEER_REVIEW_FILE_UPLOAD
    var grade_reviews: Int? = null,                  //PEER_REVIEW_FILE_UPLOAD
    var anonymous_reviews: Int? = null,              //PEER_REVIEW_FILE_UPLOAD
    var review_visibility: String? = null,           //PEER_REVIEW_FILE_UPLOAD
    var allow_review_comments: String? = null,       //PEER_REVIEW_FILE_UPLOAD
    var reviews_due_time: OffsetDateTime? = null,    //PEER_REVIEW_FILE_UPLOAD
    var reviews_closed_time: OffsetDateTime? = null, //PEER_REVIEW_FILE_UPLOAD

    var student_info: MutableList<StudentInfo> = mutableListOf()
)

@XmlRootElement(name = "response")
data class AssignmentResponse(
    var assignment: MutableList<Assignment> = mutableListOf()
)

@XmlRootElement(name = "file")
data class CourseInstanceFile(
    var file_id: Int,
    var name: String,
    var content_type: String
)

@XmlRootElement(name = "response")
data class CourseInstanceFileResponse(
    var file: MutableList<CourseInstanceFile> = mutableListOf()
)

val spaceDelimitedLocalDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

class SpaceDelimitedLocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {

    @Throws(Exception::class)
    override fun marshal(value: LocalDateTime): String =
        value.format(DateTimeFormatter.ISO_DATE_TIME)

    @Throws(Exception::class)
    override fun unmarshal(s: String): LocalDateTime = if (s.contains('T')) {
        LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)
    } else {
        LocalDateTime.parse(s, spaceDelimitedLocalDateTimeFormatter)
    }

}

@XmlRootElement(name = "lesson")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseInstanceLesson(
    var lessonid: Int,
    var name: String,
    var available_at: LocalDateTime,
    @XmlElement(name = "is_available") var is_available: Int,
    var num_discussions: Int,
    var num_new_posts: Int
)

@XmlRootElement(name = "response")
data class CourseInstanceLessonResponse(
    var lesson: MutableList<CourseInstanceLesson> = mutableListOf()
)

@XmlRootElement(name = "meeting")
data class CourseInstanceMeeting(
    var meetingid: Int,
    var roomid: Int,
    var room_name: String,
    var start: OffsetDateTime,
    var end: OffsetDateTime,
    var counts_toward_attendance_hours: Int,
    var counts_toward_clinical_hours: Int
)

@XmlRootElement(name = "response")
data class CourseInstanceMeetingResponse(
    var meeting: MutableList<CourseInstanceMeeting> = mutableListOf()
)

@XmlRootElement(name = "attendee")
data class CourseInstanceMeetingAttendance(
    var personid: Int,
    var status: String
)

@XmlRootElement(name = "response")
data class CourseInstanceMeetingAttendanceResponse(
    var attendee: MutableList<CourseInstanceMeetingAttendance> = mutableListOf()
)

@XmlEnum
enum class CourseInstanceStudentStatus { ENROLLED, AUDITOR, WITHDRAWN, INCOMPLETE }

@XmlRootElement(name = "courseinstance_student")
data class CourseInstanceStudent(
    var status: CourseInstanceStudentStatus,
    var personid: Int,
    var first: String,
    var last: String,
    var preferred: String? = null,
    var start_date: LocalDate,
    var grade: Int? = null,
    var letter_grade: String? = null,
    var attendance: Int? = null
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseInstanceStudentResponse(
    val courseinstance_student: MutableList<CourseInstanceStudent> = mutableListOf()
)

@XmlRootElement(name = "attendee")
data class CourseInstanceStudentAttendance(
    var meetingid: Int,
    var start: LocalDateTime,
    var end: LocalDateTime,
    var summary: String,
    var status: String
)

@XmlRootElement(name = "response")
data class CourseInstanceStudentAttendanceResponse(
    var attendee: MutableList<CourseInstanceStudentAttendance> = mutableListOf()
)

@XmlRootElement(name = "student")
data class Student(
    var person_id: Int,
    var student_id: String,
    var first: String,
    var last: String,
    var middle_name: String? = null,
    var preferred_name: String? = null,
    var username: String? = null,
    var prefix: String? = null,
    var suffix: String? = null,
    var former_name: String? = null,
    var gender: String,
    var birth_date: LocalDate,
    var image: String,
    var program_id: Int,
    var program_name: String,
    var standing: String,
    var degree: String,
    var full_time: Int,
    var units_attempted: Double,
    var units_granted: Double,
    var term_gpa: Double,
    var cum_units_granted: Double,
    var cum_gpa: Double,
    var cum_gpa_including_transfer: Double
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class TermStudentResponse(
    @XmlAttribute(name = "num_results") var num_results: Int, var student: MutableList<Student> = mutableListOf()
)

@XmlRootElement(name = "enrollment")
data class Enrollment(
    var person_id: Int,
    var academic_term_id: Int,
    var instance_id: Int,
    var catalog_course_id: Int,
    var status: String,
    var credits: Double,
    var hours: Double,
    var academic_term_name: String,
    var course_abbrv: String,
    var course_name: String,
    var section: Int
)

@XmlRootElement(name = "response")
data class TermEnrollmentResponse(
    var enrollment: MutableList<Enrollment> = mutableListOf()
)

class MonetaryAmountAdapter : XmlAdapter<String, MonetaryAmount>() {

    @Throws(Exception::class)
    override fun marshal(value: MonetaryAmount): String =
        NumberFormat.getCurrencyInstance().format(value.number).replace("$", "")

    @Throws(Exception::class)
    override fun unmarshal(s: String): MonetaryAmount = Money.parse("USD $s")
}

@XmlRootElement(name = "tuition_schedule_bracket")
data class TuitionScheduleBracket(
    var id: Int,
    var units: String,
    var min_units: Double,
    var max_units: Double,
    var flat_amount: MonetaryAmount,
    var per_unit_amount: MonetaryAmount,
    var per_unit_threshold: MonetaryAmount,
    var in_use: Int,
    var account_id: Int,
    var account_name: String,
    var account_number: Int
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class TuitionSchedule(
    var id: Int,
    var name: String,
    var detail: String,

    @XmlElementWrapper(name = "tuition_schedule_brackets")
    var tuition_schedule_bracket: MutableList<TuitionScheduleBracket> = mutableListOf()
)

@XmlRootElement(name = "response")
data class TuitionScheduleResponse(
    var tuition_schedule: MutableList<TuitionSchedule> = mutableListOf()
)

@XmlRootElement(name = "tuition_schedule")
data class StudentTermTuitionSchedule(
    var id: Int,
    var name: String,
    var bracket_id: Int,
    var bracket_name: String
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentTermTuitionScheduleResponse(
    @XmlElementWrapper(name = "tuition_schedules")
    var tuition_schedule: MutableList<StudentTermTuitionSchedule> = mutableListOf()
)

val localTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")

class ClockLocalTimeAdapter : XmlAdapter<String, LocalTime>() {

    @Throws(Exception::class)
    override fun marshal(value: LocalTime): String = value.format(localTimeFormatter).toLowerCase()

    @Throws(Exception::class)
    override fun unmarshal(s: String): LocalTime = LocalTime.parse(s.toUpperCase(), localTimeFormatter)
}

class DayOfWeekAdapter : XmlAdapter<String, DayOfWeek>() {

    @Throws(Exception::class)
    override fun marshal(value: DayOfWeek): String = value.toString().substring(0..1)

    @Throws(Exception::class)
    override fun unmarshal(s: String): DayOfWeek = DayOfWeek.values().first { it.toString().startsWith(s) }
}
