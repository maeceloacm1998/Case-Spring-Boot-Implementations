package teste_spring.teste.services

import org.springframework.stereotype.Service
import teste_spring.teste.data.vo.v1.ExampleVO
import teste_spring.teste.exceptions.ResourceNotFoundException
import teste_spring.teste.mapper.DozerMapper
import teste_spring.teste.models.Example
import teste_spring.teste.repository.ExampleRepository
import java.util.logging.Logger

@Service
class ExampleService(
    private val exampleRepository: ExampleRepository
) {
    private val logger = Logger.getLogger(ExampleService::class.java.name)

    fun findAll(): List<ExampleVO> {
        logger.info("Finding all Examples")
        val examples = exampleRepository.findAll()
        return DozerMapper.parseListObject(examples, ExampleVO::class.java)
    }

    fun findById(id: Long): ExampleVO {
        logger.info("Finding Example by id: $id")
        val example = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        return DozerMapper.parseObject(example, ExampleVO::class.java)
    }

    fun save(example: ExampleVO): ExampleVO {
        logger.info("Saving Example with name: ${example.name}")

        val entity = DozerMapper.parseObject(example, Example::class.java)
        val savedEntity = exampleRepository.save(entity)

        return DozerMapper.parseObject(savedEntity, ExampleVO::class.java)
    }

    fun update(id: Long, example: ExampleVO): ExampleVO {
        logger.info("Updating Example by id: $id")

        val entity = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        entity.name = example.name
        val updatedEntity = exampleRepository.save(entity)

        return DozerMapper.parseObject(updatedEntity, ExampleVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting Example by id: $id")
        val entity = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        exampleRepository.delete(entity)

        logger.info("Example deleted successfully")
    }
}