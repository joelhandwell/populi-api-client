package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object CampusSpec : Spek({
    describe("Campus") {
        it("marshal to xml") {
            val campusResponse = CampusResponse()
            campusResponse.campus.addAll(mutableListOf(
                Campus(1, "SF Campus", "ACTIVE", "San Francisco", "CA", "12345", "USA", 1),
                Campus(2, "NY Campus", "ACTIVE", "New York", "NY", "67890", "USA", 0)
            ))
            val sw = StringWriter()
            JAXB.marshal(campusResponse, sw)
            assertEquals(XML_HEADER + getCampusesXml.trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val r = JAXB.unmarshal(getCampusesXml.reader(), CampusResponse::class.java)
            assertCampuses(r.campus)
        }
    }
})

const val getCampusesXml = """
<response>
    <campus>
        <id>1</id>
        <name>SF Campus</name>
        <status>ACTIVE</status>
        <city>San Francisco</city>
        <state>CA</state>
        <zip>12345</zip>
        <country>USA</country>
        <is_primary>1</is_primary>
    </campus>
    <campus>
        <id>2</id>
        <name>NY Campus</name>
        <status>ACTIVE</status>
        <city>New York</city>
        <state>NY</state>
        <zip>67890</zip>
        <country>USA</country>
        <is_primary>0</is_primary>
    </campus>
</response>
"""

fun assertCampuses(campuses: MutableList<Campus>){
    assertEquals(2, campuses.size)
    val sf = campuses.firstOrNull { it.id == 1 }
    assertNotNull(sf)
    assertEquals("SF Campus", sf.name)
    assertEquals("ACTIVE", sf.status)
    assertEquals("San Francisco", sf.city)
    assertEquals("CA", sf.state)
    assertEquals("12345", sf.zip)
    assertEquals("USA", sf.country)
    assertEquals(1, sf.is_primary)
 }
