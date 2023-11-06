package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class PrintableLesson(
    val lesson: CellSchedule,
    val info: String,
    val fullName: String
) {
    companion object {
        fun empty(): PrintableLesson = PrintableLesson(
            CellSchedule(
                LessonType.NONE,
                CellPosition(WeekType.EVEN, DayOfWeek.MONDAY, ""),
                "",
                "",
                "",
                listOf("")
            ), "", ""
        )
    }
}