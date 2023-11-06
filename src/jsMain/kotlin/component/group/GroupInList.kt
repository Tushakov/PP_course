package component.group

import component.template.ElementInListProps
import react.FC
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Group
import ru.altmanea.webapp.data.Lesson


val CGroupInList = FC<ElementInListProps<Item<Group>>>("GroupInList") { props ->
    Link {
        +props.element.elem.name
        to = props.element.id
    }
}