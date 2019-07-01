package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val roleMemberResponse = RoleMemberResponse(
    num_results = 10549,
    person = mutableListOf(
        RolePerson(
            personID = 3093,
            first = "James",
            last = "McMcintosh",
            inactive = 0,
            username = "jamesm17"
        )
    )
)

object RoleMemberSpec : Spek({

    describe("RoleMember") {

        it("marshal to xml") { assertMarshals(roleMemberResponse) }

        it("unmarshal from xml") { assertUnmarshals(roleMemberResponse, getRoleMembersXml) }
    }
})

const val getRoleMembersXml = """
<response num_results="10549">
    <person>
        <personID>3093</personID>
        <first>James</first>
        <last>McMcintosh</last>
        <inactive>0</inactive>
        <username>jamesm17</username>
    </person>
</response>"""
