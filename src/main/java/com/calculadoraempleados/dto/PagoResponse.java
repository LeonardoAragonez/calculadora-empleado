package com.calculadoraempleados.dto;

import lombok.Getter;

@Getter
public class PagoResponse {
    private double totalBruto;
    private double descuentoSalud;
    private double descuentoPension;
    private double totalNeto;

    public PagoResponse(double totalBruto, double descuentoSalud, double descuentoPension, double totalNeto) {
        this.totalBruto = totalBruto;
        this.descuentoSalud = descuentoSalud;
        this.descuentoPension = descuentoPension;
        this.totalNeto = totalNeto;
    }

}
