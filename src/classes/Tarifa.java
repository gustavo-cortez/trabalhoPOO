package classes;
import java.time.LocalDate;
import java.io.Serializable;
/**
 * 
 * @author Gustavo
 */

/*Classe Tarifa para armazenar os valores das tarifas*/
public abstract class Tarifa implements Serializable {
    protected LocalDate inicio;
    
    public Tarifa(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getInicio() {
        return inicio;
    }

}
