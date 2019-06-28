package com.github.joelhandwell.populi

import java.time.LocalDate
import java.time.LocalDateTime
import javax.money.MonetaryAmount
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "heading")
data class ApplicationHeading(
    var content: String,
    var order_id: Int
)

@XmlRootElement(name = "file")
data class ApplicationFile(
    var file_id: Int,
    var name: String
)

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationText(
    var content: String,
    var order_id: Int,

    @XmlElementWrapper(name = "files")
    var file: MutableList<ApplicationFile> = mutableListOf()
)

@XmlRootElement(name = "option")
data class ApplicationFieldOption(
    var id: Int,
    var name: String
)

@XmlRootElement(name = "test_section")
data class SATTestSection(
    var id: Int,
    var name: String,
    var score: Int
)

@XmlRootElement(name = "answer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = ApplicationAnswerAdapter::class)
data class ApplicationAnswer(
    //address
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zip: String? = null,
    var country_abbreviation: String? = null,
    var country_name: String? = null,

    //ssn
    var ssn: String? = null,

    //options
    @XmlElementWrapper(name = "options")
    var option: MutableList<ApplicationFieldOption>? = null,

    //races
    var hispanic_latino: Int? = null,

    @XmlElementWrapper(name = "races")
    var race: MutableList<Race>? = null,

    //education level
    var education_level_id: Int? = null,
    var education_level_name: String? = null,

    //file
    var file_id: Int? = null,
    var file_name: String? = null,

    //sat
    var test_id: Int? = null,
    var test_name: String? = null,
    var test_date: LocalDate? = null,
    var test_score: Int? = null,

    @XmlElementWrapper(name = "test_sections")
    var test_section: MutableList<SATTestSection>? = null,

    //citizenship
    var abbreviation: String? = null,
    var name: String? = null,

    //state, province (duplicated with address)
    // var state: String? = null,
    // var country_abbreviation: String? = null,
    // var country_name: String? = null

    //high school gpa
    var option_id: Int? = null,
    var option_name: String? = null,

    //degree
    var degree_id: Int? = null,
    var degree_abbreviation: String? = null,
    var degree_name: String? = null,

    //specialization
    var specialization_id: Int? = null,
    var specialization_abbreviation: String? = null,
    var specialization_name: String? = null,

    //online reference
    var online_reference_status: String? = null,
    var online_reference_id: Int? = null,
    var online_reference_email: String? = null,
    var online_reference_message: String? = null
)

@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationField(
    var id: Int,
    var name: String,
    var description: String? = null,
    var is_required: Int,
    var data_type: String,
    var data_format: String? = null,
    var max_multiple_answers: Int? = null,
    var status: String,
    var submitted_at: LocalDateTime,
    var decision_at: LocalDateTime,
    var order_id: Int,
    var answer: ApplicationAnswer
)

@XmlRootElement(name = "sections")
data class ApplicationSections(
    var heading: ApplicationHeading,
    var text: ApplicationText,
    var field: MutableList<ApplicationField> = mutableListOf()
)

@XmlRootElement(name = "note")
data class ApplicationNote(
    var id: Int,
    var content: String,
    var public: Int,
    var added_by: Int,
    var added_at: LocalDateTime,
    var file_id: Int,
    var file_name: String
)

@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
data class Application(
    var id: Int,
    var lead_id: Int,
    var person_id: Int,
    var first_name: String,
    var preferred_name: String? = null,
    var middle_name: String? = null,
    var last_name: String,
    var gender: String,
    var email: String,
    var application_template_id: Int,
    var application_template_name: String,
    var representative_id: Int,
    var program_id: Int,
    var program_name: String,
    var degree_seeking: Int,
    var degree_id: Int,
    var degree_abbreviation: String,
    var degree_name: String,
    var specialization_id: Int,
    var specialization_abbreviation: String,
    var specialization_name: String,
    var academic_term_id: Int,
    var academic_term_name: String,
    var expected_enrollment: String,
    var full_time: Int,
    var started_on: LocalDate,
    var submitted_at: LocalDate? = null,
    var decision_on: LocalDate? = null,
    var withdrawn_on: LocalDate? = null,
    var submitted_type: String,
    var provisional: Int,
    var provisional_comment: String? = null,
    var fee_status: String,
    var fee_id: Int,
    var fee_amount: MonetaryAmount,
    var fee_payment: String,
    var sales_receipt_id: Int,
    var transaction_id: Int,
    var applicant_activity_at: LocalDateTime,
    var num_days_since_last_activity: Int,
    var staff_activity_at: LocalDateTime,
    var percent_completed: Int,
    var status: String,

    var sections: ApplicationSections? = null,     // appears only in getApplication()

    @XmlElementWrapper(name = "notes")
    var note: MutableList<ApplicationNote>? = null // appears only in getApplication()
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationResponse(

    @XmlAttribute(name = "num_results")
    var num_results: Int,

    var application: MutableList<Application> = mutableListOf()
)

@XmlRootElement(name = "response")
data class ApplicationDetailResponse(
    var application: Application
)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationFieldOptionResponse(

    @XmlElementWrapper(name = "application_field_options")
    var option: MutableList<ApplicationFieldOption> = mutableListOf()
)

@XmlRootElement(name = "component")
data class ApplicationComponent(
    var id: Int,
    var name: String,
    var description: String? = null,
    var received_date: LocalDate,
    var status: String
)

@XmlRootElement(name = "response")
data class ApplicationComponentResponse(
    var component: MutableList<ApplicationComponent> = mutableListOf()
)

@XmlRootElement(name = "program")
data class ApplicationTemplateProgram(
    var id: Int,
    var name: String
)

@XmlRootElement(name = "academic_term")
data class ApplicationTemplateTerm(
    var id: Int,
    var name: String
)

@XmlRootElement(name = "enrollment_option")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationTemplateEnrollmentOption(

    @XmlValue
    var option: String
)

@XmlRootElement(name = "application_template")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApplicationTemplate(
    var id: Int,
    var name: String,
    var show_online: Int,
    var fee_amount: MonetaryAmount? = null,
    var fee_payment: String,
    var allow_undecided: Int,

    @XmlElementWrapper(name = "programs")
    var program: MutableList<ApplicationTemplateProgram>? = null,

    @XmlElementWrapper(name = "academic_terms")
    var academic_term: MutableList<ApplicationTemplateTerm>? = null,

    @XmlElementWrapper(name = "enrollment_options")
    var enrollment_option: MutableList<ApplicationTemplateEnrollmentOption>? = null
)

@XmlRootElement(name = "response")
data class ApplicationTemplateResponse(
    var application_template: MutableList<ApplicationTemplate>
)
