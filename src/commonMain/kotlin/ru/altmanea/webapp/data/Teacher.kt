package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import ru.altmanea.webapp.common.ItemId

@Serializable
class Teacher(
    val position: String,
    val lastname: String,
    val firstname: String,
    val surname: String
) {
    fun fullname() = "$position $lastname $firstname $surname"
    fun shortname() = "$position $lastname ${firstname[0]}. ${surname[0]}."
}

typealias teacherId = ItemId