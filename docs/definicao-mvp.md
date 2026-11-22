# Anexo C — Definicao do MVP

**Solucao:** Portal de Eventos Academicos

**Usuario principal:** estudante de graduacao que precisa cumprir horas complementares.

**Resultado principal:** receber, sem precisar procurar, a lista dos eventos da proxima semana
da sua instituicao que valem horas complementares.

**Fluxo de ponta a ponta:**

```
o aluno preenche o cadastro (nome, e-mail, matricula, curso, instituicao)
        ↓
o sistema valida os dados, recusa e-mail repetido e grava a senha em BCrypt
        ↓
a assinatura do informativo e ativada
        ↓
toda segunda-feira as 8h o agendamento busca quem tem assinatura ativa
        ↓
para cada aluno, busca os eventos dos proximos 7 dias da instituicao dele
        ↓
monta a mensagem e envia por e-mail
        ↓
o aluno recebe a lista com data, local, categoria e carga horaria
```

**Criterios de pronto:**

- o cadastro recusa e-mail repetido com 409 e campo invalido com 400;
- a senha nunca e devolvida pela API e esta gravada em BCrypt;
- o disparo do informativo retorna quantos alunos estavam inscritos, quantos e-mails sairam
  e quais falharam;
- o e-mail chega com os eventos corretos da instituicao do aluno e apenas dos proximos 7 dias;
- um aluno que desliga a assinatura deixa de ser contado no disparo seguinte.

**Fora do escopo:**

- login do aluno e area logada;
- confirmacao de presenca e contagem automatica de horas acumuladas;
- emissao de certificado;
- aplicativo movel;
- integracao com sistema academico da instituicao;
- envio por SMS (o servico existe, mas nao foi ligado a nenhum fluxo).

**Maior risco:** depender de um servico externo de e-mail. Se o SMTP estiver indisponivel ou a
senha de aplicativo for revogada no dia da mostra, o fluxo principal nao pode ser demonstrado.

**Teste principal:** cadastrar um aluno com e-mail real, disparar o informativo pelo endpoint
manual e mostrar a mensagem chegando na caixa de entrada; em seguida desligar a assinatura e
disparar de novo para mostrar que ele deixa de receber.

**Limitacoes conhecidas:** listadas na secao "Limitacoes conhecidas" do README.
