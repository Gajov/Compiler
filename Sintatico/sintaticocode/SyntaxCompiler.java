package sintaticocode;

import lexicocode.Lexer;
import lexicocode.LexicalError;
import lexicocode.SymbolTable;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SyntaxCompiler {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: java sintaticocode.SyntaxCompiler <arquivo-fonte>");
            return;
        }

        String source = Files.readString(Path.of(args[0]), StandardCharsets.UTF_8);
        SymbolTable symbolTable = new SymbolTable();
        Lexer lexer = new Lexer(source, symbolTable);
        Parser parser = new Parser(lexer);

        try {
            parser.parse();
        } catch (SyntaxError error) {
            if (!lexer.getErrors().isEmpty()) {
                System.out.println("RESULTADO: erro lexico antes da conclusao sintatica.");
                for (LexicalError lexicalError : lexer.getErrors()) System.out.println(lexicalError.format());
                return;
            }
            System.out.println("RESULTADO: erro sintatico.");
            System.out.println(error.format());
            return;
        }

        if (!lexer.getErrors().isEmpty()) {
            System.out.println("RESULTADO: erro lexico antes da conclusao sintatica.");
            for (LexicalError lexicalError : lexer.getErrors()) System.out.println(lexicalError.format());
            return;
        }

        System.out.println("RESULTADO: sucesso na analise sintatica.");
    }
}