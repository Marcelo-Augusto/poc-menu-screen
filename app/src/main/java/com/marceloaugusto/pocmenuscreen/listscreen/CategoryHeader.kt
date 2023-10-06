package com.marceloaugusto.pocmenuscreen.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryHeader(
    header: CategoryHeader
) {

    Box(
        modifier = Modifier.background(color = Color.Gray)
            .padding(top = SmallPadding, bottom = SmallPadding),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier.height(2.dp),
            color = Color.Black
        )
        Text(
            modifier = Modifier.background(color = Color.Gray),
            fontSize = MediumSize,
            text = header.text
        )
    }
}

@Preview(
    name = "CategoryItemUnselectedPreview",
    showBackground = true,
    backgroundColor = 0xFFF3F3F3,
    widthDp = 512,
    heightDp = 288
)
@Composable
private fun CategoryItemUnselectedPreview() {
    CategoryHeader(header = CategoryHeader("Text"))
}