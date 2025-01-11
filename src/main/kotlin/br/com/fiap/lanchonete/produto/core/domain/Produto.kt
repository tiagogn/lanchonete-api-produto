package br.com.fiap.lanchonete.produto.core.domain

import java.math.BigDecimal
import java.util.UUID

data class Produto(

    var id: UUID? = null,

    val nome: String,

    val preco: BigDecimal,

    val categoria: CategoriaProduto
)

enum class CategoriaProduto {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA
}