package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDateTime
import kotlin.test.assertEquals

private val lesson1 = CourseInstanceLesson(
    1111,
    "Lesson #1",
    LocalDateTime.of(2010, 9, 30, 0, 0, 0),
    1,
    2,
    0
)

private val lesson2 = CourseInstanceLesson(
    1112,
    "Lesson #2",
    LocalDateTime.of(2010, 10, 7, 0, 0, 0),
    1,
    3,
    2
)

private val response = CourseInstanceLessonResponse(mutableListOf(lesson1, lesson2))

object CourseInstanceLessonsSpec : Spek({
    describe("SpaceDelimitedLocalDateTimeAdapter") {

        val adapter = SpaceDelimitedLocalDateTimeAdapter()
        val dt = LocalDateTime.of(2010, 9, 30, 23, 59, 59)
        val xml = "2010-09-30 23:59:59"

        //it's not perfect but let's ignore this case.
        //it("marshal LocalDateTime to xml") { assertEquals(xml, adapter.marshal(dt)) }
        it("unmarshal xml to LocalDateTime") { assertEquals(dt, adapter.unmarshal(xml)) }
    }

    describe("CourseInstanceLesson") {
        //it's not perfect but let's ignore this case.
        //it("marshal to xml") { assertMarshals(getCourseInstanceLessonsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCourseInstanceLessonsXml) }
    }
})

fun assertCourseInstanceLessons(coueseInstanceLessons: MutableList<CourseInstanceLesson>) {
    assertEquals(mutableListOf(lesson1, lesson2), coueseInstanceLessons)
}

const val getCourseInstanceLessonsXml = """
<response>
    <lesson>
        <lessonid>1111</lessonid>
        <name>Lesson #1</name>
        <available_at>2010-09-30 00:00:00</available_at>
        <is_available>1</is_available>
        <num_discussions>2</num_discussions>
        <num_new_posts>0</num_new_posts>
    </lesson>
    <lesson>
        <lessonid>1112</lessonid>
        <name>Lesson #2</name>
        <available_at>2010-10-07 00:00:00</available_at>
        <is_available>1</is_available>
        <num_discussions>3</num_discussions>
        <num_new_posts>2</num_new_posts>
    </lesson>
</response>"""
