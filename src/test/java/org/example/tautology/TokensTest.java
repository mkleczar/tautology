package org.example.tautology;

import lombok.extern.slf4j.Slf4j;
import org.example.tautology.exception.TokenNotSupportedByParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.tautology.Tokens.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class TokensTest {

    @ParameterizedTest
    @CsvSource({
            "(),2",
            "p,1",
            "p=>q,3",
            "(p|q)&~(p=>q), 12",
            " p | q ,3",
            "p|q(p),6",
            "pppp,4"
    })
    public void parserTest(String str, int size) {
        List<Token> tokenList = parse(str);
        //log.info("Token list: {}", tokenList);
        assertThat(tokenList).hasSize(size);
    }

    @ParameterizedTest
    @CsvSource({
            "p|q!(p), !(p)",
            "*p,*p",
            "a=b,=b",
            "p=>>q, >q"
    })
    public void parserErrorTest(String str, String msg) {
        Exception exception = assertThrows(TokenNotSupportedByParser.class, () -> parse(str));
        assertThat(exception.getMessage()).isEqualTo(msg);
    }
}
