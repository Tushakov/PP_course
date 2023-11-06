import component.CAddPosition
import component.CMainTable
import component.classroom.CClassRoomAdd
import component.classroom.CClassRoomEdit
import component.classroom.CClassRoomInList
import component.group.CGroupAdd
import component.group.CGroupEdit
import component.group.CGroupInList
import component.lesson.CLessonAdd
import component.lesson.CLessonEdit
import component.lesson.CLessonInList
import component.teacher.CTeacherAdd
import component.teacher.CTeacherEdit
import component.teacher.CTeacherInList
import component.template.RestContainerChildProps
import component.template.restContainer
import component.template.restList
import csstype.Display
import csstype.Flex
import csstype.JustifyContent
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.create
import react.createContext
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.ClassRoom
import ru.altmanea.webapp.data.Group
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Teacher
import tanstack.query.core.QueryClient
import tanstack.query.core.QueryKey
import tanstack.react.query.QueryClientProvider
import tanstack.react.query.devtools.ReactQueryDevtools
import web.dom.document

val invalidateRepoKey = createContext<QueryKey>()

fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()

            val links = listOf(
                Pair("Преподаватели", Config.teachersPath),
                Pair("Предметы", Config.lessonsPath),
                Pair("Группы", Config.groupsPath),
                Pair("Аудитории", Config.classroomsPath),
                Pair("Все позиции", Config.cellschedulePath + "fulltable"),
                Pair("Добавить позицию", Config.cellschedulePath),
            )
            h1 {
                +"Выберите критерий вывода расписания"
            }
            div {
                css { display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                }
                links.forEach { (name, link) ->
                    li {
                        Link {
                            css { flex = Flex.minContent }
                            +name
                            to = link
                        }
                    }
                }
            }
            Routes {
                Route {
                    path = "teachers"
                    val list: FC<RestContainerChildProps<Teacher>> =
                        restList(
                            CTeacherInList,
                            CTeacherAdd,
                            CTeacherEdit
                        )
                    element = restContainer(
                        Config.teachersPath,
                        list,
                        "teachers"
                    ).create()
                }
                Route {
                    path = "lessons"
                    val list: FC<RestContainerChildProps<Lesson>> =
                        restList(
                            CLessonInList,
                            CLessonAdd,
                            CLessonEdit
                        )
                    element = restContainer(
                        Config.lessonsPath,
                        list,
                        "lessons"
                    ).create()
                }
                Route {
                    path = "groups"
                    val list: FC<RestContainerChildProps<Group>> =
                        restList(
                            CGroupInList,
                            CGroupAdd,
                            CGroupEdit
                        )
                    element = restContainer(
                        Config.groupsPath,
                        list,
                        "groups"
                    ).create()
                }
                Route {
                    path = "classrooms"
                    val list: FC<RestContainerChildProps<ClassRoom>> =
                        restList(
                            CClassRoomInList,
                            CClassRoomAdd,
                            CClassRoomEdit
                        )
                    element = restContainer(
                        Config.classroomsPath,
                        list,
                        "classrooms"
                    ).create()
                }
                Route {
                    path = Config.teachersPath + ":id"
                    element = CMainTable.create {
                        url = Config.teachersPath
                    }
                }
                Route {
                    path = Config.lessonsPath + ":id"
                    element = CMainTable.create {
                        url = Config.lessonsPath
                    }
                }
                Route {
                    path = Config.groupsPath + ":id"
                    element = CMainTable.create {
                        url = Config.groupsPath
                    }
                }
                Route {
                    path = Config.classroomsPath + ":id"
                    element = CMainTable.create {
                        url = Config.classroomsPath
                    }
                }
                Route {
                    path = Config.cellschedulePath
                    element = CAddPosition.create()
                }
                Route {
                    path = Config.cellschedulePath + ":id"
                    element = CMainTable.create {
                        url = Config.cellschedulePath
                    }
                }
            }
            ReactQueryDevtools { }
        }
    }
}