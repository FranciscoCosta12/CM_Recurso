package ipvc.estg.cm_recurso.api

data class Post(
    val id: Int,
    val rue: String,
    val desc: String,
    val latitude: String,
    val longitude: String,
    val imagem: String
)
