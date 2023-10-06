package com.marceloaugusto.pocmenuscreen.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategoryItem(
    category: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = getBackgroundColor(isSelected))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(SmallPadding),
            textAlign = TextAlign.Center,
            fontSize = MediumSize,
            text = category.text
        )
    }
}

private fun getBackgroundColor(isSelected: Boolean): Color {
    return if (isSelected) Color.DarkGray
    else Color.LightGray
}

@Preview(
    name = "CategoryItemSelectedPreview",
    showBackground = true,
    backgroundColor = 0xFFF3F3F3,
    widthDp = 512,
    heightDp = 288
)
@Composable
private fun CategoryItemSelectedPreview() {
    CategoryItem(category = CategoryItem("Text"), isSelected = true) {}
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
    CategoryItem(category = CategoryItem("Text"), isSelected = false) {}
}