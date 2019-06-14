package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val e1 = Enrollment(
    1520,
    777,
    44444,
    14678,
    "ENROLLED",
    3.00,
    9.00,
    "Spring Term 2009-2010",
    "MAT201",
    "Calculus I",
    3
)

private val e2 = Enrollment(
    1625,
    777,
    44444,
    14678,
    "ENROLLED",
    3.00,
    9.00,
    "Spring Term 2009-2010",
    "MAT201",
    "Calculus I",
    3
)

object EnrollmentSpec : Spek({
    describe("Enrollment") {
        it("marshals to xml") {
            val r = TermEnrollmentResponse(mutableListOf(e1, e2))
            val sw = StringWriter()
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + getTermEnrollmentXml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml"){
            val r = JAXB.unmarshal(getTermEnrollmentXml.reader(), TermEnrollmentResponse::class.java)
            assertTermEnrollments(r.enrollment)
        }
    }
})

fun assertTermEnrollments(enrollments: MutableList<Enrollment>) {
    assertEquals(2, enrollments.size)
    assertEquals(setOf(e1, e2), enrollments.toSet())
}

const val getTermEnrollmentXml = """
<response>
    <enrollment>
        <academic_term_id>777</academic_term_id>
        <academic_term_name>Spring Term 2009-2010</academic_term_name>
        <catalog_course_id>14678</catalog_course_id>
        <course_abbrv>MAT201</course_abbrv>
        <course_name>Calculus I</course_name>
        <credits>3.0</credits>
        <hours>9.0</hours>
        <instance_id>44444</instance_id>
        <person_id>1520</person_id>
        <section>3</section>
        <status>ENROLLED</status>
    </enrollment>
    <enrollment>
        <academic_term_id>777</academic_term_id>
        <academic_term_name>Spring Term 2009-2010</academic_term_name>
        <catalog_course_id>14678</catalog_course_id>
        <course_abbrv>MAT201</course_abbrv>
        <course_name>Calculus I</course_name>
        <credits>3.0</credits>
        <hours>9.0</hours>
        <instance_id>44444</instance_id>
        <person_id>1625</person_id>
        <section>3</section>
        <status>ENROLLED</status>
    </enrollment>
</response>
"""
