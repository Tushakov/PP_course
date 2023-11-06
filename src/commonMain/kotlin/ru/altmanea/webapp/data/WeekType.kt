package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
enum class WeekType(private val representation: String) {
    ODD("Нечетная неделя"), EVEN("Четная неделя");

    override fun toString() = representation
}

