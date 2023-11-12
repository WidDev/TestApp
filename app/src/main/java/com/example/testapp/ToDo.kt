package com.example.testapp.models

import com.example.testapp.interfaces.IIdentifiable
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ToDo(
    override var id: UUID,
    val txt:String
) : IIdentifiable
