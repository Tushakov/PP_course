package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.repo.*

fun Route.lessonRoutes() {
    route(Config.lessonsPath) {
        repoRoutes(lessonsRepo)
        get("{id}/table") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val lesson = lessonsRepo.read(id) ?: return@get call.respondText(
                "Missing lesson",
                status = HttpStatusCode.BadRequest
            )
            val printableLessons: List<PrintableLesson>?
            val cellSchedules = cellSchedulesRepo.read().map { it.elem }.filter { it.lessonId == lesson.id }
            printableLessons = cellSchedules.map { cell ->
                val groups = cell.groupIds.map { id -> groupsRepo.read(id)?.elem?.name ?: "" }
                val classroom = classroomsRepo.read(cell.classroomId)?.elem?.name ?: ""
                val teacher = teachersRepo.read(cell.teacherId)?.elem?.shortname() ?: ""
                PrintableLesson(cell, "${cell.type} ${groups.joinToString("; ")} ауд.$classroom $teacher", "")
            }
            val res: Pair<String, List<PrintableLesson>> = Pair(lesson.elem.name, printableLessons)
            call.respond(res)
        }
    }
}

