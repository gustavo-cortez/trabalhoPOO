package classes;

/**
 *
 * @author Gustavo
 */
/*Classe Veiculo para representar um veículo*/
public class Veiculo {
    private String placa;
    private String cor;
    private String modelo;
    private Cliente proprietario;
    private TipoVeiculo tipo;
    /*Construtor e métodos getters e setters do véiculo*/

    public Veiculo(String placa, Cliente proprietario, TipoVeiculo tipo, String modelo, String cor) {
        this.placa = placa;
        this.proprietario = proprietario;
        this.tipo = tipo;
        this.modelo = modelo;
        this.cor = cor;
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

    public String getCor() {
        return cor;
    }

    public String getModelo() {
        return modelo;
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

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    } 
    
}