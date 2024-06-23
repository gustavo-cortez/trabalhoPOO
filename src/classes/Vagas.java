package classes;

import enums.EnumVagaStatus;
import enums.EnumTipoVeiculo;
import java.io.Serializable;

/**
 *
 * @author Gustavo
 */

// Classe Vaga para representar uma vaga de estacionamento
public class Vagas implements Serializable {
    private int numero;
    private String rua;
    private EnumVagaStatus status;
    private EnumTipoVeiculo tipoVeiculo; // Indica o tipo de veículo que pode estacionar nesta vaga

    // Construtor e métodos getters e setters

    public Vagas(int numero, String rua, EnumVagaStatus status, EnumTipoVeiculo tipoVeiculo) {
        this.numero = numero;
        this.rua = rua;
        this.status = status;
        this.tipoVeiculo = tipoVeiculo;
    }
    
    public int getNumero() {
        return numero;
    }

    public EnumTipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }
    
    public String getRua() {
        return rua;
    }

    public EnumVagaStatus getStatus() {
        return status;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setStatus(EnumVagaStatus status) {
        this.status = status;
    }

    public void setTipoVeiculo(EnumTipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
    
}
