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
    private static final long serialVersionUID = 1L;
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
    
    public EnumDiaSemana[] getDiasSemana() {
        return diasSemana;
    }    
    
}