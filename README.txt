Estrutura do projeto

Compiler/
  lexico/                 Codigos do analisador lexico (pacote lexicocode)
  sintatico/              Codigos do analisador sintatico (pacote sintaticocode)
  main.java               Entrada principal integrada
  tests/
    lexico_tests/         Testes e saidas da etapa lexica
    sintatico_tests/      Testes e saidas da etapa sintatica

Observacao: os codigos dos pacotes foram mantidos em .txt conforme a entrega anterior. Para compilar em Java, copie ou renomeie temporariamente os arquivos .txt para .java preservando os nomes das classes.
Exemplo:
  javac -d build lexico/*.java sintatico/*.java main.java
  java -cp build main tests/sintatico_tests/corrigidos/teste1_c.txt