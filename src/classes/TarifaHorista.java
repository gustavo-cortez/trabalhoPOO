package classes;
import enums.EnumTipoVeiculo;
import enums.EnumDiaSemana;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
/**
 *
 * @author Gustavo
 */
/* Subclasse de Tarifa para tarifas horistas */
public class TarifaHorista extends Tarifa implements Serializable  {
    protected double valorPrimeiraHora;
    protected double valorHoraSubsequente;
    protected EnumDiaSemana[] diasSemana;
    public TarifaHorista(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente, EnumDiaSemana[] diasSemana) {
        super(inicio);
        this.valorPrimeiraHora = valorPrimeiraHora;
        this.valorHoraSubsequente = valorHoraSubsequente;
        this.diasSemana = diasSemana;
    }

    public double getValorPrimeiraHora() {
        return valorPrimeiraHora;
    }

    public double getValorHoraSubsequente() {
        return valorHoraSubsequente;
    }
    
    public double getValorPrimeiraHora(EnumTipoVeiculo tipo) {
        if(tipo.equals(EnumTipoVeiculo.ÔNIBUS)){
           return valorPrimeiraHora * 1.5; 
        }
        else{
            if(tipo.equals(EnumTipoVeiculo.MOTO)){
                return valorPrimeiraHora / 2; 
            }
        }
        return valorPrimeiraHora;
    }

    public double getValorHoraSubsequente(EnumTipoVeiculo tipo) {
        if(tipo.equals(EnumTipoVeiculo.ÔNIBUS)){
           return valorHoraSubsequente * 1.5; 
        }
        else{
            if(tipo.equals(EnumTipoVeiculo.MOTO)){
                return valorHoraSubsequente / 2; 
            }
        }
        return valorHoraSubsequente;
    }

    
    public EnumDiaSemana[] getDiasSemana() {
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

    public void setDiasSemana(EnumDiaSemana[] diasSemana) {
        this.diasSemana = diasSemana;
    }
    
}