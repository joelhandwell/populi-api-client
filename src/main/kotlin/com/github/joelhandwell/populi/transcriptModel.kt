package com.github.joelhandwell.populi

import java.time.LocalDate
import java.time.Year
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "address")
data class TranscriptAddress(
    var street: String,
    var city: String,
    var state: String,
    var zip: String
)

@XmlRootElement(name = "student")
data class TranscriptStudent(
    var first_name: String,
    var last_name: String,
    var middle_name: String? = null,
    var preferred_name: String? = null,
    var birth_date: LocalDate,
    var student_id: Int,
    var enrolled_date: LocalDate? = null,
    var address: TranscriptAddress
)

@XmlRootElement(name = "honor")
data class TranscriptHonor(var honor_id: Int, var name: String)

@XmlRootElement(name = "clinical_hour_course_group")
data class ClinicalHourCourseGroup(
    var course_group_id: Int,
    var name: String,
    var clinical_hours: Double,
    var required_clinical_hours: Double
)

@XmlRootElement(name = "attendance_hour_course_group")
data class AttendanceHourCourseGroup(
    var course_group_id: Int,
    var name: String,
    var attendance_hours: Double,
    var required_attendance_hours: Double
)

@XmlRootElement(name = "specialization")
@XmlAccessorType(XmlAccessType.FIELD)
data class TranscriptSpecialization(
    var specialization_id: Int,
    var name: String,
    var type: String,
    var status: String,
    var granted_date: LocalDate? = null,

    @XmlElementWrapper(name = "clinical_hour_course_groups")
    var clinical_hour_course_group: MutableList<ClinicalHourCourseGroup> = mutableListOf(),

    @XmlElementWrapper(name = "attendance_hour_course_groups")
    var attendance_hour_course_group: MutableList<AttendanceHourCourseGroup> = mutableListOf()
)

@XmlRootElement(name = "degree")
@XmlAccessorType(XmlAccessType.FIELD)
data class TranscriptDegree(
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

    @XmlElementWrapper(name = "clinical_hour_course_groups")
    var clinical_hour_course_group: MutableList<ClinicalHourCourseGroup> = mutableListOf(),

    @XmlElementWrapper(name = "attendance_hour_course_groups")
    var attendance_hour_course_group: MutableList<AttendanceHourCourseGroup> = mutableListOf(),

    @XmlElementWrapper(name = "specializations")
    var specialization: MutableList<TranscriptSpecialization> = mutableListOf()
)

@XmlRootElement(name = "course")
data class TransferCreditCourse(
    var abbrv: String,
    var name: String,
    var attempted_units: Double,
    var earned_units: Double,
    var earned_standing_units: Double,
    var quality_points: Double,
    var grade_abbrv: String,
    var letter_grade: String,
    var attendance_hours: Double,
    var clinical_hours: Double
)

@XmlRootElement(name = "totals")
data class TransferCreditTotal(
    var gpa: Double,
    var quality_points: Double,
    var gpa_units: Double,
    var attempted_units: Double,
    var earned_units: Double,
    var earned_standing_units: Double,
    var attendance_hours: Double,
    var clinical_hours: Double
)

@XmlRootElement(name = "institution")
@XmlAccessorType(XmlAccessType.FIELD)
data class TransferCreditInstitution(

    @XmlElementWrapper(name = "courses")
    var course: MutableList<TransferCreditCourse> = mutableListOf(),

    var totals: TransferCreditTotal
)

@XmlRootElement(name = "discipline")
data class TranscriptTermDiscipline(var name: String)

@XmlRootElement(name = "course")
data class TranscriptTermCourse(
    var instance_id: Int,
    var course_id: Int,
    var abbrv: String,
    var name: String,
    var start_date: LocalDate,
    var end_date: LocalDate,
    var attempted_units: Double,
    var earned_units: Double,
    var `attempted_contra-units`: Double,
    var `earned_contra-units`: Double,
    var earned_standing_units: Double,
    var status: String,
    var grade_abbrv: String,
    var letter_grade: String,
    var numeric_grade: Double,
    var quality_points: Double,
    var gpa_units: Double,
    var course_evaluation_grades_locked: Int,
    var teacher_person_id: Int,
    var teacher_first_name: String,
    var teacher_last_name: String,
    var teacher_preferred_name: String? = null,
    var teacher_display_name: String,
    var attempted_attendance_hours: Double,
    var earned_attendance_hours: Double,
    var attempted_clinical_hours: Double,
    var earned_clinical_hours: Double
)

