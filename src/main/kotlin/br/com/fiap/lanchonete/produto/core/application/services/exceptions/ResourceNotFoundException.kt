package br.com.fiap.lanchonete.produto.core.application.services.exceptions

class ResourceNotFoundException(
    override val message: String
): RuntimeException() {
}