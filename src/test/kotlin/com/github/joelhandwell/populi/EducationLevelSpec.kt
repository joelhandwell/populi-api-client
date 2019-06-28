package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val educationLevels = mutableListOf(
    EducationLevel(
        id = 1111,
        name = "Less Than 9th Grade"
    ),
    EducationLevel(
        id = 2222,
        name = "9th Grade to 12th Grade"
    ),
    EducationLevel(
        id = 3333,
        name = "High School Diploma"
    )
)

private val response = EducationLevelResponse(educationLevels)

object EducationLevelSpec : Spek({

    describe("EducationLevel") {

        it("marshal to xml") { assertMarshals(getEducationLevelsXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getEducationLevelsXml) }
    }
})

const val getEducationLevelsXml = """
<response>
    <education_level>
        <id>1111</id>
        <name>Less Than 9th Grade</name>
    </education_level>
    <education_level>
        <id>2222</id>
        <name>9th Grade to 12th Grade</name>
    </education_level>
    <education_level>
        <id>3333</id>
        <name>High School Diploma</name>
    </education_level>
</response>"""
