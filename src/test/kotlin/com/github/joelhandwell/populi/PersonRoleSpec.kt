package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val roles = mutableListOf(
    Role(2, "Admissions", 0),
    Role(18, "Bookstore", 1)
)

private val response = RoleResponse(roles)

object PersonRoleSpec : Spek({

    describe("Role") {

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
