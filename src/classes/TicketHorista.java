package classes;
import funcoes.*;
import funcoesVisual.FunTarifasVisual;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
/**
 *
 * @author Gustavo
 */
/* Subclasse específica para representar tickets horistas */
public class TicketHorista extends Ticket {
    /* Construtor */
    private TarifaHorista tarifa;
    public TicketHorista(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga, TarifaHorista tarifa) {
        super(inicio, fim, veiculo, valor, vaga);
        this.tarifa = tarifa;
    }
    
    // Implementação do método calcularValor() para tickets horistas
    @Override
    public double calcularValor(FunTarifas tarifaIns) {
        double valorTotal;
        LocalDateTime inicio = this.getInicio();
        LocalDateTime fim = this.getFim();

        // Calcular a diferença em minutos entre início e fim
        Duration diff = Duration.between(inicio, fim);
        long diffInMinutes = diff.toMinutes();

        // Encontrar a tarifa correspondente ao dia da semana do início do ticket
        TarifaHorista tarifa = tarifaIns.encontrarTarifaHorista(inicio.getDayOfWeek());

        if (tarifa == null) {
            System.out.println("Tarifa não encontrada para o dia da semana");
            return -1.0; // Se não haver tarifas para o dia da semana ele retorna menos um e uma mensagem de aviso
        }

        // Calcula o valor da primeira hora se a diferença for menor que 60 minutos
        if (diffInMinutes <= 60) {
            valorTotal = tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo());
        } else {
            // Primeira hora completa
            valorTotal = tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo()) + tarifa.getValorHoraSubsequente(this.getVeiculo().getTipo());

            // Horas subsequentes
            long horasSubsequentes = (long) Math.ceil((double) (diffInMinutes - 60) / 60);
            valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente(this.getVeiculo().getTipo());

            // Lógica adicional para verificar se o ticket cobre mais de um dia completo ou se começa e termina em dias diferentes
            if (inicio.toLocalDate().isBefore(fim.toLocalDate())) {
                Duration diffAtraso = Duration.between(fim, LocalDateTime.now());
                long diffAtrasoMin = diffAtraso.toMinutes();
                valorTotal += (tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo()) * 2) * Math.ceil((double) diffAtrasoMin / 60);
            }
        }

        return valorTotal;
    }
    public double calcularValor(FunTarifasVisual tarifaIns) {
        double valorTotal;
        LocalDateTime inicio = this.getInicio();
        LocalDateTime fim = this.getFim();

        // Calcular a diferença em minutos entre início e fim
        Duration diff = Duration.between(inicio, fim);
        long diffInMinutes = diff.toMinutes();

        TarifaHorista tarifa = tarifaIns.encontrarTarifaHorista(inicio.getDayOfWeek());

        if (tarifa == null) {
            JOptionPane.showMessageDialog(null, "Tarifa não encontrada para o dia da semana", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1.0; // Se não haver tarifas para o dia da semana ele retorna menos um e uma mensagem de aviso
        }

        // Calcula o valor da primeira hora se a diferença for menor que 60 minutos
        if (diffInMinutes <= 60) {
            valorTotal = tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo());
        } else {
            // Primeira hora completa
            valorTotal = tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo()) + tarifa.getValorHoraSubsequente(this.getVeiculo().getTipo());

            // Horas subsequentes
            long horasSubsequentes = (long) Math.ceil((double) (diffInMinutes - 60) / 60);
            valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente(this.getVeiculo().getTipo());

            // Lógica adicional para verificar se o ticket cobre mais de um dia completo ou se começa e termina em dias diferentes
            if (inicio.toLocalDate().isBefore(fim.toLocalDate())) {
                Duration diffAtraso = Duration.between(fim, LocalDateTime.now());
                long diffAtrasoMin = diffAtraso.toMinutes();
                valorTotal += (tarifa.getValorPrimeiraHora(this.getVeiculo().getTipo()) * 2) * Math.ceil((double) diffAtrasoMin / 60);
            }
        }

        return valorTotal;
    }

}
