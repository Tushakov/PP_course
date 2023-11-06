package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.common.ItemId

@Serializable
class Lesson(
    val name: String
)

typealias lessonId = ItemId