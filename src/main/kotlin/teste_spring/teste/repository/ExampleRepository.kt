package teste_spring.teste.repository

import org.springframework.data.jpa.repository.JpaRepository
import teste_spring.teste.models.Example

interface ExampleRepository: JpaRepository<Example, Long?>