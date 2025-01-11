package br.com.fiap.lanchonete.produto.core.application.ports.output

import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import java.util.Optional
import java.util.UUID

interface ProdutoRepository {
    fun findByCategoria(categoriaProduto: CategoriaProduto): List<Produto>
    fun save(produto: Produto): Produto
    fun findById(id: UUID): Optional<Produto>
    fun delete(produto: Produto)
}