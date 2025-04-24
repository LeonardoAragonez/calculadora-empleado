package com.calculadoraempleados.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagoResponseTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        double totalBruto = 100000;
        double descuentoSalud = 4000;
        double descuentoPension = 4000;
        double totalNeto = 92000;

        // Act
        PagoResponse response = new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);

        // Assert
        assertEquals(totalBruto, response.getTotalBruto());
        assertEquals(descuentoSalud, response.getDescuentoSalud());
        assertEquals(descuentoPension, response.getDescuentoPension());
        assertEquals(totalNeto, response.getTotalNeto());
    }

    @Test
    void testPagoResponseZeroValues() {
        // Arrange
        double totalBruto = 0;
        double descuentoSalud = 0;
        double descuentoPension = 0;
        double totalNeto = 0;

        // Act
        PagoResponse response = new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);

        // Assert
        assertEquals(0, response.getTotalBruto());
        assertEquals(0, response.getDescuentoSalud());
        assertEquals(0, response.getDescuentoPension());
        assertEquals(0, response.getTotalNeto());
    }

    @Test
    void testPagoResponseNegativeValues() {
        // Arrange
        double totalBruto = -50000;
        double descuentoSalud = -2000;
        double descuentoPension = -2000;
        double totalNeto = -46000;

        // Act
        PagoResponse response = new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);

        // Assert
        assertEquals(-50000, response.getTotalBruto());
        assertEquals(-2000, response.getDescuentoSalud());
        assertEquals(-2000, response.getDescuentoPension());
        assertEquals(-46000, response.getTotalNeto());
    }

    @Test
    void testPagoResponsePositiveValues() {
        // Arrange
        double totalBruto = 120000;
        double descuentoSalud = 4800;
        double descuentoPension = 4800;
        double totalNeto = 112400;

        // Act
        PagoResponse response = new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);

        // Assert
        assertEquals(120000, response.getTotalBruto());
        assertEquals(4800, response.getDescuentoSalud());
        assertEquals(4800, response.getDescuentoPension());
        assertEquals(112400, response.getTotalNeto());
    }

    @Test
    void testPagoResponseAllLargeValues() {
        // Arrange
        double totalBruto = 9999999;
        double descuentoSalud = 399999;
        double descuentoPension = 399999;
        double totalNeto = 9199999;

        // Act
        PagoResponse response = new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);

        // Assert
        assertEquals(9999999, response.getTotalBruto());
        assertEquals(399999, response.getDescuentoSalud());
        assertEquals(399999, response.getDescuentoPension());
        assertEquals(9199999, response.getTotalNeto());
    }
}
