package br.com.zup.edu.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws")
interface CepClient {

    @Get("/{cep}/json/")
    fun consultaCep(@PathVariable cep:String) : HttpResponse<CepResponse>
}