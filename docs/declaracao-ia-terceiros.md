# Anexo E — Declaracao de uso de inteligencia artificial e de componentes de terceiros

**Solucao:** Portal de Eventos Academicos

**Turma:** (preencher)  **Equipe:** (preencher)

**Integrantes:** André Luis Ribeiro da Silva Teixeira, Charles Henrique Barbosa Sales, Kiara Toster
Santos França, Daniel Tambon (completar nome), Gabriel Ferreira (completar nome) e Ricardo
(completar nome)

---

## 1. Uso de inteligencia artificial

**Houve uso de IA?** ( X ) Sim  (   ) Nao

| Ferramenta ou modelo | Finalidade ou tarefa | Parte afetada | Revisao e validacao realizadas |
|---|---|---|---|
| Claude Opus 5.5 (Anthropic), via Claude Code | Revisao do repositorio frente aos itens de entrega; escrita dos scripts e instrucoes de demonstracao, do roteiro de teste manual e da tabela de estrutura no README; organizacao do registro de validacao a partir do relatorio e da planilha do questionario aplicados pela equipe; correcao dos comandos do roteiro de demonstracao no README. | `demo/`, `tests/README.md`, `docs/problema-validacao.md`, secao de estrutura e roteiro de demonstracao do `README.md` | Os scripts de demonstracao e cada chamada do roteiro de teste foram executados contra a aplicacao no perfil `demo` em 23/11/2026, e `./mvnw test` passou. Os numeros da validacao vieram apenas do relatorio e da planilha da equipe. (A equipe deve completar com a propria revisao.) |
| (preencher) | (preencher) | (preencher) | (preencher) |

> Cada integrante que usou IA preenche a propria linha. Descreva a tarefa de forma concreta
> (ex: "geracao do servico de envio semanal") e diga como conferiu o resultado
> (ex: "li o codigo, rodei a aplicacao e testei o endpoint").

**Observacoes sobre o uso de IA:** (preencher)

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
| Ricardo (completar nome) | |

**Data:** ____/____/________
