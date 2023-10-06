package com.marceloaugusto.pocmenuscreen.compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlinx.coroutines.launch

@Composable
fun onProductsScroll(
    data: MenuScreenData,
    listStateProducts: LazyListState,
    listStateCategories: LazyListState,
    callback: (categorySelected: Int) -> Unit
): NestedScrollConnection {
    val coroutineScope = rememberCoroutineScope()
    var triggerScroll by remember { mutableStateOf(false) }
    var indexToScroll by remember { mutableIntStateOf(0) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                listStateProducts.firstVisibleItemIndex.let { index ->
                    val categoryIndex = data.products[index].categoryIndex
                    if (categoryIndex != indexToScroll) {
                        indexToScroll = categoryIndex
                        triggerScroll = true
                        //
                    }
                }
                return Offset.Zero
            }
        }
    }

    if (triggerScroll) {
        triggerScroll = false
        LaunchedEffect(listStateCategories) {
            coroutineScope.launch {
                listStateCategories.scrollToItem(indexToScroll)
                callback(indexToScroll)
            }
        }
    }

    return nestedScrollConnection
}

@Composable
fun onCategorySelected(
    listStateProducts: LazyListState
): (index: Int) -> Unit {
    val coroutineScope = rememberCoroutineScope()
    var triggerScroll by remember { mutableStateOf(false) }
    var indexToScroll by remember { mutableIntStateOf(0) }

    if (triggerScroll) {
        triggerScroll = false
        LaunchedEffect(listStateProducts) {
            coroutineScope.launch {
                listStateProducts.scrollToItem(indexToScroll)
            }
        }
    }
    return { index ->
        triggerScroll = true
        indexToScroll = index
    }
}

@Composable
fun triggerCloseDrawer(
    drawerState: DrawerState
): () -> Unit {
    val coroutineScope = rememberCoroutineScope()
    var triggerClose by remember { mutableStateOf(false) }

    if (triggerClose) {
        triggerClose = false
        LaunchedEffect(drawerState) {
            coroutineScope.launch {
                drawerState.close()
            }
        }
    }
    return { triggerClose = true }
}

@Composable
fun triggerOpenDrawer(
    drawerState: DrawerState
): () -> Unit {
    val coroutineScope = rememberCoroutineScope()
    var triggerOpen by remember { mutableStateOf(false) }

    if (triggerOpen) {
        triggerOpen = false
        LaunchedEffect(drawerState) {
            coroutineScope.launch {
                drawerState.open()
            }
        }
    }
    return { triggerOpen = true }
}

@Composable
fun onOptionSelected(
    listStateOption: LazyListState
): (index: Int) -> Unit {
    val coroutineScope = rememberCoroutineScope()
    var triggerScroll by remember { mutableStateOf(false) }
    var indexToScroll by remember { mutableIntStateOf(0) }

    if (triggerScroll) {
        triggerScroll = false
        LaunchedEffect(listStateOption) {
            coroutineScope.launch {
                listStateOption.scrollToItem(indexToScroll)
            }
        }
    }
    return { index ->
        triggerScroll = true
        indexToScroll = index
    }
}