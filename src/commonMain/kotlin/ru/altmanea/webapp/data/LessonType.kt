package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
enum class LessonType(private val representation: String) {
    LECTURE("Лек."),
    PRACTICE("Пр."),
    LAB("Лаб."),
    KSR("КСР"),
    KRB("КРБ"),
    NONE("");
    override fun toString() = representation
}

