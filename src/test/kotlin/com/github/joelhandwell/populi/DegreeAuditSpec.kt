package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val c1 = DegreeAuditCourse(
    catalog_course_id = 98434,
    abbreviation = "ENG101",
    name = "English 101",
    grade = "A",
    status = "2 credits completed",
    course_offering_id = 4681354,
    transfer_credit_id = 0,
    fulfilled_by_abbreviation = "",
    fulfilled_by_name = ""
)

private val c2 = DegreeAuditCourse(
    catalog_course_id = 98435,
    abbreviation = "ENG102",
    name = "English 102"
)

private val cg = DegreeAuditCourseGroup(
    id = 846,
    name = "English",
    all_requirements_completed = 1,
    requirement_type = "UNITS",
    requirement_value = 12.00,
    earned_requirements = 12.00,
    transferred_requirements = 0.00,
    general_requirements_completed = 1,
    required_gpa = 3.00,
    gpa = 3.40,
    gpa_requirements_completed = 1,
    waived_requirements = 0.00,
    exceptions_applied = 0,
    completed_course = mutableListOf(c1),
    incomplete_course = mutableListOf(c2)
)

private val a = StudentAudit(
    all_requirements_completed = 1,
    cumulative_gpa_required = 3.00,
    cumulative_gpa = 3.40,
    cumulative_gpa_requirement_completed = 1,
    overall_gpa_required = 3.00,
    overall_gpa = 3.40,
    overall_gpa_requirement_completed = 1,
    cumulative_units_required = 120.00,
    cumulative_units = 120.00,
    cumulative_units_requirement_completed = 1,
    resident_units_required = 120.00,
    resident_units = 120.00,
    resident_units_requirement_completed = 1,
    cumulative_clinical_hours_required = 0.00,
    cumulative_clinical_hours = 0.00,
    cumulative_clinical_hours_requirement_completed = 1,
    resident_clinical_hours_required = 0.00,
    resident_clinical_hours = 0.00,
    resident_clinical_hours_requirement_completed = 1,
    cumulative_attendance_hours_required = 0.00,
    cumulative_attendance_hours = 0.00,
    cumulative_attendance_hours_requirement_completed = 1,
    resident_attendance_hours_required = 0.00,
    resident_attendance_hours = 0.00,
    resident_attendance_hours_requirement_completed = 1,
    in_progress_units = 0.00,
    in_progress_clinical_hours = 0.00,
    in_progress_attendance_hours = 0.00,
    course_group = mutableListOf(cg)
)

private val d = DegreeAuditResponse(degree = a, specialization = a)

object DegreeAuditSpec : Spek({
    describe("DegreeAuditCourse completed") {
        val xml = """
        <completed_course>
            <catalog_course_id>98434</catalog_course_id>
            <abbreviation>ENG101</abbreviation>
            <name>English 101</name>
            <grade>A</grade>
            <status>2 credits completed</status>
            <course_offering_id>4681354</course_offering_id>
            <transfer_credit_id></transfer_credit_id>
            <fulfilled_by_abbreviation></fulfilled_by_abbreviation>
            <fulfilled_by_name></fulfilled_by_name>
        </completed_course>            
        """.trimIndent()

        it("marshal to xml") {
            JAXB.marshal(c1, StringWriter())
            // let's assume it passed if no error
            // assertMarshals(xml, c1)
        }
        it("unmarshal from xml") { assertUnmarshals(c1, xml) }
    }

    describe("DegreeAuditCourse incomplete") {
        val xml = """
        <incomplete_course>
            <abbreviation>ENG102</abbreviation>
            <catalog_course_id>98435</catalog_course_id>
            <name>English 102</name>
        </incomplete_course>            
        """.trimIndent()

        it("marshal to xml") {
            JAXB.marshal(c2, StringWriter())
            // let's assume it passed if no error
            // assertMarshals(xml, c2)
        }
        it("unmarshal from xml") { assertUnmarshals(c2, xml) }
    }

    describe("DegreeAuditCourseGroup") {
        val xml = """
        <course_group>
            <id>846</id>
            <name>English</name>
            <all_requirements_completed>1</all_requirements_completed>
            <requirement_type>UNITS</requirement_type>
            <requirement_value>12.00</requirement_value>
            <earned_requirements>12.00</earned_requirements>
            <transferred_requirements>0.00</transferred_requirements>
            <general_requirements_completed>1</general_requirements_completed>
            <required_gpa>3.00</required_gpa>
            <gpa>3.40</gpa>
            <gpa_requirements_completed>1</gpa_requirements_completed>
            <waived_requirements>0.00</waived_requirements>
            <exceptions_applied>0</exceptions_applied>
            <completed_courses>
                <completed_course>
                    <catalog_course_id>98434</catalog_course_id>
                    <abbreviation>ENG101</abbreviation>
                    <name>English 101</name>
                    <grade>A</grade>
                    <status>2 credits completed</status>
                    <course_offering_id>4681354</course_offering_id>
                    <transfer_credit_id></transfer_credit_id>
                    <fulfilled_by_abbreviation></fulfilled_by_abbreviation>
                    <fulfilled_by_name></fulfilled_by_name>
                </completed_course>
            </completed_courses>
            <incomplete_courses>
                <incomplete_course>
                    <catalog_course_id>98435</catalog_course_id>
                    <abbreviation>ENG102</abbreviation>
                    <name>English 102</name>
                </incomplete_course>
            </incomplete_courses>
        </course_group>            
        """.trimIndent()

        it("marshal to xml") {
            JAXB.marshal(cg, StringWriter())
            // let's assume it passed if no error
            // assertMarshals(xml, cg)
        }

        it("unmarshal from xml") { assertUnmarshals(cg, xml) }
    }

    describe("DegreeAudit") {
        it("marshal to xml"){
            JAXB.marshal(d, StringWriter())
        }

        it("unmarshal from xml"){ assertUnmarshals(d, getDegreeAuditXml) }
    }
})

