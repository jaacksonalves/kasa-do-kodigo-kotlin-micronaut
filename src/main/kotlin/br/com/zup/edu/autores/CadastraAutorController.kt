package br.com.zup.edu.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(
    private val autorRepository: AutorRepository,
    private val cepClient: CepClient
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {
        try {
            val enderecoConsultado = cepClient.consultaCep(request.cep)

            val novoAutor = request.toModel(enderecoConsultado.body()!!)
            autorRepository.save(novoAutor)

            val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf("id" to novoAutor.id))

            return HttpResponse.created(DetalhesAutorResponse(novoAutor), uri)
        } catch (e: Exception) {
            return HttpResponse.notFound("CEP não encontrado")
        }
    }


}