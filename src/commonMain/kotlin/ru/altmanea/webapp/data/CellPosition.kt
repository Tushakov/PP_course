package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class CellPosition(
    val weekType: WeekType,
    val dayOfWeek: DayOfWeek,
    val lessonPeriod: String,
)