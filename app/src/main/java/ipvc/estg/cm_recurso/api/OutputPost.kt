package ipvc.estg.cm_recurso.api

data class OutputPost(
    val id: Int,
    val rue: String,
    val desc: String,
    val latitude: String,
    val longitude: String,
    val imagem: String,
    val status: String,
    val MSG: String
)
