package br.com.zemtech.devicemanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe utilitária para gerar hashes BCrypt de senhas.
 * Útil para criar senhas criptografadas para inserir no banco de dados.
 */
public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String rawPassword = "senha123";
        String hashedPassword = encoder.encode(rawPassword);
        
        System.out.println("===================================");
        System.out.println("PASSWORD HASH GENERATOR");
        System.out.println("===================================");
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password (BCrypt):");
        System.out.println(hashedPassword);
        System.out.println("===================================");
        
        // Verifica se o hash está correto
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Verification: " + (matches ? "✓ SUCCESS" : "✗ FAILED"));
        System.out.println("===================================");
    }
}
