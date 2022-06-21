package lagos.guillermo.model

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T>(
    val status: String,
    val message: String,
    val response: T? = null
)
