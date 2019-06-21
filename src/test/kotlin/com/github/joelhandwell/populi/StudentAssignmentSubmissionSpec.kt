package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import java.time.LocalDateTime
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

private val file = AssignmentFile(
    1520,
    18624,
    "Final Draft",
    "application/octet-stream",
    168742,
    "Simon Student",
    LocalDateTime.of(2013, 8, 2, 14, 11, 25)
)

private val comment = AssignmentComment(
    1525,
    "Content of comment",
    17628,
    "Robert McProfessor",
    LocalDateTime.of(2013, 8, 2, 16, 15, 56)
)

private val response = StudentAssignmentSubmission(
    file = mutableListOf(file),
    comment = mutableListOf(comment)
)

object StudentAssignmentSubmissionSpec : Spek({

    describe("StudentAssignmentSubmission") {
        it("mashal to xml") {
            // let's assume it passed if without error
            JAXB.marshal(response, StringWriter())
        }

        it("unmarshal from xml") { assertUnmarshals(response, getStudentAssignmentSubmissionsXml) }
    }
})

fun assertStudentAssignmentSubmissions(studentAssignmentSubmission: StudentAssignmentSubmission){
    assertEquals(response, studentAssignmentSubmission)
}

const val getStudentAssignmentSubmissionsXml = """
<response>
    <files>
        <file>
            <submission_id>1520</submission_id>
            <file_id>18624</file_id>
            <name>Final Draft</name>
            <content_type>application/octet-stream</content_type>
            <added_by_id>168742</added_by_id>
            <added_by_name>Simon Student</added_by_name>
            <added_time>2013-08-02 14:11:25</added_time>
        </file>
    </files>
    <comments>
        <comment>
            <submission_id>1525</submission_id>
            <content>Content of comment</content>
            <added_by_id>17628</added_by_id>
            <added_by_name>Robert McProfessor</added_by_name>
            <added_time>2013-08-02 16:15:56</added_time>
        </comment>
    </comments>
</response>
"""
