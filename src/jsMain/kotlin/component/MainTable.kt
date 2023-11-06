package component

import csstype.Border
import csstype.LineStyle
import csstype.px
import emotion.react.css
import js.core.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.router.useParams
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.common.ItemId
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.DayOfWeek
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.data.WeekType
import ru.altmanea.webapp.data.lessonPeriod
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface MainTableProps : Props {
    var url: String
}

val CMainTable = FC<MainTableProps> { props ->
    val param = useParams()["id"] as ItemId

    val table = arrayOf(props.url).unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = table,
        queryFn = {
            fetchText(props.url + param + "/table")
        }
    )
    val weekDays = DayOfWeek.values().dropLast(1)

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val printableData =
            Json.decodeFromString<Pair<String, List<PrintableLesson>>>(query.data ?: "")
        WeekType.values().forEach { weekType ->
            header {
                +"$weekType. Критерий: ${printableData.first}"
                table {
                    css {
                        border = Border(10.px, LineStyle.groove)
                    }
                    tr {
                        th { +"" }
                        weekDays.forEach { weekDay ->
                            th { +weekDay.toString() }
                        }
                    }
                    lessonPeriod.forEach { lessonPeriod ->
                        tr {
                            td {
                                +lessonPeriod
                            }
                            weekDays.forEach { weekDay ->
                                val lesson = printableData.second.firstOrNull { printable ->
                                    printable.lesson.position.weekType == weekType &&
                                            printable.lesson.position.dayOfWeek == weekDay &&
                                            printable.lesson.position.lessonPeriod == lessonPeriod
                                }
                                    ?: PrintableLesson.empty()
                                td {
                                    css {
                                        border = Border(10.px, LineStyle.groove)
                                    }
                                    +"${lesson.info}\n${lesson.fullName}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}