package classes;

/**
 *
 * @author Gustavo
 */

// Classe Vaga para representar uma vaga de estacionamento
public class Vagas{
    private int numero;
    private String rua;
    private VagaStatus status;
    private TipoVeiculo tipoVeiculo; // Indica o tipo de veículo que pode estacionar nesta vaga

    // Construtor e métodos getters e setters

    public Vagas(int numero, String rua, VagaStatus status, TipoVeiculo tipoVeiculo) {
        this.numero = numero;
        this.rua = rua;
        this.status = status;
        this.tipoVeiculo = tipoVeiculo;
    }
    
    public int getNumero() {
        return numero;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }
    
    public String getRua() {
        return rua;
    }

    public VagaStatus getStatus() {
        return status;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setStatus(VagaStatus status) {
        this.status = status;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
    
}
