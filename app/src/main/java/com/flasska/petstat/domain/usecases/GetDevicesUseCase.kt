package com.flasska.petstat.domain.usecases

import com.flasska.petstat.R
import com.flasska.petstat.domain.entities.Device
import com.flasska.petstat.domain.entities.Gender
import com.flasska.petstat.domain.entities.PetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class GetDevicesUseCase {
    private val flow = MutableStateFlow(
        listOf(
            Device(
                id = UUID.randomUUID().toString(),
                name = "Кот Мальчик",
                avatar = getAvatar(PetType.CAT, Gender.MALE, isActive = true),
                batteryLevel = 34,
                isActive = true,
                petType = PetType.CAT,
                gender = Gender.MALE
            ),
            Device(
                id = UUID.randomUUID().toString(),
                name = "Собака Девочка",
                avatar = getAvatar(PetType.DOG, Gender.FEMALE, isActive = false),
                batteryLevel = 23,
                isActive = false,
                petType = PetType.DOG,
                gender = Gender.FEMALE
            ),
            Device(
                id = UUID.randomUUID().toString(),
                name = "Собака мальчик",
                avatar = getAvatar(PetType.DOG, Gender.MALE, isActive = true),
                batteryLevel = 34,
                isActive = true,
                petType = PetType.DOG,
                gender = Gender.MALE
            ),
            Device(
                id = UUID.randomUUID().toString(),
                name = "Кот девочка",
                isActive = true,
                avatar = getAvatar(PetType.CAT, Gender.FEMALE, isActive = true),
                batteryLevel = 74,

                petType = PetType.CAT,
                gender = Gender.FEMALE
            )
        )
    )
    private fun getAvatar(petType: PetType, gender: Gender, isActive: Boolean): Int {
        return when (petType) {
            PetType.CAT -> if (isActive) {
                if (gender == Gender.MALE) R.drawable.ic_cat_male else R.drawable.ic_cat_female
            } else {
                if (gender == Gender.MALE) R.drawable.ic_cat_male_unactive else R.drawable.ic_cat_female_unactive
            }
            PetType.DOG -> if (isActive) {
                if (gender == Gender.MALE) R.drawable.ic_dog_male else R.drawable.ic_dog_female
            } else {
                if (gender == Gender.MALE) R.drawable.ic_dog_male_unactive else R.drawable.ic_dog_female_unactive
            }
            PetType.OTHER -> if (isActive) {
                if (gender == Gender.MALE) R.drawable.ic_dog_male else R.drawable.ic_dog_female
            } else {
                if (gender == Gender.MALE) R.drawable.ic_dog_male_unactive else R.drawable.ic_dog_female_unactive
            }
        }
    }
    operator fun invoke(): Flow<List<Device>> = flow.asStateFlow()
}