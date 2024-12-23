package teste_spring.teste.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import teste_spring.teste.data.vo.v1.AccountCredentialsVO
import teste_spring.teste.data.vo.v1.TokenVO
import teste_spring.teste.data.vo.v1.UpdateRegisterVO
import teste_spring.teste.services.AuthService

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var authService: AuthService

    @Operation(
        summary = "Autenticação do Usuario e retorno do token.",
        description = "Essa função serve para autenticar o usuário e retornar um token."
                + "O token é necessário para acessar os endpoints protegidos."
                + "O token deve ser passado no header da requisição com a chave Authorization e o valor Bearer token",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User authenticated successfully",
                content = [
                    Content(
                        schema = Schema(implementation = TokenVO::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Invalid username or password"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid client request"
            )
        ]
    )
    @PostMapping(value = ["/signin"])
    fun signin(@RequestBody data: AccountCredentialsVO?): ResponseEntity<*> {
        return if (data!!.username.isNullOrBlank() || data.password.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
        else authService.signin(data)
    }

    @Operation(
        summary = "Criar novo usuário",
        description = "Essa função serve para criar um novo usuário",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User created successfully",
            ),
            ApiResponse(
                responseCode = "409",
                description = "Username already exists"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid client request"
            )
        ]
    )
    @PostMapping(value = ["/signup"])
    fun signup(@RequestBody data: AccountCredentialsVO?): ResponseEntity<*> {
        return if (data!!.username.isNullOrBlank() || data.password.isNullOrBlank()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client request")
        } else {
            authService.createUser(data)
        }
    }


    @Operation(
        summary = "Atualizar dados do usuário",
        description = "Essa função serve para atualizar os dados do usuário",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User updated successfully",
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid client request"
            )
        ]
    )
    @PutMapping(value = ["/update/{username}"])
    fun updateRegister(@PathVariable username: String, @RequestBody data: UpdateRegisterVO): ResponseEntity<*> {
        return when {
            data.fullName.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Full name is required")

            data.birthDate.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Birth date is required")

            data.phoneNumber.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Phone number is required")

            data.photoUrl.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Photo URL is required")

            else -> {
                authService.updateRegister(data, username)
                ResponseEntity.ok("User updated successfully")
            }
        }
    }

    @Operation(
        summary = "Refresh token", description = "Refresh", tags = ["Auth"], responses = [
            ApiResponse(
                responseCode = "200",
                description = "Token refreshed successfully",
                content = [
                    Content(
                        schema = Schema(implementation = TokenVO::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Invalid client request"
            )
        ]
    )
    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(
        @PathVariable("username") username: String?,
        @RequestHeader("Authorization") refreshToken: String?
    ): ResponseEntity<*> {
        return if (refreshToken.isNullOrBlank() || username.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
        else authService.refreshToken(username, refreshToken)
    }
}