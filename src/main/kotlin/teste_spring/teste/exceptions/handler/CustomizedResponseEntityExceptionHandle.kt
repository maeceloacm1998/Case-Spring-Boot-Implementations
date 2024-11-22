package teste_spring.teste.exceptions.handler

import br.com.erudio.exceptions.InvalidJwtAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import teste_spring.teste.exceptions.ExceptionResponse
import teste_spring.teste.exceptions.ResourceNotFoundException
import java.util.*


@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandle {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val errorDetails = ExceptionResponse(
            date = Date(),
            message = ex.message,
            details = request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleInvalidJwtAuthenticationExceptions(ex: Exception, request: WebRequest) :
            ResponseEntity<ExceptionResponse> {
        val exceptioResponse = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ExceptionResponse>(exceptioResponse, HttpStatus.FORBIDDEN)
    }
}