package br.com.fiap.lanchonete.produto.adapters.input.rest.response

import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import java.math.BigDecimal

data class ProdutoResponse(
    val id: String,
    val nome: String,
    val preco: BigDecimal,
    val categoria: CategoriaProduto
)