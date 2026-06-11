Estrutura do projeto

Compiler/
  lexico/                 Codigos do analisador lexico (pacote lexico)
  sintatico/              Codigos do analisador sintatico (pacote sintatico)
  main.java               Entrada principal integrada
  tests/
    lexico_tests/         Testes e saidas da etapa lexica
    sintatico_tests/      Testes e saidas da etapa sintatica

Exemplo:
  javac -d build lexico/*.java sintatico/*.java main.java
  java -cp build main tests/sintatico_tests/corrigidos/teste1_s.txt