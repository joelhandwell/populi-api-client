package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val applicationFieldOptions = mutableListOf(
    ApplicationFieldOption(id = 528, name = "Baseball"),
    ApplicationFieldOption(id = 537, name = "Softball"),
    ApplicationFieldOption(id = 527, name = "Football"),
    ApplicationFieldOption(id = 529, name = "Soccer"),
    ApplicationFieldOption(id = 536, name = "Tennis")
)

private val response = ApplicationFieldOptionResponse(applicationFieldOptions)

object ApplicationFieldOptionSpec : Spek({

    describe("ApplicationFieldOptionResponse") {

        it("marshal to xml") { assertMarshals(getApplicationFieldOptionsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getApplicationFieldOptionsXml) }
    }
})

const val getApplicationFieldOptionsXml = """
<response>
    <application_field_options>
        <option>
            <id>528</id>
            <name>Baseball</name>
        </option>
        <option>
            <id>537</id>
            <name>Softball</name>
        </option>
        <option>
            <id>527</id>
            <name>Football</name>
        </option>
        <option>
            <id>529</id>
            <name>Soccer</name>
        </option>
        <option>
            <id>536</id>
            <name>Tennis</name>
        </option>
    </application_field_options>
</response>"""
