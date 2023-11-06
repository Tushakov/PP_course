package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
enum class DayOfWeek(private val representation: String) {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    override fun toString() = representation
}