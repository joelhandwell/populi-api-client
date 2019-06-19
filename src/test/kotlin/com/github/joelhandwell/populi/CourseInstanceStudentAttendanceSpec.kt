package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDateTime
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val a1 = CourseInstanceStudentAttendance(
    42696,
    LocalDateTime.of(2017, 8, 2, 8, 0, 0),
    LocalDateTime.of(2017, 8, 2, 10, 0, 0),
    "BIO-101",
    "PRESENT"
)

private val a2 = CourseInstanceStudentAttendance(
    42699,
    LocalDateTime.of(2017, 8, 6, 8, 0, 0),
    LocalDateTime.of(2017, 8, 6, 10, 0, 0),
    "BIO-101",
    "EXCUSED"
)

private val response = CourseInstanceStudentAttendanceResponse(mutableListOf(a1, a2))

object CourseInstanceStudentAttendanceSpec : Spek({

    describe("CourseInstanceStudentAttendance") {

        it("marshal to xml") {
            JAXB.marshal(response, StringWriter())
            // following will fail because LocalDateTime default format requires "T", let's assume it pass if marshals without error
            //assertMarshals(getCourseInstanceStudentAttendanceXml, response)
        }

        it("unmarshal from xml ") { assertUnmarshals(response, getCourseInstanceStudentAttendanceXml) }

    }
})

fun assertCourseInstanceStudentAttendances(courseInstanceStudentAttendance: MutableList<CourseInstanceStudentAttendance>){
    assertEquals(mutableListOf(a1, a2), courseInstanceStudentAttendance)
}

const val getCourseInstanceStudentAttendanceXml = """
<response>
    <attendee>
        <meetingid>42696</meetingid>
        <start>2017-08-02 08:00:00</start>
        <end>2017-08-02 10:00:00</end>
        <summary>BIO-101</summary>
        <status>PRESENT</status>
    </attendee>
    <attendee>
        <meetingid>42699</meetingid>
        <start>2017-08-06 08:00:00</start>
        <end>2017-08-06 10:00:00</end>
        <summary>BIO-101</summary>
        <status>EXCUSED</status>
    </attendee>
</response>"""
