# Portal de Eventos Academicos

Portal em que o estudante se cadastra uma unica vez, escolhe sua instituicao e passa a
receber por e-mail, toda semana, a lista dos eventos que valem horas complementares.
A assinatura pode ser desligada e religada pelo proprio aluno.

## Problema e publico

**Publico:** estudantes de graduacao que precisam cumprir carga horaria de atividades
complementares para colar grau.

**Problema:** os eventos que valem essas horas sao divulgados de forma espalhada, em murais,
grupos de turma e perfis diferentes. O estudante descobre o evento depois que ele ja aconteceu.

**Impacto:** o aluno chega ao fim do curso sem a carga horaria completa e precisa correr atras
de eventos no ultimo semestre.

**Alternativa atual:** acompanhar manualmente grupos de WhatsApp, Instagram da instituicao e
cartazes no campus.

**Proposta de valor:** o aluno se inscreve uma vez e a informacao passa a chegar ate ele, ja
filtrada pela instituicao dele e com a carga horaria de cada evento.

## Integrantes e turma

| Nome completo | Turma |
|---|---|
| (preencher) | (preencher) |
| (preencher) | (preencher) |
| (preencher) | (preencher) |
| (preencher) | (preencher) |
| (preencher) | (preencher) |

## Tecnologias

| Tecnologia | Versao | Por que |
|---|---|---|
| Java | 21 | Linguagem da disciplina |
| Spring Boot | 4.1.1 | Web, injecao de dependencia e agendamento |
| Spring Data JPA | — | Persistencia sem SQL manual |
| Spring Security | — | Protecao das rotas administrativas e hash de senha |
| Spring Mail | — | Envio do informativo semanal |
| PostgreSQL (Neon) | — | Banco da aplicacao |
| H2 | — | Banco em memoria para rodar sem configurar nada |
| Lombok | — | Reduz codigo repetitivo nas entidades |
| Vonage Server SDK | 9.3.1 | Envio de SMS |
| Maven Wrapper | — | Build sem instalar Maven |

## Pre-requisitos

- JDK 21
- Nao e necessario instalar Maven, o projeto usa o wrapper (`mvnw`)
- Nao e necessario instalar banco para a demonstracao, o H2 roda em memoria

## Como executar

### Opcao 1 — H2 em memoria (recomendada para avaliacao)

Um comando, sem configurar nada:

```
cd poo-advanced-project
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

No Windows, troque `./mvnw` por `mvnw.cmd`.

O perfil `demo` usa banco H2 em memoria e nao precisa de senha nem de internet para o banco.
A aplicacao sobe em `http://localhost:8080` e ja cria duas instituicoes e quatro eventos de
demonstracao automaticamente. Ao parar a aplicacao os dados somem, entao cada execucao comeca limpa.

### Opcao 2 — PostgreSQL no Neon

Copie `src/main/resources/application-local.properties.example` para
`application-local.properties` e preencha com os dados do seu banco:

```
spring.datasource.url=jdbc:postgresql://SEU_HOST.neon.tech/SEU_BANCO?sslmode=require
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

Esse arquivo esta no `.gitignore` e nao deve ser versionado. Depois rode:

```
cd poo-advanced-project
./mvnw spring-boot:run
```

As tabelas sao criadas automaticamente pelo Hibernate no primeiro start.

## Credenciais de teste

Usuarios em memoria, apenas para demonstracao:

| Usuario | Senha | Papel |
|---|---|---|
| admin | admin123 | ADMIN |
| user | user123 | USER |

## Endpoints

| Metodo | Rota | O que faz |
|---|---|---|
| POST | `/api/alunos` | Cadastra um aluno e ativa a assinatura |
| GET | `/api/alunos/{id}` | Consulta um aluno |
| PATCH | `/api/alunos/{id}/assinatura?ativa=true\|false` | Liga ou desliga a assinatura |
| POST | `/api/instituicoes` | Cadastra uma instituicao |
| GET | `/api/instituicoes` | Lista as instituicoes |
| POST | `/api/eventos` | Cadastra um evento |
| GET | `/api/eventos` | Lista os eventos |
| GET | `/api/eventos?proximos=true` | Lista apenas os eventos futuros |
| GET | `/api/eventos/{id}` | Consulta um evento |
| POST | `/api/informativo/enviar` | Dispara o informativo semanal na hora |

## Arquitetura

A aplicacao segue arquitetura em camadas, cada uma com uma responsabilidade e dependendo
apenas da camada seguinte:

```
   HTTP
     |
     v
[ controller ]  traduz HTTP: recebe JSON, valida formato, devolve status
     |
     v
[   service   ]  regra de negocio: e aqui que as decisoes acontecem
     |
     v
[ repository ]  acesso ao banco, sem regra de negocio
     |
     v
