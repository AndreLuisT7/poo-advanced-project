# Roteiro automatico da demonstracao (Windows PowerShell 5.1 ou PowerShell 7).
# Antes de rodar, suba a aplicacao com o perfil demo (ver demo/README.md).
#
# Uso:  powershell -ExecutionPolicy Bypass -File demo\demo.ps1
#       powershell -ExecutionPolicy Bypass -File demo\demo.ps1 -Email seu.email@exemplo.com

param(
    [string]$BaseUrl = "http://localhost:8080",
    [string]$Email = "",
    [string]$Admin = "admin",
    [string]$AdminSenha = "admin123"
)

$ErrorActionPreference = "Stop"

if (-not $Email) {
    $Email = "aluno.demo.$(Get-Date -Format 'HHmmss')@exemplo.com"
}
$senhaAluno = "senha12345"
$basic = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${Admin}:${AdminSenha}"))
$adminHeaders = @{ Authorization = "Basic $basic" }

function Write-Passo($numero, $texto) {
    Write-Host ""
    Write-Host "== $numero. $texto ==" -ForegroundColor Cyan
}

Write-Passo 1 "Eventos dos proximos dias (publico)"
# No PowerShell 5.1 a lista JSON chega como um unico objeto; o ForEach-Object a desdobra
$eventos = @(Invoke-RestMethod "$BaseUrl/api/eventos?proximos=true" | ForEach-Object { $_ })
$eventos | Format-Table id, nome, dataHora, cargaHoraria, instituicaoId -AutoSize
Write-Host "$($eventos.Count) evento(s) futuros."

Write-Passo 2 "Instituicoes disponiveis no cadastro (publico)"
Invoke-RestMethod "$BaseUrl/api/instituicoes" | Format-Table -AutoSize

Write-Passo 3 "Aluno se cadastra na UCSAL (instituicao 1)"
$corpo = @{
    nomeCompleto  = "Aluno Demonstracao"
    email         = $Email
    senha         = $senhaAluno
    matricula     = "D$(Get-Date -Format 'HHmmss')"
    curso         = "Analise e Desenvolvimento de Sistemas"
    instituicaoId = 1
} | ConvertTo-Json
$aluno = Invoke-RestMethod "$BaseUrl/api/alunos" -Method Post -ContentType "application/json" -Body $corpo
$aluno | Format-List id, nomeCompleto, email, instituicao, assinaturaAtiva

Write-Passo 4 "Gestor dispara o informativo semanal (exige login ADMIN)"
$envio1 = Invoke-RestMethod "$BaseUrl/api/informativo/enviar" -Method Post -Headers $adminHeaders
$envio1 | Format-List alunosInscritos, emailsEnviados, alunosSemEventos, falhas

Write-Passo 5 "Aluno cancela a propria assinatura com e-mail e senha"
$cancelar = @{ email = $Email; senha = $senhaAluno } | ConvertTo-Json
$cancelado = Invoke-RestMethod "$BaseUrl/api/assinaturas/cancelar" -Method Post -ContentType "application/json" -Body $cancelar
$cancelado | Format-List email, assinaturaAtiva

Write-Passo 6 "Novo disparo: quem cancelou nao e mais contado"
$envio2 = Invoke-RestMethod "$BaseUrl/api/informativo/enviar" -Method Post -Headers $adminHeaders
$envio2 | Format-List alunosInscritos, emailsEnviados, alunosSemEventos, falhas

Write-Host ""
if ($envio2.alunosInscritos -eq $envio1.alunosInscritos - 1) {
    Write-Host "OK: inscritos caiu de $($envio1.alunosInscritos) para $($envio2.alunosInscritos)." -ForegroundColor Green
} else {
    Write-Host "ATENCAO: esperava $($envio1.alunosInscritos - 1) inscritos, veio $($envio2.alunosInscritos)." -ForegroundColor Yellow
    exit 1
}
