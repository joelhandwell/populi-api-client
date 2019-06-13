package com.github.joelhandwell.populi

import java.time.LocalDate
import java.time.Year
import javax.xml.bind.annotation.*

@XmlRootElement(name = "account_id")
class AccountId(var id: Int)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
class AccessKeyResponse(
    var access_key: String,
    var account_id: AccountId,
    var account_type: String
)

@XmlRootElement(name = "specialization")
data class Specialization(
    var id: Int,
    var type: String,
    var name: String,
    var description: String,
    var abbrv: String,
    var status: String,
    var cip_code: String
)

@XmlRootElement(name = "degree")
@XmlAccessorType(XmlAccessType.FIELD)
data class Degree(
    var id: Int,
    var name: String,
    var abbrv: String,
    var program_id: Int,
    var program_name: String,
    var department_id: Int,
    var department_name: String,
    var status: String,
    var graduate: Int,
    var length: Int,
    var length_unit: String,

    @XmlElementWrapper(name = "specializations")
    @XmlElement
    var specialization: MutableList<Specialization> = mutableListOf()
)

@XmlRootElement(name = "response")
data class DegreeResponse(
    var degree: MutableList<Degree> = mutableListOf()
)

@XmlRootElement(name = "person")
data class User(
    var person_id: Int,
    var first: String,
    var last: String,
    var username: String,
    var blocked: Int
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

    @XmlElement(name = "is_primary")
    var is_primary: Int
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
    var yearid: Int,
    var start_year: Int,
    var end_year: Int
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
    var courseid: Int,
    var name: String,
    var abbrv: String,
    var description: String,
    var credits: Double,
    var hours: Double,
    var status: String,
    var department_id: Int,
    var department_name: String,

    @XmlElementWrapper(name = "programs") var program: MutableList<Program> = mutableListOf()
)

@XmlRootElement(name = "response")
data class CourseResponse(
    var course: MutableList<Course> = mutableListOf()
)
