package sintaticocode;

import lexicocode.Token;

public class SyntaxError extends RuntimeException {
    private final Token token;

    public SyntaxError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public String format() {
        return token.position() + " - " + getMessage();
    }
}