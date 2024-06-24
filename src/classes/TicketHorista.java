package classes;
import funcoes.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
/**
 *
 * @author Gustavo
 */
/* Subclasse específica para representar tickets horistas */
public class TicketHorista extends Ticket implements Serializable {
    /* Construtor */
    private TarifaHorista tarifa;
    private static final long serialVersionUID = 1L;
    public TicketHorista(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga, TarifaHorista tarifa) {
        super(inicio, fim, veiculo, valor, vaga);
        this.tarifa = tarifa;
    }
    
    // Implementação do método calcularValor() para tickets horista
    @Override
    public double calcularValor(FunTarifas tarifaIns) {
        LocalDateTime inicio = this.getInicio();
        LocalDateTime fim = this.getFim();
        long diferenca = (long) Math.floor((double) Duration.between(inicio, fim).toHours());
        double valorTotal = 0.0;
        double multiplicador = this.getVeiculo().getTipo().getMultiplicador();

        if (diferenca <= 1) {
            // Calcula o valor para o período de até uma hora
            valorTotal = tarifa.getValorPrimeiraHora();
        } else {
            // Calcula o valor para a primeira hora
            valorTotal = tarifa.getValorPrimeiraHora();

            // Calcula o valor para as horas subsequentes
            long horasSubsequentes = diferenca;

            if (inicio.toLocalDate().isBefore(fim.toLocalDate())) {
                // Se o período cruza dias diferentes
                LocalDateTime fimDiaInicio = inicio.toLocalDate().atTime(23, 59);
                long horasSubsequentesInicio = (long) Math.floor((double) Duration.between(inicio, fimDiaInicio).toHours());

                if (horasSubsequentesInicio > 1) {
                    // Ajusta valorTotal para o primeiro dia (se maior que 60 minutos)
                    valorTotal = tarifa.getValorPrimeiraHora();
                    valorTotal += horasSubsequentesInicio * tarifa.getValorHoraSubsequente();
                } else {
                    // Ajusta valorTotal para o primeiro dia (se menor ou igual a 60 minutos)
                    valorTotal = tarifa.getValorPrimeiraHora();
                }

                // Calcula as horas de atraso após a meia-noite do segundo dia
                LocalDateTime inicioDiaFim = fim.toLocalDate().atStartOfDay();
                long horasAtraso = (long) Math.floor((double) Duration.between(inicioDiaFim, fim).toHours());

                if (horasAtraso > 0) {
                    // Calcula as horas de atraso após a meia-noite
                    valorTotal += horasAtraso * (tarifa.getValorPrimeiraHora() * 2);
                }
            } else {
                // Se o período não cruza dias diferentes
                valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente();
            }
        }

        return valorTotal * multiplicador;
    }


}
