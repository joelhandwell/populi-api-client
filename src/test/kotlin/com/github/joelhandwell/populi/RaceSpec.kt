package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val races = mutableListOf(
    Race(1, "American Indian or Alaska Native"),
    Race(2, "Asian")
)

private val response = RaceResponse(races)

object RaceSpec : Spek({

    describe("Races") {

        it("marshal to xml") { assertMarshals(getRacesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getRacesXml) }
    }
})

const val getRacesXml = """
<response>
    <races>
        <race>
            <id>1</id>
            <name>American Indian or Alaska Native</name>
        </race>
        <race>
            <id>2</id>
            <name>Asian</name>
        </race>
    </races>
</response>"""
