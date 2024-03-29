package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import kotlin.test.assertEquals

private val e1 = Enrollment(
    person_id = 1520,
    academic_term_id = 777,
    instance_id = 44444,
    catalog_course_id = 14678,
    status = "ENROLLED",
    credits = 3.00,
    hours = 9.00,
    academic_term_name = "Spring Term 2009-2010",
    course_abbrv = "MAT201",
    course_name = "Calculus I",
    section = 3
)

private val e2 = Enrollment(
    person_id = 1625,
    academic_term_id = 777,
    instance_id = 44444,
    catalog_course_id = 14678,
    status = "ENROLLED",
    credits = 3.00,
    hours = 9.00,
    academic_term_name = "Spring Term 2009-2010",
    course_abbrv = "MAT201",
    course_name = "Calculus I",
    section = 3
)

private val termEnrollmentResponse = EnrollmentResponse(mutableListOf(e1, e2))

private val ue1 = Enrollment(
    person_id = 1520,
    academic_term_id = 777,
    instance_id = 44444,
    catalog_course_id = 14678,
    status = "ENROLLED",
    status_date = LocalDate.parse("2011-02-18"),
    credits = 3.00,
    hours = 9.00,
    academic_term_name = "Spring Term 2009-2010",
    course_abbrv = "MAT201",
    course_name = "Calculus I",
    section = 3
)

private val ue2 = Enrollment(
    person_id = 1625,
    academic_term_id = 777,
    instance_id = 44444,
    catalog_course_id = 14678,
    status = "ENROLLED",
    status_date = LocalDate.parse("2010-11-22"),
    credits = 3.00,
    hours = 9.00,
    academic_term_name = "Spring Term 2009-2010",
    course_abbrv = "MAT201",
    course_name = "Calculus I",
    section = 3
)

val updatedEnrollments = mutableListOf(ue1, ue2)

private val updatedEnrollmentResponse = EnrollmentResponse(updatedEnrollments)

object EnrollmentSpec : Spek({

    describe("Enrollment") {

        it("marshal to xml") { assertMarshals(termEnrollmentResponse) }

        it("unmarshal from xml") { assertUnmarshals(termEnrollmentResponse, getTermEnrollmentXml) }
    }

    describe("UpdatedEnrollment") {

        it("marshal to xml") { assertMarshals(updatedEnrollmentResponse) }

        it("unmarshal from xml") { assertUnmarshals(updatedEnrollmentResponse, getUpdatedEnrollmentXml) }
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

const val getUpdatedEnrollmentXml = """
<response>
	<enrollment>
		<person_id>1520</person_id>
		<term_id>777</term_id>
		<instance_id>44444</instance_id>
		<catalog_course_id>14678</catalog_course_id>
		<status>ENROLLED</status>
		<status_date>2011-02-18</status_date>
		<credits>3.00</credits>
		<hours>9.00</hours>
		<academic_term_name>Spring Term 2009-2010</academic_term_name>
		<course_abbrv>MAT201</course_abbrv>
		<course_name>Calculus I</course_name>
		<section>3</section>
	</enrollment>
	<enrollment>
		<person_id>1625</person_id>
		<term_id>777</term_id>
		<instance_id>44444</instance_id>
		<catalog_course_id>14678</catalog_course_id>
		<status>ENROLLED</status>
		<status_date>2010-11-22</status_date>
		<credits>3.00</credits>
		<hours>9.00</hours>
		<academic_term_name>Spring Term 2009-2010</academic_term_name>
		<course_abbrv>MAT201</course_abbrv>
		<course_name>Calculus I</course_name>
		<section>3</section>
	</enrollment>
</response>"""
