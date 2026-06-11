package com.diogobaptista.core.network.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("origin") val origin: String? = null,
    @SerialName("temperament") val temperament: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("life_span") val lifeSpan: String? = null,

    @SerialName("reference_image_id")
    val referenceImageId: String? = null
)