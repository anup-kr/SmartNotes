package com.androiddev.smartnotes.feature_note.presentation.notes

import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder

data class SortSectionStates(
    val sortBy: SortBy = SortBy.DATE_MODIFIED,
    val sortOrder: SortOrder = SortOrder.DESCENDING,
    val isSortByExpanded: Boolean = false,
)