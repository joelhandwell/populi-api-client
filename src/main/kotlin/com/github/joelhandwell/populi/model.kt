package com.github.joelhandwell.populi

import java.time.*
import javax.money.MonetaryAmount
import javax.xml.bind.annotation.*

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

@XmlRootElement(name = "citizenship")
data class Citizenship(
    var id: Int,
    var abbr: String
)

@XmlRootElement(name = "race")
data class Race(
    var id: Int,
    var name: String
)

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
data class Address(
    var addressid: Int,
    var type: String,
    var street: String,
    var city: String,
    var state: String? = null,
    var country: String? = null,
    var zip: String,

    @XmlElement(name = "is_primary") var is_primary: Int
)

@XmlRootElement(name = "phone")
@XmlAccessorType(XmlAccessType.FIELD)
data class Phone(
    var phoneid: Int,
    var type: String,
    var number: String,

    @XmlElement(name = "is_primary") var is_primary: Int
)

@XmlRootElement(name = "email")
@XmlAccessorType(XmlAccessType.FIELD)
data class Email(
    var emailid: Int,
    var type: String,
    var address: String,

    @XmlElement(name = "is_primary") var is_primary: Int
)

@XmlRootElement(name = "tag")
data class Tag(
    var id: Int,
    var name: String,
    var system: Int
)

