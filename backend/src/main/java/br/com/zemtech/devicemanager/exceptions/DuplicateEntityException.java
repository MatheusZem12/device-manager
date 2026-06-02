package br.com.zemtech.devicemanager.exceptions;

/**
 * Exceção lançada quando se tenta criar uma entidade que já existe
 * Exemplo: Usuário com email duplicado, Role com nome duplicado, etc.
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
