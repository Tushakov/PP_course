package component.teacher

import component.template.ElementInListProps
import react.FC
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Teacher


val CTeacherInList = FC<ElementInListProps<Item<Teacher>>>("TeacherInList") { props ->
    Link {
        +props.element.elem.fullname()
        to = props.element.id
    }
}