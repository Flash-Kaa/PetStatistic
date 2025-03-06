package com.flasska.petstat.domain.usecases

import com.flasska.petstat.domain.entities.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class GetDevicesUseCase {
    private val flow = MutableStateFlow(
        (0..6).map {
            val uuid = UUID.randomUUID().toString()
            Device(uuid, uuid)
        }
    )

    operator fun invoke(): Flow<List<Device>> = flow.asStateFlow()
}