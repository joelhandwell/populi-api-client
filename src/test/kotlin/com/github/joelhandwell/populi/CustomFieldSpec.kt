package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val customFields = mutableListOf(
    CustomField(
        id = 3333333,
        name = "Dorm Preference",
        type = "PERSON",
        input_type = "SELECT",
        value = "Robinson Hall",
        option_index = 2
    ),
    CustomField(
        id = 22222,
        name = "Joe Brown Scholarship Application Essay",
        type = "ADMISSIONS",
        input_type = "TEXT_AREA",
        value = "Interactively deliver superior intellectual capital"
    )
)

private val response = CustomFieldResponse(customFields)

object CustomFieldSpec : Spek({

    describe("CusomField") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCustomFieldsXml) }
    }

})

const val getCustomFieldsXml = """
<response>
    <custom_field>
        <id>3333333</id>
        <name>Dorm Preference</name>
        <type>PERSON</type>
        <input_type>SELECT</input_type>
        <value>Robinson Hall</value>
        <option_index>2</option_index>
    </custom_field>
    <custom_field>
        <id>22222</id>
        <name>Joe Brown Scholarship Application Essay</name>
        <type>ADMISSIONS</type>
        <input_type>TEXT_AREA</input_type>
        <value>Interactively deliver superior intellectual capital</value>
    </custom_field>
</response>"""
