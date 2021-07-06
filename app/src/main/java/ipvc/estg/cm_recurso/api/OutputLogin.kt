package ipvc.estg.cm_recurso.api

data class OutputLogin(
    val username: String,
    val password: String,
    val status: String,
    val MSG: String,
    val id: Int
)
