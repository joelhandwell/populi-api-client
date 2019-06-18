package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.OffsetDateTime
import kotlin.test.assertEquals

private val meeting1 = CourseInstanceMeeting(
    667,
    15687,
    "School Building: 101",
    OffsetDateTime.parse("2016-03-21T08:00:00-07:00"),
    OffsetDateTime.parse("2016-03-21T09:00:00-07:00"),
    0,
    0
)

private val meeting2 = CourseInstanceMeeting(
    668,
    0,
    "TBA",
    OffsetDateTime.parse("2016-04-23T08:00:00-07:00"),
    OffsetDateTime.parse("2016-04-23T09:00:00-07:00"),
    0,
    0
)

private val response = CourseInstanceMeetingResponse(mutableListOf(meeting1, meeting2))

object CourseInstanceMeetingSpec : Spek({

    describe("CourseInstanceMeeting") {

        it("marshal to xml") { assertMarshals(getCourseInstanceMeetingsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCourseInstanceMeetingsXml) }

    }
})

fun assertCourseInstanceMeetings(courseInstanceMeetings: MutableList<CourseInstanceMeeting>){
    assertEquals(mutableListOf(meeting1, meeting2), courseInstanceMeetings)
}

val getCourseInstanceMeetingsXml = """
<response>
    <meeting>
        <counts_toward_attendance_hours>0</counts_toward_attendance_hours>
        <counts_toward_clinical_hours>0</counts_toward_clinical_hours>
        <end>2016-03-21T09:00:00-07:00</end>
        <meetingid>667</meetingid>
        <room_name>School Building: 101</room_name>
        <roomid>15687</roomid>
        <start>2016-03-21T08:00:00-07:00</start>
    </meeting>
    <meeting>
        <counts_toward_attendance_hours>0</counts_toward_attendance_hours>
        <counts_toward_clinical_hours>0</counts_toward_clinical_hours>
        <end>2016-04-23T09:00:00-07:00</end>
        <meetingid>668</meetingid>
        <room_name>TBA</room_name>
        <roomid>0</roomid>
        <start>2016-04-23T08:00:00-07:00</start>
    </meeting>
</response>
"""
