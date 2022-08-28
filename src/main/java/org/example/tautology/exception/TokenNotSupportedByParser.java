package org.example.tautology.exception;

public class TokenNotSupportedByParser extends RuntimeException {
    public TokenNotSupportedByParser(String message) {
        super(message);
    }
}
