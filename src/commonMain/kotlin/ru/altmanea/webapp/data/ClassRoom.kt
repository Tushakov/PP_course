package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.common.ItemId

@Serializable
class ClassRoom(
    val name: String
)

typealias classroomId = ItemId