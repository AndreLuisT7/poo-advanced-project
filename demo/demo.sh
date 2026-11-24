#!/usr/bin/env bash
# Roteiro automatico da demonstracao (Linux, macOS ou Git Bash no Windows).
# Antes de rodar, suba a aplicacao com o perfil demo (ver demo/README.md).
#
# Uso:  bash demo/demo.sh
#       bash demo/demo.sh seu.email@exemplo.com

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
ADMIN="${ADMIN:-admin:admin123}"
EMAIL="${1:-aluno.demo.$(date +%H%M%S)@exemplo.com}"
SENHA="senha12345"

passo() { printf '\n== %s ==\n' "$1"; }

passo "1. Eventos dos proximos dias (publico)"
curl -sS --fail "$BASE_URL/api/eventos?proximos=true"; echo

passo "2. Instituicoes disponiveis no cadastro (publico)"
curl -sS --fail "$BASE_URL/api/instituicoes"; echo

passo "3. Aluno se cadastra na UCSAL (instituicao 1)"
curl -sS --fail -X POST "$BASE_URL/api/alunos" -H "Content-Type: application/json" \
  -d "{\"nomeCompleto\":\"Aluno Demonstracao\",\"email\":\"$EMAIL\",\"senha\":\"$SENHA\",\"matricula\":\"D$(date +%H%M%S)\",\"curso\":\"Analise e Desenvolvimento de Sistemas\",\"instituicaoId\":1}"; echo

passo "4. Gestor dispara o informativo semanal (exige login ADMIN)"
ENVIO1=$(curl -sS --fail -u "$ADMIN" -X POST "$BASE_URL/api/informativo/enviar")
echo "$ENVIO1"

passo "5. Aluno cancela a propria assinatura com e-mail e senha"
curl -sS --fail -X POST "$BASE_URL/api/assinaturas/cancelar" -H "Content-Type: application/json" \
  -d "{\"email\":\"$EMAIL\",\"senha\":\"$SENHA\"}"; echo

passo "6. Novo disparo: quem cancelou nao e mais contado"
ENVIO2=$(curl -sS --fail -u "$ADMIN" -X POST "$BASE_URL/api/informativo/enviar")
echo "$ENVIO2"

inscritos() { sed -E 's/.*"alunosInscritos":([0-9]+).*/\1/' <<<"$1"; }
ANTES=$(inscritos "$ENVIO1")
DEPOIS=$(inscritos "$ENVIO2")
echo
if [ "$DEPOIS" -eq $((ANTES - 1)) ]; then
  echo "OK: inscritos caiu de $ANTES para $DEPOIS."
else
  echo "ATENCAO: esperava $((ANTES - 1)) inscritos, veio $DEPOIS."
  exit 1
fi