@XmlRootElement(name = "person")
data class Person(
    var person_id: Int,
    var first: String,
    var last: String,
    var username: String? = null,
    var blocked: Int? = null
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class PersonInfo(
    var first: String,
    var last: String,
    var middle_name: String? = null,
    var preferred_name: String? = null,
    var prefix: String? = null,
    var suffix: String? = null,
    var former_name: String? = null,
    var gender: String,
    var birth_date: LocalDate,

    // ignore this as citizenship is conflicting name
    //@XmlElement(name = "citizenship")
    //var citizenship_single: String,

    @XmlElementWrapper(name = "citizenships")
    var citizenship: MutableList<Citizenship> = mutableListOf(),

    var resident_alien: Int,
    var home_city: String,
    var home_state: String,
    var home_country: String,
    var license_plate: String,
    var is_active_user: Int,

    @XmlElementWrapper(name = "races")
    var race: MutableList<Race> = mutableListOf(),

    var hispanic_latino: String,
    var image: String,
    var address: MutableList<Address> = mutableListOf(),
    var phone: MutableList<Phone> = mutableListOf(),
    var email: MutableList<Email> = mutableListOf(),

    @XmlElementWrapper(name = "tags")
    var tag: MutableList<Tag> = mutableListOf()
)

@XmlRootElement(name = "response")
data class UserResponse(
    var person: MutableList<Person> = mutableListOf()
)

@XmlRootElement(name = "campus")
@XmlAccessorType(XmlAccessType.FIELD)
data class Campus(

    @XmlElements(XmlElement(name = "id"), XmlElement(name = "campus_id"))
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
data class AssignmentStudentInfo(
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

    var student_info: MutableList<AssignmentStudentInfo> = mutableListOf()
)

@XmlRootElement(name = "response")
data class AssignmentResponse(
    var assignment: MutableList<Assignment> = mutableListOf()
)

@XmlRootElement(name = "file")
data class AssignmentFile(
    var submission_id: Int,
    var file_id: Int,
    var name: String,
    var content_type: String,
    var added_by_id: Int,
    var added_by_name: String,
    var added_time: LocalDateTime
)

@XmlRootElement(name = "comment")
data class AssignmentComment(
    var submission_id: Int,
    var content: String,
    var added_by_id: Int,
    var added_by_name: String,
    var added_time: LocalDateTime
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentAssignmentSubmission(

    @XmlElementWrapper(name = "files")
    var file: MutableList<AssignmentFile> = mutableListOf(),

    @XmlElementWrapper(name = "comments")
    var comment: MutableList<AssignmentComment> = mutableListOf()
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

@XmlRootElement(name = "link")
data class CourseOfferingLink(
    var id: Int,
    var name: String,
    var url: String
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseOfferingLinkResponse(
    @XmlElementWrapper(name = "links")
    var link: MutableList<CourseOfferingLink> = mutableListOf()
)

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
@XmlAccessorType(XmlAccessType.FIELD)
data class TermStudent(
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
    @XmlAttribute(name = "num_results") var num_results: Int, var student: MutableList<TermStudent> = mutableListOf()
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

@XmlRootElement
data class DegreeAuditCourse(
    var catalog_course_id: Int,
    var abbreviation: String,
    var name: String,
    var grade: String? = null,
    var status: String? = null,
    var course_offering_id: Int? = null,
    var transfer_credit_id: Int? = null,
    var fulfilled_by_abbreviation: String? = null,
    var fulfilled_by_name: String? = null
)

@XmlRootElement(name = "course_group")
@XmlAccessorType(XmlAccessType.FIELD)
data class DegreeAuditCourseGroup(
    var id: Int,
    var name: String,
    var all_requirements_completed: Int,
    var requirement_type: String,
    var requirement_value: Double,
    var earned_requirements: Double,
    var transferred_requirements: Double,
    var general_requirements_completed: Int,
    var required_gpa: Double,
    var gpa: Double,
    var gpa_requirements_completed: Int,
    var waived_requirements: Double,
    var exceptions_applied: Int,

    @XmlElementWrapper(name = "completed_courses")
    var completed_course: MutableList<DegreeAuditCourse> = mutableListOf(),

    @XmlElementWrapper(name = "incomplete_courses")
    var incomplete_course: MutableList<DegreeAuditCourse> = mutableListOf()
)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentAudit(
    var all_requirements_completed: Int,
    var cumulative_gpa_required: Double,
    var cumulative_gpa: Double,
    var cumulative_gpa_requirement_completed: Int,
    var overall_gpa_required: Double,
    var overall_gpa: Double,
    var overall_gpa_requirement_completed: Int,
    var cumulative_units_required: Double,
    var cumulative_units: Double,
    var cumulative_units_requirement_completed: Int,
    var resident_units_required: Double,
    var resident_units: Double,
    var resident_units_requirement_completed: Int,
    var cumulative_clinical_hours_required: Double,
    var cumulative_clinical_hours: Double,
    var cumulative_clinical_hours_requirement_completed: Int,
    var resident_clinical_hours_required: Double,
    var resident_clinical_hours: Double,
    var resident_clinical_hours_requirement_completed: Int,
    var cumulative_attendance_hours_required: Double,
    var cumulative_attendance_hours: Double,
    var cumulative_attendance_hours_requirement_completed: Int,
    var resident_attendance_hours_required: Double,
    var resident_attendance_hours: Double,
    var resident_attendance_hours_requirement_completed: Int,
    var in_progress_units: Double,
    var in_progress_clinical_hours: Double,
    var in_progress_attendance_hours: Double,

    @XmlElementWrapper(name = "course_groups")
    var course_group: MutableList<DegreeAuditCourseGroup> = mutableListOf()
)

@XmlRootElement(name = "response")
data class DegreeAuditResponse(
    var degree: StudentAudit,
    var specialization: StudentAudit? = null
)

@XmlRootElement(name = "file")
data class StudentDisciplineFile(
    var id: Long,
    var name: String,
    var type: String,
    var size: Int,
    var added_at: LocalDateTime,
    var added_by: Int,
    var added_by_name: String
)

@XmlRootElement(name = "discipline")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentDiscipline(
    var id: Int,
    var action: String,
    var start_date: LocalDate,
    var end_date: LocalDate? = null,
    var program_id: Int? = null,
    var program_name: String? = null,
    var show_on_transcript: Int,
    var generate_tag: Int,
    var comment: String? = null,
    var added_at: LocalDateTime,
    var added_by: Int,
    var added_by_name: String,
    var updated_at: LocalDateTime,
    var updated_by: Int,
    var updated_by_name: String,

    @XmlElementWrapper(name = "files")
    var file: MutableList<StudentDisciplineFile> = mutableListOf()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentDisciplineResponse(
    var discipline: MutableList<StudentDiscipline> = mutableListOf()
)

@XmlRootElement(name = "specialization")
data class StudentSpecialization(
    var specialization_id: Int,
    var name: String,
    var type: String,
    var status: String,
    var granted_date: LocalDate? = null
)

@XmlRootElement(name = "degree")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentDegree(
    var degree_id: Int,
    var degree_student_id: Int,
    var abbrv: String,
    var name: String,
    var status: String,
    var active_date: LocalDate,
    var inactive_date: LocalDate? = null,
    var graduation_date: LocalDate? = null,
    var graduate_degree: Int,
    var catalog_year_id: Int,
    var catalog_start_year: Year,
    var catalog_end_year: Year,
    var anticipated_completion_date: LocalDate,

    @XmlElementWrapper(name = "specializations")
    var specialization: MutableList<StudentSpecialization> = mutableListOf()
)

@XmlRootElement(name = "program")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentProgram(
    var id: Int,
    var program_id: Int,
    var name: String,
    var started_on: LocalDate,
    var exit_date: LocalDate? = null,
    var exit_reason: String? = null,
    var entrance_term_id: Int,
    var entrance_term_name: String,
    var previous_education_level_id: Int,
    var previous_education_level_name: String,

    @XmlElementWrapper(name = "degrees")
    var degree: MutableList<StudentDegree> = mutableListOf()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentProgramResponse(

    @XmlElementWrapper(name = "programs")
    var program: MutableList<StudentProgram> = mutableListOf()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class StudentInfo(
    var student_id: Int,
    var first: String,
    var last: String,
    var middle_name: String? = null,
    var preferred_name: String? = null,
    var prefix: String? = null,
    var suffix: String? = null,
    var gender: String,
    var birth_date: LocalDate,
    var image: String,
    var entrance_term_id: Int,
    var entrance_term_name: String,
    var exit_date: LocalDate? = null,
    var exit_reason: String? = null,
    var leave_of_absence: Int? = null,
    var leave_of_absence_start_date: LocalDate? = null,
    var leave_of_absence_anticipated_return_date: LocalDate? = null,

    @XmlElementWrapper(name = "advisors")
    var advisor: MutableList<Person> = mutableListOf(),

    @XmlElementWrapper(name = "campuses")
    var campus: MutableList<Campus> = mutableListOf()
)