@XmlRootElement(name = "totals")
data class TranscriptTermTotal(
    var gpa: Double,
    var gpa_including_transfer: Double,
    var quality_points: Double,
    var gpa_units: Double,
    var attempted_units: Double,
    var `attempted_contra-units`: Double,
    var earned_units: Double,
    var `earned_contra-units`: Double,
    var earned_standing_units: Double,
    var cumulative_gpa: Double,
    var cumulative_gpa_including_transfer: Double,
    var cum_quality_points: Double,
    var cum_gpa_units: Double,
    var cum_attempted_units: Double,
    var `cum_attempted_contra-units`: Double,
    var cum_earned_units: Double,
    var `cum_earned_contra-units`: Double,
    var attempted_attendance_hours: Double,
    var earned_attendance_hours: Double,
    var cum_attempted_attendance_hours: Double,
    var cum_earned_attendance_hours: Double,
    var attempted_clinical_hours: Double,
    var earned_clinical_hours: Double,
    var cum_attempted_clinical_hours: Double,
    var cum_earned_clinical_hours: Double
)

@XmlRootElement(name = "term")
@XmlAccessorType(XmlAccessType.FIELD)
data class TranscriptTerm(
    var term_id: Int,
    var name: String,
    var start_date: LocalDate,
    var end_date: LocalDate,

    @XmlElementWrapper(name = "honors")
    var honor: MutableList<TranscriptHonor> = mutableListOf(),

    var discipline: TranscriptTermDiscipline,

    @XmlElementWrapper(name = "courses")
    var course: MutableList<TranscriptTermCourse> = mutableListOf(),

    var totals: TranscriptTermTotal
)

@XmlRootElement(name = "totals")
data class TranscriptProgramTotal(
    var attempted_units: Double,
    var `attempted_contra-units`: Double,
    var earned_units: Double,
    var `earned_contra-units`: Double,
    var earned_standing_units: Double,
    var transfer_attempted_units: Double,
    var `transfer_attempted_contra-units`: Double,
    var transfer_earned_units: Double,
    var `transfer_earned_contra-units`: Double,
    var transfer_earned_standing_units: Double,
    var cumulative_gpa: Double,
    var quality_points: Double,
    var gpa_units: Double,
    var transfer_gpa: Double,
    var transfer_quality_points: Double,
    var transfer_gpa_units: Double,
    var attempted_attendance_hours: Double,
    var earned_attendance_hours: Double,
    var transfer_attendance_hours: Double,
    var attempted_clinical_hours: Double,
    var earned_clinical_hours: Double,
    var transfer_clinical_hours: Double
)

@XmlRootElement(name = "program")
@XmlAccessorType(XmlAccessType.FIELD)
data class TranscriptProgram(

    @XmlElementWrapper(name = "honors")
    var honor: MutableList<TranscriptHonor> = mutableListOf(),

    @XmlElementWrapper(name = "degrees")
    var degree: MutableList<TranscriptDegree> = mutableListOf(),

    @XmlElementWrapper(name = "transfer_credits")
    var institution: MutableList<TransferCreditInstitution> = mutableListOf(),

    @XmlElementWrapper(name = "term_courses")
    var term: MutableList<TranscriptTerm> = mutableListOf(),

    var totals: TranscriptProgramTotal
)

@XmlRootElement(name = "course_description")
data class TranscriptCourseDescription(
    var instance_id: Int,
    var name: String,
    var description: String
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class Transcript(
    var student: TranscriptStudent,

    @XmlElementWrapper(name = "honors")
    var honor: MutableList<TranscriptHonor> = mutableListOf(),

    @XmlElementWrapper(name = "programs")
    var program: MutableList<TranscriptProgram> = mutableListOf(),

    @XmlElementWrapper(name = "course_descriptions")
    var course_description: MutableList<TranscriptCourseDescription> = mutableListOf()
)