package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.repo.*

fun Route.groupRoutes() {
    route(Config.groupsPath) {
        repoRoutes(groupsRepo)
        get("{id}/table") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val group = groupsRepo.read(id) ?: return@get call.respondText(
                "Missing group",
                status = HttpStatusCode.BadRequest
            )
            val printableLessons: List<PrintableLesson>?
            val cellSchedules = cellSchedulesRepo.read().map { it.elem }.filter { it.groupIds.contains(group.id) }
            printableLessons = cellSchedules.map { cell ->
                val classroom = classroomsRepo.read(cell.classroomId)?.elem?.name ?: ""
                val name = lessonsRepo.read(cell.lessonId)?.elem?.name ?: ""
                val teacher = teachersRepo.read(cell.teacherId)?.elem?.shortname() ?: ""
                PrintableLesson(cell, "ауд.${classroom} $teacher", "${cell.type} $name")
            }
            val res: Pair<String, List<PrintableLesson>> = Pair(group.elem.name, printableLessons)
            call.respond(res)
        }
    }
}