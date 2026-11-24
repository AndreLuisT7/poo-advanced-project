# Portal de Eventos Academicos

Portal em que o estudante se cadastra uma unica vez, escolhe sua instituicao e passa a
receber por e-mail, toda semana, a lista dos eventos que valem horas complementares.
A assinatura pode ser desligada e religada pelo proprio aluno.

## Estrutura do repositorio e itens da entrega

| Item | Onde esta |
|---|---|
| Codigo-fonte | [`poo-advanced-project/src/main`](poo-advanced-project/src/main) (back-end Spring Boot e o portal em `resources/static/index.html`) |
| Testes | [`tests/`](tests/README.md) explica e lista os testes automatizados, que ficam em [`poo-advanced-project/src/test`](poo-advanced-project/src/test), e traz um roteiro de teste manual |
| Demonstracao | [`demo/`](demo/README.md): instrucoes e scripts (`demo.ps1` e `demo.sh`) que percorrem o fluxo principal |
| Validacao | [`docs/problema-validacao.md`](docs/problema-validacao.md), com o relatorio e as respostas do questionario em [`docs/validacao/`](docs/validacao) |
| Definicao do MVP | [`docs/definicao-mvp.md`](docs/definicao-mvp.md) |
| Pitch | [`docs/pitch.pdf`](docs/pitch.pdf) |
| Declaracao de IA e terceiros | [`docs/declaracao-ia-terceiros.md`](docs/declaracao-ia-terceiros.md) |

O projeto Maven fica na pasta `poo-advanced-project/`, por isso o codigo e os testes seguem a
estrutura padrao do Maven (`src/main` e `src/test`) dentro dela.

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

> ATENCAO equipe: faltam o nome completo de tres integrantes e a turma de todos.

| Nome completo | Turma |
|---|---|
| André Luis Ribeiro da Silva Teixeira | (preencher) |
| Charles Henrique Barbosa Sales | (preencher) |
| Kiara Toster Santos França | (preencher) |
| Daniel Tambon (completar nome) | (preencher) |
| Gabriel Ferreira (completar nome) | (preencher) |
| Ricardo (completar nome) | (preencher) |

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

## Credenciais e variaveis de ambiente

Nenhuma credencial fica versionada. O repositorio nao contem senha de e-mail, chave de API nem
dados do banco.

### Para o envio de e-mail funcionar

O cadastro, a listagem e o portal funcionam sem configurar nada. Para o informativo sair de
verdade, defina duas variaveis de ambiente antes de subir a aplicacao:

Windows (PowerShell):

```
$env:MAIL_USERNAME = "conta@gmail.com"
$env:MAIL_PASSWORD = "senha de aplicativo do Gmail"
```

Linux ou macOS:

```
export MAIL_USERNAME="conta@gmail.com"
export MAIL_PASSWORD="senha de aplicativo do Gmail"
```

Use uma senha de aplicativo do Gmail, nao a senha da conta. Sem essas variaveis a aplicacao
sobe normalmente e o disparo do informativo responde com a falha de envio no campo `falhas`,
em vez de quebrar.

Alternativa: coloque os mesmos valores em `application-local.properties`, que esta no
`.gitignore`. Use `application-local.properties.example` como modelo.

### Usuarios administrativos

Usuarios em memoria, apenas para demonstracao:

| Usuario | Senha | Papel |
|---|---|---|
| admin | admin123 | ADMIN |
| user | user123 | USER |

## Endpoints

Rotas publicas, usadas pelo portal:

| Metodo | Rota | O que faz |
|---|---|---|
| POST | `/api/alunos` | Cadastra um aluno e ativa a assinatura |
| POST | `/api/alunos/login` | O aluno entra com e-mail e senha (sem sessao: o portal reenvia as credenciais a cada acao) |
| POST | `/api/assinaturas/cancelar` | O proprio aluno cancela, informando e-mail e senha |
| POST | `/api/assinaturas/ativar` | O proprio aluno religa o informativo, informando e-mail e senha |
| GET | `/api/eventos` | Lista os eventos |
| GET | `/api/eventos?proximos=true` | Lista apenas os eventos futuros |
| GET | `/api/eventos/{id}` | Consulta um evento |
| GET | `/api/instituicoes` | Lista as instituicoes |

Rotas administrativas, exigem login com papel `ADMIN`:

| Metodo | Rota | O que faz |
|---|---|---|
| POST | `/api/eventos` | Cadastra um evento |
| POST | `/api/instituicoes` | Cadastra uma instituicao |
| GET | `/api/alunos/{id}` | Consulta os dados de um aluno |
| PATCH | `/api/alunos/{id}/assinatura?ativa=true\|false` | Liga ou desliga a assinatura de um aluno |
| POST | `/api/informativo/enviar` | Dispara o informativo semanal na hora |

Dados pessoais de aluno e o disparo em massa de e-mail ficam atras de autenticacao de proposito:
sao as duas rotas com potencial de vazamento e de abuso.

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

