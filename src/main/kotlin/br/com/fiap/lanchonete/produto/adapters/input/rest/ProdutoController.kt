package br.com.fiap.lanchonete.produto.adapters.input.rest

import br.com.fiap.lanchonete.produto.adapters.input.rest.request.ProdutoRequest
import br.com.fiap.lanchonete.produto.adapters.input.rest.request.toModel
import br.com.fiap.lanchonete.produto.adapters.input.rest.request.toUpdate
import br.com.fiap.lanchonete.produto.adapters.input.rest.response.ProdutoResponse
import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.application.ports.input.ProdutoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/produtos")
class ProdutoController(
    private val produtoService: ProdutoService
) {
    @PostMapping
    fun cadastrarProduto(
        @RequestBody produtoRequest: ProdutoRequest
    ): ProdutoResponse {
        val produto = produtoService.cadastrarProduto(produtoRequest.toModel())
        return ProdutoResponse(
            id = produto.id.toString(),
            nome = produto.nome,
            preco = produto.preco,
            categoria = produto.categoria
        )
    }

    @GetMapping("")
    @ResponseBody
    fun buscarProdutoporCategoria(@RequestParam(required = true) categoria: CategoriaProduto): ResponseEntity<List<ProdutoResponse>> {
        val produtos = produtoService.findByCategoria(categoria).map { produto ->
            ProdutoResponse(
                id = produto.id.toString(),
                nome = produto.nome,
                preco = produto.preco,
                categoria = produto.categoria
            )
        }
        return if (produtos.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(produtos)
        }
    }

    @PutMapping("/{id}")
    fun atualizarProduto(
        @PathVariable(required = true) id: UUID,
        @RequestBody(required = true) produtoRequest: ProdutoRequest
    ): ProdutoResponse {
        val produto = produtoService.atualizarProduto(produtoRequest.toUpdate(id))
        return ProdutoResponse(
            id = produto.id.toString(),
            nome = produto.nome,
            preco = produto.preco,
            categoria = produto.categoria
        )
    }

    @DeleteMapping("/{id}")
    fun deletarProduto(
        @PathVariable(required = true) id: UUID
    ): ResponseEntity<Unit> {
        produtoService.deletarProduto(id)
        return ResponseEntity.noContent().build()
    }
}