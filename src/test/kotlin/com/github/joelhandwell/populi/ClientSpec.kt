package com.github.joelhandwell.populi

import ch.qos.logback.classic.Level
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.slf4j.LoggerFactory
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertNotNull

const val wireMockPort = 8080

const val apiAccessKey =
    "694f1f255a606e89bd6acc9cdc215c277e7bfd5a326fe057d99903606562cd7f1d37bbf4631c42773321e41ed0f7241decfff04eb8bdac6dfa6b14fc9171086626a22091dd35b7a99db5d24a3df4daa094c16e0b74f5a9ecd8bfc584c13d6f2e5c2719692416cce27d49638ac88a4a8921c7c437c40d4f4ab219fb7fcd5903a8533873c40bb7cda456ff6fd71992403450b3096d5898b40ed2b5983ef970c5e68f5ace7507517283dac8472d4e2d"

object ClientSpec : Spek({

    describe("Client") {
        (LoggerFactory.getLogger("org.eclipse.jetty") as ch.qos.logback.classic.Logger).level = Level.INFO
        val wireMockServer = WireMockServer()
        beforeGroup { wireMockServer.start() }

        it("send request, receive response and parse it into object") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$apiAccessKey&task=getDegrees"))
                    .willReturn(WireMock.aResponse().withBodyFile("getDegrees.xml"))
            )

            val populi = Populi.Builder()
                .withBaseUrl("http://localhost:$wireMockPort/")
                .withAccessKey(apiAccessKey).build()
            assertGetDegrees(populi)
        }

        it("creates api client with username and password") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("username=john&password=pass"))
                    .willReturn(WireMock.aResponse().withBodyFile("accessKeyRequest.xml"))
            )

            val populi = Populi.Builder().withBaseUrl("http://localhost:$wireMockPort/").withUsername("john")
                .withPassword("pass").build()

            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$apiAccessKey&task=getDegrees"))
                    .willReturn(WireMock.aResponse().withBodyFile("getDegrees.xml"))
            )

            assertGetDegrees(populi)
        }

        afterGroup { wireMockServer.stop() }
    }
})

fun assertGetDegrees(populi: Populi) {
    val degrees = populi.getDegrees()
    assertNotNull(degrees)
    assertDegreeRequest(degrees)
}
