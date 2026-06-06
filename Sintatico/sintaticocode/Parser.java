package sintaticocode;

import lexicocode.Lexer;
import lexicocode.Token;
import lexicocode.TokenType;

import java.util.Set;

public class Parser {
    private final Lexer lexer;
    private Token current;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.current = lexer.nextToken();
    }

    public void parse() {
        program();
        match(TokenType.EOF);
    }

    private void program() {
        match(TokenType.CLASS);
        match(TokenType.IDENTIFIER);
        match(TokenType.LBRACE);
        while (isType(current.type)) declaration();
        if (current.type == TokenType.LBRACE) {
            block();
        } else {
            statementListUntil(TokenType.RBRACE);
        }
        match(TokenType.RBRACE);
    }

    private void declaration() {
        type();
        identifierList();
        match(TokenType.SEMICOLON);
    }

    private void type() {
        if (isType(current.type)) advance();
        else expected("tipo int, string ou float");
    }

    private void identifierList() {
        match(TokenType.IDENTIFIER);
        while (current.type == TokenType.COMMA) {
            advance();
            match(TokenType.IDENTIFIER);
        }
    }

    private void block() {
        match(TokenType.LBRACE);
        statementListUntil(TokenType.RBRACE);
        match(TokenType.RBRACE);
    }

    private void statementListUntil(TokenType endToken) {
        while (current.type != endToken && current.type != TokenType.EOF) {
            boolean simpleStatement = statement();
            if (current.type == TokenType.SEMICOLON) {
                while (current.type == TokenType.SEMICOLON) advance();
            } else if (simpleStatement) {
                match(TokenType.SEMICOLON);
            }
        }
    }

    private boolean statement() {
        switch (current.type) {
            case IDENTIFIER: assignment(); return true;
            case READ: readStatement(); return true;
            case WRITE: writeStatement(); return true;
            case IF: ifStatement(); return false;
            case DO: doStatement(); return false;
            case REPEAT: repeatStatement(); return false;
            default: expected("inicio de comando"); return true;
        }
    }

    private void assignment() {
        match(TokenType.IDENTIFIER);
        match(TokenType.ASSIGN);
        expression();
    }

    private void ifStatement() {
        match(TokenType.IF);
        match(TokenType.LPAREN);
        expression();
        match(TokenType.RPAREN);
        block();
        if (current.type == TokenType.ELSE) {
            advance();
            block();
        }
    }

    private void doStatement() {
        match(TokenType.DO);
        block();
        match(TokenType.WHILE);
        match(TokenType.LPAREN);
        expression();
        match(TokenType.RPAREN);
    }

    private void repeatStatement() {
        match(TokenType.REPEAT);
        block();
        match(TokenType.UNTIL);
        match(TokenType.LPAREN);
        expression();
        match(TokenType.RPAREN);
    }

    private void readStatement() {
        match(TokenType.READ);
        match(TokenType.LPAREN);
        match(TokenType.IDENTIFIER);
        match(TokenType.RPAREN);
    }

    private void writeStatement() {
        match(TokenType.WRITE);
        match(TokenType.LPAREN);
        expression();
        match(TokenType.RPAREN);
    }

    private void expression() {
        relationalExpression();
        while (current.type == TokenType.AND || current.type == TokenType.OR) {
            advance();
            relationalExpression();
        }
    }

    private void relationalExpression() {
        simpleExpression();
        if (isRelop(current.type)) {
            advance();
            simpleExpression();
        }
    }

    private void simpleExpression() {
        term();
        while (isAddop(current.type)) {
            advance();
            term();
        }
    }

    private void term() {
        factorA();
        while (isMulop(current.type)) {
            advance();
            factorA();
        }
    }

    private void factorA() {
        if (current.type == TokenType.NOT || current.type == TokenType.MINUS) advance();
        factor();
    }

    private void factor() {
        switch (current.type) {
            case IDENTIFIER:
            case INTEGER_CONST:
            case REAL_CONST:
            case STRING_LITERAL:
                advance();
                break;
            case LPAREN:
                advance();
                expression();
                match(TokenType.RPAREN);
                break;
            default:
                expected("identificador, constante ou expressao entre parenteses");
        }
    }

    private void match(TokenType expected) {
        if (current.type == expected) {
            advance();
            return;
        }
        expected(tokenName(expected));
    }

    private void advance() {
        current = lexer.nextToken();
    }

    private void expected(String expected) {
        throw new SyntaxError(current, "esperado " + expected + ", encontrado '" + current.lexeme + "'");
    }

    private String tokenName(TokenType type) {
        switch (type) {
            case CLASS: return "'class'";
            case IDENTIFIER: return "identificador";
            case LBRACE: return "'{'";
            case RBRACE: return "'}'";
            case LPAREN: return "'('";
            case RPAREN: return "')'";
            case SEMICOLON: return "';'";
            case ASSIGN: return "':='";
            default: return type.name();
        }
    }

    private boolean isType(TokenType type) { return type == TokenType.INT || type == TokenType.STRING || type == TokenType.FLOAT; }
    private boolean isRelop(TokenType type) { return Set.of(TokenType.GT, TokenType.GE, TokenType.LT, TokenType.LE, TokenType.NE, TokenType.EQ).contains(type); }
    private boolean isAddop(TokenType type) { return Set.of(TokenType.PLUS, TokenType.MINUS).contains(type); }
    private boolean isMulop(TokenType type) { return Set.of(TokenType.TIMES, TokenType.DIVIDE, TokenType.MOD).contains(type); }
}