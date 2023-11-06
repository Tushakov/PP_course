package component.teacher

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Teacher
import web.html.InputType

val CTeacherAdd = FC<EditAddProps<Teacher>>("TeacherAdd") { props ->
    var position by useState("")
    var lastname by useState("")
    var firstname by useState("")
    var surname by useState("")
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
