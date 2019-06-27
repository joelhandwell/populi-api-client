package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.xml.bind.JAXB

private val text = ApplicationText(
    content = "Tell us about yourself.",
    order_id = 2,
    file = mutableListOf(ApplicationFile(9355, "terms.pdf"))
)

private val address = ApplicationAnswer(
    street = "123 Any St",
    city = "Anytown",
    state = "ID",
    zip = "12345",
    country_abbreviation = "USA",
    country_name = "United States of America"
)

private val submitted_at = LocalDateTime.of(2014, 2, 10, 15, 46, 35)

private val decision_at = LocalDateTime.of(2014, 2, 10, 16, 10, 45)

private val addressField = ApplicationField(
    id = 19,
    name = "Home Address",
    description = "",
    is_required = 1,
    data_type = "ADDRESS",
    data_format = "",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 3,
    answer = address
)

private val ssnField = ApplicationField(
    id = 20,
    name = "SSN",
    description = "Please enter your social security number.",
    is_required = 0,
    data_type = "NUMBER",
    data_format = "SOCIAL_SECURITY_NUMBER",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 4,
    answer = ApplicationAnswer(ssn = "111-11-1111")
)

private val optionsField = ApplicationField(
    id = 21,
    name = "Hobbies and Interests",
    description = "Choose any two of the following options.",
    is_required = 1,
    data_type = "MULTIPLE_ANSWER",
    data_format = "",
    max_multiple_answers = 5,
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 5,
    answer = ApplicationAnswer(
        option = mutableListOf(
            ApplicationFieldOption(527, "Football"),
            ApplicationFieldOption(528, "Baseball")
        )
    )
)

private val racesField = ApplicationField(
    id = 22,
    name = "Race and Ethnicity",
    description = "",
    is_required = 0,
    data_type = "CHOICE",
    data_format = "RACE_ETHNICITY",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 6,
    answer = ApplicationAnswer(
        hispanic_latino = 0,
        race = mutableListOf(Race(1, "American Indian or Alaska Native"))
    )
)

private val elField = ApplicationField(
    id = 23,
    name = "Education Level",
    description = "",
    is_required = 0,
    data_type = "CHOICE",
    data_format = "EDUCATION_LEVEL",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 7,
    answer = ApplicationAnswer(
        education_level_id = 3333,
        education_level_name = "High School Diploma"
    )
)

private val fileField = ApplicationField(
    id = 24,
    name = "Upload your admissions essay",
    description = "",
    is_required = 0,
    data_type = "FILE",
    data_format = "",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 8,
    answer = ApplicationAnswer(
        file_id = 456987456,
        file_name = "admissions_essay.pdf"
    )
)

private val satField = ApplicationField(
    id = 25,
    name = "SAT score",
    description = "",
    is_required = 0,
    data_type = "STANDARDIZED_TEST",
    data_format = "",
    status = "IN_PROGRESS",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 9,
    answer = ApplicationAnswer(
        test_id = 1,
        test_name = "SAT",
        test_date = LocalDate.parse("2014-01-07"),
        test_score = 1400,
        test_section = mutableListOf(
            SATTestSection(1, "Math", 680),
            SATTestSection(2, "Evidence-Based Reading and Writing", 700)
        )
    )
)

private val citizenshipField = ApplicationField(
    id = 26,
    name = "Citizenship",
    description = "",
    is_required = 0,
    data_type = "CHOICE",
    data_format = "COUNTRY",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 10,
    answer = ApplicationAnswer(
        abbreviation = "USA",
        name = "United States of America"
    )
)

private val stateField = ApplicationField(
    id = 27,
    name = "State/Province",
    description = "",
    is_required = 0,
    data_type = "CHOICE",
    data_format = "STATE_PROVINCE",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 11,
    answer = ApplicationAnswer(
        state = "AL",
        country_abbreviation = "USA",
        country_name = "United States of America"
    )
)

private val hsgpaField = ApplicationField(
    id = 28,
    name = "High School GPA",
    description = "",
    is_required = 0,
    data_type = "CHOICE",
    data_format = "",
    status = "IN_PROGRESS",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 12,
    answer = ApplicationAnswer(
        option_id = 8976541,
        option_name = "3.0 - 3.5"
    )
)

