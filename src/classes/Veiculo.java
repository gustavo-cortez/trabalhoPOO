package classes;

import enums.*;
import java.io.Serializable;

/**
 *
 * @author Gustavo
 */
/*Classe Veiculo para representar um veículo*/
public class Veiculo implements Serializable  {
    private String placa;
    private Cor cor;
    private Modelo modelo;
    private Cliente proprietario;
    private EnumTipoVeiculo tipo;
    private EnumUsoEstacionamento UsoEstacionamento;
    private static final long serialVersionUID = 1L;
    /*Construtor e métodos getters e setters do véiculo*/

    public Veiculo(String placa, Cliente proprietario, EnumTipoVeiculo tipo, Modelo modelo, Cor cor, EnumUsoEstacionamento TipoUso) {
        this.placa = placa;
        this.proprietario = proprietario;
        this.tipo = tipo;
        this.modelo = modelo;
        this.cor = cor;
        this.UsoEstacionamento = TipoUso;
    }

    public String getPlaca() {
        return placa;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public EnumTipoVeiculo getTipo() {
        return tipo;
    }

    public Cor getCor() {
        return cor;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public EnumUsoEstacionamento getTipoUso() {
        return UsoEstacionamento;
    }

    public void setUsoEstacionamento(EnumUsoEstacionamento TipoUso) {
        this.UsoEstacionamento = TipoUso;
    }
    
}