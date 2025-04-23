package com.flasska.petstat.domain.entities

data class Device(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val avatar: Int,
    val batteryLevel: Int,
    val petType: PetType,
    val gender: Gender
)

enum class PetType{
    CAT,
    DOG,
    OTHER
}

enum class Gender{
    MALE, FEMALE
}