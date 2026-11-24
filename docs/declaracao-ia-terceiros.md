# Anexo E — Declaracao de uso de inteligencia artificial e de componentes de terceiros

**Solucao:** Portal de Eventos Academicos

**Turma:** (preencher)  **Equipe:** (preencher)

**Integrantes:** André Luis Ribeiro da Silva Teixeira, Charles Henrique Barbosa Sales, Kiara Toster
Santos França, Daniel Tambon (completar nome), Gabriel Ferreira (completar nome) e Ricardo
Boa Ventura de Sousa

---

## 1. Uso de inteligencia artificial

**Houve uso de IA?** ( X ) Sim  (   ) Nao

| Ferramenta ou modelo | Finalidade ou tarefa | Parte afetada | Revisao e validacao realizadas |
|---|---|---|---|
| Claude Opus 5.5 e Claude Sonnet 5.5 (Anthropic), usados por meio do Claude Code | Revisao do repositorio frente aos itens obrigatorios da entrega, com lista dos problemas encontrados (portal sem uso da API, envio agendado com erro, teste que dependia do Postgres, rotas administrativas abertas, senha de e-mail no historico, entre outros). | Nenhuma alteracao de codigo; resultado foi uma lista de problemas e recomendacoes. | Os problemas foram reproduzidos subindo a aplicacao, rodando `./mvnw test` e fazendo requisicoes HTTP, em 22 e 23/11/2026. |
| Claude (mesmas ferramentas) | Implementacao dos endpoints `GET /api/eventos` e `GET /api/instituicoes` na branch `feature/listagem-eventos-instituicoes`. | Somente essa branch, que **nao foi incorporada ao `main`**: a mesma funcionalidade ja havia sido feita por outro integrante. | O projeto foi compilado e os endpoints foram exercitados em um teste de integracao temporario com banco em memoria, depois removido. |
| Claude (mesmas ferramentas) | Escrita dos scripts de demonstracao (`demo/demo.ps1` e `demo/demo.sh`), de `demo/README.md` e do roteiro de teste manual (`tests/README.md`). | Pastas `demo/` e `tests/`. | Os dois scripts foram executados contra a aplicacao no perfil `demo` e terminam com sucesso. Cada resposta HTTP listada no roteiro manual foi conferida na aplicacao rodando. `./mvnw test` passou com 12 testes. |
| Claude (mesmas ferramentas) | Redacao da tabela "Estrutura do repositorio e itens da entrega" e correcao do roteiro de demonstracao do `README.md` (os comandos sem login recebiam 401 porque as rotas passaram a exigir o papel ADMIN). | `README.md`. | Os comandos corrigidos foram executados na aplicacao e retornaram o resultado descrito. |
| Claude (mesmas ferramentas) | Organizacao e redacao de `docs/problema-validacao.md` a partir do relatorio e da planilha do questionario e do pitch fornecidos pela equipe. A IA nao coletou, gerou nem estimou dados de validacao. | `docs/problema-validacao.md`. | Os numeros foram transcritos do relatorio e da planilha da equipe. A equipe deve conferir o texto contra essas fontes antes da entrega. |
| Claude (mesmas ferramentas) | Merge da branch `feature/front-integracao-api` no `main`, remocao da pasta `.idea/` do versionamento e inclusao do `.gitattributes`. | Historico do repositorio (commits `7e46b85` e `46b289b`). | O merge foi feito em uma copia separada do repositorio, sem conflitos. Foram rodados os 12 testes e o portal foi testado no navegador (login do gestor e cadastro de evento gravado no banco). |
| (preencher) | (preencher) | (preencher) | (preencher) |

> Cada integrante que usou IA preenche a propria linha. Descreva a tarefa de forma concreta
> (ex: "geracao do servico de envio semanal") e diga como conferiu o resultado
> (ex: "li o codigo, rodei a aplicacao e testei o endpoint").

**Observacoes sobre o uso de IA:**

