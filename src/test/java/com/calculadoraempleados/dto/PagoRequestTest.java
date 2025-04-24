package com.calculadoraempleados.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagoRequestTest {

    @Test
    void testSetterAndGetter() {
        // Arrange
        String tipoContrato = "TIEMPO_COMPLETO";
        int horasOrdinarias = 40;
        int horasDominicales = 10;
        int horasNocturnas = 5;
        int horasFestivo = 5;

        // Act
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(tipoContrato);
        request.setHorasOrdinarias(horasOrdinarias);
        request.setHorasDominicales(horasDominicales);
        request.setHorasNocturnas(horasNocturnas);
        request.setHorasFestivo(horasFestivo);

        // Assert
        assertEquals(tipoContrato, request.getTipoContrato());
        assertEquals(horasOrdinarias, request.getHorasOrdinarias());
        assertEquals(horasDominicales, request.getHorasDominicales());
        assertEquals(horasNocturnas, request.getHorasNocturnas());
        assertEquals(horasFestivo, request.getHorasFestivo());
    }

    @Test
    void testDefaultValues() {
        // Act
        PagoRequest request = new PagoRequest();

        // Assert
        assertNull(request.getTipoContrato());
        assertEquals(0, request.getHorasOrdinarias());
        assertEquals(0, request.getHorasDominicales());
        assertEquals(0, request.getHorasNocturnas());
        assertEquals(0, request.getHorasFestivo());
    }

    @Test
    void testSettersWithInvalidValues() {
        // Arrange
        String tipoContrato = "MEDIO_TIEMPO";
        int horasOrdinarias = -10;
        int horasDominicales = -5;
        int horasNocturnas = -3;
        int horasFestivo = -2;

        // Act
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(tipoContrato);
        request.setHorasOrdinarias(horasOrdinarias);
        request.setHorasDominicales(horasDominicales);
        request.setHorasNocturnas(horasNocturnas);
        request.setHorasFestivo(horasFestivo);

        // Assert
        assertEquals(tipoContrato, request.getTipoContrato());
        assertEquals(-10, request.getHorasOrdinarias());
        assertEquals(-5, request.getHorasDominicales());
        assertEquals(-3, request.getHorasNocturnas());
        assertEquals(-2, request.getHorasFestivo());
    }

    @Test
    void testSetterWithNullValues() {
        // Arrange
        String tipoContrato = null;
        int horasOrdinarias = 40;
        int horasDominicales = 10;
        int horasNocturnas = 5;
        int horasFestivo = 5;

        // Act
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(tipoContrato);
        request.setHorasOrdinarias(horasOrdinarias);
        request.setHorasDominicales(horasDominicales);
        request.setHorasNocturnas(horasNocturnas);
        request.setHorasFestivo(horasFestivo);

        // Assert
        assertNull(request.getTipoContrato());
        assertEquals(horasOrdinarias, request.getHorasOrdinarias());
        assertEquals(horasDominicales, request.getHorasDominicales());
        assertEquals(horasNocturnas, request.getHorasNocturnas());
        assertEquals(horasFestivo, request.getHorasFestivo());
    }

    @Test
    void testPagoRequestWithMaxValues() {
        // Arrange
        String tipoContrato = "TIEMPO_COMPLETO";
        int horasOrdinarias = 240;
        int horasDominicales = 240;
        int horasNocturnas = 240;
        int horasFestivo = 240;

        // Act
        PagoRequest request = new PagoRequest();
        request.setTipoContrato(tipoContrato);
        request.setHorasOrdinarias(horasOrdinarias);
        request.setHorasDominicales(horasDominicales);
        request.setHorasNocturnas(horasNocturnas);
        request.setHorasFestivo(horasFestivo);

        // Assert
        assertEquals(tipoContrato, request.getTipoContrato());
        assertEquals(horasOrdinarias, request.getHorasOrdinarias());
        assertEquals(horasDominicales, request.getHorasDominicales());
        assertEquals(horasNocturnas, request.getHorasNocturnas());
        assertEquals(horasFestivo, request.getHorasFestivo());
    }
}
