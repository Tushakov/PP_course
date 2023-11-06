package component.lesson

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CLessonAdd = FC<EditAddProps<Lesson>>("LessonAdd") { props ->
    var name by useState("")
    span {
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(Lesson(name))
        }
    }
}
