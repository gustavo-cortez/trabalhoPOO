package funcoes;
import classes.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

/**
 *
 * @author Gustavo
 */
public class FunTickets {
    
    public List<Ticket> tickets;

    public FunTickets() {
        
        this.tickets = new ArrayList<>();
    }
    
    // Método para cadastrar um novo cliente
    public void cadastrarTicket(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga) {
        Ticket ticket = new Ticket(inicio, fim, veiculo , valor, vaga);
        tickets.add(ticket);
    }
    
    public Ticket buscarTicketPorVaga(int numeroVaga) {
        for (Ticket ticket : tickets) {
            if (ticket.getVaga().getNumero() == numeroVaga && ticket.getFim() == null) {
                return ticket;
            }
        }
        return null; // Ticket não encontrado para a vaga especificada ou já foi encerrado
    }
    

    
    // Método para listar todas as vagas disponíveis
    public void listarTickets() {
        for (Ticket ticket : tickets) {
            System.out.println("Inicio: " + ticket.getInicio() + ", Fim: " + ticket.getFim() + ", Valor: " + ticket.getValor());
        }
    }
    
    // Método para calcular o valor total do ticket
    public double calcularValorTicket(Ticket ticket, FunTarifas tarifaIns) {

        LocalDateTime inicio = ticket.getInicio();
        LocalDateTime fim = ticket.getFim();

        // Calcular a diferença entre o início e o fim do ticket
        Duration diff = Duration.between(inicio, fim);
        long diffInMinutes = diff.toMinutes();

        // Encontrar a tarifa correspondente ao dia da semana do início do ticket
        Tarifa tarifa = null;
        for (Tarifa t : tarifaIns.tarifas) {
            for (DiaSemana dia : t.getDiasSemana()) {
                if (dia.getOpcaodia() == inicio.getDayOfWeek().getValue()) {
                    tarifa = t;
                    break;
                }
            }
            if (tarifa != null) {
                break;
            }
        }

        if (tarifa == null) {
            System.out.println("Tarifa não encontrada para o dia da semana");
            return -1.0; // Tarifa não encontrada para o dia da semana
        }

        double valorTotal;

        // Calcula o valor da primeira hora
        if (diffInMinutes <= 60) {
            valorTotal = tarifa.getValorPrimeiraHora();
        } else {
            valorTotal = tarifa.getValorPrimeiraHora() + tarifa.getValorHoraSubsequente(); // Primeira hora completa
            long horasSubsequentes = (long) Math.ceil((double) (diffInMinutes - 60) / 60);
            valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente();
        }
        System.out.println("Valor:" + valorTotal);
        return valorTotal;
    }


}
