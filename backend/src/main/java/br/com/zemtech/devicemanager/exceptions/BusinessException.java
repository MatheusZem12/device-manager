package br.com.zemtech.devicemanager.exceptions;

/**
 * Exceção lançada quando uma operação de negócio não pode ser concluída
 * devido a regras de negócio ou validações específicas
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
