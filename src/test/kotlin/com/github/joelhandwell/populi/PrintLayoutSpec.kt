package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDateTime

val printLayouts = mutableListOf(
    PrintLayout(
        id = "1598",
        name = "Custom Transcript V.2",
        type = "TRANSCRIPT",
        added_at = LocalDateTime.of(2018, 1, 22, 19, 20, 24)
    ),
    PrintLayout(
        id = "DS1",
        name = "Annual Donation Summary - Basic",
        type = "DONATION_SUMMARY",
        added_at = LocalDateTime.of(2017, 5, 10, 13, 22, 14)

    )
)

private val response = PrintLayoutResponse(printLayouts)

object PrintLayoutSpec : Spek({

    describe("PrintLayout") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getPrintLayoutsXml) }
    }
})

const val getPrintLayoutsXml = """
<response>
    <print_layout>
        <id>1598</id>
        <name>Custom Transcript V.2</name>
        <type>TRANSCRIPT</type>
        <added_at>2018-01-22 19:20:24</added_at>
    </print_layout>
    <print_layout>
        <id>DS1</id>
        <name>Annual Donation Summary - Basic</name>
        <type>DONATION_SUMMARY</type>
        <added_at>2017-05-10 13:22:14</added_at>
    </print_layout>
</response>"""
