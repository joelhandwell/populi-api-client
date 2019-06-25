package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import javax.xml.bind.JAXB

private val address1 = Address(
    type = "HOME",
    addressid = 1111,
    street = "P.O. Box 1994",
    city = "Moscow",
    state = "AR",
    zip = "88887",
    is_primary = 1
)

private val address2 = Address(
    type = "WORK",
    addressid = 22222,
    street = "1000 1st Av",
    city = "Moscow",
    state = "",
    zip = "123456",
    country = "RU",
    is_primary = 0
)

private val phone1 = Phone(
    phoneid = 44444,
    type = "MOBILE",
    number = "111-222-3333",
    is_primary = 1
)

private val phone2 = Phone(
    phoneid = 55555,
    type = "HOME",
    number = "222-222-2222",
    is_primary = 0
)

private val email1 = Email(
    emailid = 55556,
    type = "WORK",
    address = "james@populi.co",
    is_primary = 1
)

private val email2 = Email(
    emailid = 7777,
    type = "HOME",
    address = "mypersonaladdress@gmail.com",
    is_primary = 0
)

val personInfo = PersonInfo(
    first = "James",
    last = "McMcintosh",
    middle_name = "Rodrick",
    preferred_name = "Jimmy",
    prefix = "Mr.",
    suffix = "III",
    former_name = "",
    gender = "MALE",
    birth_date = LocalDate.parse("1990-01-01"),

    //ignore this as citizenship is conflicting name
    //citizenship_single = "USA",

    citizenship = mutableListOf(Citizenship(1341, "USA"), Citizenship(219, "CA")),
    resident_alien = 0,
    home_city = "Moscow",
    home_state = "ID",
    home_country = "USA",
    license_plate = "Z 29765",
    is_active_user = 1,
    race = mutableListOf(Race(7, "Asian")),
    hispanic_latino = "No",
    image = "_BASE_64_ENCODED_BINARY_IMAGE_DATA_RETURNED_HERE_",
    address = mutableListOf(address1, address2),
    phone = mutableListOf(phone1, phone2),
    email = mutableListOf(email1, email2),
    tag = mutableListOf(Tag(1, "Alumni", 1), Tag(2, "Local Student", 0))
)

object PersonSpec : Spek({

    describe("Phone") {
        val xml = """
        <phone>
            <phoneid>44444</phoneid>
            <type>MOBILE</type>
            <number>111-222-3333</number>
            <is_primary>1</is_primary>
        </phone>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(phone1, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(phone1, xml) }
    }

    describe("Email") {
        val xml = """
        <email>
            <emailid>55556</emailid>
            <type>WORK</type>
            <address>james@populi.co</address>
            <is_primary>1</is_primary>
        </email>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(email1, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(email1, xml) }
    }

    describe("Address primary") {
        val xml = """
        <address>
            <type>HOME</type>
            <addressid>1111</addressid>
            <street>P.O. Box 1994</street>
            <city>Moscow</city>
            <state>AR</state>
            <zip>88887</zip>
            <is_primary>1</is_primary>
        </address>
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(address1, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(address1, xml) }
    }

    describe("Address not primary") {
        val xml = """
        <address>
            <type>WORK</type>
            <addressid>22222</addressid>
            <street>1000 1st Av</street>
            <city>Moscow</city>
            <state></state>
            <zip>123456</zip>
            <country>RU</country>
            <is_primary>0</is_primary>
        </address>
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(address2, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(address2, xml) }
    }

    describe("PersonInfo") {

        it("marshal to xml") { JAXB.marshal(personInfo, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(personInfo, getPersonXml) }
    }
})

const val getPersonXml = """
<response>
	<first>James</first>
	<last>McMcintosh</last>
	<middle_name>Rodrick</middle_name>
	<preferred_name>Jimmy</preferred_name>
	<prefix>Mr.</prefix>
	<suffix>III</suffix>
	<former_name/>
	<gender>MALE</gender>
	<birth_date>1990-01-01</birth_date>
	<citizenship>USA</citizenship>
	<citizenships>
		<citizenship>
			<id>1341</id>
			<abbr>USA</abbr>
		</citizenship>
		<citizenship>
			<id>219</id>
			<abbr>CA</abbr>
		</citizenship>
	</citizenships>
	<resident_alien>0</resident_alien>
	<home_city>Moscow</home_city>
	<home_state>ID</home_state>
	<home_country>USA</home_country>
	<license_plate>Z 29765</license_plate>
	<is_active_user>1</is_active_user>
	<races>
		<race>
			<id>7</id>
			<name>Asian</name>
		</race>
	</races>
	<hispanic_latino>No</hispanic_latino>
	<image>_BASE_64_ENCODED_BINARY_IMAGE_DATA_RETURNED_HERE_</image>
	<address>
		<type>HOME</type>
		<addressid>1111</addressid>
		<street>P.O. Box 1994</street>
		<city>Moscow</city>
		<state>AR</state>
		<zip>88887</zip>
		<is_primary>1</is_primary>
	</address>
	<address>
		<type>WORK</type>
		<addressid>22222</addressid>
		<street>1000 1st Av</street>
		<city>Moscow</city>
		<state></state>
		<zip>123456</zip>
		<country>RU</country>
		<is_primary>0</is_primary>
	</address>
	<phone>
		<phoneid>44444</phoneid>
		<type>MOBILE</type>
		<number>111-222-3333</number>
		<is_primary>1</is_primary>
	</phone>
	<phone>
		<phoneid>55555</phoneid>
		<type>HOME</type>
		<number>222-222-2222</number>
		<is_primary>0</is_primary>
	</phone>
	<email>
		<emailid>55556</emailid>
		<type>WORK</type>
		<address>james@populi.co</address>
		<is_primary>1</is_primary>
	</email>
	<email>
		<emailid>7777</emailid>
		<type>HOME</type>
		<address>mypersonaladdress@gmail.com</address>
		<is_primary>0</is_primary>
	</email>
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
