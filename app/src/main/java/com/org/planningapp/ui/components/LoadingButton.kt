package com.org.planningapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    isLoading: Boolean,
    buttonText: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = !isLoading && enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        } else {
            Text(text = buttonText)
        }
    }
}

@Preview
@Composable
fun LoadingButtonPreview() {
    LoadingButton(
        isLoading = true,
        buttonText = "Sign In",
        enabled = true,
        onClick = {}
    )
}
