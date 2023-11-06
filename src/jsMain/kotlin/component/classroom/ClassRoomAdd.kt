package component.classroom

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.ClassRoom
import ru.altmanea.webapp.data.Group
import web.html.InputType

val CClassRoomAdd = FC<EditAddProps<ClassRoom>>("ClassRoomAdd") { props ->
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
            props.saveElement(ClassRoom(name))
        }
    }
}
