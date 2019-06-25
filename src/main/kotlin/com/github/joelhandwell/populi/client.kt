package com.github.joelhandwell.populi

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

inline fun <reified R : Any> R.logger(): Logger =
    LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

class Populi(
    private val accessKey: String, private val api: PopuliApi
) {
    companion object {
        private val log = logger()
    }

    class Builder {
        private var username: String? = null
        private var password: String? = null
        private var accessKey: String? = null
        private var baseUrl: String? = null

        fun withUsername(username: String) = apply { this.username = username }
        fun withPassword(password: String) = apply { this.password = password }
        fun withAccessKey(accessKey: String) = apply { this.accessKey = accessKey }
        fun withBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }

        fun build(): Populi {

            val builder = Retrofit.Builder().baseUrl(baseUrl ?: throw RuntimeException("baseUrl is null"))
                .addConverterFactory(PopuliResponseConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())

            val api = builder.build().create(PopuliApi::class.java)

            if (accessKey == null) {
                log.info("fetching accessKey with username and password")
                val response = api.requestAccessKey(
                    username ?: throw RuntimeException("username null"), password
                        ?: throw RuntimeException("password null")
                )

                val body = sendRequest(response)

                if (body.access_key.isBlank()) {
                    throw RuntimeException("accessKey was blank")
                } else {
                    Populi.log.info("We got accessKey!")
                }

                accessKey = body.access_key.trim()
            } else {
                log.info("using existing accessKey")
            }

            return Populi(accessKey!!, api)
        }
    }

    /**
     * Method to confirm xml from real populi server
     */
    fun getRaw(task: String, instance_id: Int? = null, person_id: Int? = null): String {
        val call = if (instance_id == null) {
            this.api.getRaw(accessKey, task)
        } else {
            if (person_id == null) {
                this.api.getRawWithCourseInstanceId(accessKey, task, instance_id = instance_id)
            } else {
                this.api.getRawWithCourseInstanceIdPersonId(accessKey, task, instance_id = instance_id, person_id = person_id)
            }
        }
        return call.execute().body().toString()
    }

    /**
     * Returns information about each degree configured at the school. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCountries)
     */
    fun getDegrees(): MutableList<Degree>? = sendRequest(this.api.getDegrees(accessKey)).degree

    /**
     * Returns all users. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getUsers)
     */
    fun getUsers(): MutableList<Person> = sendRequest(this.api.getUsers(accessKey)).person

    /**
     * Returns all campuses. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCampuses)
     */
    fun getCampuses(): MutableList<Campus> = sendRequest(this.api.getCampuses(accessKey)).campus

    /**
     * Returns information about each program configured at the school. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getPrograms)
     */
    fun getPrograms(): MutableList<Program> = sendRequest(this.api.getPrograms(accessKey)).program

    /**
     * Returns all academic year IDs, as well as calendar years associated with each. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getAcademicYears)
     */
    fun getAcademicYears(): MutableList<AcademicYear> = sendRequest(this.api.getAcademicYears(accessKey)).academic_year

    /**
     * Returns all academic terms. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getAcademicTerms)
     */
    fun getAcademicTerms(): MutableList<AcademicTerm> = sendRequest(this.api.getAcademicTerms(accessKey)).academic_term

    /**
     * Returns the current academic term. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCurrentAcademicTerm)
     */
    fun getCurrentAcademicTerm(): AcademicTerm = sendRequest(this.api.getCurrentAcademicTerm(accessKey))

    /**
     * Returns courses from your catalog (only active courses are returned by default). [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseCatalog)
     * @param include_retired If set to true, retired courses will be returned as well. Not required.
     */
    fun getCourseCatalog(include_retired: Boolean = false): MutableList<Course> =
        sendRequest(this.api.getCourseCatalog(accessKey, include_retired = if (include_retired) 1 else null)).course

    /**
     * Returns a list of course groups. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseGroups)
     */
    fun getCourseGroups(): MutableList<CourseGroup> = sendRequest(this.api.getCourseGroups(accessKey)).course_group

    /**
     * Returns information about a course group.[ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseGroupInfo)
     * @param course_group_id The numeric ID of the course group you're interested in. Required.
     * @param academic_year_id The numeric ID of the academic year you're interested in. Defaults to the current academic year ID. Not required.
     */
    fun getCourseGroupInfo(course_group_id: Int, academic_year_id: Int? = null) =
        sendRequest(this.api.getCourseGroupInfo(accessKey, course_group_id = course_group_id, academic_year_id = academic_year_id))

    /**
     * Returns course instances for a given term (only active course instances are returned by default). [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getTermCourseInstances)
     * @param term_id The numeric ID of the term you're interested in. Required.
     */
    fun getTermCourseInstances(term_id: Int) =
        sendRequest(this.api.getTermCourseInstances(accessKey, term_id = term_id)).course_instance

    /**
     * Returns term students. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getTermStudents)
     * You must have the Registrar or Academic Admin role to call this task.
     * If the person has a profile picture, the <image> element will contain base64 encoded binary data. The <image> element won't be returned unless the person has a profile picture set.
     * There is a limit of 1000 results in the response.
     * The "num_results" attribute (in the <response> element) indicates the total number of possible results (regardless of the limit or the page parameter).
     * @param term_id Numeric ID of the term you're interested in. Defaults to the current academic term_id. Not Required.
     * @param program_id Possible values: ALL (default), NONE, or any numeric program_id. Not Required.
     * @param campus_id Possible values: ALL (default), 0 (None), or any numeric campus_id. Not Required.
     * @param return_image_data Boolean (e.g. 1 or 0). Returning binary image data will result in slower response times. Defaults to 0. Not Required.
     * @param page We limit the number of results returned (see comments), so which "page" would you like (e.g. page=1, page=2, page=3). Not Required.
     */
    fun getTermStudents(term_id: Int? = null, program_id: Int? = null, campus_id: Int? = null, return_image_data: Boolean = false, page: Int? = null) =
        sendRequest(this.api.getTermStudents(accessKey, term_id = term_id, program_id = program_id, campus_id = campus_id, return_image_data = if (return_image_data) 1 else 0, page = page))

    /**
     * Returns term enrollment for a particular academic term. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getTermEnrollment)
     * @param term_id Numeric ID of the academic term. Required.
     */
    fun getTermEnrollment(term_id: Int) =
        sendRequest(this.api.getTermEnrollment(accessKey, term_id = term_id)).enrollment

    /**
     * Returns all information related to tuition schedules and brackets configured for the institution. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getTuitionSchedules)
     */
    fun getTuitionSchedules() = sendRequest(this.api.getTuitionSchedules(accessKey)).tuition_schedule

    /**
     * Returns a student's term tuition schedules. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getStudentTermTuitionSchedules)
     * @param person_id The numeric person ID of the student you're interested in. Required.
     * @param academic_term_id The numeric ID of the academic term you're interested in. Required.
     */
    fun getStudentTermTuitionSchedules(person_id: Int, academic_term_id: Int) =
        sendRequest(this.api.getStudentTermTuitionSchedules(accessKey, person_id = person_id, academic_term_id = academic_term_id)).tuition_schedule

    /**
     * A course instance is created each time a course from the catalog is offered in a particular term.
     * If the same catalog course is offered multiple times in the same term, each instance will have a unique section number.[ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstance)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstance(instance_id: Int) =
        sendRequest(this.api.getCourseInstance(accessKey, instance_id = instance_id))

    /**
     * Assignment groups are worth a fixed percentage of the course (e.g. Quizzes are worth 10% of each student's final grade), and you can then add as many assignments within the Quizzes group as you like,
     * or even add new assignments to the group part-way through the term, and be guaranteed that the value of all those assignments together will still equal 10% of the course.
     * Even if no assignment groups are set up, a default assignment group of "Other" with a <groupid> of 0 will always be returned (and worth 100% of the course). [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceAssignmentGroups)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceAssignmentGroups(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceAssignmentGroups(accessKey, instance_id = instance_id)).assignment_group

    /**
     * Returns information about each assignment in a course - including which Assignment Group it belongs to.
     * Every assignment is attached to an Assignment Group - even if it's only the default "Other" group (which has a groupid of 0).
     * The "student_info" data will only be returned if you have the Academic Admin role, the Registrar role, or you are a course Faculty member. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceAssignments)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceAssignments(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceAssignments(accessKey, instance_id = instance_id)).assignment

    /**
     * Returns assignment submissions for a particular assignment and person. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getStudentAssignmentSubmissions)
     * @param assignment_id Numeric ID of the assignment. Required.
     * @param person_id Numeric ID of the person. Required.
     */
    fun getStudentAssignmentSubmissions(assignment_id: Int, person_id: Int) =
        sendRequest(this.api.getStudentAssignmentSubmissions(accessKey, assignment_id = assignment_id, person_id = person_id))

    /**
     * Returns the files attached to a course instance. See downloadFile. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceFiles)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceFiles(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceFiles(accessKey, instance_id = instance_id)).file

    /**
     * Returns links attached to a particular course offering. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseOfferingLinks)
     * @param course_offering_id The numeric ID of the course offering you're interested in.
     */
    fun getCourseOfferingLinks(course_offering_id: Int) =
        sendRequest(this.api.getCourseOfferingLinks(accessKey, course_offering_id = course_offering_id)).link

    /**
     * Returns the lessons attached to a course instance. See getLessonContent to get the HTML content of each lesson. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceLessons)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceLessons(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceLessons(accessKey, instance_id = instance_id)).lesson

    /**
     * Returns the lessons attached to a course instance. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getLessonContent)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     * @param lesson_id The numeric ID of the lesson you're interested in. Required.
     */
    fun getLessonContent(instance_id: Int, lesson_id: Int) =
        sendRequest(this.api.getLessonContent(accessKey, instance_id = instance_id, lesson_id = lesson_id))

    /**
     * Returns the meetings attached to a course instance. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceMeetings)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceMeetings(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceMeetings(accessKey, instance_id = instance_id)).meeting

    /**
     * Gets attendance for a course instance meeting. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceMeetingAttendance)
     * @param instanceID The numeric ID of the course instance you're interested in. Required.
     * @param meetingID The numeric ID of the meeting. Required.
     */
    fun getCourseInstanceMeetingAttendance(instanceID: Int, meetingID: Int) =
        sendRequest(this.api.getCourseInstanceMeetingAttendance(accessKey, instanceID = instanceID, meetingID = meetingID)).attendee

    /**
     * Returns all students enrolled, auditing, incomoplete, or withdrawn in a course instance. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceStudents)
     * Possible values for the status element are: ENROLLED, AUDITOR, WITHDRAWN, or INCOMPLETE. Note that there are some additional elements returned if the student is ENROLLED: grade, letter_grade, and attendance (both grade and attendance are percentages: so 97 mean 97%).
     * The <start_date> element represents the day the student started his or her current status... so if Jerry enrolled in the course on April 1 but then switched to auditing on May 16, his <status> would be AUDITOR and his <start_date> would be May 16.
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     */
    fun getCourseInstanceStudents(instance_id: Int) =
        sendRequest(this.api.getCourseInstanceStudents(accessKey, instance_id = instance_id)).courseinstance_student

    /**
     * Returns a single student from a course instance. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceStudent)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     * @param person_id The numeric ID of the student you're interested in. Required.
     */
    fun getCourseInstanceStudent(instance_id: Int, person_id: Int) =
        sendRequest(this.api.getCourseInstanceStudent(accessKey, instance_id = instance_id, person_id = person_id))

    /**
     * Gets attendance for all course instance meetings for a particular student. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCourseInstanceStudentAttendance)
     * @param instance_id The numeric ID of the course instance you're interested in. Required.
     * @param person_id The numeric ID of the student you're interested in. Required.
     */
    fun getCourseInstanceStudentAttendance(instance_id: Int, person_id: Int) =
        sendRequest(this.api.getCourseInstanceStudentAttendance(accessKey, instance_id = instance_id, person_id = person_id)).attendee

    /**
     * Returns the degree audit for a particular student. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getDegreeAudit)
     * @param person_id The numeric ID of the person you're interested in. Required.
     * @param degree_id The numeric ID of the degree you're interested in. Required.
     * @param academic_year_id The numeric ID of the academic year you're interested in. Required.
     * @param specialization_id The numeric ID of the specialization you're interested in. Not required.
     */
    fun getDegreeAudit(person_id: Int, degree_id: Int, academic_year_id: Int, specialization_id: Int? = null) =
        sendRequest(this.api.getDegreeAudit(accessKey, person_id = person_id, degree_id = degree_id, academic_year_id = academic_year_id, specialization_id = specialization_id))

    /**
     * Returns student discipline information for a particular person.
     * The current user must have the Discipline role, the Registrar role, the Academic Admin role, the Academic Auditor role, or be the Advisor of this student to call this task.
     * File data will only be returned if the current user has the Discipline or Academic Admin role. Files size is in bytes.
     * See the downloadFile or getFileDownloadURL API tasks to download the files. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getStudentDiscipline)
     * @param person_id The numeric ID of the person you're interested in. Required.
     */
    fun getStudentDiscipline(person_id: Int) =
        sendRequest(this.api.getStudentDiscipline(accessKey, person_id = person_id)).discipline

    /**
     * Returns the programs, degrees, and specializations for a particular student. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getStudentPrograms)
     * @param person_id The numeric ID of the person you're interested in. Required.
     */
    fun getStudentPrograms(person_id: Int) =
        sendRequest(this.api.getStudentPrograms(accessKey, person_id = person_id)).program

    /**
     * Returns student information for a particular person. If the person has a profile picture, the <image> element will contain base64 encoded binary data.
     * The <image> element won't be returned unless the person has a profile picture set.
     * An optional <advisors> element is returned for active students.
     * An optional <campuses> element is returned if the current user has the Staff role and your school has at least one campus set up.
     * <leave_of_absence_start_date> & <leave_of_absence_anticipated_return_date> will only be returned if the <leave_of_absence> value is "1". [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getStudentInfo)
     * @param person_id The numeric ID of the person you're interested in. Required.
     * @param return_image_data Boolean. Returning binary image data will result in slower response times. Defaults to false. Not required.
     */
    fun getStudentInfo(person_id: Int, return_image_data: Boolean? = null) =
        sendRequest(this.api.getStudentInfo(accessKey, person_id = person_id, return_image_data = return_image_data))

    /**
     * Returns basic profile data about a person: name, age, gender, tags, and contact information (address, phone, email).
     * If the person has a profile picture, the <image> element will contain base64 encoded binary data.
     * The <image> element won't be returned unless the person has a profile picture set. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getPerson)
     * @param person_id The numeric ID of the person you're interested in. Required.
     * @param return_image_data Boolean. Returning binary image data will result in slower response times. Defaults to false. Not required.
     */
    fun getPerson(person_id: Int, return_image_data: Boolean? = null) =
        sendRequest(this.api.getPerson(accessKey, person_id = person_id, return_image_data = return_image_data))

    /**
     * Gets the social security number for a particular person.
     * You must have SSN access permissions to call this task. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getPersonSSN)
     * @param person_id The numeric ID of the person you're interested in. Required.
     */
    fun getPersonSSN(person_id: Int) = sendRequest(this.api.getPersonSSN(accessKey, person_id = person_id))

    /**
     * Returns the transcript for a particular student. You must have the Academic Admin or Registrar role to call this task.
     * If pdf = true, this will return raw binary data rather than XML. The Content-Type HTTP header will indicate MIME type, and the Content-Disposition header will contain the file name.
     * If pdf = true, then this is a heavy API task. Multiple simultaneous calls to this task are not permitted and will fail with a rate limit error.
     * "Contra-units" - If your program uses credits the contra-units would be hours, and vice versa. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getTranscript)
     * @param person_id The numeric ID of the person you're interested in. Required.
     * @param pdf Boolean. If set to true, pdf content will be returned instead of xml. Defaults to false. Not required.
     * @param layout_id The numeric ID of the custom transcript layout you want to use. If not specified, the built-in default layout will be used. Not required.
     * @param program_id The numeric ID of the student's program you wish to export a transcript for. When using a custom layout, this parameter is required. Not required.
     * @param official (Requires the "pdf" parameter to be set to true) Boolean. If set to true, the official transcript pdf content will be returned. Defaults to false. Not required.
     * @param recipient (Requires the "pdf" parameter to be set to true) String. The recipient who will be receiving this transcript. Not required.
     * @param include_course_desciptions (Requires the "pdf" parameter to be set to true) Boolean. If set to true, course descriptions will be returned in the pdf content. Note: course descriptions will always be returned in the xml. The parameter has no effect on custom layouts. Defaults to false. Not required.
     */
    fun getTranscript(person_id: Int, pdf: Boolean? = null, layout_id: Int? = null, program_id: Int? = null, official: Boolean? = null, recipient: String? = null, include_course_desciptions: Boolean? = null) =
        sendRequest(this.api.getTranscript(accessKey, person_id = person_id, pdf = pdf, layout_id = layout_id, program_id = program_id, official = official, recipient = recipient, include_course_desciptions = include_course_desciptions))

    /**
     * Returns all available communication plans. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getCommunicationPlans)
     */
    fun getCommunicationPlans() = sendRequest(this.api.getCommunicationPlans(accessKey)).communication_plan

    /**
     * Returns all active communication plans attached to a person. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getPersonCommunicationPlans)
     * @param person_id The numeric ID of the person you're interested in. Required.
     */
    fun getPersonCommunicationPlans(person_id: Int) =
        sendRequest(this.api.getPersonCommunicationPlans(accessKey, person_id = person_id)).communication_plan_instance

    /**
     * Returns applications based on the filter conditions.You must have the Admissions role to call this task.
     * The "start_date" and "end_date" parameters are required when filtering by the "date_field" parameter.
     * There is a limit of 200 results in the response.
     * The "num_results" attribute in the <response> element indicates the total number of results regardless of the limit or offset. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getApplications)
     * @param date_field The name of the date field you want to filter by (e.g. APPLIED, DECISION, SUBMITTED, or WITHDRAWN). Not required.
     * @param start_date The start date used to filter the "date_field" parameter. Not required.
     * @param end_date The end date used to filter the "date_field" parameter. Not required.
     * @param term_id The numeric ID of the academic term you're interested in. Not required.
     * @param program_id The numeric ID of the program you're interested in. Not required.
     * @param degree_id The numeric ID of the degree you're interested in. Not required.
     * @param specialization_id The numeric ID of the specialization you're interested in. Not required.
     * @param offset The numeric value you want to offset the results by. Not required.
     */
    fun getApplications(date_field: String? = null, start_date: String? = null, end_date: String? = null, term_id: Int? = null, program_id: Int? = null, degree_id: Int? = null, specialization_id: Int? = null, offset: Int? = null) =
        sendRequest(this.api.getApplications(accessKey, date_field = date_field, start_date = start_date, end_date = end_date, term_id = term_id, program_id = program_id, degree_id = degree_id, specialization_id = specialization_id, offset = offset))

    /**
     * Returns all application components for a particular application. [ref](https://support.populiweb.com/hc/en-us/articles/223798747-API-Reference#getApplicationComponents)
     * @param application_id The numeric ID of the application you're interested in. Required.
     */
    fun getApplicationComponents(application_id: Int) =
        sendRequest(this.api.getApplicationComponents(accessKey, application_id = application_id)).component
}

