package com.lab5.ui.screens.subjectsList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectsListViewModel(
    private val database: Lab5Database
) : ViewModel() {

    /**
    Flow - the (container, channel, observer), can accept and move data from producer to consumers
    StateFlow - the flow which also store data.
    MutableStateFlow - the stateFlow which can accept data (which you can fill)

    _subjectListStateFlow - private MutableStateFlow - ViewModel (add new data here)
    subjectListStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     */
    private val _subjectListStateFlow = MutableStateFlow<List<SubjectEntity>>(emptyList())
    val subjectListStateFlow: StateFlow<List<SubjectEntity>>
        get() = _subjectListStateFlow


    /**
    Init block of ViewModel - invokes once the ViewModel is created
     */
    init {
        viewModelScope.launch {
            val subjects = database.subjectsDao.getAllSubjects()
            if (subjects.isEmpty()) {
                preloadData()
            } else {
                _subjectListStateFlow.value = subjects
            }
            Log.d("SubjectsListViewModel", "Subjects loaded: ${_subjectListStateFlow.value}")
        }
    }

    private suspend fun  preloadData() {
        // List of subjects
        val listOfSubject = listOf(
            SubjectEntity(id = 1, title = "Програмування мобільних додатків"),
            SubjectEntity(id = 2, title = "Розгортання інформаційно-комунікаційних систем"),
            SubjectEntity(id = 3, title = "Проєктування мультисервісних інформаційно-комунікаційних систем"),
            SubjectEntity(id = 4, title = "Мережева безпека"),
        )

        // List of labs
        val listOfSubjectLabs = listOf(
            SubjectLabEntity(
                id = 1,
                subjectId = 1,
                title = "Знайомство з Android ",
                description = "Встановлення середовища розробки",
                comment = "Дедлайн 09.17" ,
            ),
            SubjectLabEntity(
                id = 2,
                subjectId = 1,
                title = "Вивчення UI операцій",
                description = "Написати простий ToDo додаток",
                comment = "Виконано" ,
                isCompleted = true
            ),

            SubjectLabEntity(
                id = 3,
                subjectId = 2 ,
                title = "Створення віртуальної машини за допомогою Vagrant",
                description = "Дослідити існуючі програми для віртуалізації ОС, описати процес та розгорнути віртуальну машину (Ubnuntu без GUI) на власній хостовій ОС",
                comment = "",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 4,
                subjectId = 2 ,
                title = "Intro to Docker",
                description = "Дослідити існуючі програми для віртуалізації ОС, описати процес та розгорнути віртуальну машину (Ubnuntu без GUI) на власній хостовій ОС",
                comment = "Захист в понеділок",
                inProgress = true
            ),
            SubjectLabEntity(
                id = 5,
                subjectId = 3 ,
                title = "Визначення вимогдо розроблюваної інформаційної системи",
                description = "Методологія і процес мережного планування",
                comment = "",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 6,
                subjectId = 3 ,
                title = "Побудова локальної мережі на базі Ethernet комутатора з використанням техніки VLAN",
                description = "Ознайомлення з принципами роботи та використанням технології віртуальних локальних мереж (VLAN).",
                comment = "Захищено",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 7,
                subjectId = 4 ,
                title = "Налаштування функцій Syslog, NTP і SSH на маршрутизаторах Cisco",
                description = "Навчитись налаштовувати функції Syslog, NTP і SSH на маршрутизаторах Cisco",
                comment = "Зробити звіт",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 8,
                subjectId = 4 ,
                title = "Підготувати доповідь",
                description = "Зробити презентацію і доповідь про NGFW",
                comment = "Здав",
                isCompleted = true
            ),
        )

        listOfSubject.forEach { subject ->
            database.subjectsDao.addSubject(subject)
        }
        listOfSubjectLabs.forEach { lab ->
            database.subjectLabsDao.addSubjectLab(lab)
        }
    }
}