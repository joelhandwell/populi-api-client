package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

private val link1 = CourseOfferingLink(1, "Library of Congress", "https://www.loc.gov/")
private val link2 = CourseOfferingLink(2, "United States Digital Service", "https://www.usds.gov/")
private val response = CourseOfferingLinkResponse(mutableListOf(link1, link2))

object CourseOfferingLinkSpec : Spek({

    describe("CourseOfferingLink") {

        it("marshal to xml") { assertMarshals(getCourseOfferingLinksXml, response) }

        it("unmarshal from xml") { assertUnmarshals(response, getCourseOfferingLinksXml) }

    }
})

fun assertCourseOfferingLinks(courseOfferingLinks: MutableList<CourseOfferingLink>) {
    assertEquals(mutableListOf(link1, link2), courseOfferingLinks)
}

const val getCourseOfferingLinksXml = """
<response>
    <links>
        <link>
            <id>1</id>
            <name>Library of Congress</name>
            <url>https://www.loc.gov/</url>
        </link>
        <link>
            <id>2</id>
            <name>United States Digital Service</name>
            <url>https://www.usds.gov/</url>
        </link>
    </links>
</response>"""
