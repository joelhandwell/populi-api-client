package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import java.time.Year
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val ss = StudentSpecialization(
    specialization_id = 513,
    name = "English",
    type = "MAJOR",
    status = "ACTIVE",
    granted_date = null
)

private val sd = StudentDegree(
    degree_id = 1,
    degree_student_id = 10228,
    abbrv = "B.A.",
    name = "Bachelor of Arts",
    status = "ACTIVE",
    active_date = LocalDate.parse("2000-02-07"),
    inactive_date = null,
    graduation_date = null,
    graduate_degree = 0,
    catalog_year_id = 48,
    catalog_start_year = Year.of(2000),
    catalog_end_year = Year.of(2001),
    anticipated_completion_date = LocalDate.parse("2004-02-07"),
    specialization = mutableListOf(ss)
)

private val sp = StudentProgram(
    id = 145987,
    program_id = 26,
    name = "Undergraduate",
    started_on = LocalDate.parse("2000-02-07"),
    exit_date = null,
    exit_reason = "",
    entrance_term_id = 123456,
    entrance_term_name = "2000-2001: Fall",
    previous_education_level_id = 3,
    previous_education_level_name = "High School Diploma",
    degree = mutableListOf(sd)
)

private val response = StudentProgramResponse(mutableListOf(sp))

object StudentProgramSpec : Spek({
    describe("StudentSpecialization") {
        val xml = """
        <specialization>
            <specialization_id>513</specialization_id>
            <name>English</name>
            <type>MAJOR</type>
            <status>ACTIVE</status>
            <granted_date></granted_date>
        </specialization>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(ss, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(ss, xml) }
    }

    describe("StudentDegree") {

        val xml = """ 
        <degree>
            <degree_id>1</degree_id>
            <degree_student_id>10228</degree_student_id>
            <abbrv>B.A.</abbrv>
            <name>Bachelor of Arts</name>
            <status>ACTIVE</status>
            <active_date>2000-02-07</active_date>
            <inactive_date></inactive_date>
            <graduation_date></graduation_date>
            <graduate_degree>0</graduate_degree>
            <catalog_year_id>48</catalog_year_id>
            <catalog_start_year>2000</catalog_start_year>
            <catalog_end_year>2001</catalog_end_year>
            <anticipated_completion_date>2004-02-07</anticipated_completion_date>
            <specializations>
                <specialization>
                    <specialization_id>513</specialization_id>
                    <name>English</name>
                    <type>MAJOR</type>
                    <status>ACTIVE</status>
                    <granted_date></granted_date>
                </specialization>
            </specializations>
        </degree>
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(sd, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(sd, xml) }
    }

    describe("StudentProgram") {

        it("marshal to xml") { JAXB.marshal(response, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(response, getStudentProgramsXml) }
    }
})

fun assertStudentPrograms(studentPrograms: MutableList<StudentProgram>){
    assertEquals(mutableListOf(sp), studentPrograms)
}

const val getStudentProgramsXml = """
<response>
    <programs>
        <program>
            <id>145987</id>
            <program_id>26</program_id>
            <name>Undergraduate</name>
            <started_on>2000-02-07</started_on>
            <exit_date></exit_date>
            <exit_reason></exit_reason>
            <entrance_term_id>123456</entrance_term_id>
            <entrance_term_name>2000-2001: Fall</entrance_term_name>
            <previous_education_level_id>3</previous_education_level_id>
            <previous_education_level_name>High School Diploma</previous_education_level_name>
            <degrees>
                <degree>
                    <degree_id>1</degree_id>
                    <degree_student_id>10228</degree_student_id>
                    <abbrv>B.A.</abbrv>
                    <name>Bachelor of Arts</name>
                    <status>ACTIVE</status>
                    <active_date>2000-02-07</active_date>
                    <inactive_date></inactive_date>
                    <graduation_date></graduation_date>
                    <graduate_degree>0</graduate_degree>
                    <catalog_year_id>48</catalog_year_id>
                    <catalog_start_year>2000</catalog_start_year>
                    <catalog_end_year>2001</catalog_end_year>
                    <anticipated_completion_date>2004-02-07</anticipated_completion_date>
                    <specializations>
                        <specialization>
                            <specialization_id>513</specialization_id>
                            <name>English</name>
                            <type>MAJOR</type>
                            <status>ACTIVE</status>
                            <granted_date></granted_date>
                        </specialization>
                    </specializations>
                </degree>
            </degrees>
        </program>
    </programs>
</response>
"""
