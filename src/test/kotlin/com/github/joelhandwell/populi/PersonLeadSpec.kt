package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

val leads = mutableListOf(
    Lead(
        id = 1,
        first_name = "James",
        preferred_name = "Jimmy",
        middle_name = "Rodrick",
        last_name = "McMcintosh",
        active = 1,
        most_recent = 1,
        added_on = LocalDate.parse("2016-07-27"),
        representative_id = 2,
        representative_first_name = "George",
        representative_preferred_name = "",
        representative_middle_name = "",
        representative_last_name = "Washington",
        status = "ENROLLED",
        program_id = 1,
        program_name = "Undergraduate",
        degree_id = 1,
        degree_name = "Bachelor of Arts",
        specialization_id = 1,
        specialization_name = "Psychology",
        academic_term_id = 1,
        academic_term_name = "2016-2017: Fall",
        source_id = 1,
        source_name = "Advertisement (Billboard)",
        source_comment = "The billboard paid off",
        education_level_id = 4,
        education_level_name = "Some College",
        declined_reason_id = 1,
        declined_reason_name = "Program/Curriculum",
        declined_reason_comment = "",
        high_school_grad_date = LocalDate.parse("2012-05-01"),
        inquiry = mutableListOf(PersonLeadInquiry(id = 1)),
        application = mutableListOf(PersonLeadApplication(id = 1))
    )
)

private val response = LeadResponse(leads)

object PersonLeadSpec : Spek({

    describe("Lead") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getPersonLeadsXml) }
    }
})

const val getPersonLeadsXml = """
<response>
    <leads>
        <lead>
            <id>1</id>
            <first_name>James</first_name>
            <preferred_name>Jimmy</preferred_name>
            <middle_name>Rodrick</middle_name>
            <last_name>McMcintosh</last_name>
            <active>1</active>
            <most_recent>1</most_recent>
            <added_on>2016-07-27</added_on>
            <representative_id>2</representative_id>
            <representative_first_name>George</representative_first_name>
            <representative_preferred_name></representative_preferred_name>
            <representative_middle_name></representative_middle_name>
            <representative_last_name>Washington</representative_last_name>
            <status>ENROLLED</status>
            <program_id>1</program_id>
            <program_name>Undergraduate</program_name>
            <degree_id>1</degree_id>
            <degree_name>Bachelor of Arts</degree_name>
            <specialization_id>1</specialization_id>
            <specialization_name>Psychology</specialization_name>
            <academic_term_id>1</academic_term_id>
            <academic_term_name>2016-2017: Fall</academic_term_name>
            <source_id>1</source_id>
            <source_name>Advertisement (Billboard)</source_name>
            <source_comment>The billboard paid off</source_comment>
            <education_level_id>4</education_level_id>
            <education_level_name>Some College</education_level_name>
            <declined_reason_id>1</declined_reason_id>
            <declined_reason_name>Program/Curriculum</declined_reason_name>
            <declined_reason_comment></declined_reason_comment>
            <high_school_grad_date>2012-05-01</high_school_grad_date>
            <inquiries>
                <inquiry>
                    <id>1</id>
                </inquiry>
            </inquiries>
            <applications>
                <application>
                    <id>1</id>
                </application>
            </applications>
        </lead>
    </leads>
</response>
"""
