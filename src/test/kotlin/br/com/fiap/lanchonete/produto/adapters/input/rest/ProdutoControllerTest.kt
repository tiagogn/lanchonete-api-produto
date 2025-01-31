package br.com.fiap.lanchonete.produto.adapters.input.rest

import br.com.fiap.lanchonete.produto.adapters.input.rest.request.ProdutoRequest
import br.com.fiap.lanchonete.produto.core.application.ports.input.ProdutoService
import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.util.UUID

@WebMvcTest(ProdutoController::class)
class ProdutoControllerTest(@Autowired private val mockMvc: MockMvc) {

 @MockkBean
 lateinit var produtoService: ProdutoService

 private val objectMapper = ObjectMapper()
 private lateinit var produto: Produto
 private lateinit var produtoRequest: ProdutoRequest
 private val produtoId = UUID.randomUUID()

 @BeforeEach
 fun setup() {
  produto = Produto(
   id = produtoId,
   nome = "Hamburguer",
   preco = BigDecimal.valueOf(15.99),
   categoria = CategoriaProduto.LANCHE
  )

  produtoRequest = ProdutoRequest(
   nome = "Hamburguer",
   preco = BigDecimal.valueOf(15.99),
   categoria = CategoriaProduto.LANCHE
  )
 }

 @Test
 fun `should create a product`() {
  every { produtoService.cadastrarProduto(any()) } returns produto

  mockMvc.perform(
   post("/v1/produtos")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(produtoRequest))
  )
   .andExpect(status().isOk)
   .andExpect(jsonPath("$.id").value(produtoId.toString()))
   .andExpect(jsonPath("$.nome").value(produto.nome))
   .andExpect(jsonPath("$.preco").value(produto.preco))
   .andExpect(jsonPath("$.categoria").value(produto.categoria.toString()))
 }

 @Test
 fun `should find products by category`() {
  every { produtoService.findByCategoria(CategoriaProduto.LANCHE) } returns listOf(produto)

  mockMvc.perform(
   get("/v1/produtos").param("categoria", CategoriaProduto.LANCHE.toString())
    .contentType(MediaType.APPLICATION_JSON)
  )
   .andExpect(status().isOk)
   .andExpect(jsonPath("$[0].id").value(produtoId.toString()))
   .andExpect(jsonPath("$[0].nome").value(produto.nome))
   .andExpect(jsonPath("$[0].preco").value(produto.preco))
   .andExpect(jsonPath("$[0].categoria").value(produto.categoria.toString()))
 }

 @Test
 fun `should update a product`() {
  every { produtoService.atualizarProduto(any()) } returns produto

  mockMvc.perform(
   put("/v1/produtos/$produtoId")
    .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(produtoRequest))
  )
   .andExpect(status().isOk)
   .andExpect(jsonPath("$.id").value(produtoId.toString()))
   .andExpect(jsonPath("$.nome").value(produto.nome))
   .andExpect(jsonPath("$.preco").value(produto.preco))
   .andExpect(jsonPath("$.categoria").value(produto.categoria.toString()))
 }

 @Test
 fun `should delete a product`() {
  every { produtoService.deletarProduto(produtoId) } just runs

  mockMvc.perform(
   delete("/v1/produtos/$produtoId")
    .contentType(MediaType.APPLICATION_JSON)
  )
   .andExpect(status().isNoContent)
 }
}
