package component

import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.span
import react.useRef
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.*
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import web.html.HTMLSelectElement
import kotlin.js.json

val CAddPosition = FC<Props>("CAddPosition") {
    val lessonTypeSelectRef = useRef<HTMLSelectElement>()
    val weekTypeSelectRef = useRef<HTMLSelectElement>()
    val dayOfWeekSelectRef = useRef<HTMLSelectElement>()
    val lessonPeriodSelectRef = useRef<HTMLSelectElement>()
    val lessonIdSelectRef = useRef<HTMLSelectElement>()
    val teacherIdSelectRef = useRef<HTMLSelectElement>()
    val classroomIdSelectRef = useRef<HTMLSelectElement>()
    val groupIdsSelectRef = useRef<HTMLSelectElement>()

    val queryClient = useQueryClient()

    val lessonQuery = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("lessonId").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.lessonsPath)
        }
    )

    val teacherQuery = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("teacherId").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.teachersPath)
        }
    )

    val classroomQuery = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("classroomId").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.classroomsPath)
        }
    )

    val groupQuery = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("groupIds").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.groupsPath)
        }
    )

    val addMutation = useMutation<HTTPResult, Any, CellSchedule, Any>(
        mutationFn = { element: CellSchedule ->
            fetch(
                Config.cellschedulePath,
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(element)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(arrayOf("addPosition").unsafeCast<QueryKey>())
            }
        }
    )

    if (lessonQuery.isLoading) ReactHTML.div { +"Loading .." }
    else if (teacherQuery.isLoading) ReactHTML.div { +"Loading .." }
    else if (classroomQuery.isLoading) ReactHTML.div { +"Loading .." }
    else if (groupQuery.isLoading) ReactHTML.div { +"Loading .." }
    else {
        val lessons = Json.decodeFromString<List<Item<Lesson>>>(lessonQuery.data ?: "")
        val teachers = Json.decodeFromString<List<Item<Teacher>>>(teacherQuery.data ?: "")
        val classrooms = Json.decodeFromString<List<Item<ClassRoom>>>(classroomQuery.data ?: "")
        val groups = Json.decodeFromString<List<Item<Group>>>(groupQuery.data ?: "")
        div {
            li {
                select {
                    ref = lessonTypeSelectRef
                    LessonType.values().forEach { lessonType ->
                        option {
                            +"$lessonType"
                            value = Json.encodeToString(lessonType)
                        }
                    }
                }
            }
            li {
                span {
                    select {
                        ref = weekTypeSelectRef
                        WeekType.values().forEach { weekType ->
                            option {
                                +"$weekType"
                                value = Json.encodeToString(weekType)
                            }
                        }
                    }
                    select {
                        ref = dayOfWeekSelectRef
                        DayOfWeek.values().forEach { dayOfWeek ->
                            option {
                                +"$dayOfWeek"
                                value = Json.encodeToString(dayOfWeek)
                            }
                        }
                    }
                    select {
                        ref = lessonPeriodSelectRef
                        lessonPeriod.forEach { lessonPeriod ->
                            option {
                                +lessonPeriod
                                value = lessonPeriod
                            }
                        }
                    }
                }
            }
            li {
                select {
                    ref = lessonIdSelectRef
                    lessons.forEach { lesson ->
                        option {
                            +lesson.elem.name
                            value = lesson.id
                        }
                    }
                }
            }
            li {
                select {
                    ref = teacherIdSelectRef
                    teachers.forEach { teacher ->
                        option {
                            +teacher.elem.fullname()
                            value = teacher.id
                        }
                    }
                }
            }
            li {
                select {
                    ref = classroomIdSelectRef
                    classrooms.forEach { classroom ->
                        option {
                            +classroom.elem.name
                            value = classroom.id
                        }
                    }
                }
            }
            li {
                select {
                    ref = groupIdsSelectRef
                    groups.forEach { group ->
                        option {
                            +group.elem.name
                            value = group.id
                        }
                    }
                }
            }
        }
        button {
            +"Добавить урок"
            onClick = {
                addMutation.mutateAsync(
                    CellSchedule(
                        type = Json.decodeFromString(lessonTypeSelectRef.current!!.value),
                        position = CellPosition(
                            Json.decodeFromString(weekTypeSelectRef.current!!.value),
                            Json.decodeFromString(dayOfWeekSelectRef.current!!.value),
                            lessonPeriodSelectRef.current!!.value
                        ),
                        lessonId = lessonIdSelectRef.current!!.value,
                        teacherId = teacherIdSelectRef.current!!.value,
                        classroomId = classroomIdSelectRef.current!!.value,
                        groupIds = listOf(groupIdsSelectRef.current!!.value)
                    ), null
                )
            }
        }
    }
}