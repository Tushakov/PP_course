package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class CellSchedule(
    val type: LessonType,
    val position: CellPosition,
    val lessonId: lessonId,
    val teacherId: teacherId,
    val classroomId: classroomId,
    val groupIds: List<groupId>
)
