package com.marceloaugusto.pocmenuscreen.listscreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun categoriesWidth(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val width = screenWidth.toDouble() / 3
    Log.d("MARCELO", "screenWidth: $screenWidth, width: $width, width.dp: ${width.dp}")
    return width.dp

}

@Composable
fun drawerWidth(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val width = (screenWidth.toDouble() / 2)
    Log.d("MARCELO", "screenWidth: $screenWidth, width: $width, width.dp: ${width.dp}")
    return width.dp
}

@Composable
fun photoSize(): Dp {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = (screenHeight.toDouble() / 3)
    Log.d("MARCELO", "screenHeight: $screenHeight, height: $height, height.dp: ${height.dp}")
    return height.dp
}

@Composable
fun productHeaderHeight(): Dp {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = (screenHeight.toDouble() / 3)
    Log.d("MARCELO", "screenHeight: $screenHeight, height: $height, height.dp: ${height.dp}")
    return height.dp
}

@Composable
fun popUpHeight(): Dp {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = (screenHeight.toDouble() / 4) * 3
    Log.d("MARCELO", "screenHeight: $screenHeight, height: $height, height.dp: ${height.dp}")
    return height.dp
}

@Composable
fun popUpWidth(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val width = (screenWidth.toDouble() / 4) * 3
    Log.d("MARCELO", "screenHeight: $screenWidth, width: $width, height.dp: ${width.dp}")
    return width.dp
}

@Composable
fun popUpPhotoSize() = popUpHeight() / 4