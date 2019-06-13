package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object AcademicYearSpec : Spek({
    describe("AcademicYear"){
        it("marshal to xml"){
            val r = AcademicYearResponse()
            r.academic_year.addAll(mutableListOf(AcademicYear(111, 1901, 1902), AcademicYear(222, 1902, 1903)))
            val sw = StringWriter()
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + getAcademicYearsXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val r = JAXB.unmarshal(getAcademicYearsXml.reader(), AcademicYearResponse::class.java)
            assertAcademicYears(r.academic_year)
        }
    }
})

const val getAcademicYearsXml = """
<response>
    <academic_year>
        <end_year>1902</end_year>
        <start_year>1901</start_year>
        <yearid>111</yearid>
    </academic_year>
    <academic_year>
        <end_year>1903</end_year>
        <start_year>1902</start_year>
        <yearid>222</yearid>
    </academic_year>
</response>
"""

fun assertAcademicYears(academicYears: MutableList<AcademicYear>){
    assertEquals(2, academicYears.size)
    val y = academicYears.firstOrNull { it.yearid == 111 }
    assertNotNull(y)
    assertEquals(1901, y.start_year)
    assertEquals(1902, y.end_year)
}
