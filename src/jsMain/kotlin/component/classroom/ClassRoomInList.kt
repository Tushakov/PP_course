package component.classroom

import component.template.ElementInListProps
import react.FC
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.ClassRoom
import ru.altmanea.webapp.data.Group


val CClassRoomInList = FC<ElementInListProps<Item<ClassRoom>>>("ClassRoomInList") { props ->
    Link {
        +props.element.elem.name
        to = props.element.id
    }
}