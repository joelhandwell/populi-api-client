package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val personRoles = mutableListOf(
    PersonRole(2, "Admissions", 0),
    PersonRole(18, "Bookstore", 1)
)

private val response = PersonRoleResponse(personRoles)

object PersonRoleSpec : Spek({

    describe("PersonRole") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getRolesXml) }
    }
})

const val getRolesXml = """
<response>
    <role>
        <id>2</id>
        <name>Admissions</name>
        <inactive>0</inactive>
    </role>
    <role>
        <id>18</id>
        <name>Bookstore</name>
        <inactive>1</inactive>
    </role>
</response>"""
