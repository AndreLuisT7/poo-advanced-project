# Anexos A e B — Ficha do problema e registro de validacao

**Solucao:** Portal de Eventos Academicos

**Equipe, turma e integrantes:** ver a secao "Integrantes e turma" do [README](../README.md).

**Evidencias originais (sem dados pessoais):**

- [Relatorio do questionario](validacao/relatorio-questionario.pdf): analise das respostas,
  com graficos e respostas abertas transcritas.
- [Respostas brutas](validacao/respostas-questionario.xlsx): planilha exportada do formulario,
  com 5 respostas. Nao contem nome, e-mail nem outro identificador, so data e hora do envio.

> Todos os numeros abaixo vem dessas duas fontes. Nenhum dado foi estimado ou completado.

---

## Anexo A — Ficha do problema

| Campo | Preenchimento |
|---|---|
| **Publico** | Estudantes de graduacao presencial. |
| **Situacao** | Ao longo do curso, quando palestras, cursos e eventos sao divulgados em canais diferentes (WhatsApp, e-mail institucional, redes sociais, murais, professores). |
| **Problema** | O estudante nao sabe onde procurar e nao consegue reencontrar a informacao; fica sabendo do evento tarde, com prazo encerrado ou depois que ele aconteceu. |
| **Impacto** | Deixa de participar de oportunidades que queria. Para quem precisa de horas complementares, a falta aparece no fim do curso. |
| **Alternativa atual** | Acompanhar varios canais ao mesmo tempo. Cada um dos 5 respondentes indicou um canal principal diferente. |
| **Hipotese critica** | O estudante se cadastra uma vez e prefere receber a informacao filtrada por e-mail a procurar por conta propria. |
| **Evidencia** | Questionario com 5 estudantes, de 04/11 a 16/11/2026 (detalhes no Anexo B). |
| **Recorte** | Cadastro do estudante com sua instituicao e envio semanal, por e-mail, dos eventos dos proximos dias, com data, local e carga horaria. |

---

## Anexo B — Registro de validacao

### Metodo

Questionario online com 33 perguntas (perfil, meios de informacao, dificuldades, oportunidades
perdidas, grau de concordancia de 1 a 5 e perguntas abertas), respondido de 04/11 a 16/11/2026.

### Perfil dos participantes

Cinco estudantes de graduacao presencial, um de cada curso:

| Curso | Periodo | Turno | Atividades alem das disciplinas (12 meses) |
|---|---|---|---|
| Direito | 4o | Noturno | 4 a 5 |
| Arquitetura e Urbanismo | 6o | Matutino | 2 a 3 |
| Enfermagem | 8o | Mais de um turno | 1 |
| Psicologia | 6o | Matutino | 6 ou mais |
| Pedagogia | 4o | Noturno | Nenhuma |

### Hipoteses, evidencias e decisoes

| Hipotese | Evidencia observada | Resultado | Decisao |
|---|---|---|---|
| Os estudantes querem acompanhar eventos academicos. | 5 de 5 marcaram "eventos, palestras e seminarios" como informacao importante a acompanhar. | Confirmada | Manter eventos como o centro do produto. |
| A informacao esta espalhada em muitos canais. | Cada estudante indicou um canal principal diferente (WhatsApp, e-mail institucional, colegas, redes de cursos/ligas, redes institucionais). | Confirmada | Reunir os eventos num lugar so, filtrados pela instituicao do estudante. |
| O estudante tem dificuldade de encontrar e reencontrar a informacao. | 3 de 5 sabem onde procurar "nunca" ou "raramente"; 3 de 5 reencontram uma informacao so "raramente"; ninguem respondeu "na maioria das vezes" para reencontrar. Menor media de concordancia: "consigo acompanhar oportunidades compativeis com meus interesses" (2,6). | Confirmada | Enviar a informacao ao estudante em vez de depender de ele procurar. |
| O estudante perde oportunidades por receber a informacao tarde. | Os 2 que responderam essa parte (Arquitetura e Urbanismo, Enfermagem) ja deixaram de participar por falta de informacao ou por recebe-la tarde. Os outros 3 deixaram em branco. | Indicio, nao confirmada | Manter, registrando que so 2 de 5 responderam. |
| O e-mail e um canal que o estudante acompanha. | 4 de 5 citam o e-mail institucional entre seus meios de informacao; e o meio mais citado. Mas so 1 de 5 o tem como principal. A preferencia por receber por e-mail nao foi perguntada diretamente. | Parcial | Manter o e-mail como canal do MVP e citar a limitacao. Outros canais ficam como proximo passo. |
| A carga horaria e o dado que mais pesa na decisao. | So 2 de 5 marcaram "carga horaria". "Local ou modalidade" foi marcado por 5 de 5; "area/tema" e "aproveitamento academico" por 4 de 5. | Contrariada | O evento mostra local, data e carga horaria. O problema passou a ser apresentado como acesso a informacao sobre oportunidades, nao so horas complementares. |

### O que o MVP nao resolve

- As barreiras mais citadas foram conflito com horario de aula, distancia/deslocamento e falta de
  interesse nas oportunidades conhecidas (3 de 5 cada). O portal nao atua sobre elas.
- Um estudante (Direito) disse nao ter dificuldade para receber e procurar informacoes.
- Um estudante (Arquitetura e Urbanismo) citou o pagamento de inscricao em cursos e oficinas.
  O portal nao trata custo.

### Sintese dos aprendizados

O interesse em acompanhar eventos e unanime na amostra, e o ponto fraco esta em saber onde
procurar e em reencontrar a informacao, nao em entender se ela e relevante (maior media: 4,2 em
"consigo distinguir as oportunidades relevantes"). A perda de oportunidades por informacao tardia
aparece, mas so 2 estudantes responderam essa parte. O e-mail e amplamente usado, mas nao e o canal
principal da maioria.

### Limites da validacao

A amostra tem 5 estudantes, um por curso. Os resultados servem para descrever essas respostas e
orientar as proximas etapas; nao generalizam para toda a universidade.

### Mudancas realizadas no problema ou na solucao

- O recorte do MVP ficou no caminho do cadastro ate o recebimento do informativo, com local e data
  em cada evento.
- O problema passou a ser descrito de forma mais ampla, como oportunidades que se perdem por falta
  de informacao, e nao apenas como falta de horas complementares.
