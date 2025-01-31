import br.com.fiap.lanchonete.produto.core.application.ports.output.ProdutoRepository
import br.com.fiap.lanchonete.produto.core.application.services.ProdutoServiceImpl
import br.com.fiap.lanchonete.produto.core.application.services.exceptions.ResourceNotFoundException
import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProdutoServiceImplTest {

 private lateinit var produtoRepository: ProdutoRepository
 private lateinit var produtoService: ProdutoServiceImpl

 @BeforeEach
 fun setup() {
  produtoRepository = mockk()
  produtoService = ProdutoServiceImpl(produtoRepository)
 }

 @Test
 fun `should register a product`() {
  val produto = Produto(
   id = UUID.randomUUID(),
   nome = "Hamburguer",
   preco = BigDecimal(25.00),
   categoria = CategoriaProduto.LANCHE
  )

  every { produtoRepository.save(produto) } returns produto

  val savedProduto = produtoService.cadastrarProduto(produto)

  assertNotNull(savedProduto)
  assertEquals(produto, savedProduto)
  verify(exactly = 1) { produtoRepository.save(produto) }
 }

 @Test
 fun `should update an existing product`() {
  val produto = Produto(
   id = UUID.randomUUID(),
   nome = "Hamburguer",
   preco = BigDecimal(25.00),
   categoria = CategoriaProduto.LANCHE
  )

  every { produtoRepository.findById(produto.id!!) } returns Optional.of(produto)
  every { produtoRepository.save(produto) } returns produto

  val updatedProduto = produtoService.atualizarProduto(produto)

  assertNotNull(updatedProduto)
  assertEquals(produto, updatedProduto)
  verify(exactly = 1) { produtoRepository.findById(produto.id!!) }
  verify(exactly = 1) { produtoRepository.save(produto) }
 }

 @Test
 fun `should throw exception when updating a non-existing product`() {
  val produto = Produto(
   id = UUID.randomUUID(),
   nome = "Pizza",
   preco = BigDecimal(50.00),
   categoria = CategoriaProduto.LANCHE
  )

  every { produtoRepository.findById(produto.id!!) } returns Optional.empty()

  assertThrows<ResourceNotFoundException> {
   produtoService.atualizarProduto(produto)
  }
  verify(exactly = 1) { produtoRepository.findById(produto.id!!) }
  verify(exactly = 0) { produtoRepository.save(any()) }
 }

 @Test
 fun `should delete a product`() {
  val produto = Produto(
   id = UUID.randomUUID(),
   nome = "Refrigerante",
   preco = BigDecimal(10.00),
   categoria = CategoriaProduto.BEBIDA
  )

  every { produtoRepository.findById(produto.id!!) } returns Optional.of(produto)
  every { produtoRepository.delete(produto) } just Runs

  produtoService.deletarProduto(produto.id!!)

  verify(exactly = 1) { produtoRepository.findById(produto.id!!) }
  verify(exactly = 1) { produtoRepository.delete(produto) }
 }

 @Test
 fun `should throw exception when deleting a non-existing product`() {
  val id = UUID.randomUUID()

  every { produtoRepository.findById(id) } returns Optional.empty()

  assertThrows<ResourceNotFoundException> {
   produtoService.deletarProduto(id)
  }
  verify(exactly = 1) { produtoRepository.findById(id) }
  verify(exactly = 0) { produtoRepository.delete(any()) }
 }

 @Test
 fun `should find products by category`() {
  val categoria = CategoriaProduto.LANCHE
  val produtos = listOf(
   Produto(UUID.randomUUID(), "Hamburguer", BigDecimal(25.00), categoria),
   Produto(UUID.randomUUID(), "Pizza", BigDecimal(50.00), categoria)
  )

  every { produtoRepository.findByCategoria(categoria) } returns produtos

  val result = produtoService.findByCategoria(categoria)

  assertEquals(produtos.size, result.size)
  assertEquals(produtos, result)
  verify(exactly = 1) { produtoRepository.findByCategoria(categoria) }
 }
}