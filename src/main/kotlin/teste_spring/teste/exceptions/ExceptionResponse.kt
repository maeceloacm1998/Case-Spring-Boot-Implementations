package teste_spring.teste.exceptions

import java.util.Date

data class ExceptionResponse(
    val date: Date,
    val message: String?,
    val details: String
)
