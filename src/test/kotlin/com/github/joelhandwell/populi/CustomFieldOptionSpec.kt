package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val customFieldOptions = mutableListOf(
    CustomFieldOption(
        index = 1,
        name = "Hadley Hall"
    ),

    CustomFieldOption(
        index = 2,
        name = "Williams Dorm"
    ),

    CustomFieldOption(
        index = 4,
        name = "McIntyre Commons"
    )
)

private val response = CustomFieldOptionResponse(customFieldOptions)

object CustomFieldOptionSpec : Spek({

    describe("CustomFieldOption") {

        it("marshal to xml") { assertMarshals(getCustomFieldOptionsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCustomFieldOptionsXml) }
    }
})

const val getCustomFieldOptionsXml = """
<response>
    <option>
        <index>1</index>
        <name>Hadley Hall</name>
    </option>
    <option>
        <index>2</index>
        <name>Williams Dorm</name>
    </option>
    <option>
        <index>4</index>
        <name>McIntyre Commons</name>
    </option>
</response>"""
