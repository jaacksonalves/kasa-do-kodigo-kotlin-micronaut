package br.com.zup.edu.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [CampoUnicoValidator::class])
annotation class CampoUnico(
    val field: String,
    val domainClass: KClass<*>,
    val message: String = "campo já cadastrado",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)


@Singleton
class CampoUnicoValidator(private val em: EntityManager) : ConstraintValidator<CampoUnico, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<CampoUnico>,
        context: ConstraintValidatorContext
    ): Boolean {
        val campo = annotationMetadata.stringValue("field").get()
        val klass = annotationMetadata.classValue("domainClass").get()
        val resultList = em.createQuery("SELECT k FROM $klass k WHERE k.$campo= $value").resultList
        return resultList.isEmpty()

    }

}
