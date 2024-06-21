package classes;
import java.time.LocalDate;
/**
 * 
 * @author Gustavo
 */

/*Classe Tarifa para armazenar os valores das tarifas*/
public abstract class Tarifa {
    protected LocalDate inicio;

    public Tarifa(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getInicio() {
        return inicio;
    }

}
