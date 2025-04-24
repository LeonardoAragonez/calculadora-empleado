package com.calculadoraempleados.controller;

import com.calculadoraempleados.dto.PagoRequest;
import com.calculadoraempleados.dto.PagoResponse;
import com.calculadoraempleados.service.PagoDocenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagoDocenteControllerTest {

    @Mock
    private PagoDocenteService pagoDocenteService;

    @InjectMocks
    private PagoDocenteController pagoDocenteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalcularPagoSuccess() {
        // Arrange
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        PagoResponse expectedResponse = new PagoResponse(100000, 4000, 4000, 92000);
        when(pagoDocenteService.calcularPago(request)).thenReturn(expectedResponse);

        // Act
        PagoResponse response = pagoDocenteController.calcularPago(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getTotalBruto(), response.getTotalBruto());
        assertEquals(expectedResponse.getDescuentoSalud(), response.getDescuentoSalud());
        assertEquals(expectedResponse.getDescuentoPension(), response.getDescuentoPension());
        assertEquals(expectedResponse.getTotalNeto(), response.getTotalNeto());
        verify(pagoDocenteService, times(1)).calcularPago(request);  // Verifica que el servicio fue llamado una vez
    }

    @Test
    void testCalcularPagoResponseError() {
        // Arrange
        PagoRequest request = new PagoRequest();
        request.setTipoContrato("TIEMPO_COMPLETO");
        request.setHorasOrdinarias(40);
        request.setHorasDominicales(10);
        request.setHorasNocturnas(5);
        request.setHorasFestivo(5);

        when(pagoDocenteService.calcularPago(request)).thenThrow(new IllegalArgumentException("Error en el cálculo"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pagoDocenteController.calcularPago(request);
        });
        assertEquals("Error en el cálculo", exception.getMessage());
        verify(pagoDocenteService, times(1)).calcularPago(request);  // Verifica que el servicio fue llamado una vez
    }
}
