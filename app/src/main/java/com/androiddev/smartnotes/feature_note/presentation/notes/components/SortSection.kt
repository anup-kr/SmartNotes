import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesEvent
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesViewModel
import com.androiddev.smartnotes.feature_note.presentation.notes.SortSectionStates
import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder

@Composable
fun SortSection(
    viewModel: NotesViewModel = hiltViewModel()
) {

    val sortSectionStates: MutableState<SortSectionStates> = remember {
        mutableStateOf(SortSectionStates())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        SortBySection(
            sortBy = sortSectionStates.value.sortBy,
            isSortByExpanded = sortSectionStates.value.isSortByExpanded,
            onClick = {
                sortSectionStates.value = sortSectionStates.value.copy(
                    isSortByExpanded = !sortSectionStates.value.isSortByExpanded
                )
            },
            onSortBySelection = {
                sortSectionStates.value = sortSectionStates.value.copy(
                    sortBy = it,
                    isSortByExpanded = false
                )
                viewModel.onEvent(
                    NotesEvent.SortNotes(
                        sortBy = it,
                        sortOrder = sortSectionStates.value.sortOrder
                    )
                )
            },
            onDismiss = {
                sortSectionStates.value = sortSectionStates.value.copy(
                    isSortByExpanded = false
                )
            }
        )
        Divider(
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .width(0.6.dp)
                .height(20.dp)
        )
        SortOrderArrow(
            sortOrder = sortSectionStates.value.sortOrder,
            onArrowClick = {
                val newSortOrder = if (sortSectionStates.value.sortOrder == SortOrder.DESCENDING)
                    SortOrder.ASCENDING
                else
                    SortOrder.DESCENDING
                sortSectionStates.value = sortSectionStates.value.copy(
                    sortOrder = newSortOrder
                )
                viewModel.onEvent(
                    NotesEvent.SortNotes(
                        sortBy = sortSectionStates.value.sortBy,
                        sortOrder = newSortOrder
                    )
                )
            }
        )
    }
}

@Composable
fun SortBySection(
    sortBy: SortBy,
    isSortByExpanded: Boolean,
    onClick: () -> Unit,
    onSortBySelection: (SortBy) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
   Row(
        modifier = modifier
            .padding(2.dp)
            .clickable { onClick() }
   ) {
       Icon(
           imageVector = Icons.Default.Sort,
           contentDescription = "sort by options"
       )
       Text(text = sortBy.value)
       DropdownMenu(
           expanded = isSortByExpanded,
           onDismissRequest = { onDismiss() }) {
           for (sortByOption in SortBy.values()) {
               DropdownMenuItem(
                   onClick = {
                       onSortBySelection(sortByOption)
                   }
               ) {
                   Text(
                       text = sortByOption.value,
                       color = if(sortByOption == sortBy) Color.Green else Color.LightGray
                   )
               }
           }
       }
   }
}

@Composable
fun SortOrderArrow(
    sortOrder: SortOrder = SortOrder.DESCENDING,
    onArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = if (sortOrder == SortOrder.DESCENDING)
                Icons.Default.ArrowUpward
            else
                Icons.Default.ArrowDownward,
        contentDescription = "sort notes",
        modifier = modifier
            .padding(2.dp)
            .clickable { onArrowClick() }
    )
}