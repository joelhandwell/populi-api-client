package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val states = mutableListOf(
    State("AL", "Alabama"),
    State("AK", "Alaska")
)

private val response = StateResponse(states)

object StatesSpec : Spek({

    describe("State") {

        it("marshal to xml") { assertMarshals(getStatesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getStatesXml) }
    }
})

const val getStatesXml = """
<response>
    <states>
        <state>
            <abbreviation>AL</abbreviation>
            <name>Alabama</name>
        </state>
        <state>
            <abbreviation>AK</abbreviation>
            <name>Alaska</name>
        </state>
    </states>
</response>"""
