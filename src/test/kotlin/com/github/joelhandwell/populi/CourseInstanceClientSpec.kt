package com.github.joelhandwell.populi

import com.github.tomakehurst.wiremock.WireMockServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.spekframework.spek2.style.specification.xdescribe
import kotlin.test.assertEquals

object CourseInstanceClientSpec : Spek({

    val server = WireMockServer()
    val populi = mockClient()

    beforeGroup { server.start() }

    describe("Populi Client work on CourseInstance"){

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

        it("send request, receive response and parse it into CourseInstance") {
            stubForPopuli("getCourseInstance", getCourseInstanceXml)
            assertCourseInstanceResponse(populi.getCourseInstance(1111))
        }

        it("send request, receive response and parse it into AssignmentGroups for a CourseInstance") {
            stubForPopuli("getCourseInstanceAssignmentGroups", getCourseInstanceAssignmentGroupsXml)
            assertCourseInstanceAssignmentGroups(populi.getCourseInstanceAssignmentGroups(1111))
        }

        it("send request, receive response and parse it into Assignments for a CourseInstance") {
            stubForPopuli("getCourseInstanceAssignments", getCourseInstanceAssignmentsXml)
            assertAssignments(populi.getCourseInstanceAssignments(1111))
        }

        it("send request, receive response and parse it into Submissions of Assignments for a Student") {
            stubForPopuli("getStudentAssignmentSubmissions", getStudentAssignmentSubmissionsXml)
            assertStudentAssignmentSubmissions(populi.getStudentAssignmentSubmissions(1111, 2222))
        }

        it("send request, receive response and parse it into Files for a CourseInstance") {
            stubForPopuli("getCourseInstanceFiles", getCourseInstanceFilesXml)
            assertCourseInstanceFiles(populi.getCourseInstanceFiles(1111))
        }

        it("send request, receive response and parse it into Links for a CourseOffering") {
            stubForPopuli("getCourseOfferingLinks", getCourseOfferingLinksXml)
            assertCourseOfferingLinks(populi.getCourseOfferingLinks(1111))
        }

        it("send request, receive response and parse it into Lessons for a CourseInstance") {
            stubForPopuli("getCourseInstanceLessons", getCourseInstanceLessonsXml)
            assertCourseInstanceLessons(populi.getCourseInstanceLessons(1111))
        }

        it("send request, receive response and parse it into LessonContent") {
            val html = "<html><body>Lesson Content</body></html>"
            stubForPopuli("getLessonContent", html)
            assertEquals(html, populi.getLessonContent(1111, 2222))
        }

        it("send request, receive response and parse it into Meetings for a CourseInstance") {
            stubForPopuli("getCourseInstanceMeetings", getCourseInstanceMeetingsXml)
            assertCourseInstanceMeetings(populi.getCourseInstanceMeetings(1111))
        }

        it("send request, receive response and parse it into MeetingAttendances for a CourseInstance") {
            stubForPopuli("getCourseInstanceMeetingAttendance", getCourseInstanceMeetingAttendanceXml)
            assertCourseInstanceMeetingAttendances(populi.getCourseInstanceMeetingAttendance(1111, 2222))
        }

        it("send request, receive response and parse it into CourseInstance of a specific Person") {
            stubForPopuli("getMyCourses", getMyCoursesXml)
            assertEquals(myCourses, populi.getMyCourses())
        }

        it("send request, receive response and parse it into Students for a CourseInstance") {
            stubForPopuli("getCourseInstanceStudents", getCourseInstanceStudentsXml)
            assertCourseInstanceStudents(populi.getCourseInstanceStudents(1111))
        }

        it("send request, receive response and parse it into Students for a CourseInstance") {
            stubForPopuli("getCourseInstanceStudent", courseInstanceStudentXml)
            assertCourseInstanceStudent(populi.getCourseInstanceStudent(1111, 2222))
        }

        it("send request, receive response and parse it into a Student's Attendances for a CourseInstance for a Term") {
            stubForPopuli("getCourseInstanceStudentAttendance", getCourseInstanceStudentAttendanceXml)
            assertCourseInstanceStudentAttendances(populi.getCourseInstanceStudentAttendance(1111, 2222))
        }
    }

    afterGroup { server.stop() }

    xdescribe("Populi Client with real info on CourseInstance"){
        //val real = realClient()
        //val termId = LocalProperty.termId
        //val courseInstanceId = LocalProperty.courseInstanceId
        //val personId = LocalProperty.personId

        //println(real.getTermCourseInstances(termId))
        //println(real.getTermStudents(term_id = termId))
        //println(real.getTermEnrollment(termId))
        //println(real.getTuitionSchedules())
        //println(real.getCourseGroupInfo(course_group_id = LocalProperty.course_group_id))
        //println(real.getTermCourseInstances(termId))
        //println(real.getCourseInstance(courseInstanceId))
        //println(real.getCourseInstanceAssignmentGroups(courseInstanceId))
        //println(real.getCourseInstanceAssignments(courseInstanceId))
        //println(real.getStudentAssignmentSubmissions(LocalProperty.courseInstanceAssignmentId, personId))
        //println(real.getCourseInstanceFiles(courseInstanceId))
        //println(real.getRaw("getCourseInstanceLessons", courseInstanceId))
        //println(real.getLessonContent(courseInstanceId, LocalProperty.lessonId)) // got: <?xml version="1.0" encoding="UTF-8"?><error><code>OTHER_ERROR</code><message>This task is no longer supported</message></error>
        //println(real.getMyCourses(person_id = personId, term_id = termId))
        //println(real.getCourseInstanceStudents(courseInstanceId))
        //println(real.getRaw("getCourseInstanceStudent", courseInstanceId, personId))
        //println(real.getRaw("getCourseInstanceMeetings", courseInstanceId))
        //println(real.getCourseInstanceMeetings(courseInstanceId))
        //println(real.getRaw("getCourseInstanceStudentAttendance", courseInstanceId, personId))
        //println(real.getRaw("getCurrentAcademicTerm"))
        //println(real.getCurrentAcademicTerm())
        //println(real.getCourseOfferingLinks(courseInstanceId))
    }
})
