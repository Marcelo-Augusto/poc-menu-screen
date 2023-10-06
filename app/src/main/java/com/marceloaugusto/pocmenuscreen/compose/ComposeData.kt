package com.marceloaugusto.pocmenuscreen.compose

data class MenuScreenData(
    val categories: List<CategoryData>,
    val products: List<ProductData>
)

data class CategoryData(
    var productHeaderIndex: Int? = null,
    val header: CategoryHeader? = null,
    val item: CategoryItem? = null
)

data class CategoryHeader(val text: String)
data class CategoryItem(val text: String)

data class ProductData(
    val categoryIndex: Int,
    val header: ProductHeader? = null,
    val item: ProductItem? = null
)

data class ProductHeader(val text: String)
data class ProductItem(
    val title: String,
    val description: String,
    val price: String
)

fun getListScreenData(): MenuScreenData {
    val categories = getCategoriesList()
    return MenuScreenData(categories, getProductList(categories))
}

private fun getCategoriesList() = mutableListOf<CategoryData>().apply {
    for (headerIndex in 0..4) {
        val headerText = categoryHeaderText(headerIndex)
        add(CategoryData(header = CategoryHeader(headerText)))
        for (itemIndex in 0..4) {
            add(CategoryData(item = CategoryItem(categoryItemText(itemIndex))))
        }
    }
}

private fun getProductList(categories: List<CategoryData>) = mutableListOf<ProductData>().apply {
    categories.forEachIndexed { index, categoryData ->
        categoryData.item?.let {
            val headerIndex = size
            categoryData.productHeaderIndex = headerIndex
            add(
                ProductData(
                    categoryIndex = index,
                    header = ProductHeader(it.text)
                )
            )
            for (itemIndex in 0..9) {
                add(
                    ProductData(
                        categoryIndex = index,
                        item = ProductItem(
                            title = productItemText(itemIndex),
                            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                            price = "R$ 123,45"
                        )
                    )
                )
            }
        }
    }
}

private fun categoryHeaderText(index: Int): String {
    return when (index) {
        0 -> "Entradas"
        1 -> "Pratos principais"
        2 -> "Sobremesas"
        3 -> "Kids"
        else -> "Bebidas"
    }
}

private fun categoryItemText(index: Int): String {
    return when (index) {
        0 -> "Hamburgueres"
        1 -> "Pizzas"
        2 -> "Pasteis"
        3 -> "Sucos"
        else -> "Doces"
    }
}

private fun productItemText(index: Int): String {
    return when (index) {
        0 -> "Picanha"
        1 -> "Bacon"
        2 -> "Frango"
        3 -> "Coca"
        4 -> "Guarana"
        5 -> "Laranja"
        6 -> "Maracuja"
        7 -> "Brigadeiro"
        8 -> "Doce de leite"
        else -> "Cocada"
    }
}