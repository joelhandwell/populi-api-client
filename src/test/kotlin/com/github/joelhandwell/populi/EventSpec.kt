package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.OffsetDateTime
import kotlin.test.assertEquals

private val color = EventColor(79, 140, 255)

private val e1 = Event(
    eventid = 1111,
    ownertype = "PERSON",
    ownerid = 1234,
    calname = "My Calendar",
    summary = "Lunch",
    description = "",
    allday = 0,
    recurrence = LocalDate.parse("2017-01-26"),
    color = color,
    start = OffsetDateTime.parse("2017-01-26T13:00:00-06:00"),
    end = OffsetDateTime.parse("2017-01-26T14:00:00-06:00")
)

private val e2 = Event(
    eventid = 2222,
    ownertype = "INSTANCE",
    ownerid = 5678,
    calname = "ENG101: English 101",
    summary = "Lecture",
    description = "",
    allday = 0,
    recurrence = LocalDate.parse("2017-01-26"),
    color = color,
    start = OffsetDateTime.parse("2017-01-26T15:00:00-06:00"),
    end = OffsetDateTime.parse("2017-01-26T16:00:00-06:00")
)

val events = mutableListOf(e1, e2)

private val response = EventResponse(events)

val event = Event(
    eventid = 1111,
    ownertype = "PERSON",
    ownerid = 1234,
    calname = "My Calendar",
    summary = "Lunch",
    description = "",
    allday = 0,
    start = OffsetDateTime.parse("2017-01-26T13:00:00-06:00"),
    end = OffsetDateTime.parse("2017-01-26T14:00:00-06:00")
)

private val responseSingle = EventSingleResponse(event)

object EventSpec : Spek({

    describe("Events") {
        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getEventsXml) }

        it("converts fields of getEvents into Map of String") {
            val emptyMap = getEventsFieldMap()
            assert(emptyMap.isEmpty())

            val startDateString = "2019-07-09"
            val startDate = LocalDate.parse(startDateString)
            val mapWithStartDate = getEventsFieldMap(startDate = startDate)
            assertEquals(mapOf("startDate" to startDateString), mapWithStartDate)

            val endDateString = "2019-07-10"
            val endDate = LocalDate.parse(endDateString)
            val mapWithStartEndDate = getEventsFieldMap(startDate = startDate, endDate = endDate)
            assertEquals(mapOf("startDate" to startDateString, "endDate" to endDateString), mapWithStartEndDate)

            val calendars = mutableListOf(
                EventCalendar(CalendarOwnerType.PERSON, 1234),
                EventCalendar(CalendarOwnerType.INSTANCE, 5678)
            )

            val expectedMap = mapOf(
                "startDate" to startDateString,
                "endDate" to endDateString,
                "calendars[0][ownertype]" to "PERSON",
                "calendars[0][ownerid]" to "1234",
                "calendars[1][ownertype]" to "INSTANCE",
                "calendars[1][ownerid]" to "5678"
            )
            assertEquals(expectedMap, getEventsFieldMap(startDate, endDate, calendars))
        }
    }

    describe("Event (single)") {

        it("marshal to xml") { assertMarshals(responseSingle) }

        it("unmarshal from xml") { assertUnmarshals(responseSingle, getEventXml) }
    }
})

const val getEventsXml = """
<response>
    <event>
        <eventid>1111</eventid>
        <ownertype>PERSON</ownertype>
        <ownerid>1234</ownerid>
        <calname>My Calendar</calname>
        <summary>Lunch</summary>
        <description></description>
        <allday>0</allday>
        <recurrence>2017-01-26</recurrence>
        <color>
            <red>79</red>
            <green>140</green>
            <blue>255</blue>
        </color>
        <start>2017-01-26T13:00:00-06:00</start>
        <end>2017-01-26T14:00:00-06:00</end>
    </event>
    <event>
        <eventid>2222</eventid>
        <ownertype>INSTANCE</ownertype>
        <ownerid>5678</ownerid>
        <calname>ENG101: English 101</calname>
        <summary>Lecture</summary>
        <description></description>
        <allday>0</allday>
        <recurrence>2017-01-26</recurrence>
        <color>
            <red>79</red>
            <green>140</green>
            <blue>255</blue>
        </color>
        <start>2017-01-26T15:00:00-06:00</start>
        <end>2017-01-26T16:00:00-06:00</end>
    </event>
</response>"""

const val getEventXml = """
<response>
    <event>
        <eventid>1111</eventid>
        <ownertype>PERSON</ownertype>
        <ownerid>1234</ownerid>
        <calname>My Calendar</calname>
        <summary>Lunch</summary>
        <description></description>
        <allday>0</allday>
        <start>2017-01-26T13:00:00-06:00</start>
        <end>2017-01-26T14:00:00-06:00</end>
    </event>
</response>"""
