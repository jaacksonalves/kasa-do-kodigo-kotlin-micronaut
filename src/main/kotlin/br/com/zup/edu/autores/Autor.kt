package br.com.zup.edu.autores

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Autor(
    val nome: String,
    @Column(unique = true) val email: String,
    @Column(length = 400) var descricao: String,
    @field:Embedded val endereco: Endereco
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(updatable = false)
    val criadoEm: LocalDateTime = LocalDateTime.now()

}