[   banco     ]
```

Duas estruturas atravessam as camadas sem pertencer a nenhuma:

- **`model`** — as entidades JPA (`Aluno`, `Instituicao`, `Evento`), que representam o dominio
  e as tabelas.
- **`dto`** — o contrato da API. Sao `record` imutaveis, separados das entidades de proposito:
  `CadastroAlunoRequest` recebe a senha, `AlunoResponse` nunca a devolve. Se a entidade mudar,
  o contrato com o front nao quebra junto.

Pacotes de apoio:

- **`config`** — `SecurityConfig` (regras de acesso e BCrypt) e `DadosDemonstracao` (carga inicial).
- **`exception`** — excecoes de negocio e o `ApiExceptionHandler`, que centraliza a traducao
  de erro em resposta HTTP.
- **`service/notificacao`** — a abstracao do canal de aviso ao aluno.

### Fluxo do informativo semanal

```
@Scheduled (segunda, 8h)   ou   POST /api/informativo/enviar
                 |
                 v
     InformativoSemanalService
                 |
     +-----------+------------+
     |                        |
     v                        v
AlunoRepository          EventoRepository
(assinatura ativa)    (proximos 7 dias, por instituicao)
                 |
                 v
          Notificador  <interface>
                 |
                 v
          NotificadorEmail  ->  EmailService  ->  SMTP
