package classes;
import java.util.Date;
/**
 * 
 * @author Gustavo
 */

// Classe Tarifa para armazenar os valores das tarifas
public class Tarifa {
    private Date inicio;
    private double valorPrimeiraHora;
    private double valorHoraSubsequente;
    private Date fim;
    private DiaSemana[] diasSemana;
             
    // Construtor e métodos getters e setters

    public Tarifa(Date inicio, double valorPrimeiraHora, double valorHoraSubsequente, DiaSemana[] diasSemana, Date fim) {
        this.inicio = inicio;
        this.valorPrimeiraHora = valorPrimeiraHora;
        this.valorHoraSubsequente = valorHoraSubsequente;
        this.diasSemana = diasSemana;
        this.fim = fim;
    }

    public Date getInicio() {
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

    public Date getFim() {
        return fim;
    }
    
    public void setInicio(Date inicio) {
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

    public void setFim(Date fim) {
        this.fim = fim;
    }
    
}
