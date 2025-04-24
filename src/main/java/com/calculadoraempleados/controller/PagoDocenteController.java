package com.calculadoraempleados.controller;

import com.calculadoraempleados.dto.PagoRequest;
import com.calculadoraempleados.dto.PagoResponse;
import com.calculadoraempleados.service.PagoDocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pago-docente")
public class PagoDocenteController {

    private  final PagoDocenteService pagoDocenteService;

    @PostMapping("/calcular")
    public PagoResponse calcularPago(@RequestBody PagoRequest request) {
        return pagoDocenteService.calcularPago(request);
    }
}