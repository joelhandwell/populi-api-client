package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

var leadSources = mutableListOf(
    LeadSource(
        id = 1111,
        name = "Admissions Poster at Conference",
        type = "ADVERTISEMENT"
    ),
    LeadSource(
        id = 2222,
        name = "Datatel 2010 Lead List #1",
        type = "PURCHASED"
    )
)

private var response = LeadSourceResponse(leadSources)

object LeadSourceSpec : Spek({

    describe("LeadSource") {

        it("marshals to xml") { assertMarshals(getLeadSourcesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getLeadSourcesXml) }
    }
})

const val getLeadSourcesXml = """
<response>
    <lead_source>
        <id>1111</id>
        <name>Admissions Poster at Conference</name>
        <type>ADVERTISEMENT</type>
    </lead_source>
    <lead_source>
        <id>2222</id>
        <name>Datatel 2010 Lead List #1</name>
        <type>PURCHASED</type>
    </lead_source>
</response>"""