### Correcoes de projeto feitas apos a revisao por pares

Um integrante revisou o codigo e apontou problemas reais. O que mudou:

- **Vazamento de abstracao no agendamento.** O envio automatico quebrava com
  `LazyInitializationException`: o metodo anotado com `@Scheduled` chamava o metodo transacional
  pela propria instancia (`this`), o proxy do Spring nao entrava em acao e nao havia sessao
  aberta para carregar a instituicao do aluno. A correcao foi na consulta, nao na anotacao: os
  repositorios passaram a usar `join fetch` e devolvem o agregado completo, o que elimina a
  dependencia de uma transacao aberta durante o envio.
- **Injecao por campo.** `EmailService` usava `@Autowired` em atributo; passou a receber o
  `JavaMailSender` pelo construtor, como as demais classes.
- **Dependencia criada com `new` dentro do metodo.** `SmsService` construia o `VonageClient` a
  cada envio. O cliente virou um bean (`VonageConfig`) injetado por construtor.
- **Responsabilidade acumulada.** `AlunoService` cuidava tambem da assinatura; a assinatura saiu
  para `AssinaturaService`. A redacao da mensagem saiu de `InformativoSemanalService` para
  `RedatorInformativo`.
- **Modelagem.** A carga horaria vivia dentro do texto da categoria ("Palestra - 4h"), o que
  impedia somar horas. Virou o campo numerico `cargaHoraria` em `Evento`.

### Onde o projeto ainda nao esta bem resolvido

Declarado por honestidade, como pede o manual:

- O `@Scheduled` nao e idempotente: dois disparos no mesmo dia reenviam o mesmo conteudo.
- `NotificadorSms` existe e respeita o contrato, mas o cadastro ainda nao coleta telefone, entao
  o canal nao tem como ser usado de verdade.
- Nao ha teste de camada web (`@WebMvcTest`); os testes cobrem servico e regra de negocio.

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

4. Dispare o informativo sem esperar a segunda-feira. A rota e administrativa, entao exige o
   login do gestor:

```
curl -u admin:admin123 -X POST http://localhost:8080/api/informativo/enviar
```

A resposta mostra quantos alunos estavam inscritos, quantos e-mails sairam e quais falharam.
O e-mail chega com os eventos da proxima semana daquela instituicao, com data, local e
carga horaria.

5. Mostre que o aluno pode sair da lista, cancelando com o e-mail e a senha do cadastro:

```
curl -X POST http://localhost:8080/api/assinaturas/cancelar -H "Content-Type: application/json" -d "{\"email\":\"maria@exemplo.com\",\"senha\":\"senha12345\"}"
curl -u admin:admin123 -X POST http://localhost:8080/api/informativo/enviar
```

Agora `alunosInscritos` cai para zero: quem desligou a assinatura nao recebe mais.

Os passos 2 a 5 tambem estao automatizados em [`demo/`](demo/README.md).

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

- O login do aluno (`POST /api/alunos/login`) so confere e-mail e senha (BCrypt). Nao ha
  sessao nem token no servidor: o portal guarda as credenciais apenas na memoria da aba e as
  reenvia ao ligar ou desligar o informativo. As rotas que expoem dados pessoais ficam
  restritas ao papel `ADMIN`.
- Nao existe inscricao por evento: o aluno assina o informativo semanal da instituicao, e ele
  liga ou desliga isso em "Minha assinatura". A API tambem nao tem editar nem excluir evento,
  so cadastrar (`POST /api/eventos`, papel `ADMIN`).
- O login do gestor usa HTTP Basic do Spring Security. O portal guarda o cabecalho so na memoria
  da aba (nunca em localStorage) e a sessao expira em 30 minutos.
- Os usuarios administrativos sao fixos em memoria (`admin` e `user`), definidos em
  `SecurityConfig`. Nao ha cadastro de administrador.
- O informativo nao guarda historico de envio, entao dois disparos no mesmo dia mandam o mesmo
  conteudo duas vezes.
- O canal de SMS esta implementado (`NotificadorSms`) mas nao e o canal ativo. Para usa-lo seria
  preciso trocar qual implementacao de `Notificador` e a primaria, e o cadastro do aluno ainda
  nao coleta telefone.
- O portal inteiro (cadastro, login, assinatura, listagem e cadastro de eventos) usa a API; nao
  resta dado simulado no navegador.
- A carga horaria e um numero inteiro de horas; nao ha suporte a fracao.
- Nao ha paginacao nas listagens.

## Proximos passos

1. Inscricao por evento, com area logada e token (JWT) no lugar de reenviar a senha.
2. Registrar historico de envios para evitar repeticao.
3. Coletar telefone no cadastro e ativar o canal de SMS.
4. Somar as horas acumuladas por aluno e mostrar o progresso em relacao a exigencia do curso.
5. Paginar as listagens de eventos.

## Fontes, componentes de terceiros e uso de IA

Declarados em [docs/declaracao-ia-terceiros.md](docs/declaracao-ia-terceiros.md).
