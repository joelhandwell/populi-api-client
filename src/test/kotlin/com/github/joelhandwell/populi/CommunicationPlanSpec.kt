package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDateTime
import javax.xml.bind.JAXB

private val communicationPlan = CommunicationPlan(
    id = 13,
    name = "Leads Communication Plan",
    added_by = 3093,
    added_at = LocalDateTime.of(2017, 2, 8, 22, 9, 27)
)

val communicationPlanResponse = CommunicationPlanResponse(mutableListOf(communicationPlan))

object CommunicationPlanSpec : Spek({
    describe("CommunicationPlan") {

        it("marshal to xml") { JAXB.marshal(communicationPlanResponse, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(communicationPlanResponse, getCommunicationPlansXml) }
    }
})

const val getCommunicationPlansXml = """
<response>
    <communication_plan>
        <id>13</id>
        <name>Leads Communication Plan</name>
        <added_by>3093</added_by>
        <added_at>2017-02-08 22:09:27</added_at>
    </communication_plan>
</response>"""
