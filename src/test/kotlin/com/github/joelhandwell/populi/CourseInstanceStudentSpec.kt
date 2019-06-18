package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import kotlin.test.assertEquals
import com.github.joelhandwell.populi.CourseInstanceStudentStatus.*

private val s1 = CourseInstanceStudent(
    ENROLLED,
    4004,
    "Christopher",
    "Anderson",
    "",
    LocalDate.parse("2010-05-05"),
    92,
    "A-",
    94
)

private val s2 = CourseInstanceStudent(
    AUDITOR,
    8000,
    "George",
    "Washington",
    "Jerry",
    LocalDate.parse("2010-05-16")
)

private val response = CourseInstanceStudentResponse(mutableListOf(s1, s2))

object CourseInstanceStudentSpec : Spek({

    describe("CourseInstanceStudent with no preferred name") {

        it("marshal to xml") { assertMarshals(courseInstanceStudentXml, s1) }
        it("unmarshal from xml") { assertUnmarshals(s1, courseInstanceStudentXml) }

    }

    describe("CourseInstanceStudent with no grade nor attendance") {
        val xml = """
        <courseinstance_student>
            <first>George</first>
            <last>Washington</last>
            <personid>8000</personid>
            <preferred>Jerry</preferred>
            <start_date>2010-05-16</start_date>
            <status>AUDITOR</status>
        </courseinstance_student>
        """.trimIndent()
        it("marshal to xml") { assertMarshals(xml, s2) }
        it("unmarshal from xml") { assertUnmarshals(s2, xml) }
    }

    describe("CourseInstanceStudent") {
        it("marshal to xml") { assertMarshals(getCourseInstanceStudentsXml, response) }
        it("unmarshal from xml") { assertUnmarshals(response, getCourseInstanceStudentsXml) }
    }
})

fun assertCourseInstanceStudent(courseInstanceStudent: CourseInstanceStudent){
    assertEquals(s1, courseInstanceStudent)
}

fun assertCourseInstanceStudents(courseInstanceStudents: MutableList<CourseInstanceStudent>) {
    assertEquals(mutableListOf(s1, s2), courseInstanceStudents)
}

val courseInstanceStudentXml = """
<courseinstance_student>
    <attendance>94</attendance>
    <first>Christopher</first>
    <grade>92</grade>
    <last>Anderson</last>
    <letter_grade>A-</letter_grade>
    <personid>4004</personid>
    <preferred></preferred>
    <start_date>2010-05-05</start_date>
    <status>ENROLLED</status>
</courseinstance_student>
""".trimIndent()

const val getCourseInstanceStudentsXml = """
<response>
    <courseinstance_student>
        <attendance>94</attendance>
        <first>Christopher</first>
        <grade>92</grade>
        <last>Anderson</last>
        <letter_grade>A-</letter_grade>
        <personid>4004</personid>
        <preferred></preferred>
        <start_date>2010-05-05</start_date>
        <status>ENROLLED</status>
    </courseinstance_student>
    <courseinstance_student>
        <first>George</first>
        <last>Washington</last>
        <personid>8000</personid>
        <preferred>Jerry</preferred>
        <start_date>2010-05-16</start_date>
        <status>AUDITOR</status>
    </courseinstance_student>
</response>
"""
