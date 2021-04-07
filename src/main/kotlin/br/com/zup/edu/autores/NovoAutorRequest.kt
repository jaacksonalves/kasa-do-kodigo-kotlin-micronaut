package br.com.zup.edu.autores

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email @field:CampoUnico(
        field = "email",
        domainClass = Autor::class,
        message = "Email já cadastrado"
    ) val email: String,
    @field:NotBlank @field:Size(min = 5, max = 400) val descricao: String,
    @field:NotBlank @field:Pattern(
        regexp = "^[0-9]{8}\$",
        message = "Cep deve ser preenchido somente com números"
    ) val cep: String,
    @field:NotBlank val numero: String
) {

    fun toModel(enderecoConsultado: CepResponse): Autor {
        val endereco = Endereco(enderecoConsultado, numero)
        return Autor(nome, email, descricao, endereco)
    }

}
