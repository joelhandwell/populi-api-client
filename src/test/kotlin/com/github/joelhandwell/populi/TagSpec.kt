package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val tags = mutableListOf(Tag(1, "Alumni", 1), Tag(2, "Local Student", 0))

private val response = TagResponse(tags)

object TagSpec : Spek({

    describe("Tag") {

        it("marshal to xml") { assertMarshals(getTagsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getTagsXml) }
    }
})

const val getTagsXml = """
<response>
    <tags>
        <tag>
            <id>1</id>
            <name>Alumni</name>
            <system>1</system>
        </tag>
        <tag>
            <id>2</id>
            <name>Local Student</name>
            <system>0</system>
        </tag>
    </tags>
</response>"""
