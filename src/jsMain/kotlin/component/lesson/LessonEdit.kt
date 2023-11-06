package component.lesson

import component.template.EditItemProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CLessonEdit = FC<EditItemProps<Lesson>>("LessonEdit") { props ->
    var name by useState(props.item.elem.name)
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
