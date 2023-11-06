package component.lesson

import component.template.ElementInListProps
import react.FC
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Teacher


val CLessonInList = FC<ElementInListProps<Item<Lesson>>>("LessonInList") { props ->
    Link {
        +props.element.elem.name
        to = props.element.id
    }
}