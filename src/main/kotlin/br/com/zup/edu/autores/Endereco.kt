package br.com.zup.edu.autores

import javax.persistence.Embeddable

@Embeddable
class Endereco(
    cepResponse: CepResponse,
    val numero: String,
) {
    val cep = cepResponse.cep
    val logradouro = cepResponse.logradouro
    val complementoCep = cepResponse.complemento
    val bairro = cepResponse.bairro
    val municipio = cepResponse.localidade
    val uf = cepResponse.uf
}