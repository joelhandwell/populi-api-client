package com.github.joelhandwell.populi

import ch.qos.logback.classic.Level
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.slf4j.LoggerFactory
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertNotNull

object ClientSpec : Spek({

    describe("Client") {
        (LoggerFactory.getLogger("org.eclipse.jetty") as ch.qos.logback.classic.Logger).level = Level.INFO
        val wireMockServer = WireMockServer()
        val populi = Populi.Builder().withBaseUrl("http://localhost:$WIREMOCK_PORT/").withAccessKey(TEST_API_ACCESS_KEY).build()
        beforeGroup { wireMockServer.start() }

        it("creates api client with username and password") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("username=john&password=pass"))
                    .willReturn(WireMock.aResponse().withBodyFile("accessKeyRequest.xml"))
            )

            val populiWithUsernamePassword =
                Populi.Builder().withBaseUrl("http://localhost:$WIREMOCK_PORT/").withUsername("john")
                    .withPassword("pass").build()

            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$TEST_API_ACCESS_KEY&task=getDegrees"))
                    .willReturn(WireMock.aResponse().withBodyFile("getDegrees.xml"))
            )

            val degrees = populiWithUsernamePassword.getDegrees()
            assertNotNull(degrees)
            assertDegrees(degrees)
        }

        it("send request, receive response and parse it into Degrees") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$TEST_API_ACCESS_KEY&task=getDegrees"))
                    .willReturn(WireMock.aResponse().withBodyFile("getDegrees.xml"))
            )
            val degrees = populi.getDegrees()
            assertNotNull(degrees)
            assertDegrees(degrees)
        }

        it("send request, receive response and parse it into Users") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$TEST_API_ACCESS_KEY&task=getUsers"))
                    .willReturn(WireMock.aResponse().withBody(getUsersXml))
            )
            assertUsers(populi.getUsers())
        }

        afterGroup { wireMockServer.stop() }
    }
})
