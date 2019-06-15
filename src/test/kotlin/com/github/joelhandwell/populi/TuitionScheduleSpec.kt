package com.github.joelhandwell.populi

import org.javamoney.moneta.Money
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.money.MonetaryAmount
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

fun usd(number: Double): MonetaryAmount = Money.of(number, "USD")

private val tsb1 = TuitionScheduleBracket(
    22,
    "CREDITS",
    1.00,
    12.00,
    usd(350.00),
    usd(0.00),
    usd(0.00),
    2,
    107,
    "Tuition",
    5180
)

private val tsb2 = TuitionScheduleBracket(
    105,
    "CREDITS",
    1.00,
    10.00,
    usd(0.00),
    usd(300.00),
    usd(0.00),
    1,
    107,
    "Tuition",
    5180
)

private val tsb3 = TuitionScheduleBracket(
    16,
    "CREDITS",
    11.00,
    15.00,
    usd(3500.00),
    usd(0.00),
    usd(0.00),
    0,
    107,
    "Tuition",
    5180
)

private val ts1 = TuitionSchedule(
    107,
    "Auditor",
    "1.00-12.00 credits: $350.00, 0.00 per unit",
    mutableListOf(tsb1)
)

private val ts2 = TuitionSchedule(
    2,
    "Graduate",
    "1.00-10.00 credits: $0.00, 300.00 per unit&lt;br/&gt; 11.00-15.00 credits: $3500.00, 0.00 per unit",
    mutableListOf(tsb2, tsb3)
)

object TuitionScheduleSpec : Spek({

    val a = MonetaryAmountAdapter()

    describe("MonetaryAmountAdapter") {
        it("marshals 350.00 to xml value") {
            assertEquals("350.00", a.marshal(Money.of(350.00, "USD")))
        }
    }

    describe("TuitionSchedule") {
        it("unmarshal from xml value") {
            assertEquals(Money.of(350.00, "USD"), a.unmarshal("350.00"))
        }
    }

    describe("TuitionScheduleBracket") {
        val xml = """
            <tuition_schedule_bracket>
                <account_id>107</account_id>
                <account_name>Tuition</account_name>
                <account_number>5180</account_number>
                <flat_amount>350.00</flat_amount>
                <id>22</id>
                <in_use>2</in_use>
                <max_units>12.0</max_units>
                <min_units>1.0</min_units>
                <per_unit_amount>0.00</per_unit_amount>
                <per_unit_threshold>0.00</per_unit_threshold>
                <units>CREDITS</units>
            </tuition_schedule_bracket>
            """

        it("marshal to xml") {
            val sw = StringWriter()
            JAXB.marshal(tsb1, sw)
            assertEquals(XML_HEADER + xml.trimIndent().trim(), sw.toString().trim())
        }

        it("unmarshal from xml") {
            assertEquals(tsb1, JAXB.unmarshal(xml.reader(), TuitionScheduleBracket::class.java))
        }
    }

    describe("TuitionSchedule") {
        it("marshal to xml") {
            val xml = """
            <response>
                <tuition_schedule>
                    <id>107</id>
                    <name>Auditor</name>
                    <detail>1.00-12.00 credits: ${'$'}350.00, 0.00 per unit</detail>
                    <tuition_schedule_brackets>
                        <tuition_schedule_bracket>
                            <account_id>107</account_id>
                            <account_name>Tuition</account_name>
                            <account_number>5180</account_number>
                            <flat_amount>350.00</flat_amount>
                            <id>22</id>
                            <in_use>2</in_use>
                            <max_units>12.0</max_units>
                            <min_units>1.0</min_units>
                            <per_unit_amount>0.00</per_unit_amount>
                            <per_unit_threshold>0.00</per_unit_threshold>
                            <units>CREDITS</units>
                        </tuition_schedule_bracket>
                    </tuition_schedule_brackets>
                </tuition_schedule>
                <tuition_schedule>
                    <id>2</id>
                    <name>Graduate</name>
                    <detail>1.00-10.00 credits: ${'$'}0.00, 300.00 per unit&amp;lt;br/&amp;gt; 11.00-15.00 credits: ${'$'}3500.00, 0.00 per unit</detail>
                    <tuition_schedule_brackets>
                        <tuition_schedule_bracket>
                            <account_id>107</account_id>
                            <account_name>Tuition</account_name>
                            <account_number>5180</account_number>
                            <flat_amount>0.00</flat_amount>
                            <id>105</id>
                            <in_use>1</in_use>
                            <max_units>10.0</max_units>
                            <min_units>1.0</min_units>
                            <per_unit_amount>300.00</per_unit_amount>
                            <per_unit_threshold>0.00</per_unit_threshold>
                            <units>CREDITS</units>
                        </tuition_schedule_bracket>
                        <tuition_schedule_bracket>
                            <account_id>107</account_id>
                            <account_name>Tuition</account_name>
                            <account_number>5180</account_number>
                            <flat_amount>3,500.00</flat_amount>
                            <id>16</id>
                            <in_use>0</in_use>
                            <max_units>15.0</max_units>
                            <min_units>11.0</min_units>
                            <per_unit_amount>0.00</per_unit_amount>
                            <per_unit_threshold>0.00</per_unit_threshold>
                            <units>CREDITS</units>
                        </tuition_schedule_bracket>
                    </tuition_schedule_brackets>
                </tuition_schedule>
            </response>
            """.trimIndent()
            val sw = StringWriter()
            val r = TuitionScheduleResponse(mutableListOf(ts1, ts2))
            JAXB.marshal(r, sw)
            assertEquals(XML_HEADER + xml.trim(), sw.toString().trim())
        }

        it("unmarshal from xml") {
            val r = JAXB.unmarshal(getTuitionSchedulesXml.reader(), TuitionScheduleResponse::class.java)
            assertTuitionSchedules(r.tuition_schedule)
        }
    }
})

