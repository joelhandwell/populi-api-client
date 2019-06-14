package com.github.joelhandwell.populi

import java.time.LocalDate
import java.time.Year
import javax.xml.bind.annotation.*

@XmlRootElement(name = "account_id")
class AccountId(var id: Int)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
class AccessKeyResponse(
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

@XmlRootElement(name = "academic_term")
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

@XmlRootElement(name = "course_instance")
@XmlAccessorType(XmlAccessType.FIELD)
data class CourseInstance(
    var instanceid: Int,
    var courseid: Int,
    var name: String,
    var abbrv: String,
    var section: Int,
    var primary_faculty_id: Int,
    var primary_faculty_name: String,
    var description: String,
    var department_id: Int,
    var department_name: String,
    var credits: Double,
    var hours: Double,
    var delivery_method_id: Int,
    var delivery_method_name: String,
    var campus_id: Int,
    var campus_name: String,
    var start_date: LocalDate,
    var end_date: LocalDate,
    var open_to_students_date: LocalDate,
    var closed_to_students_date: LocalDate,
    var max_enrolled: Int,
    var max_auditors: Int,
    var published: Int,

    @XmlElementWrapper(name = "programs") var program: MutableList<Program> = mutableListOf()
)

@XmlRootElement(name = "response")
data class TermCourseInstanceResponse(
    var course_instance : MutableList<CourseInstance> = mutableListOf()
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
