package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val provinces = mutableListOf(
    Province("AB", "Alberta"),
    Province("BC", "British Columbia")
)

private val response = ProvinceResponse(provinces)

object ProvinceSpec : Spek({

    describe("Province") {

        it("marshal to xml") { assertMarshals(getProvincesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getProvincesXml) }
    }
})

const val getProvincesXml = """
<response>
    <provinces>
        <province>
            <abbreviation>AB</abbreviation>
            <name>Alberta</name>
        </province>
        <province>
            <abbreviation>BC</abbreviation>
            <name>British Columbia</name>
        </province>
    </provinces>
</response>"""
