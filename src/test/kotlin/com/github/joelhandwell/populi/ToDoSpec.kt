package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.LocalDateTime

private val todo = ToDo(
    todoid = 777,
    due_date = LocalDate.parse("2009-07-02"),
    overdue = 1,
    assigned_to = 11111,
    assigned_by = 22222,
    attached_to = 33333,
    completed_time = null,
    added_time = LocalDateTime.of(2009, 6, 25, 17, 57, 21),
    content = "Talk to Billy about his lack of academic progress",
    assigned_to_name = "Robert McStaff",
    assigned_by_name = "Reginald McPresident",
    attached_to_name = "Billy Student",
    attached_to_type = "PERSON"
)

val toDoResponse = ToDoResponse(356, mutableListOf(todo))

object ToDoSpec : Spek({

    describe("ToDo") {

        it("marshal to xml") { assertMarshals(toDoResponse) }

        it("unmarshal from xml") { assertUnmarshals(toDoResponse, getTodosXml) }
    }
})

const val getTodosXml = """
<response num_results="356">
    <todo>
        <todoid>777</todoid>
        <due_date>2009-07-02</due_date>
        <overdue>1</overdue>
        <assigned_to>11111</assigned_to>
        <assigned_by>22222</assigned_by>
        <attached_to>33333</attached_to>
        <completed_time/>
        <added_time>2009-06-25 17:57:21</added_time>
        <content>Talk to Billy about his lack of academic progress</content>
        <assigned_to_name>Robert McStaff</assigned_to_name>
        <assigned_by_name>Reginald McPresident</assigned_by_name>
        <attached_to_name>Billy Student</attached_to_name>
        <attached_to_type>PERSON</attached_to_type>
    </todo>
</response>"""