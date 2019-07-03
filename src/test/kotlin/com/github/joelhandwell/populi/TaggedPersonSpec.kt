package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val taggedPersonResponse = PersonResponse(
    num_results = 2438,
    person = mutableListOf(
        Person(person_id = 3093, first = "James", last = "McMcintosh"),
        Person(person_id = 54987, first = "Robert", last = "Jones")
    )
)

object TaggedPersonSpec : Spek({

    describe("TaggedPerson") {

        it("marshal to xml") { assertMarshals(taggedPersonResponse) }

        it("unmarshal from xml") { assertUnmarshals(taggedPersonResponse, getTaggedPeopleXml) }
    }
})

const val getTaggedPeopleXml = """
<response num_results="2438">
    <person>
        <person_id>3093</person_id>
        <first_name>James</first_name>
        <last_name>McMcintosh</last_name>
    </person>
    <person>
        <person_id>54987</person_id>
        <first_name>Robert</first_name>
        <last_name>Jones</last_name>
    </person>
</response>"""
