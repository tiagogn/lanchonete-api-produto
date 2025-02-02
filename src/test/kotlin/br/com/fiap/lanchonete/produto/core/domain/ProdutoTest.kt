package br.com.fiap.lanchonete.produto.core.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class ProdutoTest {

 @Test
 fun `should create a valid product`() {
  val produto = Produto(
   id = UUID.randomUUID(),
   nome = "Hamburguer",
   preco = BigDecimal(15.99),
   categoria = CategoriaProduto.LANCHE
  )

  assertNotNull(produto.id)
  assertEquals("Hamburguer", produto.nome)
  assertEquals(BigDecimal(15.99), produto.preco)
  assertEquals(CategoriaProduto.LANCHE, produto.categoria)
 }

 @Test
 fun `should check equality between products`() {
  val id = UUID.randomUUID()
  val produto1 = Produto(id, "Batata Frita", BigDecimal(8.99), CategoriaProduto.ACOMPANHAMENTO)
  val produto2 = Produto(id, "Batata Frita", BigDecimal(8.99), CategoriaProduto.ACOMPANHAMENTO)

  assertEquals(produto1, produto2)
 }

 @Test
 fun `should allow null id`() {
  val produto = Produto(
   id = null,
   nome = "Refrigerante",
   preco = BigDecimal(5.99),
   categoria = CategoriaProduto.BEBIDA
  )

  assertNull(produto.id)
 }
}