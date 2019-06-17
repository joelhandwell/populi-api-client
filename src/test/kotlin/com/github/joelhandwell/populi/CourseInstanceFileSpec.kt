package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

private val f1 = CourseInstanceFile(55555, "ChemicalBonds.jpg", "image/jpeg")
private val f2 = CourseInstanceFile(55556, "ChemicalBonds2.jpg", "image/jpeg")
private val courseInstanceFileResponse = CourseInstanceFileResponse(mutableListOf(f1, f2))

object CourseInstanceFileSpec : Spek({

    describe("CourseInstanceFile") {

        it("marshal to xml") { assertMarshals(getCourseInstanceFilesXml, courseInstanceFileResponse) }

        it("unmarshal from xml") { assertUnmarshals(courseInstanceFileResponse, getCourseInstanceFilesXml) }

    }

})

fun assertCourseInstanceFiles(courseInstanceFiles: MutableList<CourseInstanceFile>){
    assertEquals(mutableListOf(f1, f2), courseInstanceFiles)
}

const val getCourseInstanceFilesXml = """
<response>
    <file>
        <content_type>image/jpeg</content_type>
        <file_id>55555</file_id>
        <name>ChemicalBonds.jpg</name>
    </file>
    <file>
        <content_type>image/jpeg</content_type>
        <file_id>55556</file_id>
        <name>ChemicalBonds2.jpg</name>
    </file>
</response>
"""
