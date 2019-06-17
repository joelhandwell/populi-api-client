package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private val ag1 = AssignmentGroup(3333, "Participation", 30, 0, 0)
private val ag2 = AssignmentGroup(4444, "Test", 60, 0, 0)

object CourseInstanceAssignmentGroupSpec : Spek({

    val response = AssignmentGroupResponse(mutableListOf(ag1, ag2))

    describe("CourseInstanceAssignmentGroup") {
        it("marshal to xml") {
            assertMarshals(getCourseInstanceAssignmentGroupsXml, response)
        }

        it("unmarshal from xml") {
            assertUnmarshals(response, getCourseInstanceAssignmentGroupsXml)
        }
    }
})

const val getCourseInstanceAssignmentGroupsXml = """
<response>
    <assignment_group>
        <drop_lowest>0</drop_lowest>
        <extra_credit>0</extra_credit>
        <groupid>3333</groupid>
        <name>Participation</name>
        <weight_percent>30</weight_percent>
    </assignment_group>
    <assignment_group>
        <drop_lowest>0</drop_lowest>
        <extra_credit>0</extra_credit>
        <groupid>4444</groupid>
        <name>Test</name>
        <weight_percent>60</weight_percent>
    </assignment_group>
</response>
"""
