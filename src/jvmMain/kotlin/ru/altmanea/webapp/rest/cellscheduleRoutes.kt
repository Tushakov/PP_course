package ru.altmanea.webapp.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.PrintableLesson
import ru.altmanea.webapp.repo.*

fun Route.cellscheduleRoutes() {
    route(Config.cellschedulePath) {
        repoRoutes(cellSchedulesRepo)
        get("fulltable/table") {
            val printableLessons: List<PrintableLesson>?
            val cellSchedules = cellSchedulesRepo.read().map { it.elem }
            printableLessons = cellSchedules.map { cell ->
                val groups = cell.groupIds.map { id -> groupsRepo.read(id)?.elem?.name ?: "" }
                val teacher = teachersRepo.read(cell.teacherId)?.elem?.shortname() ?: ""
                val classroom = classroomsRepo.read(cell.classroomId)?.elem?.name ?: ""
                val lesson = lessonsRepo.read(cell.lessonId)?.elem?.name ?: ""
                PrintableLesson(cell, "${groups.joinToString("; ")} $classroom $teacher", "${cell.type} $lesson")
            }
            val res: Pair<String, List<PrintableLesson>> = Pair("full", printableLessons)
            call.respond(res)
        }
    }
}