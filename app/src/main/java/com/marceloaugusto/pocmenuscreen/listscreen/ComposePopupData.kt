package com.marceloaugusto.pocmenuscreen.listscreen

data class ProductPopupData(
    val options: List<OptionData>,
    val description: String,
    val items: List<OptionItem>
)

data class OptionData(
    val title: String,
    val items: List<OptionItem>
)

data class OptionItem(val text: String)

fun getProductPopupData() = ProductPopupData(
    options = getOptions(),
    description = "Descrição",
    items = getItems()
)

private fun getOptions() = mutableListOf<OptionData>().apply {
    for (i in 0..2) {
        add(
            OptionData(
                title = "Opção $i",
                items = getItems()
            )
        )
    }
}

private fun getItems() = mutableListOf<OptionItem>().apply {
    for (i in 0..5) {
        add(OptionItem("Item $i"))
    }
}