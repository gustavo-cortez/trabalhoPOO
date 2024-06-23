package classes;

import enums.*;
import java.io.Serializable;

/**
 *
 * @author Gustavo
 */
/*Classe Veiculo para representar um veículo*/
public class Modelo implements Serializable  {
    private String modelo;
  
    private static final long serialVersionUID = 1L;
    /*Construtor e métodos getters e setters do véiculo*/

    public Modelo(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
}