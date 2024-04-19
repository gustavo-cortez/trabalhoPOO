package funcoes;
import classes.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class FunTickets {
    
    public List<Ticket> tickets;

    public FunTickets() {
        
        this.tickets = new ArrayList<>();
    }
    
    // Método auxiliar para buscar um ticket pelo número da vaga
    public Ticket buscarTicketPorVaga(int numeroVaga) {
        for (Ticket ticket : tickets) {
            if (ticket.getVaga().getNumero() == numeroVaga) {
                return ticket;
            }
        }
        return null; // Ticket não encontrado
    }

    // Método para calcular o valor total do ticket
    public double calcularValorTicket(Ticket ticket) {
        FunTarifas tarifasIns = new FunTarifas();
        Date inicio = ticket.getInicio();
        Date fim = ticket.getFim();
        long diffInMillies = Math.abs(fim.getTime() - inicio.getTime());
        long diffInMinutes = diffInMillies / (60 * 1000);

        // Encontrar a tarifa correspondente ao dia da semana
        Tarifa tarifa = null;
        for (Tarifa t : tarifasIns.tarifas) {
            
            tarifa = t;
            break;
            
        }

        if (tarifa == null) {
            return -1; // Tarifa não encontrada para o dia da semana
        }

        double valorTotal = tarifa.getValorPrimeiraHora();
        long horasSubsequentes = (long) Math.ceil((double) (diffInMinutes - 60) / 60);
        valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente();

        return valorTotal;
    }
    
    // Método para gerar um ticket de estacionamento
    public Ticket gerarTicket(Veiculo veiculo, Cliente cliente) {
        FunVagas vagaIns = new FunVagas();
        // Encontrar uma vaga disponível para o veículo
        Vagas vagaDisponivel = null;
        for (Vagas vaga : vagaIns.vagas) {
            if (vaga.getStatus() == VagaStatus.DISPONIVEL && vaga.getTipoVeiculo() == veiculo.getTipo()) {
                vagaDisponivel = vaga;
                break;
            }
        }

        // Verificar se há vagas disponíveis
        if (vagaDisponivel == null) {
            System.out.println("Não há vagas disponíveis para estacionar o veículo.");
            return null;
        }

        // Criar um novo ticket
        Ticket novoTicket = new Ticket(new Date(), new Date(), veiculo, 0.0, vagaDisponivel);

        // Atualizar o status da vaga para ocupada
        vagaDisponivel.setStatus(VagaStatus.OCUPADA);

        // Adicionar o ticket à lista de tickets
        tickets.add(novoTicket);

        System.out.println("Ticket gerado com sucesso!");
        return novoTicket;
    }
}
