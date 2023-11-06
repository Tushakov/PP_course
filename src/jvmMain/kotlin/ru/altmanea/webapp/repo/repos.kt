package ru.altmanea.webapp.repo

import ru.altmanea.webapp.data.*

val groupsRepo = ListRepo<Group>()
val teachersRepo = ListRepo<Teacher>()
val classroomsRepo = ListRepo<ClassRoom>()
val cellSchedulesRepo = ListRepo<CellSchedule>()
val lessonsRepo = ListRepo<Lesson>()

fun createTestData() {
    listOf(
        Lesson("ООП"),
        Lesson("ТОАПС"),
        Lesson("Базы данных"),
        Lesson("Экономика"),
        Lesson("Схемотехника")
    ).forEach {
        lessonsRepo.create(it)
    }

    listOf(
        Teacher("доц.", "Альтман", "Евгений", "Анатольевич"),
        Teacher("доц.", "Окишев", "Андрей", "Сергеевич"),
        Teacher("доц.", "Тихонова", "Наталья", "Алексеевна"),
        Teacher("доц.", "Севостьянова", "Елена", "Владимировна"),
        Teacher("ст.пр.", "Циркин", "Виталий", "Степанович"),
    ).forEach {
        teachersRepo.create(it)
    }

    listOf(
        ClassRoom("1-112"),
        ClassRoom("1-329"),
        ClassRoom("1-325"),
        ClassRoom("1-330"),
        ClassRoom("1-421"),
        ClassRoom("1-210")
    ).forEach { classroomsRepo.create(it) }

    listOf(Group("20з"), Group("20м")).forEach { groupsRepo.create(it) }

    val lessons = lessonsRepo.read()

    val oop = lessons.findLast { it.elem.name == "ООП" }!!
    val toaps = lessons.findLast { it.elem.name == "ТОАПС" }!!
    val bd = lessons.findLast { it.elem.name == "Базы данных" }!!
    val economy = lessons.findLast { it.elem.name == "Экономика" }!!
    val schem = lessons.findLast { it.elem.name == "Схемотехника" }!!

    val teachers = teachersRepo.read()

    val altman = teachers.findLast { it.elem.lastname == "Альтман" }!!
    val okishev = teachers.findLast { it.elem.lastname == "Окишев" }!!
    val tihonova = teachers.findLast { it.elem.lastname == "Тихонова" }!!
    val sevostyanova = teachers.findLast { it.elem.lastname == "Севостьянова" }!!
    val tsirkin = teachers.findLast { it.elem.lastname == "Циркин" }!!

    val classrooms = classroomsRepo.read()

    val room112 = classrooms.findLast { it.elem.name == "1-112" }!!
    val room329 = classrooms.findLast { it.elem.name == "1-329" }!!
    val room325 = classrooms.findLast { it.elem.name == "1-325" }!!
    val room330 = classrooms.findLast { it.elem.name == "1-330" }!!
    val room421 = classrooms.findLast { it.elem.name == "1-421" }!!
    val room210 = classrooms.findLast { it.elem.name == "1-210" }!!

    val groups = groupsRepo.read()

    val group20m = groups.findLast { it.elem.name == "20м" }!!
    val group20z = groups.findLast { it.elem.name == "20з" }!!

    listOf(
        CellSchedule(
            LessonType.LECTURE,
            CellPosition(WeekType.EVEN, DayOfWeek.MONDAY, lessonPeriod[0]),
            oop.id,
            altman.id,
            room112.id,
            listOf(group20z.id, group20m.id)
        ), CellSchedule(
            LessonType.LAB,
            CellPosition(WeekType.EVEN, DayOfWeek.TUESDAY, lessonPeriod[4]),
            toaps.id,
            okishev.id,
            room329.id,
            listOf(group20m.id)
        ), CellSchedule(
            LessonType.LAB,
            CellPosition(WeekType.EVEN, DayOfWeek.WEDNESDAY, lessonPeriod[1]),
            oop.id,
            altman.id,
            room325.id,
            listOf(group20z.id)
        ), CellSchedule(
            LessonType.LAB,
            CellPosition(WeekType.ODD, DayOfWeek.MONDAY, lessonPeriod[1]),
            bd.id,
            tihonova.id,
            room330.id,
            listOf(group20z.id)
        ), CellSchedule(
            LessonType.PRACTICE, CellPosition(
                WeekType.ODD, DayOfWeek.WEDNESDAY, lessonPeriod[1]
            ), oop.id, altman.id, room112.id, listOf(group20m.id)
        ), CellSchedule(
            LessonType.KSR,
            CellPosition(WeekType.ODD, DayOfWeek.TUESDAY, lessonPeriod[1]),
            economy.id,
            sevostyanova.id,
            room421.id,
            listOf(group20m.id)
        ), CellSchedule(
            LessonType.KRB,
            CellPosition(WeekType.ODD, DayOfWeek.THURSDAY, lessonPeriod[1]),
            schem.id,
            tsirkin.id,
            room210.id,
            listOf(group20z.id, group20m.id)
        )
    ).forEach {
        cellSchedulesRepo.create(it)
    }
}
