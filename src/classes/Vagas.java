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
    private static final long serialVersionUID = 1L;
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

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void ocupar() {
        this.status = EnumVagaStatus.OCUPADA;
    }
    
    public void desocupar() {
        this.status = EnumVagaStatus.DISPONIVEL;
    }
    
    public void alugar() {
        this.status = EnumVagaStatus.ALUGADA_MENSAL;
    }
    
    public void indisponibilizar() {
        this.status = EnumVagaStatus.INDISPONIVEL;
    }
    
    public void setTipoVeiculo(EnumTipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
    
}
