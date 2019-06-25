package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.xml.bind.JAXB

private val application = Application(
    id = 54321,
    lead_id = 3214,
    person_id = 1234,
    first_name = "Carl",
    preferred_name = "",
    middle_name = "Allen",
    last_name = "Michaelson",
    gender = "MALE",
    email = "carl@email.org",
    application_template_id = 112,
    application_template_name = "Basic",
    representative_id = 2068,
    program_id = 333,
    program_name = "Undergraduate",
    degree_seeking = 1,
    degree_id = 444,
    degree_abbreviation = "B.A.",
    degree_name = "Bachelor of Arts",
    specialization_id = 5555,
    specialization_abbreviation = "ENG",
    specialization_name = "English",
    academic_term_id = 7769,
    academic_term_name = "2014-2015: Fall",
    expected_enrollment = "FULL_TIME",
    full_time = 1,
    started_on = LocalDate.parse("2014-02-07"),
    submitted_at = null,
    decision_on = null,
    withdrawn_on = null,
    submitted_type = "ONLINE",
    provisional = 0,
    provisional_comment = "",
    fee_status = "PAID",
    fee_id = 8467,
    fee_amount = usd(25.00),
    fee_payment = "BEFORE_START",
    sales_receipt_id = 12365789,
    transaction_id = 549873215,
    applicant_activity_at = LocalDateTime.of(2014, 2, 10, 15, 46, 35),
    num_days_since_last_activity = 3,
    staff_activity_at = LocalDateTime.of(2014, 2, 10, 16, 10, 45),
    percent_completed = 95,
    status = "IN_PROGRESS"
)

val applicationResponse = ApplicationResponse(
    num_results = 356,
    application = mutableListOf(application)
)

object ApplicationSpec : Spek({

    describe("Application") {

        it("marshal to xml") { JAXB.marshal(applicationResponse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(applicationResponse, getApplicationsXml) }
    }
})

const val getApplicationsXml = """
<response num_results="356">
    <application>
        <id>54321</id>
        <lead_id>3214</lead_id>
        <person_id>1234</person_id>
        <first_name>Carl</first_name>
        <preferred_name></preferred_name>
        <middle_name>Allen</middle_name>
        <last_name>Michaelson</last_name>
        <gender>MALE</gender>
        <email>carl@email.org</email>
        <application_template_id>112</application_template_id>
        <application_template_name>Basic</application_template_name>
        <representative_id>2068</representative_id>
        <program_id>333</program_id>
        <program_name>Undergraduate</program_name>
        <degree_seeking>1</degree_seeking>
        <degree_id>444</degree_id>
        <degree_abbreviation>B.A.</degree_abbreviation>
        <degree_name>Bachelor of Arts</degree_name>
        <specialization_id>5555</specialization_id>
        <specialization_abbreviation>ENG</specialization_abbreviation>
        <specialization_name>English</specialization_name>
        <academic_term_id>7769</academic_term_id>
        <academic_term_name>2014-2015: Fall</academic_term_name>
        <expected_enrollment>FULL_TIME</expected_enrollment>
        <full_time>1</full_time>
        <started_on>2014-02-07</started_on>
        <submitted_at></submitted_at>
        <decision_on></decision_on>
        <withdrawn_on></withdrawn_on>
        <submitted_type>ONLINE</submitted_type>
        <provisional>0</provisional>
        <provisional_comment></provisional_comment>
        <fee_status>PAID</fee_status>
        <fee_id>8467</fee_id>
        <fee_amount>25.00</fee_amount>
        <fee_payment>BEFORE_START</fee_payment>
        <sales_receipt_id>12365789</sales_receipt_id>
        <transaction_id>549873215</transaction_id>
        <applicant_activity_at>2014-02-10 15:46:35</applicant_activity_at>
        <num_days_since_last_activity>3</num_days_since_last_activity>
        <staff_activity_at>2014-02-10 16:10:45</staff_activity_at>
        <percent_completed>95</percent_completed>
        <status>IN_PROGRESS</status>
    </application>
</response>"""
