package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.CadastroEventoRequest;
import com.example.poo_advanced_project.dto.EventoResponse;
import com.example.poo_advanced_project.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoResponse> cadastrar(
            @RequestBody @Valid CadastroEventoRequest dados) {

        EventoResponse evento = eventoService.cadastrar(dados);

        return ResponseEntity
                .created(URI.create("/api/eventos/" + evento.id()))
                .body(evento);
    }
}