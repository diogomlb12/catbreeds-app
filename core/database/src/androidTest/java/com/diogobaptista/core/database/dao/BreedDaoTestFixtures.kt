package com.diogobaptista.core.database.dao

import com.diogobaptista.core.database.entity.BreedEntity

val fakeEntity = BreedEntity(
    id = "1",
    name = "Siamese",
    origin = "Thailand",
    temperament = "Affectionate",
    description = "Social cat",
    lifeSpan = "12-15",
    imageUrl = null,
    isFavourite = false
)

val fakeEntityList = listOf(
    fakeEntity,
    fakeEntity.copy(id = "2", name = "Persian", isFavourite = true),
    fakeEntity.copy(id = "3", name = "Maine Coon")
)