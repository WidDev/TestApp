package com.example.testapp.models

import com.example.testapp.interfaces.IIdentifiable
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TeamMember(
    override public val id: UUID,
    public var name:String,
    public var team:Team?
) : IIdentifiable