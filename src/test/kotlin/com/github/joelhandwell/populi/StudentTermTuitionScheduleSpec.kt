package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val ts1 = StudentTermTuitionSchedule(1, "Graduate", 104, "1.00-10.00 credits")
private val ts2 = StudentTermTuitionSchedule(2, "Graduate", 105, "1.00-10.00 credits")

object StudentTermTuitionScheduleSpec : Spek({
    describe("StudentTermTuitionSchedule"){
        it("marshals to xml"){
            val r = StudentTermTuitionScheduleResponse(mutableListOf(ts1, ts2))
            val sw = StringWriter()
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + getStudentTermTuitionSchedulesXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val r = JAXB.unmarshal(getStudentTermTuitionSchedulesXml.reader(), StudentTermTuitionScheduleResponse::class.java)
            assertStudentTermTuitionSchedules(r.tuition_schedule)
        }
    }
})

fun assertStudentTermTuitionSchedules(studentTermTuitionSchedule: MutableList<StudentTermTuitionSchedule>) {
    assertEquals(setOf(ts1, ts2), studentTermTuitionSchedule.toSet())
}

const val getStudentTermTuitionSchedulesXml = """
<response>
    <tuition_schedules>
        <tuition_schedule>
            <bracket_id>104</bracket_id>
            <bracket_name>1.00-10.00 credits</bracket_name>
            <id>1</id>
            <name>Graduate</name>
        </tuition_schedule>
        <tuition_schedule>
            <bracket_id>105</bracket_id>
            <bracket_name>1.00-10.00 credits</bracket_name>
            <id>2</id>
            <name>Graduate</name>
        </tuition_schedule>
    </tuition_schedules>
</response>
"""