private val degreeField = ApplicationField(
    id = 29,
    name = "Degree",
    description = "",
    is_required = 1,
    data_type = "CHOICE",
    data_format = "DEGREE",
    status = "SUBMITTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 13,
    answer = ApplicationAnswer(
        degree_id = 444,
        degree_abbreviation = "B.A.",
        degree_name = "Bachelor of Arts"
    )
)

private val specializationField = ApplicationField(
    id = 30,
    name = "Specialization",
    description = "",
    is_required = 1,
    data_type = "CHOICE",
    data_format = "SPECIALIZATION",
    status = "SUBMITTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 14,
    answer = ApplicationAnswer(
        specialization_id = 5555,
        specialization_abbreviation = "ENG",
        specialization_name = "English"
    )
)

private val onlineReferenceField = ApplicationField(
    id = 31,
    name = "Reference",
    description = "",
    is_required = 1,
    data_type = "ONLINE_REFERENCE",
    data_format = "REFERENCE",
    status = "ACCEPTED",
    submitted_at = submitted_at,
    decision_at = decision_at,
    order_id = 15,
    answer = ApplicationAnswer(
        online_reference_status = "RECEIVED",
        online_reference_id = 549873541,
        online_reference_email = "reference@email.org",
        online_reference_message = "Personal note to referrer."
    )
)

private val note = ApplicationNote(
    id = 5579,
    content = "The student is especially interested in classes from Dr. Thompson.",
    public = 0,
    added_by = 338590,
    added_at = LocalDateTime.of(2014, 1, 15, 14, 42, 25),
    file_id = 774481,
    file_name = "note.pdf"
)

private val sections = ApplicationSections(
    heading = ApplicationHeading(content = "Personal Information", order_id = 1),
    text = text,
    field = mutableListOf(
        addressField,
        ssnField,
        optionsField,
        racesField,
        elField,
        fileField,
        satField,
        citizenshipField,
        stateField,
        hsgpaField,
        degreeField,
        specializationField,
        onlineReferenceField
    )
)

val applicationDetail = Application(
    id = 54321,
    lead_id = 3214,
    person_id = 1234,
    first_name = "Carl",
    preferred_name = "",
    middle_name = "Allen",
    last_name = "Michaelson",
    gender = "MALE",
    email = "carl@email.org",
    application_template_id = 112,
    application_template_name = "Basic",
    representative_id = 2068,
    program_id = 333,
    program_name = "Undergraduate",
    degree_seeking = 1,
    degree_id = 444,
    degree_abbreviation = "B.A.",
    degree_name = "Bachelor of Arts",
    specialization_id = 5555,
    specialization_abbreviation = "ENG",
    specialization_name = "English",
    academic_term_id = 7769,
    academic_term_name = "2014-2015: Fall",
    expected_enrollment = "FULL_TIME",
    full_time = 1,
    started_on = LocalDate.parse("2014-02-07"),
    submitted_at = null,
    decision_on = null,
    withdrawn_on = null,
    submitted_type = "ONLINE",
    provisional = 0,
    provisional_comment = "",
    fee_status = "PAID",
    fee_id = 8467,
    fee_amount = usd(25.00),
    fee_payment = "BEFORE_START",
    sales_receipt_id = 12365789,
    transaction_id = 549873215,
    applicant_activity_at = LocalDateTime.of(2014, 2, 10, 15, 46, 35),
    num_days_since_last_activity = 3,
    staff_activity_at = LocalDateTime.of(2014, 2, 10, 16, 10, 45),
    percent_completed = 95,
    status = "IN_PROGRESS",
    sections = sections,
    note = mutableListOf(note)
)

private val response = ApplicationDetailResponse(applicationDetail)

