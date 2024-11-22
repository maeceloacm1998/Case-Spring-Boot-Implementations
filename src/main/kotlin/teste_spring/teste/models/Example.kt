package teste_spring.teste.models

import jakarta.persistence.*

@Entity
@Table(name = "example")
data class Example (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = ""
)