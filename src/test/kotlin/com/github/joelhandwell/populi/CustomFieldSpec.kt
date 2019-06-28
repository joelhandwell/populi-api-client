package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import com.github.joelhandwell.populi.CustomFieldInputType.*

val customFields = mutableListOf(
    CustomField(
        id = 3333333,
        name = "Dorm Preference",
        type = "PERSON",
        input_type = SELECT,
        value = "Robinson Hall",
        option_index = 2
    ),
    CustomField(
        id = 22222,
        name = "Joe Brown Scholarship Application Essay",
        type = "ADMISSIONS",
        input_type = TEXT_AREA,
        value = "Interactively deliver superior intellectual capital"
    )
)

val customFieldsWithDescription = mutableListOf(
    CustomField(
        id = 111,
        name = "Dorm Preference",
        description = "Quickly deliver enterprise-wide best practices rather than cross-media vortals.",
        type = "STUDENT",
        input_type = SELECT
    ),
    CustomField(
        id = 555,
        name = "Number of Previous Colleges Attended",
        description = "Collaboratively leverage other's mission-critical networks before accurate processes.",
        type = "ADMISSIONS",
        input_type = INTEGER
    )
)

private val getCustomFieldResponse = CustomFieldResponse(customFields)

private val getAllCustomFieldResponse = CustomFieldResponse(customFieldsWithDescription)

object CustomFieldSpec : Spek({

    describe("CustomField") {

        it("marshal to xml") { assertMarshals(getCustomFieldResponse) }

        it("unmarshal from xml") { assertUnmarshals(getCustomFieldResponse, getCustomFieldsXml) }
    }

    describe("CustomField with description") {

        it("marshal to xml") { assertMarshals(getAllCustomFieldResponse) }

        it("unmarshal from xml") { assertUnmarshals(getAllCustomFieldResponse, getAllCustomFieldsXml) }
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

const val getAllCustomFieldsXml = """
<response>
    <custom_field>
        <id>111</id>
        <name>Dorm Preference</name>
        <description>Quickly deliver enterprise-wide best practices rather than cross-media vortals.</description>
        <type>STUDENT</type>
        <input_type>SELECT</input_type>
    </custom_field>
    <custom_field>
        <id>555</id>
        <name>Number of Previous Colleges Attended</name>
        <description>Collaboratively leverage other's mission-critical networks before accurate processes.</description>
        <type>ADMISSIONS</type>
        <input_type>INTEGER</input_type>
    </custom_field>
</response>"""
