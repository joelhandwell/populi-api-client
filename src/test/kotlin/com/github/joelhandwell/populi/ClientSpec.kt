package com.github.joelhandwell.populi

import ch.qos.logback.classic.Level
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.slf4j.LoggerFactory
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

object ClientSpec : Spek({

    describe("Client") {
        (LoggerFactory.getLogger("org.eclipse.jetty") as ch.qos.logback.classic.Logger).level = Level.INFO
        val server = WireMockServer()
        val populi = mockClient()
        beforeGroup { server.start() }

        it("creates api client with username and password") {
            stubFor(
                post("/api/").withRequestBody(
                    containing("username=john&password=pass")
                ).willReturn(
                    aResponse().withBodyFile("accessKeyRequest.xml")
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

        it("send request, receive response and parse it into EducationLevel") {
            stubForPopuli("getEducationLevels", getEducationLevelsXml)
            assertEquals(educationLevels, populi.getEducationLevels())
        }

        it("send request, receive response and parse it into Users") {
            stubForPopuli("getUsers", getUsersXml)
            assertUsers(populi.getUsers())
        }

        it("send request, receive response and parse it into Country") {
            stubForPopuli("getCountries", getCountriesXml)
            assertCountries(populi.getCountries())
        }

        it("send request, receive response and parse it into State") {
            stubForPopuli("getStates", getStatesXml)
            assertEquals(states, populi.getStates())
        }

        it("send request, receive response and parse it into Province") {
            stubForPopuli("getProvinces", getProvincesXml)
            assertEquals(provinces, populi.getProvinces())
        }

        it("send request, receive response and parse it into Race") {
            stubForPopuli("getRaces", getRacesXml)
            assertEquals(races, populi.getRaces())
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

        it("send request, receive response and parse it into current AcademicTerm") {
            stubForPopuli("getCurrentAcademicTerm", getCurrentAcademicTermXml)
            assertAcademicTerm(populi.getCurrentAcademicTerm())
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

        it("send request, receive response and parse it into Url to download File") {
            stubForPopuli("getFileDownloadURL", getFileDownloadURLXml)
            assertEquals(fileDownloadURL.url, populi.getFileDownloadURL(1111))
        }

        it("send request, receive response and parse it into a Student's Degree Audit") {
            stubForPopuli("getDegreeAudit", getDegreeAuditXml)
            assertDegreeAudit(populi.getDegreeAudit(1111, 2222, 3333, 4444))
        }

        it("send request, receive response and parse it into a StudentDiscipline") {
            stubForPopuli("getStudentDiscipline", getStudentDisciplineXml)
            assertStudentDisciplines(populi.getStudentDiscipline(1111))
        }

        it("send request, receive response and parse it into Programs of a Student") {
            stubForPopuli("getStudentPrograms", getStudentProgramsXml)
            assertStudentPrograms(populi.getStudentPrograms(1111))
        }

        it("send request, receive response and parse it into StudentInfo") {
            stubForPopuli("getStudentInfo", getStudentInfoXml)
            assertStudentInfo(populi.getStudentInfo(1111))
        }

        it("send request, receive response and parse it into PersonInfo") {
            stubForPopuli("getPerson", getPersonXml)
            assertEquals(personInfo, populi.getPerson(1111))
        }

        it("send request, receive response and parse it into PersonSSN") {
            stubForPopuli("getPersonSSN", getPersonSSNXml)
            assertEquals(personSSN, populi.getPersonSSN(1111))
        }

        it("send request, receive response and parse it into all available Roles") {
            stubForPopuli("getAvailableRoles", getAvailableRolesXml)
            assertEquals(roles, populi.getAvailableRoles())
        }

        it("send request, receive response and parse it into Roles of a Person") {
            stubForPopuli("getRoles", getRolesXml)
            assertEquals(personRoles, populi.getRoles())
        }

        it("send request, throws error when tying to get Members of a PersonRole without specifying neither roleID nor roleName") {
            assertFailsWith<IllegalArgumentException>(message = "either roleID or roleName must be set") {
                populi.getRoleMembers()
            }
        }

        it("send request, receive response and parse it into Members of a PersonRole") {
            stubForPopuli("getRoleMembers", getRoleMembersXml)
            assertEquals(roleMemberResponse, populi.getRoleMembers(roleID = 1111))
        }

        it("send request, throws error when tying to get CustomField without specifying neither person_id nor organization_id") {
            assertFailsWith<IllegalArgumentException>(message = "either person_id or organization_id must be set") {
                populi.getCustomFields()
            }
        }

        it("send request, receive response and parse it into CustomField for a specific Person") {
            stubForPopuli("getCustomFields", getCustomFieldsXml)
            assertEquals(customFields, populi.getCustomFields(person_id = 1111))
        }

        it("send request, receive response and parse it into CustomField") {
            stubForPopuli("getAllCustomFields", getAllCustomFieldsXml)
            assertEquals(customFieldsWithDescription, populi.getAllCustomFields())
        }

        it("send request, receive response and parse it into CustomFieldOption") {
            stubForPopuli("getCustomFieldOptions", getCustomFieldOptionsXml)
            assertEquals(customFieldOptions, populi.getCustomFieldOptions(1111))
        }

        it("send request, receive response and parse it into Transcript") {
            stubForPopuli("getTranscript", getTranscriptXml)
            assertEquals(transcript, populi.getTranscript(1111))
        }

        it("send request, receive response and parse it into CommunicationPlan") {
            stubForPopuli("getCommunicationPlans", getCommunicationPlansXml)
            assertEquals(communicationPlanResponse.communication_plan, populi.getCommunicationPlans())
        }

        it("send request, receive response and parse it into CommunicationPlan for a Person") {
            stubForPopuli("getPersonCommunicationPlans", getPersonCommunicationPlansXml)
            assertEquals(mutableListOf(communicationPlanInstance), populi.getPersonCommunicationPlans(1111))
        }

        it("send request, receive response and parse it into Lead attached to a specific Person") {
            stubForPopuli("getPersonLeads", getPersonLeadsXml)
            assertEquals(leads, populi.getPersonLeads(1111))
        }

        it("send request, receive response and parse it into LeadSource") {
            stubForPopuli("getLeadSources", getLeadSourcesXml)
            assertEquals(leadSources, populi.getLeadSources())
        }

        it("send request, receive response and parse it into Inquiry") {
            stubForPopuli("getInquiry", getInquiryXml)
            assertEquals(inquiries, populi.getInquiry(1111))
        }

        it("send request, receive response and parse it into Tag") {
            stubForPopuli("getTags", getTagsXml)
            assertEquals(tags, populi.getTags())
        }

        it("send request, throws error when tying to get Person with specific Tag without specifying tagID nor tagName") {
            assertFailsWith<IllegalArgumentException>(message = "either tagID or tagName must be set") {
                populi.getTaggedPeople()
            }
        }

        it("send request, receive response and parse it into Person with specific Tag ") {
            stubForPopuli("getTaggedPeople", getTaggedPeopleXml)
            assertEquals(taggedPersonResponse, populi.getTaggedPeople(tagID = 1111))
        }

        it("send request, receive response and parse it into Events with specific owners") {
            stubForPopuli("getEvents", getEventsXml)
            val calendars = mutableListOf(
                EventCalendar(CalendarOwnerType.PERSON, 1234),
                EventCalendar(CalendarOwnerType.INSTANCE, 5678)
            )
            assertEquals(events, populi.getEvents(LocalDate.of(2019, 7, 9), LocalDate.of(2019, 7, 10), calendars))
        }

        it("send request, receive response and parse it into single Event") {
            stubForPopuli("getEvent", getEventXml)
            assertEquals(event, populi.getEvent(eventID = 1111))
        }

        it("send request, receive response and parse it into NewsArticle") {
            stubForPopuli("getNews", getNewsXml)
            assertEquals(newsArticleResponse, populi.getNews())
        }

        it("send request, receive response and parse it into ToDo") {
            stubForPopuli("getTodos", getTodosXml)
            assertEquals(toDoResponse, populi.getTodos())
        }

        it("send request, receive response and parse it into PrintLayout") {
            stubForPopuli("getPrintLayouts", getPrintLayoutsXml)
            assertEquals(printLayouts, populi.getPrintLayouts())
        }

        it("send request, receive response and parse it into UpdatedPerson") {
            stubForPopuli("getUpdatedPeople", getUpdatedPeopleXml)
            assertEquals(updatedPeopleResponse, populi.getUpdatedPeople(LocalDateTime.of(2018, 11, 23, 22, 59, 59)))
        }

        xit("real") {
            val real = realClient()

            // test with your real populi account info
            //println(real.getEducationLevels())
            //println(real.getCountries())
            //println(real.getRaces())
            //println(real.getStates())
            //println(real.getProvinces())
            //println(real.getAvailableRoles())
            //println(real.getRoles())
            //println(real.getRoleMembers(roleName = "Faculty"))
            //println(real.getRaw("getPersonRoles"))
            //println(real.getDegrees())
            //println(real.getPrograms())
            //println(real.getAcademicYears())
            //println(real.getAcademicTerms())
            //println(real.getFileDownloadURL(fileId))
            //println(real.getDegreeAudit(personId, degreeId, yearId))
            //println(real.getStudentDiscipline(personId))
            //println(real.getStudentPrograms(personId))
            //println(real.getStudentInfo(personId))
            //println(real.getPerson(personId))
            //println(real.getCustomFields(person_id = personId))
            //println(real.getAllCustomFields())
            //println(real.getCustomFieldOptions(customFieldId))
            //println(real.getTranscript(personId))
            //println(real.getCommunicationPlans())
            //println(real.getPersonCommunicationPlans(personId))
            //println(real.getPersonLeads(personId))
            //println(real.getLeadSources())
            //println(real.getInquiry(inquiryId))
            //println(real.getTags())
            //println(real.getTaggedPeople(tagID = LocalProperty.tagId))
            //println(real.getEvents(calendars = mutableListOf(EventCalendar(CalendarOwnerType.INSTANCE, LocalProperty.courseInstanceId))))
            //println(real.getEvent(LocalProperty.eventId))
            //println(real.getNews())
            //println(real.getTodos())
            //println(real.getPrintLayouts())
            println(real.getUpdatedPeople(LocalDateTime.of(2018, 11, 23, 22, 59, 59)))
        }

        afterGroup { server.stop() }
    }
})
