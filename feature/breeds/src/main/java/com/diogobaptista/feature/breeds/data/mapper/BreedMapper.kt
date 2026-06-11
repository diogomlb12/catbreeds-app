package com.diogobaptista.feature.breeds.data.mapper

import com.diogobaptista.core.database.entity.BreedEntity
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.network.dto.BreedDto

fun BreedDto.toEntity(): BreedEntity = BreedEntity(
    id = id,
    name = name,
    origin = origin.orEmpty(),
    temperament = temperament.orEmpty(),
    description = description.orEmpty(),
    lifeSpan = lifeSpan.orEmpty(),
    imageUrl = referenceImageId?.let {
        "https://cdn2.thecatapi.com/images/$it.jpg"
    }
)

fun BreedEntity.toDomain(): Breed = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    lifeSpan = lifeSpan,
    imageUrl = imageUrl,
    isFavourite = isFavourite
)