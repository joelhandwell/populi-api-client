package com.github.joelhandwell.populi

import com.joelhandwell.populi.jaxb.sanitizeXml
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals

private val article = NewsArticle(
    article_id = 56,
    title = "Snow Day!",
    content = "<p>HTML content (with <tags> and embedded media and stuff) goes here.</p>",
    pinned = 0,
    pinned_until = LocalDate.parse("2011-04-30"),
    added_at = LocalDateTime.of(2011, 4, 29, 14, 44, 31),
    added_by = 2920,
    added_by_name = "Bob McStaff",
    updated_at = null,
    updated_by = 0,
    updated_by_name = "",
    role = mutableListOf(Role(5, "Student"), Role(3, "Faculty"))
)

val newsArticleResponse = NewsArticleResponse(
    num_results = 356,
    article = mutableListOf(article)
)

object NewsSpec : Spek({

    describe("sanitizeXml") {

        it("fixes broken CDATA section") {

            val broken = """<content><
                              ![CDATA[<p>HTML content (with <tags> and embedded media and stuff) goes here.</p>]]>
                            </content>"""

            val fixed = "<content><![CDATA[<p>HTML content (with <tags> and embedded media and stuff) goes here.</p>]]></content>"

            assertEquals(fixed, sanitizeXml(broken))
        }
    }

    describe("NewsArticle") {

        it("marshal to xml") { assertMarshals(newsArticleResponse) }

        it("unmarshal from xml") { assertUnmarshals(newsArticleResponse, getNewsXml) }
    }
})

const val getNewsXml = """
<response num_results="356">
  <article>
    <article_id>56</article_id>
    <title>Snow Day!</title>
    <content><
      ![CDATA[<p>HTML content (with <tags> and embedded media and stuff) goes here.</p>]]>
    </content>
    <pinned></pinned>
    <pinned_until>2011-04-30</pinned_until>
    <added_at>2011-04-29 14:44:31</added_at>
    <added_by>2920</added_by>
    <added_by_name>Bob McStaff</added_by_name>
    <updated_at></updated_at>
    <updated_by></updated_by>
    <updated_by_name></updated_by_name>
    <roles>
      <role>
        <id>5</id>
        <name>Student</name>
      </role>
      <role>
        <id>3</id>
        <name>Faculty</name>
      </role>
    </roles>
  </article>
</response>"""
