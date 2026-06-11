echo off
echo =========================
echo RODANDO TESTES ORIGINAIS
echo =========================
echo .
call tests\sintatico_tests\run_originais.bat
echo .
echo =========================
echo RODANDO TESTES CORRIGIDOS
echo =========================
echo .
call tests\sintatico_tests\run_corrigidos.bat