const val getTuitionSchedulesXml = """
<response>
    <tuition_schedule>
        <id>107</id>
        <name>Auditor</name>
        <detail>1.00-12.00 credits: ${'$'}350.00, 0.00 per unit</detail>
        <tuition_schedule_brackets>
            <tuition_schedule_bracket>
                <id>22</id>
                <units>CREDITS</units>
                <min_units>1.00</min_units>
                <max_units>12.00</max_units>
                <flat_amount>350.00</flat_amount>
                <per_unit_amount>0.00</per_unit_amount>
                <per_unit_threshold>0.00</per_unit_threshold>
                <in_use>2</in_use>
                <account_id>107</account_id>
                <account_name>Tuition</account_name>
                <account_number>5180</account_number>
            </tuition_schedule_bracket>
        </tuition_schedule_brackets>
    </tuition_schedule>
    <tuition_schedule>
        <id>2</id>
        <name>Graduate</name>
        <detail>1.00-10.00 credits: ${'$'}0.00, 300.00 per unit&lt;br/&gt; 11.00-15.00 credits: ${'$'}3500.00, 0.00 per unit</detail>
        <tuition_schedule_brackets>
            <tuition_schedule_bracket>
                <id>105</id>
                <units>CREDITS</units>
                <min_units>1.00</min_units>
                <max_units>10.00</max_units>
                <flat_amount>0.00</flat_amount>
                <per_unit_amount>300.00</per_unit_amount>
                <per_unit_threshold>0.00</per_unit_threshold>
                <in_use>1</in_use>
                <account_id>107</account_id>
                <account_name>Tuition</account_name>
                <account_number>5180</account_number>
            </tuition_schedule_bracket>
            <tuition_schedule_bracket>
                <id>16</id>
                <units>CREDITS</units>
                <min_units>11.00</min_units>
                <max_units>15.00</max_units>
                <flat_amount>3500.00</flat_amount>
                <per_unit_amount>0.00</per_unit_amount>
                <per_unit_threshold>0.00</per_unit_threshold>
                <in_use>0</in_use>
                <account_id>107</account_id>
                <account_name>Tuition</account_name>
                <account_number>5180</account_number>
            </tuition_schedule_bracket>
        </tuition_schedule_brackets>
    </tuition_schedule>
</response>
"""

fun assertTuitionSchedules(tuitionSchedules: MutableList<TuitionSchedule>) {
    tuitionSchedules.forEach { it.detail = it.detail.replace("<br/>", "&lt;br/&gt;") } // yes it's a dirty hack.
    assertEquals(setOf(ts1, ts2), tuitionSchedules.toSet())
}
