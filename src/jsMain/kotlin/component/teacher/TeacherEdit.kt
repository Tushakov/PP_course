package component.teacher

import component.template.EditItemProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CTeacherEdit = FC<EditItemProps<Teacher>>("TeacherEdit") { props ->
    var position by useState(props.item.elem.position)
    var lastname by useState(props.item.elem.lastname)
    var firstname by useState(props.item.elem.firstname)
    var surname by useState(props.item.elem.surname)
    span {
        input {
            type = InputType.text
            value = position
            onChange = { position = it.target.value }
        }
        input {
            type = InputType.text
            value = lastname
            onChange = { lastname = it.target.value }
        }
        input {
            type = InputType.text
            value = firstname
            onChange = { firstname = it.target.value }
        }
        input {
            type = InputType.text
            value = surname
            onChange = { surname = it.target.value }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(Teacher(position, lastname, firstname, surname))
        }
    }
}
