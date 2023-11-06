package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.repo.*

fun Route.classroomRoutes() {
    route(Config.classroomsPath) {
        repoRoutes(classroomsRepo)
        get("{id}/table") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val classroom = classroomsRepo.read(id) ?: return@get call.respondText(
                "Missing classroom",
                status = HttpStatusCode.BadRequest
            )
            val printableLessons: List<PrintableLesson>?
            val cellSchedules = cellSchedulesRepo.read().map { it.elem }.filter { it.classroomId == classroom.id }
            printableLessons = cellSchedules.map { cell ->
                val groups = cell.groupIds.map { id -> groupsRepo.read(id)?.elem?.name ?: "" }
                val teacher = teachersRepo.read(cell.teacherId)?.elem?.shortname() ?: ""
                val name = lessonsRepo.read(cell.lessonId)?.elem?.name ?: ""
                PrintableLesson(cell, "${groups.joinToString("; ")} $teacher", "${cell.type} $name")
            }
            val res: Pair<String, List<PrintableLesson>> = Pair(classroom.elem.name, printableLessons)
            call.respond(res)
        }
    }
}