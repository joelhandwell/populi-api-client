package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

private val exampleCountries = mutableListOf(
    Country(abbreviation = "USA", name = "United States of America"),
    Country(abbreviation = "CA", name = "Canada")
)

private val response = CountryResponse(exampleCountries)

object CountrySpec : Spek({

    describe("CountryResponse") {

        it("marshal to xml") { assertMarshals(getCountriesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCountriesXml) }
    }

})

fun assertCountries(countries: MutableList<Country>){
    assertEquals(exampleCountries, countries)
}

const val getCountriesXml = """
<response>
    <countries>
        <country>
            <abbreviation>USA</abbreviation>
            <name>United States of America</name>
        </country>
        <country>
            <abbreviation>CA</abbreviation>
            <name>Canada</name>
        </country>
    </countries>
</response>"""
