package com.example.mymenu.coreData.ModelsEntitys

import com.example.mymenu.coreModels.DishItem

fun DishItem.toDomainDishItem(): DishItem {
    return DishItem(
        id = this.id,
        url = this.url,
        name = this.name,
        price = this.price,
        weight = this.weight,
        description = this.description,
        categoryId = this.categoryId,
        count = this.count
    )
}