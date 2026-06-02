package br.com.zemtech.devicemanager.exceptions;

/**
 * Exceção lançada quando os dados fornecidos são inválidos
 * Exemplo: Coordenadas geográficas fora do intervalo válido, etc.
 */
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
