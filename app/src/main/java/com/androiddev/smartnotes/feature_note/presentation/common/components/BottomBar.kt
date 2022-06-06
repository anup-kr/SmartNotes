package com.androiddev.smartnotes.feature_note.presentation.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteState
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.BottomBarButton
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.ColorSelector
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun BottomBar(
    items: List<BottomBarButton>,
    isBottomBarVisible: Boolean = true,
    backgroundColor: Color = Color.Black.copy(0.2f),
    onItemClick: (BottomBarButton) -> Unit,
    modifier: Modifier = Modifier
) {
    if(isBottomBarVisible) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = backgroundColor
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                            tint = Color(item.color),
                            modifier = item.modifier,
                        )
                        if(item.name == "Color") {
                            ColorSelector()
                        }
                    },
                    selected = false,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}