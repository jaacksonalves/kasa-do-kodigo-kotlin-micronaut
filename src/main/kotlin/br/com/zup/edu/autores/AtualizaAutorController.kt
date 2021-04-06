package br.com.zup.edu.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutorController(private val autorRepository: AutorRepository) {

    @Patch
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound("Autor não encontrado")
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao

        return HttpResponse.ok(DetalhesAutorResponse(autor))
    }
}