package br.com.fiap.lanchonete.produto.adapters.output

import br.com.fiap.lanchonete.produto.core.domain.CategoriaProduto
import br.com.fiap.lanchonete.produto.core.domain.Produto
import br.com.fiap.lanchonete.produto.core.application.ports.output.ProdutoRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Qualifier("produtoRepository")
class ProdutoRepositoryAdapter(
    private val jdbcClient: JdbcClient
) : ProdutoRepository {
    override fun findByCategoria(categoriaProduto: CategoriaProduto): List<Produto> {
        return jdbcClient.sql(
            """
            SELECT id, nome, preco, categoria
            FROM produto
            WHERE categoria = :categoria
        """
        )
            .params(
                mapOf(
                    "categoria" to categoriaProduto.name
                )
            )
            .query { rs, _ ->
                Produto(
                    id = UUID.fromString(rs.getString("id")),
                    nome = rs.getString("nome"),
                    preco = rs.getBigDecimal("preco"),
                    categoria = CategoriaProduto.valueOf(rs.getString("categoria"))
                )
            }
            .list()
    }

    override fun save(produto: Produto): Produto {
        if (produto.id == null) {
            persist(produto)
        } else {
            update(produto)
        }
        return produto
    }

    private fun persist(produto: Produto) {
        produto.id = UUID.randomUUID()
        jdbcClient.sql(
            """
            INSERT INTO produto (id, nome, preco, categoria)
            VALUES (:id, :nome, :preco, :categoria)
        """
        )
            .params(
                mapOf(
                    "id" to produto.id,
                    "nome" to produto.nome,
                    "preco" to produto.preco,
                    "categoria" to produto.categoria.name
                )
            )
            .update()
    }

    private fun update(produto: Produto) {
        jdbcClient.sql(
            """
            UPDATE produto
            SET nome = :nome, preco = :preco, categoria = :categoria
            WHERE id = :id
        """
        )
            .params(
                mapOf(
                    "id" to produto.id,
                    "nome" to produto.nome,
                    "preco" to produto.preco,
                    "categoria" to produto.categoria.name
                )
            )
            .update()
    }

    override fun findById(id: UUID): Optional<Produto> {
        return jdbcClient.sql(
            """
            SELECT id, nome, preco, categoria
            FROM produto
            WHERE id = :id
        """
        )
            .params(
                mapOf(
                    "id" to id
                )
            )
            .query { rs, _ ->
                Produto(
                    id = UUID.fromString(rs.getString("id")),
                    nome = rs.getString("nome"),
                    preco = rs.getBigDecimal("preco"),
                    categoria = CategoriaProduto.valueOf(rs.getString("categoria"))
                )
            }
            .optional()
    }

    override fun delete(produto: Produto) {
        jdbcClient.sql(
            """
            DELETE FROM produto
            WHERE id = :id
        """
        )
            .params(
                mapOf(
                    "id" to produto.id
                )
            )
            .update()
    }
}