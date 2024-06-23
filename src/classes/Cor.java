package classes;

import enums.*;
import java.io.Serializable;

/**
 *
 * @author Gustavo
 */
/*Classe Veiculo para representar um veículo*/
public class Cor implements Serializable  {
    private String nomeCor;
  
    private static final long serialVersionUID = 1L;
    /*Construtor e métodos getters e setters do véiculo*/

    public Cor(String nome) {
        this.nomeCor = nome;
    }

    public String getNome() {
        return nomeCor;
    }

    public void setNome(String nome) {
        this.nomeCor = nome;
    }
    
}