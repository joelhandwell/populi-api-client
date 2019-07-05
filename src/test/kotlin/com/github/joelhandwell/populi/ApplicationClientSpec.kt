package com.github.joelhandwell.populi

import com.github.tomakehurst.wiremock.WireMockServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.spekframework.spek2.style.specification.xdescribe
import kotlin.test.assertEquals

object ApplicationClientSpec : Spek({

    val server = WireMockServer()
    val populi = mockClient()

    beforeGroup { server.start() }

    describe("Populi Client work on Application") {

        it("send request, receive response and parse it into Application") {
            stubForPopuli("getApplications", getApplicationsXml)
            assertEquals(applicationResponse, populi.getApplications())
        }

        it("send request, receive response and parse it into Application associated with a specific Person") {
            stubForPopuli("getPersonApplications", getPersonApplicationsXml)
            assertEquals(personApplications, populi.getPersonApplications(1111))
        }

        it("send request, receive response and parse it into Application Detail") {
            stubForPopuli("getApplication", getApplicationXml)
            assertEquals(applicationDetail, populi.getApplication(1111))
        }

        it("send request, receive response and parse it into ApplicationFieldOption") {
            stubForPopuli("getApplicationFieldOptions", getApplicationFieldOptionsXml)
            assertEquals(applicationFieldOptions, populi.getApplicationFieldOptions(1111))
        }

        it("send request, receive response and parse it into Component of an Application") {
            stubForPopuli("getApplicationComponents", getApplicationComponentsXml)
            assertApplicationComponents(populi.getApplicationComponents(1111))
        }

        it("send request, receive response and parse it into ApplicationTemplate") {
            stubForPopuli("getApplicationTemplates", getApplicationTemplatesXml)
            assertApplicationTemplates(populi.getApplicationTemplates())
        }
    }

    afterGroup { server.stop() }

    xdescribe("Populi Client with real info on Application") {
        val real = realClient()
        println(real.getApplications())
        println(real.getApplicationTemplates())
        println(real.getPersonApplications(LocalProperty.personId))
        println(real.getApplication(LocalProperty.applicationId))
        println(real.getApplicationFieldOptions(LocalProperty.applicationFieldId))
        println(real.getApplicationComponents(LocalProperty.applicationId))
    }
})