interface PopuliApi {
    @FormUrlEncoded @POST(API_URI) fun requestAccessKey(@Field("username") username: String, @Field("password") password: String): Call<AccessKeyResponse>
    @FormUrlEncoded @POST(API_URI) fun getDegrees(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getDegrees"): Call<DegreeResponse>
    @FormUrlEncoded @POST(API_URI) fun getUsers(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getUsers"): Call<UserResponse>
    @FormUrlEncoded @POST(API_URI) fun getCampuses(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCampuses"): Call<CampusResponse>
    @FormUrlEncoded @POST(API_URI) fun getPrograms(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getPrograms"): Call<ProgramResponse>
    @FormUrlEncoded @POST(API_URI) fun getAcademicYears(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getAcademicYears"): Call<AcademicYearResponse>
    @FormUrlEncoded @POST(API_URI) fun getAcademicTerms(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getAcademicTerms"): Call<AcademicTermResponse>
    @FormUrlEncoded @POST(API_URI) fun getCurrentAcademicTerm(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCurrentAcademicTerm"): Call<AcademicTerm>
    @FormUrlEncoded @POST(API_URI) fun getCourseCatalog(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseCatalog", @Field("include_retired") include_retired: Int? = null): Call<CourseResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseGroups(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseGroups"): Call<CourseGroupResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseGroupInfo(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseGroupInfo", @Field("course_group_id") course_group_id: Int, @Field("academic_year_id") academic_year_id: Int? = null): Call<CourseGroupInfoResponse>
    @FormUrlEncoded @POST(API_URI) fun getTermCourseInstances(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getTermCourseInstances", @Field("term_id") term_id: Int): Call<TermCourseInstanceResponse>
    @FormUrlEncoded @POST(API_URI) fun getTermStudents(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getTermStudents", @Field("term_id") term_id: Int? = null, @Field("program_id") program_id: Int? = null, @Field("campus_id") campus_id: Int? = null, @Field("return_image_data") return_image_data: Int? = null, @Field("page") page: Int? = null): Call<TermStudentResponse>
    @FormUrlEncoded @POST(API_URI) fun getTermEnrollment(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getTermEnrollment", @Field("term_id") term_id: Int): Call<TermEnrollmentResponse>
    @FormUrlEncoded @POST(API_URI) fun getTuitionSchedules(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getTuitionSchedules"): Call<TuitionScheduleResponse>
    @FormUrlEncoded @POST(API_URI) fun getStudentTermTuitionSchedules(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getStudentTermTuitionSchedules", @Field("person_id") person_id: Int, @Field("academic_term_id") academic_term_id: Int): Call<StudentTermTuitionScheduleResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstance(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstance", @Field("instance_id") instance_id: Int): Call<CourseInstance>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceAssignmentGroups(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceAssignmentGroups", @Field("instance_id") instance_id: Int): Call<AssignmentGroupResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceAssignments(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceAssignments", @Field("instance_id") instance_id: Int): Call<AssignmentResponse>
    @FormUrlEncoded @POST(API_URI) fun getStudentAssignmentSubmissions(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getStudentAssignmentSubmissions", @Field("assignment_id") assignment_id: Int, @Field("person_id") person_id: Int): Call<StudentAssignmentSubmission>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceFiles(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceFiles", @Field("instance_id") instance_id: Int): Call<CourseInstanceFileResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseOfferingLinks(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseOfferingLinks", @Field("course_offering_id") course_offering_id: Int): Call<CourseOfferingLinkResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceLessons(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceLessons", @Field("instance_id") instance_id: Int): Call<CourseInstanceLessonResponse>
    @FormUrlEncoded @POST(API_URI) fun getLessonContent(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getLessonContent", @Field("instance_id") instance_id: Int, @Field("lesson_id") lesson_id: Int): Call<String>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceMeetings(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceMeetings", @Field("instanceID") instance_id: Int): Call<CourseInstanceMeetingResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceMeetingAttendance(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceMeetingAttendance", @Field("instanceID") instanceID: Int, @Field("meetingID") meetingID: Int): Call<CourseInstanceMeetingAttendanceResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceStudents(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceStudents", @Field("instance_id") instance_id: Int): Call<CourseInstanceStudentResponse>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceStudent(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceStudent", @Field("instance_id") instance_id: Int, @Field("person_id") person_id: Int): Call<CourseInstanceStudent>
    @FormUrlEncoded @POST(API_URI) fun getCourseInstanceStudentAttendance(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCourseInstanceStudentAttendance", @Field("instanceID") instance_id: Int, @Field("person_id") person_id: Int): Call<CourseInstanceStudentAttendanceResponse>
    @FormUrlEncoded @POST(API_URI) fun getDegreeAudit(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getDegreeAudit", @Field("person_id") person_id: Int, @Field("degree_id") degree_id: Int, @Field("academic_year_id") academic_year_id: Int, @Field("specialization_id") specialization_id: Int? = null): Call<DegreeAuditResponse>
    @FormUrlEncoded @POST(API_URI) fun getStudentDiscipline(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getStudentDiscipline", @Field("person_id") person_id: Int): Call<StudentDisciplineResponse>
    @FormUrlEncoded @POST(API_URI) fun getStudentPrograms(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getStudentPrograms", @Field("person_id") person_id: Int): Call<StudentProgramResponse>
    @FormUrlEncoded @POST(API_URI) fun getStudentInfo(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getStudentInfo", @Field("person_id") person_id: Int, @Field("return_image_data") return_image_data: Boolean? = null): Call<StudentInfo>
    @FormUrlEncoded @POST(API_URI) fun getPerson(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getPerson", @Field("person_id") person_id: Int, @Field("return_image_data") return_image_data: Boolean? = null): Call<PersonInfo>
    @FormUrlEncoded @POST(API_URI) fun getPersonSSN(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getPersonSSN", @Field("person_id") person_id: Int): Call<PersonSSN>
    @FormUrlEncoded @POST(API_URI) fun getTranscript(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getTranscript", @Field("person_id") person_id: Int, @Field("pdf") pdf: Boolean? = null, @Field("layout_id") layout_id: Int? = null, @Field("program_id") program_id: Int? = null, @Field("official") official: Boolean? = null, @Field("recipient") recipient: String? = null, @Field("include_course_desciptions") include_course_desciptions: Boolean? = null): Call<Transcript>
    @FormUrlEncoded @POST(API_URI) fun getCommunicationPlans(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getCommunicationPlans"): Call<CommunicationPlanResponse>
    @FormUrlEncoded @POST(API_URI) fun getPersonCommunicationPlans(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getPersonCommunicationPlans", @Field("person_id") person_id: Int): Call<PersonCommunicationPlanResponse>
    @FormUrlEncoded @POST(API_URI) fun getApplications(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getApplications", @Field("date_field") date_field: String? = null, @Field("start_date") start_date: String? = null, @Field("end_date") end_date: String? = null, @Field("term_id") term_id: Int? = null, @Field("program_id") program_id: Int? = null, @Field("degree_id") degree_id: Int? = null, @Field("specialization_id") specialization_id: Int? = null, @Field("offset") offset: Int? = null): Call<ApplicationResponse>
    @FormUrlEncoded @POST(API_URI) fun getApplicationComponents(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String = "getApplicationComponents", @Field("application_id") application_id: Int): Call<ApplicationComponentResponse>

    //for debug
    @FormUrlEncoded @POST(API_URI) fun getRaw(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String): Call<String>

    @FormUrlEncoded @POST(API_URI) fun getRawWithCourseInstanceId(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String, @Field("instance_id") instance_id: Int): Call<String>
    @FormUrlEncoded @POST(API_URI) fun getRawWithCourseInstanceIdPersonId(@Field(FIELD_ACCESS_KEY) accessKey: String, @Field(FIELD_TASK) task: String, @Field("instanceID") instance_id: Int, @Field("person_id") person_id: Int): Call<String>
}

private const val API_URI = "api/"
private const val FIELD_ACCESS_KEY = "access_key"
private const val FIELD_TASK = "task"

private fun <T> sendRequest(call: Call<T>): T {
    val response: Response<T> = call.execute()

    if (!response.isSuccessful) {
        throw RuntimeException("request not success, error body: ${response.errorBody()}")
    }
    return response.body() ?: throw RuntimeException("response body was null")
}
