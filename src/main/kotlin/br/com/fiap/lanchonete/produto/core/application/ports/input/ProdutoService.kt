package br.com.fiap.lanchonete.produto.core.application.ports.input

import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import java.util.UUID

interface ProdutoService {
    fun cadastrarProduto(produto: Produto): Produto
    fun atualizarProduto(produto: Produto): Produto
    fun deletarProduto(id: UUID)
    fun findByCategoria(categoriaProduto: CategoriaProduto): List<Produto>
}