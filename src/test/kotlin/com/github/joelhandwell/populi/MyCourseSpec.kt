package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private val c1 = MyCourse(
    instanceid = 1111,
    name = "Intro to Classical & Modern Geometry",
    abbrv = "EDU321",
    section = 2,
    credits = 2.00,
    hours = 14.00,
    role = "FACULTY"
)

private val c2 = MyCourse(
    instanceid = 2222,
    name = "Calculus discussion group",
    abbrv = "CAL201",
    section = 1,
    credits = 0.25,
    hours = 2.00,
    role = "STUDENT",
    enrollment_status = CourseInstanceStudentStatus.ENROLLED,
    grade = 97.00,
    letter_grade = "A+"
)

val myCourses = mutableListOf(c1, c2)

private val response = MyCourseResponse(myCourses)

object MyCourseSpec : Spek({

    describe("MuCourse as FACULTY") {

        val xml = """
        <my_course>
            <instanceid>1111</instanceid>
            <name>Intro to Classical &amp; Modern Geometry</name>
            <abbrv>EDU321</abbrv>
            <section>2</section>
            <credits>2.00</credits>
            <hours>14.00</hours>
            <role>FACULTY</role>
        </my_course>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(c1) }

        it("unmarshal from xml") { assertUnmarshals(c1, xml) }
    }

    describe("MuCourse as STUDENT") {

        val xml = """
        <my_course>
            <instanceid>2222</instanceid>
            <name>Calculus discussion group</name>
            <abbrv>CAL201</abbrv>
            <section>1</section>
            <credits>0.25</credits>
            <hours>2.00</hours>
            <role>STUDENT</role>
            <enrollment_status>ENROLLED</enrollment_status>
            <grade>97.00</grade>
            <letter_grade>A+</letter_grade>
        </my_course>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(c2) }

        it("unmarshal from xml") { assertUnmarshals(c2, xml) }
    }

    describe("MuCourseResponse") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getMyCoursesXml) }
    }
})

const val getMyCoursesXml = """
<response>
    <my_course>
        <instanceid>1111</instanceid>
        <name>Intro to Classical &amp; Modern Geometry</name>
        <abbrv>EDU321</abbrv>
        <section>2</section>
        <credits>2.00</credits>
        <hours>14.00</hours>
        <role>FACULTY</role>
    </my_course>
    <my_course>
        <instanceid>2222</instanceid>
        <name>Calculus discussion group</name>
        <abbrv>CAL201</abbrv>
        <section>1</section>
        <credits>0.25</credits>
        <hours>2.00</hours>
        <role>STUDENT</role>
        <enrollment_status>ENROLLED</enrollment_status>
        <grade>97.00</grade>
        <letter_grade>A+</letter_grade>
    </my_course>
</response>"""
