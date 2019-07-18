package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.LocalDateTime

private val p1 = UpdatedPersonInfo(
    id = 4444444,
    first_name = "Doug",
    last_name = "McFaculty",
    preferred_name = "",
    middle_name = "",
    prefix = "Mr.",
    suffix = "",
    former_name = "",
    maiden_name = "",
    gender = "MALE",
    birth_date = LocalDate.parse("1979-10-02"),
    status = PersonStatus.ACTIVE,
    is_active_user = 1,
    user_name = "dougf",
    updated_at = LocalDateTime.of(2011, 5, 9, 13, 42, 0)
)

private val p2 = UpdatedPersonInfo(
    id = 88888,
    first_name = "James",
    last_name = "McProspect",
    preferred_name = "Jim",
    middle_name = "",
    prefix = "",
    suffix = "III",
    former_name = "",
    maiden_name = "",
    gender = "MALE",
    birth_date = LocalDate.parse("1988-06-04"),
    status = PersonStatus.ACTIVE,
    is_active_user = 0,
    user_name = "",
    updated_at = LocalDateTime.of(2011, 4, 9, 13, 42, 0)
)

val updatedPeopleResponse = UpdatedPersonResponse(356, mutableListOf(p1, p2))

object UpdatedPeopleSpec : Spek({

    describe("UpdatedPerson") {

        it("marshal to xml") { assertMarshals(updatedPeopleResponse) }

        it("unmarshal from xml") { assertUnmarshals(updatedPeopleResponse, getUpdatedPeopleXml) }
    }
})

const val getUpdatedPeopleXml = """
<response num_results="356">
	<person>
		<id>4444444</id>
		<first_name>Doug</first_name>
		<last_name>McFaculty</last_name>
		<preferred_name/>
		<middle_name/>
		<prefix>Mr.</prefix>
		<suffix/>
		<former_name/>
		<maiden_name/>
		<gender>MALE</gender>
		<birth_date>1979-10-02</birth_date>
		<status>ACTIVE</status>
		<is_active_user>1</is_active_user>
		<user_name>dougf</user_name>
		<updated_at>2011-05-09 13:42:00</updated_at>
	</person>
	<person>
		<id>88888</id>
		<first_name>James</first_name>
		<last_name>McProspect</last_name>
		<preferred_name>Jim</preferred_name>
		<middle_name/>
		<prefix/>
		<suffix>III</suffix>
		<former_name/>
		<maiden_name/>
		<gender>MALE</gender>
		<birth_date>1988-06-04</birth_date>
		<status>ACTIVE</status>
		<is_active_user>0</is_active_user>
		<user_name/>
		<updated_at>2011-04-09 13:42:00</updated_at>
	</person>
</response>"""
