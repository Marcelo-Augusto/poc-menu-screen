package com.marceloaugusto.pocmenuscreen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductPopup(
    modifier: Modifier = Modifier,
    data: ProductPopupData = getProductPopupData(),
    callback: () -> Unit
) {
    var selectedOption by remember { mutableIntStateOf(0) }
    val listStateOptions = rememberLazyListState()
    val scrollToOption = onOptionSelected(listStateOption = listStateOptions)

    Box(
        modifier = modifier
            .clickable(enabled = false, onClick = {})
            .background(color = Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .height(popUpHeight())
                .width(popUpWidth()),
            backgroundColor = Color.LightGray
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                LeftPopupComponent(
                    modifier = Modifier.width(popUpWidth() / 3),
                    description = data.description,
                    options = data.options,
                    selectedOption = selectedOption,
                    listStateOptions = listStateOptions
                ) { selectedIndex ->
                    selectedOption = selectedIndex
                }
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = Color.Gray
                )
                RightPopupComponent(
                    modifier = Modifier.fillMaxWidth(),
                    optionData = if (selectedOption != -1) data.options[selectedOption] else null,
                    items = if (selectedOption == -1) data.items else null,
                    onNextOption = {
                        var nextIndex = if (selectedOption.plus(1) == data.options.size) -1
                        else selectedOption.plus(1)

                        scrollToOption(if (nextIndex == -1) data.options.size else nextIndex)
                        selectedOption = nextIndex
                    },
                    onFinish = { callback() }
                )
            }
        }
    }
}

@Composable
fun LeftPopupComponent(
    modifier: Modifier = Modifier,
    description: String,
    options: List<OptionData>,
    selectedOption: Int,
    listStateOptions: LazyListState,
    callback: (selectedIndex: Int) -> Unit
) {
    val localDensity = LocalDensity.current
    var totalHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(SmallPadding),
    ) {
        LazyColumn(
            state = listStateOptions,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = SmallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    modifier = Modifier
                        .background(Color.Gray)
                        .height(popUpPhotoSize())
                        .width(popUpPhotoSize()),
                    text = "Photo"
                )
                Text(
                    modifier = Modifier.padding(bottom = MediumPadding, top = MediumPadding),
                    text = description
                )
            }

            items(options.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SmallPadding)
                        .clickable { callback(index) },
                    elevation = 0.dp,
                    backgroundColor = getOptionColor(index, selectedOption)
                ) {
                    Text(
                        modifier = Modifier.padding(top = SmallPadding, bottom = SmallPadding),
                        textAlign = TextAlign.Center,
                        text = options[index].title
                    )
                }
            }

            item {
                val index = -1
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = totalHeight)
                        .clickable { callback(-1) },
                    elevation = 0.dp,
                    backgroundColor = getOptionColor(index, selectedOption)
                ) {
                    Text(
                        modifier = Modifier.padding(top = SmallPadding, bottom = SmallPadding),
                        textAlign = TextAlign.Center,
                        text = "Quantidade"
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .padding(top = SmallPadding)
                    .onGloballyPositioned { coordinates ->
                        totalHeight = with(localDensity) { coordinates.size.height.toDp() }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total")
                Text(text = "RS123,45")
            }
        }
    }
}

@Composable
fun RightPopupComponent(
    modifier: Modifier = Modifier,
    optionData: OptionData?,
    items: List<OptionItem>?,
    onNextOption: () -> Unit,
    onFinish: () -> Unit
) {
    optionData?.let { OptionContent(modifier = modifier, optionData, callback = onNextOption) }
    items?.let { QuantityContent(modifier = modifier, items, callback = onFinish) }
}

@Composable
fun OptionContent(
    modifier: Modifier = Modifier,
    optionData: OptionData,
    callback: () -> Unit
) {
    val localDensity = LocalDensity.current
    var totalHeight by remember { mutableStateOf(0.dp) }

    Column(
        modifier = modifier.padding(SmallPadding)
    ) {
        Row(
            modifier = Modifier.padding(bottom = MediumPadding)
        ) {
            Text(
                fontSize = MediumSize,
                text = optionData.title
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = SmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(optionData.items.size) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 0.dp,
                        backgroundColor = Color.LightGray
                    ) {
                        var checked by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = checked, onCheckedChange = { checked = it })
                            Text(text = optionData.items[index].text)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .padding(top = SmallPadding)
                        .onGloballyPositioned { coordinates ->
                            totalHeight = with(localDensity) { coordinates.size.height.toDp() }
                        },
                    onClick = { callback() }
                ) {
                    Text(text = "Proximo")
                }
            }
        }
    }
}

@Composable
fun QuantityContent(
    modifier: Modifier = Modifier,
    items: List<OptionItem>,
    callback: () -> Unit
) {
    val localDensity = LocalDensity.current
    var totalHeight by remember { mutableStateOf(0.dp) }
    var quantity by remember { mutableStateOf(1) }

    Column(
        modifier = modifier.padding(SmallPadding)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SmallPadding),
            textAlign = TextAlign.Center,
            fontSize = MediumSize,
            text = "Quantidade"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MediumPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            QuantityButton(text = "-") { quantity-- }
            QuantityButton(
                modifier = Modifier.padding(start = SmallPadding, end = SmallPadding),
                text = "$quantity",
                background = false
            )
            QuantityButton(text = "+") { quantity++ }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = SmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items.size) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 0.dp,
                        backgroundColor = Color.LightGray
                    ) {
                        var checked by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = checked, onCheckedChange = { checked = it })
                            Text(text = items[index].text)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .padding(top = SmallPadding)
                        .onGloballyPositioned { coordinates ->
                            totalHeight = with(localDensity) { coordinates.size.height.toDp() }
                        },
                    onClick = { callback() }
                ) {
                    Text(text = "Adicionar")
                }
            }
        }
    }
}

@Composable
fun QuantityButton(
    modifier: Modifier = Modifier,
    text: String,
    background: Boolean = true,
    callback: (() -> Unit) = {}
) {
    val color = if (background) MaterialTheme.colors.surface else Color.LightGray
    Card(
        modifier = modifier
            .size(ButtonHeight)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .clickable { callback() },
        backgroundColor = color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(fontSize = LargeSize, text = text)
        }
    }
}

private fun getOptionColor(index: Int, selectedOption: Int): Color {
    return if (index == selectedOption) Color.DarkGray else Color.LightGray
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
    ProductPopup() {}
}