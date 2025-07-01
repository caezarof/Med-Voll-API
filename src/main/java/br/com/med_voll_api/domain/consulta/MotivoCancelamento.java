package br.com.med_voll_api.domain.consulta;

import jakarta.validation.ValidationException;

public enum MotivoCancelamento {
    PACIENTE_DESISTIU("Paciente desistiu"),
    MEDICO_CANCELOU("Médico cancelou"),
    OUTROS("Outros");

    private String motivo;

    MotivoCancelamento(String motivo){
        this.motivo = motivo;
    }

    public static MotivoCancelamento fromString(String text){
        for (MotivoCancelamento motivoCancelamento :MotivoCancelamento.values()){
            if (motivoCancelamento.motivo.equalsIgnoreCase(text)){
                return motivoCancelamento;
            }
        }
        throw new ValidationException("Motivo de cancelamento inválido.");
    }

    public static String fromMotivoCancelamento(MotivoCancelamento motivo){
        for (MotivoCancelamento motivoCancelamento :MotivoCancelamento.values()){
            if (motivoCancelamento.equals(motivo)){
                return motivoCancelamento.motivo;
            }
        }
        throw new ValidationException("Motivo de cancelamento inválido");
    }
}
