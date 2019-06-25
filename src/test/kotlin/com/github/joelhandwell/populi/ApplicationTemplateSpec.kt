package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val at1 = ApplicationTemplate(
    id = 112,
    name = "Basic",
    show_online = 0,
    fee_amount = null,
    fee_payment = "BEFORE_START",
    allow_undecided = 1
)

private val eo = ApplicationTemplateEnrollmentOption("FULL_TIME")

private val at2 = ApplicationTemplate(
    id = 114,
    name = "Standard",
    show_online = 1,
    fee_amount = usd(50.00),
    fee_payment = "BEFORE_START",
    allow_undecided = 1,
    program = mutableListOf(
        ApplicationTemplateProgram(26, "Undergraduate"),
        ApplicationTemplateProgram(27, "Graduate")
    ),
    academic_term = mutableListOf(
        ApplicationTemplateTerm(194, "2015-2016: Spring"),
        ApplicationTemplateTerm(189, "2015-2016: Winter"),
        ApplicationTemplateTerm(190, "2015-2016: Fall")
    ),
    enrollment_option = mutableListOf(
        eo,
        ApplicationTemplateEnrollmentOption("HALF_TIME"),
        ApplicationTemplateEnrollmentOption("LESS_THAN_HALF_TIME")
    )
)

private val response = ApplicationTemplateResponse(mutableListOf(at1, at2))

object ApplicationTemplateSpec : Spek({

    describe("ApplicationTemplate simple") {
        val xml = """
        <application_template>
            <id>112</id>
            <name>Basic</name>
            <show_online>0</show_online>
            <fee_amount></fee_amount>
            <fee_payment>BEFORE_START</fee_payment>
            <allow_undecided>1</allow_undecided>
        </application_template>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(at1, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(at1, xml) }
    }

    describe("ApplicationTemplateEnrollmentOption") {
        val xml = "<enrollment_option>FULL_TIME</enrollment_option>"

        it("marshal to xml") { assertMarshals(xml, eo) }

        it("unmarshal from xml") { assertUnmarshals(eo, xml) }
    }

    describe("ApplicationTemplate complex") {

        it("marshal to xml") { JAXB.marshal(response, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(response, getApplicationTemplatesXml) }
    }
})

fun assertApplicationTemplates(applicationTemplates: MutableList<ApplicationTemplate>) {
    assertEquals(mutableListOf(at1, at2), applicationTemplates)
}

const val getApplicationTemplatesXml = """
<response>
    <application_template>
        <id>112</id>
        <name>Basic</name>
        <show_online>0</show_online>
        <fee_amount></fee_amount>
        <fee_payment>BEFORE_START</fee_payment>
        <allow_undecided>1</allow_undecided>
    </application_template>
    <application_template>
        <id>114</id>
        <name>Standard</name>
        <show_online>1</show_online>
        <fee_amount>50</fee_amount>
        <fee_payment>BEFORE_START</fee_payment>
        <allow_undecided>1</allow_undecided>
        <programs>
            <program>
                <id>26</id>
                <name>Undergraduate</name>
            </program>
            <program>
                <id>27</id>
                <name>Graduate</name>
            </program>
        </programs>
        <academic_terms>
            <academic_term>
                <id>194</id>
                <name>2015-2016: Spring</name>
            </academic_term>
            <academic_term>
                <id>189</id>
                <name>2015-2016: Winter</name>
            </academic_term>
            <academic_term>
                <id>190</id>
                <name>2015-2016: Fall</name>
            </academic_term>
        </academic_terms>
        <enrollment_options>
            <enrollment_option>FULL_TIME</enrollment_option>
            <enrollment_option>HALF_TIME</enrollment_option>
            <enrollment_option>LESS_THAN_HALF_TIME</enrollment_option>
        </enrollment_options>
    </application_template>
</response>"""