- As linhas acima descrevem o uso da ferramenta Claude registrado nesta etapa de revisao e
  fechamento da entrega (22 e 23/11/2026). O uso de IA por outros integrantes em outras partes
  do projeto deve ser acrescentado, uma linha por pessoa, na linha em branco da tabela.
- A IA nao definiu o problema, o publico nem a solucao, e nao coletou nem aplicou o
  questionario. O relatorio, a planilha e o pitch foram fornecidos pela equipe e apenas lidos
  pela ferramenta.
- Os arquivos de validacao lidos pela IA nao continham nome nem e-mail dos respondentes.
- Os commits feitos com apoio da IA trazem a linha `Co-Authored-By: Claude` na mensagem.
- Como qualquer saida de IA, o conteudo pode conter erros. Por isso cada alteracao foi
  executada e testada, mas a equipe deve revisar o que sera entregue.

---

## 2. Componentes e recursos de terceiros

**Houve uso de componentes de terceiros?** ( X ) Sim  (   ) Nao

| Componente ou recurso | Origem ou link | Versao e licenca | Funcao e alteracoes realizadas |
|---|---|---|---|
| Spring Boot | https://spring.io/projects/spring-boot | 4.1.1 — Apache 2.0 | Base da aplicacao web, injecao de dependencia e agendamento. Usado sem alteracao. |
| Spring Data JPA | https://spring.io/projects/spring-data-jpa | via Spring Boot — Apache 2.0 | Persistencia e consultas. Usado sem alteracao. |
| Spring Security | https://spring.io/projects/spring-security | via Spring Boot — Apache 2.0 | Protecao de rotas e hash BCrypt. Configurado pela equipe. |
| Spring Mail | https://spring.io/projects/spring-boot | via Spring Boot — Apache 2.0 | Envio do informativo semanal. Configurado pela equipe. |
| Project Lombok | https://projectlombok.org | via Spring Boot — MIT | Getters, setters e construtores nas entidades. Usado sem alteracao. |
| PostgreSQL JDBC Driver | https://jdbc.postgresql.org | runtime — BSD 2-Clause | Conexao com o banco no Neon. Usado sem alteracao. |
| H2 Database | https://www.h2database.com | runtime — MPL 2.0 / EPL 1.0 | Banco em memoria para execucao sem configuracao. Usado sem alteracao. |
| Vonage Server SDK | https://github.com/Vonage/vonage-java-sdk | 9.3.1 — Apache 2.0 | Envio de SMS. Usado sem alteracao. |
| Neon | https://neon.tech | servico — plano gratuito | Hospedagem do banco PostgreSQL. |
| Tailwind CSS (CDN) | https://tailwindcss.com | CDN — MIT | Estilos da interface web. Usado sem alteracao. |

> Confiram se a interface usa alguma outra biblioteca por CDN (fontes, icones) e acrescentem.

---

## 3. Materiais ou codigo preexistentes

**Houve material produzido antes do PooaLAB?** (   ) Sim  ( X ) Nao

**Identificacao e origem:** Nao se aplica. O projeto foi iniciado do zero pelo Spring Initializr
durante a disciplina.

**Contribuicao nova realizada pela equipe:** Modelagem do dominio, cadastro de alunos,
instituicoes e eventos, configuracao de seguranca, servico de informativo semanal agendado,
interface web e documentacao.

---

## 4. Declaracao da equipe

Declaramos que as informacoes acima sao completas e verdadeiras; que revisamos e testamos as
contribuicoes apoiadas por IA; que identificamos as fontes, licencas e partes preexistentes
relevantes; que nao apresentamos como autoria propria material de terceiros; e que nao
compartilhamos dados pessoais, credenciais ou informacoes restritas de forma indevida.

**Responsavel pela submissao:** (preencher)

### Ciencia dos integrantes

| Nome completo | Assinatura ou registro de ciencia |
|---|---|
| André Luis Ribeiro da Silva Teixeira | |
| Charles Henrique Barbosa Sales | |
| Kiara Toster Santos França | |
| Daniel Tambon (completar nome) | |
| Gabriel Ferreira (completar nome) | |
| Ricardo Boa Ventura de Sousa | |

**Data:** ____/____/________
