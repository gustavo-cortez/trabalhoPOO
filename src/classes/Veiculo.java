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
    private String cor;
    private String modelo;
    private Cliente proprietario;
    private EnumTipoVeiculo tipo;
    private EnumUsoEstacionamento TipoUso;
    private static final long serialVersionUID = 1L;
    /*Construtor e métodos getters e setters do véiculo*/

    public Veiculo(String placa, Cliente proprietario, EnumTipoVeiculo tipo, String modelo, String cor, EnumUsoEstacionamento TipoUso) {
        this.placa = placa;
        this.proprietario = proprietario;
        this.tipo = tipo;
        this.modelo = modelo;
        this.cor = cor;
        this.TipoUso = TipoUso;
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

    public String getCor() {
        return cor;
    }

    public String getModelo() {
        return modelo;
    }

    public EnumUsoEstacionamento getTipoUso() {
        return TipoUso;
    }

    public void setTipoUso(EnumUsoEstacionamento TipoUso) {
        this.TipoUso = TipoUso;
    }

    public void setTipo(EnumTipoVeiculo tipo) {
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