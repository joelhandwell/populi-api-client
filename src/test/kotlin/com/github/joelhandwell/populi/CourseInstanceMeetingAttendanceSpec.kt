package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

private val a1 = CourseInstanceMeetingAttendance(1234, "TARDY")
private val a2 = CourseInstanceMeetingAttendance(1235, "PRESENT")
private val response = CourseInstanceMeetingAttendanceResponse(mutableListOf(a1, a2))

object CourseInstanceMeetingAttendanceSpec : Spek({

    describe("Attendance") {

        it("marshal to xml") { assertMarshals(getCourseInstanceMeetingAttendanceXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCourseInstanceMeetingAttendanceXml) }

    }
})

fun assertCourseInstanceMeetingAttendances(courseInstanceMeetingAttendances: MutableList<CourseInstanceMeetingAttendance>) {
    assertEquals(mutableListOf(a1, a2), courseInstanceMeetingAttendances)
}

const val getCourseInstanceMeetingAttendanceXml = """
<response>
    <attendee>
        <personid>1234</personid>
        <status>TARDY</status>
    </attendee>
    <attendee>
        <personid>1235</personid>
        <status>PRESENT</status>
    </attendee>
</response>
"""
