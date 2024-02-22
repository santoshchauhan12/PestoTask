package com.android.pestotask.model

enum class TaskFilters(s: String) {

    BYDATE("Date Created"), BYTITLE("Title")
}

enum class TaskStatus {
    TODO, INPROGRESS, DONE, NOTDO
}