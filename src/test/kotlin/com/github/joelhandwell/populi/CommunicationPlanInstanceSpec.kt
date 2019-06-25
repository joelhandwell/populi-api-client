package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDateTime
import javax.xml.bind.JAXB

val communicationPlanInstance = CommunicationPlanInstance(
    id = 98,
    communication_plan_id = 13,
    communication_plan_name = "Leads Communication Plan",
    sender_id = 3093,
    added_by = 3093,
    added_at = LocalDateTime.of(2017, 2, 9, 23, 45, 27)
)

private val personCommunicationPlanResponse = PersonCommunicationPlanResponse(mutableListOf(communicationPlanInstance))

object CommunicationPlanInstanceSpec : Spek({
    describe("CommunicationPlanInstance") {

        it("marshal to xml") { JAXB.marshal(personCommunicationPlanResponse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(personCommunicationPlanResponse, getPersonCommunicationPlansXml) }
    }
})

const val getPersonCommunicationPlansXml = """
<response>
    <communication_plan_instance>
        <id>98</id>
        <communication_plan_id>13</communication_plan_id>
        <communication_plan_name>Leads Communication Plan</communication_plan_name>
        <sender_id>3093</sender_id>
        <added_by>3093</added_by>
        <added_at>2017-02-09 23:45:27</added_at>
    </communication_plan_instance>
</response>"""
