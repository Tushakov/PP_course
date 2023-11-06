package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.repo.*

fun Route.teacherRoutes() {
    route(Config.teachersPath) {
        repoRoutes(teachersRepo)
        get("{id}/table") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val teacher = teachersRepo.read(id) ?: return@get call.respondText(
                "Missing teacher",
                status = HttpStatusCode.BadRequest
            )
            val printableLessons: List<PrintableLesson>?
            val cellSchedules = cellSchedulesRepo.read().map { it.elem }.filter { it.teacherId == teacher.id }
            printableLessons = cellSchedules.map { cell ->
                val groups = cell.groupIds.map { id -> groupsRepo.read(id)?.elem?.name ?: "" }
                val classroom = classroomsRepo.read(cell.classroomId)?.elem?.name ?: ""
                val name = lessonsRepo.read(cell.lessonId)?.elem?.name ?: ""
                PrintableLesson(cell, "${groups.joinToString("; ")} ауд.${classroom}", "${cell.type} $name")
            }
            val res: Pair<String, List<PrintableLesson>> = Pair(teacher.elem.shortname(), printableLessons)
            call.respond(res)
        }
    }
}