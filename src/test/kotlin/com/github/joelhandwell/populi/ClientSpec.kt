package com.github.joelhandwell.populi

import ch.qos.logback.classic.Level
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.slf4j.LoggerFactory
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertNotNull

object ClientSpec : Spek({

    describe("Client") {
        (LoggerFactory.getLogger("org.eclipse.jetty") as ch.qos.logback.classic.Logger).level = Level.INFO
        val wireMockServer = WireMockServer()
        val populi = Populi.Builder()
            .withBaseUrl("http://localhost:$WIREMOCK_PORT/")
            .withAccessKey(TEST_API_ACCESS_KEY)
            .build()
        beforeGroup { wireMockServer.start() }

        it("creates api client with username and password") {
            WireMock.stubFor(
                WireMock.post("/api/").withRequestBody(WireMock.containing("username=john&password=pass")).willReturn(
                    WireMock.aResponse().withBodyFile("accessKeyRequest.xml")
                )
            )

            val populiWithUsernamePassword = Populi.Builder()
                .withBaseUrl("http://localhost:$WIREMOCK_PORT/")
                .withUsername("john")
                .withPassword("pass")
                .build()

            stubForPopuli("getDegrees", getDegreesXml)

            val degrees = populiWithUsernamePassword.getDegrees()
            assertNotNull(degrees)
            assertDegrees(degrees)
        }

        it("send request, receive response and parse it into Degrees") {
            stubForPopuli("getDegrees", getDegreesXml)
            val degrees = populi.getDegrees()
            assertNotNull(degrees)
            assertDegrees(degrees)
        }

        it("send request, receive response and parse it into Users") {
            stubForPopuli("getUsers", getUsersXml)
            assertUsers(populi.getUsers())
        }

        it("send request, receive response and parse it into Campuses") {
            stubForPopuli("getCampuses", getCampusesXml)
            assertCampuses(populi.getCampuses())
        }

        it("send request, receive response and parse it into Programs") {
            stubForPopuli("getPrograms", getProgramsXml)
            assertPrograms(populi.getPrograms())
        }

        it("send request, receive response and parse it into AcademicYear") {
            stubForPopuli("getAcademicYears", getAcademicYearsXml)
            assertAcademicYears(populi.getAcademicYears())
        }

        it("send request, receive response and parse it into AcademicTerm") {
            stubForPopuli("getAcademicTerms", getAcademicTermsXml)
            assertAcademicTerms(populi.getAcademicTerms())
        }

        it("send request, receive response and parse it into Course") {
            stubForPopuli("getCourseCatalog", getCourseCatalogXml)
            assertCourses(populi.getCourseCatalog())
            assertCourses(populi.getCourseCatalog(true)) //better to test with retired courses
        }

        it("send request, receive response and parse it into CourseGroup") {
            stubForPopuli("getCourseGroups", getCourseGroupsXml)
            assertCourseGroups(populi.getCourseGroups())
        }

        it("send request, receive response and parse it into CourseGroupInfo") {
            stubForPopuli("getCourseGroupInfo", getCourseGroupInfoXml)
            assertCourseGroupInfo(populi.getCourseGroupInfo(1111))
            assertCourseGroupInfo(populi.getCourseGroupInfo(1111, 2222))
        }

        it("send request, receive response and parse it into CourseInstance") {
            stubForPopuli("getTermCourseInstances", getTermCourseInstancesXml)
            assertTermCourseInstanceResponses(populi.getTermCourseInstances(1111))
        }

        it("send request, receive response and parse it into Student") {
            stubForPopuli("getTermStudents", getTermStudentsXml)
            assertTermStudentResponse(populi.getTermStudents())
        }

        it("send request, receive response and parse it into Enrollment") {
            stubForPopuli("getTermEnrollment", getTermEnrollmentXml)
            assertTermEnrollments(populi.getTermEnrollment(1111))
        }

        it("send request, receive response and parse it into TuitionSchedule") {
            stubForPopuli("getTuitionSchedules", getTuitionSchedulesXml)
            assertTuitionSchedules(populi.getTuitionSchedules())
        }

        it("send request, receive response and parse it into StudentTermTuitionSchedule") {
            stubForPopuli("getStudentTermTuitionSchedules", getStudentTermTuitionSchedulesXml)
            assertStudentTermTuitionSchedules(populi.getStudentTermTuitionSchedules(1111, 2222))
        }

        it("send request, receive response and parse it into CourseInstance") {
            stubForPopuli("getCourseInstance", getCourseInstanceXml)
            assertCourseInstanceResponse(populi.getCourseInstance(1111))
        }

        it("send request, receive response and parse it into AssignmentGroup") {
            stubForPopuli("getCourseInstanceAssignmentGroups", getCourseInstanceAssignmentGroupsXml)
            assertUnmarshals(populi.getCourseInstanceAssignmentGroups(1111), getCourseInstanceAssignmentGroupsXml)
        }

        it("send request, receive response and parse it into Assignment") {
            stubForPopuli("getCourseInstanceAssignments", getCourseInstanceAssignmentsXml)
            assertAssignments(populi.getCourseInstanceAssignments(1111))
        }

        it("send request, receive response and parse it into File") {
            stubForPopuli("getCourseInstanceFiles", getCourseInstanceFilesXml)
            assertCourseInstanceFiles(populi.getCourseInstanceFiles(1111))
        }

        xit("real") {
            val input = Paths.get("${System.getProperty("user.dir")}\\local.properties")
                .toFile()
                .inputStream()
            val p = Properties()
            p.load(input)

            val real = Populi.Builder()
                .withBaseUrl(p.getProperty("real.baseurl"))
                .withUsername(p.getProperty("real.username"))
                .withPassword(p.getProperty("real.password"))
                .withDebugFlag(true)
                .build()

            //val termId = p.getProperty("real.term_id").toInt()
            val courseInstanceId = p.getProperty("real.course_instance_id").toInt()

            /* test with your real populi account info
            println(real.getCourseGroupInfo(course_group_id = p.getProperty("real.course_group_id").toInt()))
            println(real.getAcademicTerms())
            println(real.getTermCourseInstances(termId))
            println(real.getTermStudents(term_id = termId))
            println(real.getTermEnrollment(termId))
            println(real.getTuitionSchedules())
            println(real.getTermCourseInstances(termId))
            println(real.getCourseInstance(courseInstanceId))
            println(real.getCourseInstanceAssignmentGroups(courseInstanceId))
            println(real.getRaw("getCourseInstanceAssignments", courseInstanceId))
            */

            println(real.getCourseInstanceFiles(courseInstanceId))
        }

        afterGroup { wireMockServer.stop() }
    }
})

fun stubForPopuli(task: String, xml: String) {
    WireMock.stubFor(
        WireMock.post("/api/").withRequestBody(WireMock.containing("access_key=$TEST_API_ACCESS_KEY&task=$task")).willReturn(
            WireMock.aResponse().withBody(xml)
        )
    )
}
