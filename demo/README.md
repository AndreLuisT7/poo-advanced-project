# Demonstracao

Fluxo principal: **o estudante se cadastra e passa a receber os eventos da semana por e-mail;
quando cancela a assinatura, deixa de receber.**

## 1. Subir a aplicacao

Pre-requisito: JDK 21. Na raiz do repositorio:

```
cd poo-advanced-project
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

No Windows, use `mvnw.cmd` no lugar de `./mvnw`.

O perfil `demo` usa banco H2 em memoria e ja cria 2 instituicoes e 4 eventos de demonstracao
(3 nos proximos 7 dias e 1 depois disso). Cada execucao comeca com o banco limpo.

O portal fica em `http://localhost:8080`.

## 2. Rodar o roteiro automatico

Com a aplicacao no ar, em outro terminal, na raiz do repositorio:

Windows (PowerShell):

```
powershell -ExecutionPolicy Bypass -File demo\demo.ps1
```

Linux, macOS ou Git Bash:

```
bash demo/demo.sh
```

O script percorre os 6 passos abaixo e termina com `OK: inscritos caiu de 1 para 0.`

| Passo | O que acontece | Quem pode |
|---|---|---|
| 1 | Lista os eventos futuros | qualquer pessoa |
| 2 | Lista as instituicoes do cadastro | qualquer pessoa |
| 3 | Cadastra um estudante na UCSAL | qualquer pessoa |
| 4 | Dispara o informativo semanal na hora | gestor (`admin` / `admin123`) |
| 5 | O estudante cancela a assinatura com e-mail e senha | o proprio estudante |
| 6 | Dispara de novo: quem cancelou nao e contado | gestor |

## 3. Ver o e-mail chegar (opcional)

Sem credenciais de e-mail o fluxo funciona igual, mas o envio aparece em `falhas` na resposta
do passo 4. Para a mensagem chegar de verdade:

1. Configure as credenciais do Gmail como explicado no README principal, na secao
   "Credenciais e variaveis de ambiente".
2. Rode o script passando um e-mail que voce consiga abrir:

```
powershell -ExecutionPolicy Bypass -File demo\demo.ps1 -Email seu.email@exemplo.com
bash demo/demo.sh seu.email@exemplo.com
```

No passo 4, `emailsEnviados` passa a ser 1 e a mensagem chega com os eventos da semana da
UCSAL, com data, local e carga horaria.

## Contingencia

Se a aplicacao nao subir no dia da apresentacao, os passos acima, com a resposta esperada de
cada chamada, estao tambem no [roteiro de teste manual](../tests/README.md).

A contingencia nao substitui o MVP executavel.
