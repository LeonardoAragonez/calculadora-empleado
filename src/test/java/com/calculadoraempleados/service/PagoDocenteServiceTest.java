package com.calculadoraempleados.service;

import com.calculadoraempleados.dto.PagoRequest;
import com.calculadoraempleados.dto.PagoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagoDocenteServiceTest {

    private PagoDocenteService pagoDocenteService;

    @BeforeEach
    void setUp() {
        pagoDocenteService = new PagoDocenteService();
    }

    @Test
    void testCalcularPagoNullTipoContrato() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(null);
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteService.calcularPago(request);
        });
        assertEquals("El tipo de contrato no puede ser nulo", exception.getMessage());
    }

    @Test
    void testCalcularPagoHorasNegativas() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(-40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteService.calcularPago(request);
        });
        assertEquals("Las horas no pueden ser negativas", exception.getMessage());
    }

    @Test
    void testCalcularPagoTotalHorasExcedeLimite() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(150);
        request.setHorasDominicales(50);
        request.setHorasNocturnas(30);
        request.setHorasFestivo(20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteService.calcularPago(request);
        });
        assertEquals("El total de horas no puede superar las 240 al mes", exception.getMessage());
    }

    @Test
    void testCalcularPagoTipoContratoInvalido() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("INVALIDO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteService.calcularPago(request);
        });
        assertEquals("Tipo de contrato inválido. Use 'MEDIO_TIEMPO' o 'TIEMPO_COMPLETO'", exception.getMessage());
    }

    @Test
    void testCalcularPagoMedioTiempo() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("MEDIO_TIEMPO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 20000 + 10 * 20000 * 1.75 + 5 * 20000 * 1.35 + 5 * 20000 * 1.75, response.getTotalBruto());
        assertEquals(response.getTotalBruto() * 0.04, response.getDescuentoSalud());
        assertEquals(response.getTotalBruto() * 0.04, response.getDescuentoPension());
        assertEquals(response.getTotalBruto() - response.getDescuentoSalud() - response.getDescuentoPension(), response.getTotalNeto());
    }

    @Test
    void testCalcularPagoTiempoCompleto() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 30000 + 10 * 30000 * 1.75 + 5 * 30000 * 1.35 + 5 * 30000 * 1.75, response.getTotalBruto());
        assertEquals(response.getTotalBruto() * 0.04, response.getDescuentoSalud());
        assertEquals(response.getTotalBruto() * 0.04, response.getDescuentoPension());
        assertEquals(response.getTotalBruto() - response.getDescuentoSalud() - response.getDescuentoPension(), response.getTotalNeto());
    }

    @Test
    void testCalcularPagoHorasCero() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(0);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(0);
        request.setHorasFestivo(0);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(0, response.getTotalBruto());
        assertEquals(0, response.getDescuentoSalud());
        assertEquals(0, response.getDescuentoPension());
        assertEquals(0, response.getTotalNeto());
    }

    @Test
    void testCalcularPagoHorasMaximas() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(240);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(0);
        request.setHorasFestivo(0);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(240 * 30000, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoHorasMinimas() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(1);
        request.setHorasDominicales(1);
        request.setHorasNocturnas(1);
        request.setHorasFestivo(1);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(1 * 30000 + 1 * 30000 * 1.75 + 1 * 30000 * 1.35 + 1 * 30000 * 1.75, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoSinHorasDominicales() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 30000 + 5 * 30000 * 1.35 + 5 * 30000 * 1.75, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoSinHorasNocturnas() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(0);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 30000 + 10 * 30000 * 1.75 + 5 * 30000 * 1.75, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoSinHorasFestivo() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(0);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 30000 + 10 * 30000 * 1.75 + 5 * 30000 * 1.35, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoCeroHoras() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("MEDIO_TIEMPO");
        request.setHorasOrdinarias(0);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(0);
        request.setHorasFestivo(0);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(0, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoHorasExcedenLimite() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(250);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(0);
        request.setHorasFestivo(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteService.calcularPago(request);
        });
        assertEquals("El total de horas no puede superar las 240 al mes", exception.getMessage());
    }

    @Test
    void testCalcularPagoMedioTiempoNocturno() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("MEDIO_TIEMPO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(0);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 20000 + 5 * 20000 * 1.35 + 5 * 20000 * 1.75, response.getTotalBruto());
    }

    @Test
    void testCalcularPagoMedioTiempoFestivo() {
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("MEDIO_TIEMPO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse response = pagoDocenteService.calcularPago(request);

        assertNotNull(response);
        assertEquals(40 * 20000 + 10 * 20000 * 1.75 + 5 * 20000 * 1.35 + 5 * 20000 * 1.75, response.getTotalBruto());
    }
}