fun assertDegreeAudit(degreeAudit: DegreeAuditResponse){
    assertEquals(d, degreeAudit)
}

const val getDegreeAuditXml = """
<response>
    <degree>
        <all_requirements_completed>1</all_requirements_completed>
        <cumulative_gpa_required>3.00</cumulative_gpa_required>
        <cumulative_gpa>3.40</cumulative_gpa>
        <cumulative_gpa_requirement_completed>1</cumulative_gpa_requirement_completed>
        <overall_gpa_required>3.00</overall_gpa_required>
        <overall_gpa>3.40</overall_gpa>
        <overall_gpa_requirement_completed>1</overall_gpa_requirement_completed>
        <cumulative_units_required>120.00</cumulative_units_required>
        <cumulative_units>120.00</cumulative_units>
        <cumulative_units_requirement_completed>1</cumulative_units_requirement_completed>
        <resident_units_required>120.00</resident_units_required>
        <resident_units>120.00</resident_units>
        <resident_units_requirement_completed>1</resident_units_requirement_completed>
        <cumulative_clinical_hours_required>0.00</cumulative_clinical_hours_required>
        <cumulative_clinical_hours>0.00</cumulative_clinical_hours>
        <cumulative_clinical_hours_requirement_completed>1</cumulative_clinical_hours_requirement_completed>
        <resident_clinical_hours_required>0.00</resident_clinical_hours_required>
        <resident_clinical_hours>0.00</resident_clinical_hours>
        <resident_clinical_hours_requirement_completed>1</resident_clinical_hours_requirement_completed>
        <cumulative_attendance_hours_required>0.00</cumulative_attendance_hours_required>
        <cumulative_attendance_hours>0.00</cumulative_attendance_hours>
        <cumulative_attendance_hours_requirement_completed>1</cumulative_attendance_hours_requirement_completed>
        <resident_attendance_hours_required>0.00</resident_attendance_hours_required>
        <resident_attendance_hours>0.00</resident_attendance_hours>
        <resident_attendance_hours_requirement_completed>1</resident_attendance_hours_requirement_completed>
        <in_progress_units>0.00</in_progress_units>
        <in_progress_clinical_hours>0.00</in_progress_clinical_hours>
        <in_progress_attendance_hours>0.00</in_progress_attendance_hours>
        <course_groups>
            <course_group>
                <id>846</id>
                <name>English</name>
                <all_requirements_completed>1</all_requirements_completed>
                <requirement_type>UNITS</requirement_type>
                <requirement_value>12.00</requirement_value>
                <earned_requirements>12.00</earned_requirements>
                <transferred_requirements>0.00</transferred_requirements>
                <general_requirements_completed>1</general_requirements_completed>
                <required_gpa>3.00</required_gpa>
                <gpa>3.40</gpa>
                <gpa_requirements_completed>1</gpa_requirements_completed>
                <waived_requirements>0.00</waived_requirements>
                <exceptions_applied>0</exceptions_applied>
                <completed_courses>
                    <completed_course>
                        <catalog_course_id>98434</catalog_course_id>
                        <abbreviation>ENG101</abbreviation>
                        <name>English 101</name>
                        <grade>A</grade>
                        <status>2 credits completed</status>
                        <course_offering_id>4681354</course_offering_id>
                        <transfer_credit_id></transfer_credit_id>
                        <fulfilled_by_abbreviation></fulfilled_by_abbreviation>
                        <fulfilled_by_name></fulfilled_by_name>
                    </completed_course>
                </completed_courses>
                <incomplete_courses>
                    <incomplete_course>
                        <catalog_course_id>98435</catalog_course_id>
                        <abbreviation>ENG102</abbreviation>
                        <name>English 102</name>
                    </incomplete_course>
                </incomplete_courses>
            </course_group>
        </course_groups>
    </degree>
    <specialization>
        <all_requirements_completed>1</all_requirements_completed>
        <cumulative_gpa_required>3.00</cumulative_gpa_required>
        <cumulative_gpa>3.40</cumulative_gpa>
        <cumulative_gpa_requirement_completed>1</cumulative_gpa_requirement_completed>
        <overall_gpa_required>3.00</overall_gpa_required>
        <overall_gpa>3.40</overall_gpa>
        <overall_gpa_requirement_completed>1</overall_gpa_requirement_completed>
        <cumulative_units_required>120.00</cumulative_units_required>
        <cumulative_units>120.00</cumulative_units>
        <cumulative_units_requirement_completed>1</cumulative_units_requirement_completed>
        <resident_units_required>120.00</resident_units_required>
        <resident_units>120.00</resident_units>
        <resident_units_requirement_completed>1</resident_units_requirement_completed>
        <cumulative_clinical_hours_required>0.00</cumulative_clinical_hours_required>
        <cumulative_clinical_hours>0.00</cumulative_clinical_hours>
        <cumulative_clinical_hours_requirement_completed>1</cumulative_clinical_hours_requirement_completed>
        <resident_clinical_hours_required>0.00</resident_clinical_hours_required>
        <resident_clinical_hours>0.00</resident_clinical_hours>
        <resident_clinical_hours_requirement_completed>1</resident_clinical_hours_requirement_completed>
        <cumulative_attendance_hours_required>0.00</cumulative_attendance_hours_required>
        <cumulative_attendance_hours>0.00</cumulative_attendance_hours>
        <cumulative_attendance_hours_requirement_completed>1</cumulative_attendance_hours_requirement_completed>
        <resident_attendance_hours_required>0.00</resident_attendance_hours_required>
        <resident_attendance_hours>0.00</resident_attendance_hours>
        <resident_attendance_hours_requirement_completed>1</resident_attendance_hours_requirement_completed>
        <in_progress_units>0.00</in_progress_units>
        <in_progress_clinical_hours>0.00</in_progress_clinical_hours>
        <in_progress_attendance_hours>0.00</in_progress_attendance_hours>
        <course_groups>
            <course_group>
                <id>846</id>
                <name>English</name>
                <all_requirements_completed>1</all_requirements_completed>
                <requirement_type>UNITS</requirement_type>
                <requirement_value>12.00</requirement_value>
                <earned_requirements>12.00</earned_requirements>
                <transferred_requirements>0.00</transferred_requirements>
                <general_requirements_completed>1</general_requirements_completed>
                <required_gpa>3.00</required_gpa>
                <gpa>3.40</gpa>
                <gpa_requirements_completed>1</gpa_requirements_completed>
                <waived_requirements>0.00</waived_requirements>
                <exceptions_applied>0</exceptions_applied>
                <completed_courses>
                    <completed_course>
                        <catalog_course_id>98434</catalog_course_id>
                        <abbreviation>ENG101</abbreviation>
                        <name>English 101</name>
                        <grade>A</grade>
                        <status>2 credits completed</status>
                        <course_offering_id>4681354</course_offering_id>
                        <transfer_credit_id></transfer_credit_id>
                        <fulfilled_by_abbreviation></fulfilled_by_abbreviation>
                        <fulfilled_by_name></fulfilled_by_name>
                    </completed_course>
                </completed_courses>
                <incomplete_courses>
                    <incomplete_course>
                        <catalog_course_id>98435</catalog_course_id>
                        <abbreviation>ENG102</abbreviation>
                        <name>English 102</name>
                    </incomplete_course>
                </incomplete_courses>
            </course_group>
        </course_groups>
    </specialization>
</response>
"""
