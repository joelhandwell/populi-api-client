package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val roles = mutableListOf(
    Role(1234, "Academic Admin"),
    Role(12345, "Admissions"),
    Role(123456, "Advisor")
)

private val response = RoleResponse(roles)

object RoleSpec : Spek({

    describe("Role") {

        it("marshal to xml") { assertMarshals(getAvailableRolesXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getAvailableRolesXml) }
    }
})

const val getAvailableRolesXml = """
<response>
    <role>
        <id>1234</id>
        <name>Academic Admin</name>
    </role>
    <role>
        <id>12345</id>
        <name>Admissions</name>
    </role>
    <role>
        <id>123456</id>
        <name>Advisor</name>
    </role>
</response>"""
