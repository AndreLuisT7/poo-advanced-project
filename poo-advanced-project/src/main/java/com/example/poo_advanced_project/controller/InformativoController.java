package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.InformativoResultado;
import com.example.poo_advanced_project.service.InformativoSemanalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/informativo")
@RequiredArgsConstructor
public class InformativoController {

    private final InformativoSemanalService informativoSemanalService;

    // disparo manual, usado na demonstracao para nao depender do agendamento semanal
    @PostMapping("/enviar")
    public InformativoResultado enviarAgora() {
        return informativoSemanalService.enviar();
    }
}
