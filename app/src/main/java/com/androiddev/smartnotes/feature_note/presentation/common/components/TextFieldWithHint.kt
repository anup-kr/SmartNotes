package com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components

import android.graphics.drawable.Icon
import androidx.compose.material.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun TextFieldWithHint(
    text: String,
    hintText: String,
    textStyle: TextStyle = TextStyle(),
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = Color.Transparent,
    isSingleLine: Boolean,
    isFocused: Boolean = false,
    modifier:Modifier = Modifier,
    onFocusChange: (FocusState) -> Unit,
    onTextChange: (String) -> Unit,
) {

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            singleLine = isSingleLine,
            textStyle = textStyle,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .padding(horizontal = 6.dp, vertical = 8.dp)
                .onFocusChanged {
                    onFocusChange(it)
                },
            onValueChange = onTextChange,
        )
        if(!isFocused && text.isBlank()) {
            Text(
                text = hintText,
                style = textStyle,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
            )
        }
    }

}