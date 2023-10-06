package com.marceloaugusto.pocmenuscreen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    item: ProductItem,
    onAddButtonPressed: () -> Unit
) {

    val localDensity = LocalDensity.current
    var cardHeight by remember { mutableStateOf(0.dp) }
    var mod = if (cardHeight == 0.dp) {
        modifier.fillMaxSize().onGloballyPositioned { coordinates ->
            cardHeight = with(localDensity) { coordinates.size.height.toDp()  }
        }
    } else {
        modifier.fillMaxWidth().height(cardHeight)
    }

    Card(
        modifier = mod,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .width(photoSize())
                    .height(photoSize())
                    .background(color = Color.Gray),
                text = "Photo"
            )
            DescriptionComponent(item, onAddButtonPressed)
        }
    }
}

@Composable
fun DescriptionComponent(
    item: ProductItem,
    onAddButtonPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = SmallPadding, end = SmallPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = SmallPadding),
                fontWeight = FontWeight.Bold,
                fontSize = MediumSize,
                text = item.title
            )
            Text(
                modifier = Modifier.padding(bottom = MediumPadding),
                fontSize = SmallSize,
                text = item.description
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = SmallSize,
                text = item.price
            )
            Button(onClick = { onAddButtonPressed() }) {
                Text(text = "Adicionar")
            }
        }
    }
}

@Preview(
    name = "preview",
    showBackground = true,
    backgroundColor = 0xFFF3F3F3,
    widthDp = 512,
    heightDp = 288
)
@Composable
private fun Preview() {
    ProductItem(
        item = ProductItem(
            title = "Title",
            description = "Description",
            price = "Price",
        )
    ) {}
}