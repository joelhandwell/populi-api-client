package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.Year
import javax.xml.bind.JAXB
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val at1 = AcademicTerm(
    1111,
    "Fall",
    "2010-2011: Fall",
    LocalDate.of(2010, 8, 15),
    LocalDate.of(2010, 12, 18),
    "STANDARD_TERM",
    222,
    Year.of(2010),
    Year.of(2011),
    0
)

private val at2 = AcademicTerm(
    4444,
    "Spring",
    "2010-2011: Spring",
    LocalDate.of(2010, 1, 15),
    LocalDate.of(2010, 5, 18),
    "STANDARD_TERM",
    222,
    Year.of(2010),
    Year.of(2011),
    0
)

object AcademicTermSpec : Spek({
    describe("AcademicTerm") {
        it("marshal to xml") {
            val r = AcademicTermResponse(mutableListOf(at1, at2))
            assertMarshals(getAcademicTermsXml, r)
        }

        it("unmarshal from xml") {
            val r = JAXB.unmarshal(getAcademicTermsXml.reader(), AcademicTermResponse::class.java)
            assertAcademicTerms(r.academic_term)
        }
    }
})

const val getAcademicTermsXml = """
<response>
    <academic_term>
        <end_date>2010-12-18</end_date>
        <end_year>2011</end_year>
        <fullname>2010-2011: Fall</fullname>
        <name>Fall</name>
        <nonstandard>0</nonstandard>
        <start_date>2010-08-15</start_date>
        <start_year>2010</start_year>
        <termid>1111</termid>
        <type>STANDARD_TERM</type>
        <yearid>222</yearid>
    </academic_term>
    <academic_term>
        <end_date>2010-05-18</end_date>
        <end_year>2011</end_year>
        <fullname>2010-2011: Spring</fullname>
        <name>Spring</name>
        <nonstandard>0</nonstandard>
        <start_date>2010-01-15</start_date>
        <start_year>2010</start_year>
        <termid>4444</termid>
        <type>STANDARD_TERM</type>
        <yearid>222</yearid>
    </academic_term>
</response>
"""

const val getCurrentAcademicTermXml = """
<response>
    <end_date>2010-12-18</end_date>
    <end_year>2011</end_year>
    <fullname>2010-2011: Fall</fullname>
    <name>Fall</name>
    <nonstandard>0</nonstandard>
    <start_date>2010-08-15</start_date>
    <start_year>2010</start_year>
    <termid>1111</termid>
    <type>STANDARD_TERM</type>
    <yearid>222</yearid>
</response>
"""

fun assertAcademicTerms(academicTerms: MutableList<AcademicTerm>) {
    assertEquals(2, academicTerms.size)
    val t = academicTerms.firstOrNull { it.termid == 1111 }
    assertNotNull(t)
    assertAcademicTerm(t)
}

fun assertAcademicTerm(t: AcademicTerm){
    assertEquals(1111, t.termid)
    assertEquals("Fall", t.name)
    assertEquals("2010-2011: Fall", t.fullname)
    assertEquals(LocalDate.of(2010, 8, 15), t.start_date)
    assertEquals(LocalDate.of(2010, 12, 18), t.end_date)
    assertEquals("STANDARD_TERM", t.type)
    assertEquals(222, t.yearid)
    assertEquals(Year.of(2010), t.start_year)
    assertEquals(Year.of(2011), t.end_year)
    assertEquals(0, t.nonstandard)
}
