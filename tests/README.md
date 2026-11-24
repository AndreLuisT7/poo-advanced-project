# Testes

O projeto tem dois tipos de teste: os **automatizados**, que rodam com o Maven, e um
**roteiro manual** de chamadas HTTP com o resultado esperado de cada uma.

## Testes automatizados

Ficam no lugar padrao do Maven, para que o `./mvnw test` os encontre:
[`poo-advanced-project/src/test/java`](../poo-advanced-project/src/test/java/com/example/poo_advanced_project).

| Classe | O que verifica |
|---|---|
| `PooAdvancedProjectApplicationTests` | A aplicacao sobe com todas as configuracoes. |
| `CadastroAlunoTest` | Cadastro com assinatura ativa e senha em BCrypt; recusa de e-mail repetido, de matricula repetida na mesma instituicao e de instituicao inexistente. |
| `InformativoSemanalTest` | O informativo vai para quem tem eventos na semana e inclui a carga horaria; quem cancelou a assinatura nao recebe; o cancelamento pelo proprio aluno exige a senha correta. |
| `AcessoAlunoTest` | O aluno entra com e-mail e senha corretos; senha errada e e-mail desconhecido sao recusados; o aluno cancela e reativa o informativo; reativar exige a senha correta. |

Os testes usam banco H2 em memoria e um servidor de e-mail falso
(`src/test/resources/application.properties`). Nao precisam de Postgres, internet nem credencial.

### Como rodar

```
cd poo-advanced-project
./mvnw test
```

No Windows, use `mvnw.cmd test`. Resultado esperado: `Tests run: 12, Failures: 0, Errors: 0`.

## Roteiro de teste manual

Suba a aplicacao com o perfil `demo` (ver [demo/README.md](../demo/README.md)) e faca as
chamadas abaixo. Os usuarios administrativos sao `admin` / `admin123` (papel `ADMIN`) e
`user` / `user123` (papel `USER`).

| # | Caso | Chamada | Esperado |
|---|---|---|---|
| 1 | Portal abre | `GET /` | 200 |
| 2 | Lista eventos futuros | `GET /api/eventos?proximos=true` | 200, 4 eventos de demonstracao |
| 3 | Evento inexistente | `GET /api/eventos/999` | 404 |
| 4 | Cadastro valido | `POST /api/alunos` com todos os campos | 201, `assinaturaAtiva: true`, sem a senha na resposta |
| 5 | E-mail repetido | repetir o caso 4 com o mesmo e-mail | 409 |
| 6 | Matricula repetida na mesma instituicao | caso 4 com outro e-mail e a mesma matricula | 409 |
| 7 | Senha curta | caso 4 com `"senha":"123"` | 400, com a mensagem do campo |
| 8 | Instituicao inexistente | caso 4 com `"instituicaoId":999` | 404 |
| 9 | Cadastrar evento sem login | `POST /api/eventos` | 401 |
| 10 | Cadastrar evento com usuario sem papel ADMIN | `POST /api/eventos` com `-u user:user123` | 403 |
| 11 | Cadastrar evento como gestor | `POST /api/eventos` com `-u admin:admin123` e todos os campos | 201 |
| 12 | Dados de aluno sem login | `GET /api/alunos/1` | 401 |
| 13 | Disparar informativo sem login | `POST /api/informativo/enviar` | 401 |
| 14 | Disparar informativo como gestor | `POST /api/informativo/enviar` com `-u admin:admin123` | 200, com `alunosInscritos` e `emailsEnviados` |
| 15 | Cancelar assinatura com senha errada | `POST /api/assinaturas/cancelar` | 401 |
| 16 | Cancelar assinatura com senha certa | `POST /api/assinaturas/cancelar` | 200, `assinaturaAtiva: false` |

Exemplo de corpo para o caso 4:

```
curl -X POST http://localhost:8080/api/alunos -H "Content-Type: application/json" -d "{\"nomeCompleto\":\"Teste\",\"email\":\"teste@exemplo.com\",\"senha\":\"senha12345\",\"matricula\":\"M1\",\"curso\":\"ADS\",\"instituicaoId\":1}"
```

Exemplo de corpo para o caso 11:

```
curl -u admin:admin123 -X POST http://localhost:8080/api/eventos -H "Content-Type: application/json" -d "{\"nome\":\"Palestra\",\"descricao\":\"Teste\",\"local\":\"Auditorio\",\"dataHora\":\"2030-01-01T10:00:00\",\"categoria\":\"Palestra\",\"cargaHoraria\":2,\"instituicaoId\":1}"
```

Todos os resultados desta tabela foram conferidos em 23/11/2026 com a aplicacao no perfil `demo`.
