package lagos.guillermo.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Cat(
    val id: Int,
    val name: String,
    val age: Int,
    val isFat: Boolean
)

val catsNames = mutableListOf(
    "Peluza",
    "Apolo",
    "William",
    "Luna",
    "Lili",
    "Memo",
    "Luchin"
)

val catsStorage = (1..6).map {
    Cat(
        id = it,
        name = catsNames.random(),
        age = (1..5).random(),
        isFat = Random.nextBoolean()
    )
}.toMutableList()