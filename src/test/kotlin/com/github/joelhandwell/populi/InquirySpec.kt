package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.LocalDateTime

val inquiries = mutableListOf(
    Inquiry(
        id = 13,
        person_id = 9216473,
        lead_id = 46,
        first_name = "Sally",
        middle_name = "T",
        last_name = "Smith",
        email = "sally@university.edu",
        phone = "(123) 123-1234",
        address_id = 40798,
        subject = "I am interested in your school.",
        content = "I am interested in your school. Can you give me a call?",
        program_id = 26,
        degree_id = 0,
        specialization_id = 0,
        academic_term_id = 190,
        counselor_id = 2921,
        auto_assigned_to = 0,
        status = "WAITING_ON_US",
        lead_source_id = 5,
        localization_id = 0,
        added_on = LocalDate.of(2014, 9, 25),
        added_by = 12574,
        added_at = LocalDateTime.of(2014, 9, 29, 16, 19),
        deleted_by = 0,
        deleted_at = null
    )
)

private val response = InquiryResponse(inquiries)

object InquirySpec : Spek({

    describe("Inquiry") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getInquiryXml) }
    }
})

const val getInquiryXml = """
<response>
    <inquiry>
        <id>13</id>
        <person_id>9216473</person_id>
        <lead_id>46</lead_id>
        <first_name>Sally</first_name>
        <middle_name>T</middle_name>
        <last_name>Smith</last_name>
        <email>sally@university.edu</email>
        <phone>(123) 123-1234</phone>
        <address_id>40798</address_id>
        <subject>I am interested in your school.</subject>
        <content>I am interested in your school. Can you give me a call?</content>
        <program_id>26</program_id>
        <degree_id></degree_id>
        <specialization_id></specialization_id>
        <academic_term_id>190</academic_term_id>
        <counselor_id>2921</counselor_id>
        <auto_assigned_to></auto_assigned_to>
        <status>WAITING_ON_US</status>
        <lead_source_id>5</lead_source_id>
        <localization_id></localization_id>
        <added_on>Sep 25, 2014</added_on>
        <added_by>12574</added_by>
        <added_at>Sep 29, 2014 4:19pm</added_at>
        <deleted_by></deleted_by>
        <deleted_at></deleted_at>
    </inquiry>
</response>"""
