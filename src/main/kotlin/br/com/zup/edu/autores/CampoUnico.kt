package br.com.zup.edu.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [])
annotation class CampoUnico(
    val field: String,
    val domainClass: KClass<*>,
    val message: String = "campo já cadastrado"
//    val groups: Array<KClass<Any>> = [],
//    val payload: Array<KClass<Payload>> = []
)

@Singleton
open class CampoUnicoValidator(private val em: EntityManager) : ConstraintValidator<CampoUnico, String> {

    @Transactional
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<CampoUnico>,
        context: ConstraintValidatorContext
    ): Boolean {
        val campo = annotationMetadata.stringValue("field").get()
        val klass = annotationMetadata.classValue("domainClass").get()
        val resultList = em.createQuery("SELECT k FROM ${klass.simpleName} k WHERE k.$campo=:value")
            .setParameter("value", value).resultList

        return resultList.isEmpty()
    }

}