object ApplicationDetailSpec : Spek({

    describe("ApplicationText") {
        val xml = """
        <text>
            <content>Tell us about yourself.</content>
            <order_id>2</order_id>
            <files>
                <file>
                    <file_id>9355</file_id>
                    <name>terms.pdf</name>
                </file>
            </files>
        </text>
        """.trimIndent()

        it("marshal to xml") { assertMarshals(text) }

        it("unmarshal from xml") { assertUnmarshals(text, xml) }
    }

    val addressAnswerXml = """
        <answer>
            <city>Anytown</city>
            <country_abbreviation>USA</country_abbreviation>
            <country_name>United States of America</country_name>
            <state>ID</state>
            <street>123 Any St</street>
            <zip>12345</zip>
        </answer>
        """.trimIndent()

    describe("AddressAnswer") {

        it("marshal to xml") { assertMarshals(address) }

        it("unmarshal from xml") { assertUnmarshals(address, addressAnswerXml) }
    }

    describe("Field with address") {
        val xml = """
        <field>
            <id>19</id>
            <name>Home Address</name>
            <description/>
            <is_required>1</is_required>
            <data_type>ADDRESS</data_type>
            <data_format/>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>3</order_id>
            <answer>
                <street>123 Any St</street>
                <city>Anytown</city>
                <state>ID</state>
                <zip>12345</zip>
                <country_abbreviation>USA</country_abbreviation>
                <country_name>United States of America</country_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(addressField) }

        it("unmarshal from xml") {
            assertUnmarshals(addressField, xml)
        }
    }

    describe("Field with SSN") {
        val xml = """
        <field>
            <id>20</id>
            <name>SSN</name>
            <description>Please enter your social security number.</description>
            <is_required>0</is_required>
            <data_type>NUMBER</data_type>
            <data_format>SOCIAL_SECURITY_NUMBER</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>4</order_id>
            <answer>111-11-1111</answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(ssnField) }

        it("unmarshal from xml") { assertUnmarshals(ssnField, xml) }
    }

    describe("Field with options") {
        val xml = """
        <field>
            <id>21</id>
            <name>Hobbies and Interests</name>
            <description>Choose any two of the following options.</description>
            <is_required>1</is_required>
            <data_type>MULTIPLE_ANSWER</data_type>
            <data_format/>
            <max_multiple_answers>5</max_multiple_answers>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>5</order_id>
            <answer>
                <options>
                    <option>
                        <id>527</id>
                        <name>Football</name>
                    </option>
                    <option>
                        <id>528</id>
                        <name>Baseball</name>
                    </option>
                </options>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { JAXB.marshal(optionsField, StringWriter()) }

        it("unmarshal from xml") { assertUnmarshals(optionsField, xml) }
    }

    describe("Field with races") {
        val xml = """
        <field>
            <id>22</id>
            <name>Race and Ethnicity</name>
            <description/>
            <is_required>0</is_required>
            <data_type>CHOICE</data_type>
            <data_format>RACE_ETHNICITY</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>6</order_id>
            <answer>
                <hispanic_latino>0</hispanic_latino>
                <races>
                    <race>
                        <id>1</id>
                        <name>American Indian or Alaska Native</name>
                    </race>
                </races>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(racesField) }

        it("unmarshal from xml") { assertUnmarshals(racesField, xml) }
    }

    describe("Field with education level") {
        val xml = """
        <field>
            <id>23</id>
            <name>Education Level</name>
            <description/>
            <is_required>0</is_required>
            <data_type>CHOICE</data_type>
            <data_format>EDUCATION_LEVEL</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>7</order_id>
            <answer>
                <education_level_id>3333</education_level_id>
                <education_level_name>High School Diploma</education_level_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(elField) }

        it("unmarshal from xml") { assertUnmarshals(elField, xml) }
    }

    describe("Field with file") {
        val xml = """
        <field>
            <id>24</id>
            <name>Upload your admissions essay</name>
            <description/>
            <is_required>0</is_required>
            <data_type>FILE</data_type>
            <data_format/>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>8</order_id>
            <answer>
                <file_id>456987456</file_id>
                <file_name>admissions_essay.pdf</file_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(fileField) }

        it("unmarshal from xml") { assertUnmarshals(fileField, xml) }
    }

    describe("Field with SAT") {
        val xml = """
        <field>
            <id>25</id>
            <name>SAT score</name>
            <description/>
            <is_required>0</is_required>
            <data_type>STANDARDIZED_TEST</data_type>
            <data_format/>
            <status>IN_PROGRESS</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>9</order_id>
            <answer>
                <test_id>1</test_id>
                <test_name>SAT</test_name>
                <test_date>2014-01-07</test_date>
                <test_score>1400</test_score>
                <test_sections>
                    <test_section>
                        <id>1</id>
                        <name>Math</name>
                        <score>680</score>
                    </test_section>
                    <test_section>
                        <id>2</id>
                        <name>Evidence-Based Reading and Writing</name>
                        <score>700</score>
                    </test_section>
                </test_sections>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(satField) }

        it("unmarshal from xml") { assertUnmarshals(satField, xml) }
    }

    describe("Field with citizenship") {
        val xml = """
        <field>
            <id>26</id>
            <name>Citizenship</name>
            <description/>
            <is_required>0</is_required>
            <data_type>CHOICE</data_type>
            <data_format>COUNTRY</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>10</order_id>
            <answer>
                <abbreviation>USA</abbreviation>
                <name>United States of America</name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(citizenshipField) }

        it("unmarshal from xml") { assertUnmarshals(citizenshipField, xml) }
    }

    describe("Field with state") {
        val xml = """
        <field>
            <id>27</id>
            <name>State/Province</name>
            <description/>
            <is_required>0</is_required>
            <data_type>CHOICE</data_type>
            <data_format>STATE_PROVINCE</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>11</order_id>
            <answer>
                <state>AL</state>
                <country_abbreviation>USA</country_abbreviation>
                <country_name>United States of America</country_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(stateField) }

        it("unmarshal from xml") { assertUnmarshals(stateField, xml) }
    }

    describe("Field with hight school gpa") {
        val xml = """
        <field>
            <id>28</id>
            <name>High School GPA</name>
            <description/>
            <is_required>0</is_required>
            <data_type>CHOICE</data_type>
            <data_format/>
            <status>IN_PROGRESS</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>12</order_id>
            <answer>
                <option_id>8976541</option_id>
                <option_name>3.0 - 3.5</option_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(hsgpaField) }

        it("unmarshal from xml") { assertUnmarshals(hsgpaField, xml) }
    }

    describe("Field with degree") {
        val xml = """
        <field>
            <id>29</id>
            <name>Degree</name>
            <description/>
            <is_required>1</is_required>
            <data_type>CHOICE</data_type>
            <data_format>DEGREE</data_format>
            <status>SUBMITTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>13</order_id>
            <answer>
                <degree_id>444</degree_id>
                <degree_abbreviation>B.A.</degree_abbreviation>
                <degree_name>Bachelor of Arts</degree_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(degreeField) }

        it("unmarshal from xml") { assertUnmarshals(degreeField, xml) }
    }

    describe("Field with specialization") {
        val xml = """
        <field>
            <id>30</id>
            <name>Specialization</name>
            <description/>
            <is_required>1</is_required>
            <data_type>CHOICE</data_type>
            <data_format>SPECIALIZATION</data_format>
            <status>SUBMITTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>14</order_id>
            <answer>
                <specialization_id>5555</specialization_id>
                <specialization_abbreviation>ENG</specialization_abbreviation>
                <specialization_name>English</specialization_name>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(specializationField) }

        it("unmarshal from xml") { assertUnmarshals(specializationField, xml) }
    }

    describe("Field with online reference") {
        val xml = """
        <field>
            <id>31</id>
            <name>Reference</name>
            <description/>
            <is_required>1</is_required>
            <data_type>ONLINE_REFERENCE</data_type>
            <data_format>REFERENCE</data_format>
            <status>ACCEPTED</status>
            <submitted_at>2014-02-10 15:46:35</submitted_at>
            <decision_at>2014-02-10 16:10:45</decision_at>
            <order_id>15</order_id>
            <answer>
                <online_reference_status>RECEIVED</online_reference_status>
                <online_reference_id>549873541</online_reference_id>
                <online_reference_email>reference@email.org</online_reference_email>
                <online_reference_message>Personal note to referrer.</online_reference_message>
            </answer>
        </field>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(onlineReferenceField) }

        it("unmarshal from xml") { assertUnmarshals(onlineReferenceField, xml) }
    }

    describe("ApplicationNote") {
        val xml = """
        <note>
            <id>5579</id>
            <content>The student is especially interested in classes from Dr. Thompson.</content>
            <public>0</public>
            <added_by>338590</added_by>
            <added_at>2014-01-15 14:42:25</added_at>
            <file_id>774481</file_id>
            <file_name>note.pdf</file_name>
        </note>            
        """.trimIndent()

        it("marshal to xml") { assertMarshals(note) }

        it("unmarshal from xml") { assertUnmarshals(note, xml) }
    }

    describe("ApplicationDetailResponse") {

        it("marshal to xml") { assertMarshals(response) }

        it("unmarshal from xml") { assertUnmarshals(response, getApplicationXml) }
    }
})

const val getApplicationXml = """
<response>
    <application>
        <id>54321</id>
        <lead_id>3214</lead_id>
        <person_id>1234</person_id>
        <first_name>Carl</first_name>
        <preferred_name></preferred_name>
        <middle_name>Allen</middle_name>
        <last_name>Michaelson</last_name>
        <gender>MALE</gender>
        <email>carl@email.org</email>
        <application_template_id>112</application_template_id>
        <application_template_name>Basic</application_template_name>
        <representative_id>2068</representative_id>
        <program_id>333</program_id>
        <program_name>Undergraduate</program_name>
        <degree_seeking>1</degree_seeking>
        <degree_id>444</degree_id>
        <degree_abbreviation>B.A.</degree_abbreviation>
        <degree_name>Bachelor of Arts</degree_name>
        <specialization_id>5555</specialization_id>
        <specialization_abbreviation>ENG</specialization_abbreviation>
        <specialization_name>English</specialization_name>
        <academic_term_id>7769</academic_term_id>
        <academic_term_name>2014-2015: Fall</academic_term_name>
        <expected_enrollment>FULL_TIME</expected_enrollment>
        <full_time>1</full_time>
        <started_on>2014-02-07</started_on>
        <submitted_at></submitted_at>
        <decision_on></decision_on>
        <withdrawn_on></withdrawn_on>
        <submitted_type>ONLINE</submitted_type>
        <provisional>0</provisional>
        <provisional_comment></provisional_comment>
        <fee_status>PAID</fee_status>
        <fee_id>8467</fee_id>
        <fee_amount>25.00</fee_amount>
        <fee_payment>BEFORE_START</fee_payment>
        <sales_receipt_id>12365789</sales_receipt_id>
        <transaction_id>549873215</transaction_id>
        <applicant_activity_at>2014-02-10 15:46:35</applicant_activity_at>
        <num_days_since_last_activity>3</num_days_since_last_activity>
        <staff_activity_at>2014-02-10 16:10:45</staff_activity_at>
        <percent_completed>95</percent_completed>
        <status>IN_PROGRESS</status>
        <sections>
            <heading>
                <content>Personal Information</content>
                <order_id>1</order_id>
            </heading>
            <text>
                <content>Tell us about yourself.</content>
                <order_id>2</order_id>
                <files>
                    <file>
                        <file_id>9355</file_id>
                        <name>terms.pdf</name>
                    </file>
                </files>
            </text>
            <field>
                <id>19</id>
                <name>Home Address</name>
                <description></description>
                <is_required>1</is_required>
                <data_type>ADDRESS</data_type>
                <data_format></data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>3</order_id>
                <answer>
                    <street>123 Any St</street>
                    <city>Anytown</city>
                    <state>ID</state>
                    <zip>12345</zip>
                    <country_abbreviation>USA</country_abbreviation>
                    <country_name>United States of America</country_name>
                </answer>
            </field>
            <field>
                <id>20</id>
                <name>SSN</name>
                <description>Please enter your social security number.</description>
                <is_required>0</is_required>
                <data_type>NUMBER</data_type>
                <data_format>SOCIAL_SECURITY_NUMBER</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>4</order_id>
                <answer>111-11-1111</answer>
            </field>
            <field>
                <id>21</id>
                <name>Hobbies and Interests</name>
                <description>Choose any two of the following options.</description>
                <is_required>1</is_required>
                <data_type>MULTIPLE_ANSWER</data_type>
                <data_format></data_format>
                <max_multiple_answers>5</max_multiple_answers>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>5</order_id>
                <answer>
                    <options>
                        <option>
                            <id>527</id>
                            <name>Football</name>
                        </option>
                        <option>
                            <id>528</id>
                            <name>Baseball</name>
                        </option>
                    </options>
                </answer>
            </field>
            <field>
                <id>22</id>
                <name>Race and Ethnicity</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>CHOICE</data_type>
                <data_format>RACE_ETHNICITY</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>6</order_id>
                <answer>
                    <hispanic_latino>0</hispanic_latino>
                    <races>
                        <race>
                            <id>1</id>
                            <name>American Indian or Alaska Native</name>
                        </race>
                    </races>
                </answer>
            </field>
            <field>
                <id>23</id>
                <name>Education Level</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>CHOICE</data_type>
                <data_format>EDUCATION_LEVEL</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>7</order_id>
                <answer>
                    <education_level_id>3333</education_level_id>
                    <education_level_name>High School Diploma</education_level_name>
                </answer>
            </field>
            <field>
                <id>24</id>
                <name>Upload your admissions essay</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>FILE</data_type>
                <data_format></data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>8</order_id>
                <answer>
                    <file_id>456987456</file_id>
                    <file_name>admissions_essay.pdf</file_name>
                </answer>
            </field>
            <field>
                <id>25</id>
                <name>SAT score</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>STANDARDIZED_TEST</data_type>
                <data_format></data_format>
                <status>IN_PROGRESS</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>9</order_id>
                <answer>
                    <test_id>1</test_id>
                    <test_name>SAT</test_name>
                    <test_date>2014-01-07</test_date>
                    <test_score>1400</test_score>
                    <test_sections>
                        <test_section>
                            <id>1</id>
                            <name>Math</name>
                            <score>680</score>
                        </test_section>
                        <test_section>
                            <id>2</id>
                            <name>Evidence-Based Reading and Writing</name>
                            <score>700</score>
                        </test_section>
                    </test_sections>
                </answer>
            </field>
            <field>
                <id>26</id>
                <name>Citizenship</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>CHOICE</data_type>
                <data_format>COUNTRY</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>10</order_id>
                <answer>
                    <abbreviation>USA</abbreviation>
                    <name>United States of America</name>
                </answer>
            </field>
            <field>
                <id>27</id>
                <name>State/Province</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>CHOICE</data_type>
                <data_format>STATE_PROVINCE</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>11</order_id>
                <answer>
                    <state>AL</state>
                    <country_abbreviation>USA</country_abbreviation>
                    <country_name>United States of America</country_name>
                </answer>
            </field>
            <field>
                <id>28</id>
                <name>High School GPA</name>
                <description></description>
                <is_required>0</is_required>
                <data_type>CHOICE</data_type>
                <data_format></data_format>
                <status>IN_PROGRESS</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>12</order_id>
                <answer>
                    <option_id>8976541</option_id>
                    <option_name>3.0 - 3.5</option_name>
                </answer>
            </field>
            <field>
                <id>29</id>
                <name>Degree</name>
                <description></description>
                <is_required>1</is_required>
                <data_type>CHOICE</data_type>
                <data_format>DEGREE</data_format>
                <status>SUBMITTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>13</order_id>
                <answer>
                    <degree_id>444</degree_id>
                    <degree_abbreviation>B.A.</degree_abbreviation>
                    <degree_name>Bachelor of Arts</degree_name>
                </answer>
            </field>
            <field>
                <id>30</id>
                <name>Specialization</name>
                <description></description>
                <is_required>1</is_required>
                <data_type>CHOICE</data_type>
                <data_format>SPECIALIZATION</data_format>
                <status>SUBMITTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>14</order_id>
                <answer>
                    <specialization_id>5555</specialization_id>
                    <specialization_abbreviation>ENG</specialization_abbreviation>
                    <specialization_name>English</specialization_name>
                </answer>
            </field>
            <field>
                <id>31</id>
                <name>Reference</name>
                <description></description>
                <is_required>1</is_required>
                <data_type>ONLINE_REFERENCE</data_type>
                <data_format>REFERENCE</data_format>
                <status>ACCEPTED</status>
                <submitted_at>2014-02-10 15:46:35</submitted_at>
                <decision_at>2014-02-10 16:10:45</decision_at>
                <order_id>15</order_id>
                <answer>
                    <online_reference_status>RECEIVED</online_reference_status>
                    <online_reference_id>549873541</online_reference_id>
                    <online_reference_email>reference@email.org</online_reference_email>
                    <online_reference_message>Personal note to referrer.</online_reference_message>
                </answer>
            </field>
        </sections>
        <notes>
            <note>
                <id>5579</id>
                <content>The student is especially interested in classes from Dr. Thompson.</content>
                <public>0</public>
                <added_by>338590</added_by>
                <added_at>2014-01-15 14:42:25</added_at>
                <file_id>774481</file_id>
                <file_name>note.pdf</file_name>
            </note>
        </notes>
    </application>
</response>
"""
