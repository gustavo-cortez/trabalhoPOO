package classes;

/**
 *
 * @author Gustavo
 */
// Classe Veiculo para representar um veículo
public class Veiculo {
    private String placa;
    private Cliente proprietario;
    private TipoVeiculo tipo;
    // Construtor e métodos getters e setters

    public Veiculo(String placa, Cliente proprietario, TipoVeiculo tipo) {
        this.placa = placa;
        this.proprietario = proprietario;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }
    
}