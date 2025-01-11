package br.com.fiap.lanchonete.produto.adapters.config

import br.com.fiap.lanchonete.produto.core.application.ports.input.ProdutoService
import br.com.fiap.lanchonete.produto.core.application.ports.output.ProdutoRepository
import br.com.fiap.lanchonete.produto.core.application.services.ProdutoServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigBeans(
    private val produtoRepository: ProdutoRepository,
) {
    @Bean
    fun produtoService(): ProdutoService {
        return ProdutoServiceImpl(produtoRepository)
    }
}