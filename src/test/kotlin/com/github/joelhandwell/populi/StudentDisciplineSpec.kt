package com.github.joelhandwell.populi

import com.github.joelhandwell.populi.jaxb.LocalDateTimeAdapter
import com.github.joelhandwell.populi.jaxb.UsaLocalDateAdapter
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val time1 = LocalDateTime.of(2017, 10, 21, 17, 11)

private val time2 = LocalDateTime.of(2018, 1, 1, 12, 32)

private val date = LocalDate.of(2017, 10, 21)

private val file1 = StudentDisciplineFile(
    5463876513,
    "Academic Warning Letter.pdf",
    "application/pdf",
    83591,
    time1,
    56898976,
    "William Smith"
)

private val file2 = StudentDisciplineFile(
    6549873215,
    "Academic Suspension Letter.pdf",
    "application/pdf",
    92136,
    time2,
    56898976,
    "William Smith"
)

private val d1 = StudentDiscipline(
    id = 654987123,
    action = "Academic Warning",
    start_date = date,
    end_date = LocalDate.of(2017, 12, 31),
    program_id = 0,
    program_name = "",
    show_on_transcript = 1,
    generate_tag = 1,
    comment = "",
    added_at = LocalDateTime.of(2017, 10, 21, 17, 11),
    added_by = 56898976,
    added_by_name = "William Smith",
    updated_at = LocalDateTime.of(2017, 10, 23, 14, 47),
    updated_by = 56898976,
    updated_by_name = "William Smith",
    file = mutableListOf(file1)
)

private val d2 = StudentDiscipline(
    id = 754987165,
    action = "Academic Suspension",
    start_date = LocalDate.of(2018, 1, 1),
    end_date = null,
    program_id = 2165489,
    program_name = "Undergraduate",
    show_on_transcript = 1,
    generate_tag = 1,
    comment = "",
    added_at = time2,
    added_by = 56898976,
    added_by_name = "William Smith",
    updated_at = LocalDateTime.of(2018, 1, 2, 10, 15),
    updated_by = 56898976,
    updated_by_name = "William Smith",
    file = mutableListOf(file2)
)

private val response = StudentDisciplineResponse(mutableListOf(d1, d2))

object StudentDisciplineSpec : Spek({
    describe("LocalDateTimeAdapter") {
        it("unmarshal xml to LocalDateTime") {
            val adapter = LocalDateTimeAdapter()
            assertEquals(time1, adapter.unmarshal("Oct 21, 2017 5:11pm"))
            assertEquals(time2, adapter.unmarshal("Jan 1, 2018 12:32pm"))
        }
    }

    describe("UsaLocalDateAdapter") {
        it("unmarshal xml to LocalDate with USA format") {
            val adapter = UsaLocalDateAdapter()
            assertEquals(date, adapter.unmarshal("Oct 21, 2017"))
        }
    }

    describe("StudentDisciplineFile") {
        val xml = """
        <file>
            <id>5463876513</id>
            <name>Academic Warning Letter.pdf</name>
            <type>application/pdf</type>
            <size>83591</size>
            <added_at>Oct 21, 2017 5:11pm</added_at>
            <added_by>56898976</added_by>
            <added_by_name>William Smith</added_by_name>
        </file>""".trimIndent()

        it("marshal to xml") { JAXB.marshal(file1, StringWriter()) }
        it("unmarshal from xml") { assertUnmarshals(file1, xml) }
    }

    describe("StudentDiscipline") {
        it("marshal to xml") { JAXB.marshal(response, StringWriter()) }
        it("unmarshal from xml") { assertUnmarshals(response, getStudentDisciplineXml) }
    }
})

fun assertStudentDisciplines(studentDisciplines: MutableList<StudentDiscipline>) {
    assertEquals(mutableListOf(d1, d2), studentDisciplines)
}

const val getStudentDisciplineXml = """
<response>
    <discipline>
        <id>654987123</id>
        <action>Academic Warning</action>
        <start_date>Oct 21, 2017</start_date>
        <end_date>Dec 31, 2017</end_date>
        <program_id></program_id>
        <program_name></program_name>
        <show_on_transcript>1</show_on_transcript>
        <generate_tag>1</generate_tag>
        <comment></comment>
        <added_at>Oct 21, 2017 5:11pm</added_at>
        <added_by>56898976</added_by>
        <added_by_name>William Smith</added_by_name>
        <updated_at>Oct 23, 2017 2:47pm</updated_at>
        <updated_by>56898976</updated_by>
        <updated_by_name>William Smith</updated_by_name>
        <files>
            <file>
                <id>5463876513</id>
                <name>Academic Warning Letter.pdf</name>
                <type>application/pdf</type>
                <size>83591</size>
                <added_at>Oct 21, 2017 5:11pm</added_at>
                <added_by>56898976</added_by>
                <added_by_name>William Smith</added_by_name>
            </file>
        </files>
    </discipline>
    <discipline>
        <id>754987165</id>
        <action>Academic Suspension</action>
        <start_date>Jan 1, 2018</start_date>
        <end_date></end_date>
        <program_id>2165489</program_id>
        <program_name>Undergraduate</program_name>
        <show_on_transcript>1</show_on_transcript>
        <generate_tag>1</generate_tag>
        <comment></comment>
        <added_at>Jan 1, 2018 12:32pm</added_at>
        <added_by>56898976</added_by>
        <added_by_name>William Smith</added_by_name>
        <updated_at>Jan 2, 2018 10:15am</updated_at>
        <updated_by>56898976</updated_by>
        <updated_by_name>William Smith</updated_by_name>
        <files>
            <file>
                <id>6549873215</id>
                <name>Academic Suspension Letter.pdf</name>
                <type>application/pdf</type>
                <size>92136</size>
                <added_at>Jan 1, 2018 12:32pm</added_at>
                <added_by>56898976</added_by>
                <added_by_name>William Smith</added_by_name>
            </file>
        </files>
    </discipline>
</response>
"""
