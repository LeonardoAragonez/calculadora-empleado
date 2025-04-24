package com.calculadoraempleados.service;

import com.calculadoraempleados.dto.PagoRequest;
import com.calculadoraempleados.dto.PagoResponse;
import org.springframework.stereotype.Service;

@Service
public class PagoDocenteService {

    public enum TipoContrato {
        MEDIO_TIEMPO,
        TIEMPO_COMPLETO
    }

    public PagoResponse calcularPago(PagoRequest request) {

        if (request.getTipoContrato() == null) {
            throw new IllegalArgumentException("El tipo de contrato no puede ser nulo");
        }

        if (request.getHorasOrdinarias() < 0 || request.getHorasDominicales() < 0 ||
                request.getHorasNocturnas() < 0 || request.getHorasFestivo() < 0) {
            throw new IllegalArgumentException("Las horas no pueden ser negativas");
        }

        int totalHoras = request.getHorasOrdinarias() + request.getHorasDominicales() +
                request.getHorasNocturnas() + request.getHorasFestivo();

        if (totalHoras > 240) {
            throw new IllegalArgumentException("El total de horas no puede superar las 240 al mes");
        }

        TipoContrato tipoContrato;
        try {
            tipoContrato = TipoContrato.valueOf(request.getTipoContrato().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de contrato inválido. Use 'MEDIO_TIEMPO' o 'TIEMPO_COMPLETO'");
        }

        double valorHora = tipoContrato == TipoContrato.MEDIO_TIEMPO ? 20000 : 30000;

        double pagoOrdinario = request.getHorasOrdinarias() * valorHora;
        double pagoDominical = request.getHorasDominicales() * valorHora * 1.75;
        double pagoNocturno = request.getHorasNocturnas() * valorHora * 1.35;
        double pagoFestivo = request.getHorasFestivo() * valorHora * 1.75;
        double totalBruto = pagoOrdinario + pagoDominical + pagoNocturno + pagoFestivo;
        double descuentoSalud = totalBruto * 0.04;
        double descuentoPension = totalBruto * 0.04;
        double totalNeto = totalBruto - descuentoSalud - descuentoPension;

        return new PagoResponse(totalBruto, descuentoSalud, descuentoPension, totalNeto);
    }
}