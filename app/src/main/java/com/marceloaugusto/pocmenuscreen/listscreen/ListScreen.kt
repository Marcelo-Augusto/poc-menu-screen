package com.marceloaugusto.pocmenuscreen.listscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(
    data: ListScreenData = getListScreenData()
) {
    val scaffoldState = rememberScaffoldState()
    val closeDrawer = triggerCloseDrawer(drawerState = scaffoldState.drawerState)
    val openDrawer = triggerOpenDrawer(drawerState = scaffoldState.drawerState)
    var openPopup by remember { mutableStateOf(false) }
    var drawerGesturesEnabled by remember { mutableStateOf(true) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerBackgroundColor = Color.Transparent,
            drawerElevation = 0.dp,
            drawerContent = { DrawerContent { closeDrawer() } },
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                CategoryListAndProductList(
                    modifier = Modifier.padding(it),
                    data = data
                ) { openPopup = true }
                CartButton(modifier = Modifier.padding(it)) { openDrawer() }
                if (openPopup) {
                    drawerGesturesEnabled = false
                    ProductPopup(modifier = Modifier.fillMaxSize()) {
                        openPopup = false
                        drawerGesturesEnabled = true
                        openDrawer()
                    }
                }
            }
        }
    }

}

@Composable
fun CartButton(
    modifier: Modifier = Modifier,
    callback: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Card(
            modifier = Modifier.clickable { callback() },
            shape = RoundedCornerShape(bottomStart = 10.dp),
            elevation = 10.dp
        ) {
            Text(
                modifier = Modifier.padding(
                    start = MediumPadding,
                    top = SmallPadding,
                    bottom = SmallPadding,
                    end = MediumPadding
                ),
                fontSize = MediumSize,
                text = "Carrinho"
            )
        }
    }
}

@Composable
fun DrawerContent(
    callback: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .width(drawerWidth())
                .background(color = Color.Gray)
                .fillMaxSize()
                .padding(start = SmallPadding, end = SmallPadding, bottom = MediumPadding),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "RS 123,45")
                Text(text = "Total pedido")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Text(text = "Enviar pedido")
            }
        }
        Button(onClick = { callback() }) {
            Text(text = "Adicionar mais itens")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryListAndProductList(
    modifier: Modifier = Modifier,
    data: ListScreenData,
    onAddButtonPressed: () -> Unit
) {
    Row(
        modifier = modifier
    ) {

        val listStateCategories = rememberLazyListState()
        val listStateProducts = rememberLazyListState()
        var categorySelected by remember { mutableIntStateOf(1) }
        val onCategorySelected = onCategorySelected(listStateProducts)
        val nestedScroll = onProductsScroll(data, listStateProducts, listStateCategories) {
            categorySelected = it
        }

        LazyColumn(
            modifier = Modifier
                .width(categoriesWidth())
                .fillMaxHeight(),
            state = listStateCategories
        ) {

            data.categories.forEachIndexed { categoryIndex, categoryData ->
                categoryData.header?.let { categoryHeader ->
                    item { CategoryHeader(header = categoryHeader) }
                }

                categoryData.item?.let { categoryItem ->
                    item {
                        CategoryItem(
                            category = categoryItem,
                            isSelected = categoryIndex == categorySelected,
                            onClick = {
                                categorySelected = categoryIndex
                                categoryData.productHeaderIndex?.let { onCategorySelected(it) }
                            }
                        )
                    }
                }
            }
        }

        LazyColumn(
            state = listStateProducts,
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(nestedScroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(data.products) { productData ->
                productData.header?.let { productHeader ->
                    ProductHeader(modifier = Modifier.padding(SmallPadding), header = productHeader)
                }
                productData.item?.let { productItem ->
                    ProductItem(
                        modifier = Modifier.padding(SmallPadding),
                        item = productItem,
                        onAddButtonPressed = onAddButtonPressed
                    )
                }
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
    ListScreen()
}
