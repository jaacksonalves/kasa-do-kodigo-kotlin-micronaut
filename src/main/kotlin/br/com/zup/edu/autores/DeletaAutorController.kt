package br.com.zup.edu.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional

@Controller("/autores/{id}")
class DeletaAutorController(private val autorRepository: AutorRepository) {

    @Delete
    @Transactional
    fun deleta(@PathVariable id: Long): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound("Autor não encontrado")
        }

        val autor = possivelAutor.get()
        val detalhesAutorResponse = DetalhesAutorResponse(autor)

        autorRepository.delete(autor)
        return HttpResponse.ok("Autor deletado: \n $detalhesAutorResponse")
    }
}