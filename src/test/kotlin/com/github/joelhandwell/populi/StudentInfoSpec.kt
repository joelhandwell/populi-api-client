package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val student = StudentInfo(
    student_id = 2010000043,
    first = "James",
    last = "McMcintosh",
    middle_name = "Rodrick",
    preferred_name = "Jimmy",
    prefix = "Mr.",
    suffix = "III",
    gender = "MALE",
    birth_date = LocalDate.parse("1990-01-01"),
    image = "_BASE_64_ENCODED_BINARY_IMAGE_DATA_RETURNED_HERE_",
    entrance_term_id = 1,
    entrance_term_name = "Fall 1994-1995",
    exit_date = null,
    exit_reason = "",
    leave_of_absence = 1,
    leave_of_absence_start_date = LocalDate.parse("2019-01-07"),
    leave_of_absence_anticipated_return_date = LocalDate.parse("2019-07-06"),
    advisor = mutableListOf(Person(1654987, "Chris", "Jones"), Person(14684357, "Joe", "Bob")),
    campus = mutableListOf(
        Campus(
            id = 146873,
            name = "Main Campus",
            status = "ACTIVE",
            city = "Moscow",
            state = "AZ",
            zip = "12345",
            country = "USA",
            is_primary = 1
        )
    )
)

object StudentInfoSpec : Spek({
    describe("StudentInfo") {

        it("marshal to xml") { JAXB.marshal(student, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(student, getStudentInfoXml) }
    }
})

fun assertStudentInfo(studentInfo: StudentInfo){
    assertEquals(student, studentInfo)
}

const val getStudentInfoXml = """
<response>
    <student_id>2010000043</student_id>
    <first>James</first>
    <last>McMcintosh</last>
    <middle_name>Rodrick</middle_name>
    <preferred_name>Jimmy</preferred_name>
    <prefix>Mr.</prefix>
    <suffix>III</suffix>
    <gender>MALE</gender>
    <birth_date>1990-01-01</birth_date>
    <image>_BASE_64_ENCODED_BINARY_IMAGE_DATA_RETURNED_HERE_</image>
    <entrance_term_id>1</entrance_term_id>
    <entrance_term_name>Fall 1994-1995</entrance_term_name>
    <exit_date/>
    <exit_reason/>
    <leave_of_absence>1</leave_of_absence>
    <leave_of_absence_start_date>2019-01-07</leave_of_absence_start_date>
    <leave_of_absence_anticipated_return_date>2019-07-06</leave_of_absence_anticipated_return_date>
    <advisors>
        <advisor>
            <person_id>1654987</person_id>
            <first>Chris</first>
            <last>Jones</last>
        </advisor>
        <advisor>
            <person_id>14684357</person_id>
            <first>Joe</first>
            <last>Bob</last>
        </advisor>
    </advisors>
    <campuses>
        <campus>
            <campus_id>146873</campus_id>
            <name>Main Campus</name>
            <status>ACTIVE</status>
            <city>Moscow</city>
            <state>AZ</state>
            <zip>12345</zip>
            <country>USA</country>
            <is_primary>1</is_primary>
        </campus>
    </campuses>
</response>
"""
