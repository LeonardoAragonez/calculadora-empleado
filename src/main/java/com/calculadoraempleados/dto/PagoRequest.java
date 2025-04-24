package com.calculadoraempleados.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoRequest {
    private String tipoContrato;
    private int horasOrdinarias;
    private int horasDominicales;
    private int horasNocturnas;
    private int horasFestivo;

}
