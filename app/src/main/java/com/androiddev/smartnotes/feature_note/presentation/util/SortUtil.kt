package com.androiddev.smartnotes.feature_note.presentation.util

enum class SortBy(val value: String) {
    DATE_CREATED("Date Created"),
    DATE_MODIFIED("Date Modified"),
    NOTE_COLOR("Color"),
    NOTE_TITLE("Title")
}

enum class SortOrder(val value: String) {
    ASCENDING("Ascending"),
    DESCENDING("Descending")
}