```

## Aplicacao dos conteudos de POOA

### SOLID

**S — Responsabilidade unica.** Cada camada tem um motivo para mudar. `AlunoController` so
cuida de HTTP e nao sabe o que e e-mail duplicado; `AlunoService` decide a regra e nao sabe que
existe HTTP; `AlunoRepository` so busca e grava. O mesmo vale dentro do informativo:
`InformativoSemanalService` decide *quem* recebe e *o que* vai na mensagem, enquanto
`NotificadorEmail` cuida de *como* entregar.

**O — Aberto/fechado.** Dois pontos no codigo:

- `ApiExceptionHandler` concentra o tratamento de erro. Uma excecao nova so precisa de um novo
  metodo anotado com `@ExceptionHandler`; nenhum controller existente e alterado.
- `Notificador` e uma interface. Para passar a avisar tambem por SMS, basta uma classe nova
  implementando `Notificador`, sem encostar em `InformativoSemanalService`.

**L — Substituicao de Liskov.** Os repositorios estendem `JpaRepository` e sao usados pelo tipo
da interface. `InformativoSemanalService` recebe um `Notificador` e funciona com qualquer
implementacao que respeite o contrato, sem saber qual chegou.

**I — Segregacao de interfaces.** Em vez de um repositorio unico com todas as consultas do
sistema, existem tres (`AlunoRepository`, `EventoRepository`, `InstituicaoRepository`), cada um
com os metodos do seu agregado. Quem precisa de evento nao carrega metodo de aluno junto.
`Notificador` tambem e minima: um unico metodo.

**D — Inversao de dependencia.** Nenhum service instancia suas dependencias com `new`. Todas
chegam pelo construtor (via `@RequiredArgsConstructor`), e sempre pelo tipo mais abstrato
disponivel: os services dependem das *interfaces* de repositorio, e `InformativoSemanalService`
depende da interface `Notificador`, nao de `EmailService`.

### Padrao GoF

**Adapter** — `NotificadorEmail` adapta a assinatura do `EmailService`, que devolve uma `String`
com mensagem de erro embutida, ao contrato tipado `Notificador.Resultado`. Isso permitiu colocar
o `EmailService` atras da abstracao sem alterar uma linha dele.

### Collections e Streams

Usados onde ha transformacao de colecao, nao por enfeite:

- `EventoService.listar()` e `listarProximos()` convertem `List<Evento>` em `List<EventoResponse>`
  com `stream().map(EventoResponse::de).toList()`.
- `InstituicaoService.listar()` segue o mesmo caminho.
- As consultas derivadas do Spring Data (`findByAssinaturaAtivaTrue`,
  `findByInstituicaoIdAndDataHoraBetweenOrderByDataHoraAsc`) devolvem `List` ja ordenada,
  evitando ordenacao manual.

### Anotacoes e reflexao

O projeto se apoia em metaprogramacao em tres frentes:

- **JPA** (`@Entity`, `@ManyToOne`, `@Column`) — o Hibernate le as anotacoes por reflexao em
  tempo de execucao e gera o esquema e as consultas.
- **Spring** (`@Service`, `@RestController`, `@Scheduled`) — o container varre as classes,
  instancia e injeta as dependencias por reflexao.
- **Bean Validation** (`@NotBlank`, `@Email`, `@Size`) — as regras ficam declaradas no proprio
  `record` do DTO e sao aplicadas antes do metodo do controller executar.
- **Lombok** (`@Getter`, `@RequiredArgsConstructor`) — processador de anotacoes que gera o
  codigo repetitivo em tempo de compilacao.

### Recursos de Java moderno

- **`record`** (Java 16+) em todos os DTOs, garantindo imutabilidade do contrato da API.
- **Metodo de fabrica estatico** `EventoResponse.de(...)`, `AlunoResponse.de(...)`, mantendo a
  conversao junto do tipo que ela produz.
- **Concorrencia** — o `@Scheduled` do informativo roda em uma thread do pool do Spring,
  separada das threads que atendem as requisicoes HTTP, entao o envio semanal nao bloqueia a API.

### Onde o projeto ainda nao esta bem resolvido

Declarado por honestidade, como pede o manual:

- `SmsService` ainda nao implementa `Notificador`; ele existe mas nao esta ligado a nenhum fluxo.
  Encaixa-lo e justamente o exercicio de OCP descrito acima.
- `AlunoService` acumula cadastro, consulta e troca de assinatura. Com o sistema crescendo,
  valeria separar a assinatura em um service proprio.
- A carga horaria vive dentro do texto de `categoria` ("Oficina - 2h"), o que impede somar as
  horas de um aluno. O certo seria um campo numerico no `Evento`.

## Roteiro da demonstracao

O fluxo principal e: **o aluno se inscreve e passa a receber os eventos da semana por e-mail**.

1. Suba a aplicacao com `./mvnw spring-boot:run -Dspring-boot.run.profiles=demo`. Os dados de
   demonstracao ja entram sozinhos.

2. Confira os eventos que serao divulgados:

```
curl http://localhost:8080/api/eventos?proximos=true
```

3. Cadastre um aluno na instituicao 1 (UCSAL), usando um e-mail real para ver a mensagem chegar:

```
curl -X POST http://localhost:8080/api/alunos -H "Content-Type: application/json" -d "{\"nomeCompleto\":\"Maria Souza\",\"email\":\"maria@exemplo.com\",\"senha\":\"senha12345\",\"matricula\":\"2023001\",\"curso\":\"Analise e Desenvolvimento\",\"instituicaoId\":1}"
```

A resposta vem com `assinaturaAtiva: true`.

4. Dispare o informativo sem esperar a segunda-feira:

```
curl -X POST http://localhost:8080/api/informativo/enviar
```

A resposta mostra quantos alunos estavam inscritos, quantos e-mails sairam e quais falharam.
O e-mail chega com os eventos da proxima semana daquela instituicao, com data, local e
carga horaria.

5. Mostre que o aluno pode sair da lista:

```
curl -X PATCH "http://localhost:8080/api/alunos/1/assinatura?ativa=false"
curl -X POST http://localhost:8080/api/informativo/enviar
```

Agora `alunosInscritos` cai para zero: quem desligou a assinatura nao recebe mais.

No uso normal esse disparo acontece sozinho toda segunda-feira as 8h, pelo agendamento em
`InformativoSemanalService`. O endpoint manual existe para a demonstracao ao vivo.

## Como executar os testes

```
cd poo-advanced-project
./mvnw test
```

## Erros tratados

| Situacao | Resposta |
|---|---|
| Campo invalido ou em branco | 400 com `{campo: mensagem}` |
| E-mail ja cadastrado | 409 |
| Instituicao, aluno ou evento inexistente | 404 |

## Limitacoes conhecidas

- A interface web (`index.html`) ainda nao consome a API. As telas existem e navegam, mas os
  dados sao de exemplo no navegador. O fluxo principal e demonstrado pela API.
- Nao existe login do aluno. O `PasswordEncoder` ja grava a senha em BCrypt no cadastro, mas a
  autenticacao de aluno nao foi implementada; so existem os usuarios administrativos em memoria.
- Por causa disso, `GET /api/alunos/{id}` esta aberto e devolve dados de aluno sem autenticacao.
- O envio de SMS (`SmsService`) esta implementado mas nao e acionado por nenhum fluxo.
- O campo de carga horaria do evento e texto livre dentro de `categoria` (ex: "Oficina - 2h"),
  nao um numero separado. Isso impede somar as horas de um aluno.
- A matricula nao e unica no banco.
- O agendamento semanal nao guarda historico do que ja foi enviado, entao dois disparos no mesmo
  dia enviam o mesmo conteudo duas vezes.
- A cobertura de testes automatizados e minima.

## Proximos passos

1. Conectar a interface web a API.
2. Implementar o login do aluno e fechar as rotas de consulta.
3. Separar a carga horaria em campo numerico e somar as horas por aluno.
4. Registrar o historico de envios para evitar repeticao.

## Fontes, componentes de terceiros e uso de IA

Declarados em [docs/declaracao-ia-terceiros.md](docs/declaracao-ia-terceiros.md).
