package br.com.zup.edu.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.util.*
import javax.transaction.Transactional

@Controller("/autores")
class BuscaAutoresController(private val autorRepository: AutorRepository) {


    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {
        if (email.isBlank()) {
            val detalhesAutores = autorRepository.findAll().map { autor -> DetalhesAutorResponse(autor) }
            return HttpResponse.ok(detalhesAutores)
        }

        val possivelAutor: Optional<Autor> = autorRepository.findByEmail(email)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound("Autor não encontrado, verifique email digitado")
        }

        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesAutorResponse(autor))
    }
}