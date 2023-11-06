package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.common.ItemId

@Serializable
class Group(
    val name: String
)

typealias groupId = ItemId