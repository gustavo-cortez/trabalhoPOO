package classes;
import java.time.LocalDate;
/**
 * 
 * @author Gustavo
 */

/*Classe Tarifa para armazenar os valores das tarifas*/
public class Tarifa {
    private LocalDate inicio;
    private double valorPrimeiraHora;
    private double valorHoraSubsequente;
    private DiaSemana[] diasSemana;
             
    /*Construtor e métodos getters e setters da tarifa*/

    public Tarifa(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente, DiaSemana[] diasSemana) {
        this.inicio = inicio;
        this.valorPrimeiraHora = valorPrimeiraHora;
        this.valorHoraSubsequente = valorHoraSubsequente;
        this.diasSemana = diasSemana;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public double getValorPrimeiraHora() {
        return valorPrimeiraHora;
    }

    public double getValorHoraSubsequente() {
        return valorHoraSubsequente;
    }

    public DiaSemana[] getDiasSemana() {
        return diasSemana;
    }
    
    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public void setValorPrimeiraHora(double valorPrimeiraHora) {
        this.valorPrimeiraHora = valorPrimeiraHora;
    }

    public void setValorHoraSubsequente(double valorHoraSubsequente) {
        this.valorHoraSubsequente = valorHoraSubsequente;
    }

    public void setDiasSemana(DiaSemana[] diasSemana) {
        this.diasSemana = diasSemana;
    }
    
}
