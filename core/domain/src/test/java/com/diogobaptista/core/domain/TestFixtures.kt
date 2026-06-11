package com.diogobaptista.core.domain

import com.diogobaptista.core.domain.model.Breed

val fakeBreed = Breed(
    id = "1",
    name = "Siamese",
    origin = "Thailand",
    temperament = "Affectionate, social",
    description = "A very social cat.",
    lifeSpan = "12-15 years",
    imageUrl = "https://example.com/siamese.jpg",
    isFavourite = false
)

val fakeBreedList = listOf(
    fakeBreed,
    fakeBreed.copy(id = "2", name = "Persian", isFavourite = true),
    fakeBreed.copy(id = "3", name = "Maine Coon")
)