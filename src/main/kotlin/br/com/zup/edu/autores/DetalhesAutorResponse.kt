package br.com.zup.edu.autores

class DetalhesAutorResponse(autor: Autor) {

    val id = autor.id
    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao
    val endereco = autor.endereco
}
