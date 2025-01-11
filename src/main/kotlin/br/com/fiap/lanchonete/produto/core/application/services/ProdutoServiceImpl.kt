package br.com.fiap.lanchonete.produto.core.application.services

import br.com.fiap.lanchonete.produto.core.application.ports.input.ProdutoService
import br.com.fiap.lanchonete.produto.core.application.ports.output.ProdutoRepository
import br.com.fiap.lanchonete.produto.core.application.services.exceptions.ResourceNotFoundException
import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import java.util.UUID

class ProdutoServiceImpl(
    private val produtoRepository: ProdutoRepository
) : ProdutoService {

    override fun cadastrarProduto(produto: Produto): Produto {
        return produtoRepository.save(produto)
    }

    override fun atualizarProduto(produto: Produto): Produto {
        produtoRepository.findById(produto.id!!)
            .orElseThrow { ResourceNotFoundException("Produto não encontrado") }
        return produtoRepository.save(produto)
    }

    override fun deletarProduto(id: UUID) {
        val produto = produtoRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Produto não encontrado") }
        produtoRepository.delete(produto)
    }

    override fun findByCategoria(categoriaProduto: CategoriaProduto): List<Produto> {
        return produtoRepository.findByCategoria(categoriaProduto)

    }

}