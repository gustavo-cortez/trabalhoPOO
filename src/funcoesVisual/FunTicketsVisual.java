package funcoesVisual;

import enums.EnumVagaStatus;
import enums.EnumStatus;
import enums.EnumUsoEstacionamento;
import classes.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDate;
import javax.swing.JOptionPane;
/**
 * 
 * @author Gustavo
 */
public class FunTicketsVisual {

    public List<Ticket> tickets;

    public FunTicketsVisual() {
        this.tickets = new ArrayList<>();
    }

    /* Método para cadastrar um novo ticket, o valor de fim nesses cadastros será sempre nulo e o valor 0.0 */
    public void cadastrarTicket(LocalDateTime inicio, Veiculo veiculo, Vagas vaga) {
        Ticket ticket = null;
        if(veiculo.getTipoUso().equals(EnumUsoEstacionamento.HORISTA)){
            ticket = new TicketHorista(inicio, null, veiculo, 0.0, vaga, null);
            JOptionPane.showMessageDialog(null, "Ticket cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            LocalDateTime fim = inicio.plusDays(30); // Definir o final 30 dias após o início
            ticket = new TicketMensalista(inicio, fim, veiculo, 0.0, vaga, null);
            JOptionPane.showMessageDialog(null, "Ticket cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        tickets.add(ticket);
        
    }

    public Ticket buscarTicketPorVaga(int numeroVaga) {
        for (Ticket ticket : tickets) {
            if (ticket.getVaga().getNumero() == numeroVaga && ticket.getStatus().equals(EnumStatus.EM_ABERTO)) {
                return ticket;
            }
        }
        return null; /* Ticket não encontrado para a vaga especificada ou já foi encerrado */
    }

    /* Método para listar todos tickets gerados */
    public void listarTickets() {
        StringBuilder mensagem = new StringBuilder();
        if(tickets.isEmpty()){
            JOptionPane.showMessageDialog(null, "Sem tickets cadastrados.", "Lista de Tickets", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            for (Ticket ticket : tickets) {
                mensagem.append("Inicio: ").append(ticket.getInicio()).append(", Fim: ").append(ticket.getFim())
                        .append(", Valor: ").append(ticket.getValor()).append(", Veiculo: ").append(ticket.getVeiculo().getPlaca())
                        .append(", Vaga: ").append(ticket.getVaga().getNumero())
                        .append(", Tipo: ").append(ticket.getTipo()).append("\n");
            }
            JOptionPane.showMessageDialog(null, mensagem.toString(), "Lista de Tickets", JOptionPane.INFORMATION_MESSAGE);        
        }
    }

    /* Método para calcular o valor total do ticket com base nas tarifas disponíveis antes da data atual e do dia da semana atual */
    public double calcularValorTicket(Ticket ticket, FunTarifasVisual tarifaIns) {
        double valorTotal;
        if(ticket instanceof TicketHorista){
            TicketHorista ticketHo = new TicketHorista(ticket.getInicio(), ticket.getFim(), ticket.getVeiculo(), ticket.getValor(), ticket.getVaga(), tarifaIns.encontrarTarifaHorista(ticket.getInicio().getDayOfWeek()));
            valorTotal = ticketHo.calcularValor(tarifaIns);
        }
        else{
            TicketMensalista ticketMensal = new TicketMensalista(ticket.getInicio(), ticket.getFim(), ticket.getVeiculo(), ticket.getValor(), ticket.getVaga(), tarifaIns.encontrarTarifaMensalista());
            valorTotal = ticketMensal.calcularValor(tarifaIns);
        }
        return valorTotal; 
    }
    
    public void verificarTicketsMensalistas(FunVagasVisual vagasIns) {
        LocalDateTime hoje = LocalDateTime.now();
        for (Ticket ticket : tickets) {
            if (ticket instanceof TicketMensalista && ticket.getFim().isBefore(hoje)) {
                ticket.setStatus(EnumStatus.FINALIZADO);
                Vagas vaga = vagasIns.buscarVaga(ticket.getVaga().getNumero());
                if (vaga != null) {
                    vaga.setStatus(EnumVagaStatus.DISPONIVEL);
                }
            }
        }
    }
    
    /* Método para consultar o faturamento em um período específico, buscando todos tickets que 
    foram gerados nesse meio tempo e fazendo a soma dos valores */
    public double consultarFaturamentoPeriodo(LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo) {
        double faturamento = 0;
        for (Ticket ticket : tickets) {
            LocalDateTime fimTicket = ticket.getFim();
            if (fimTicket != null && fimTicket.isAfter(inicioPeriodo) && fimTicket.isBefore(fimPeriodo)) {
                faturamento += ticket.getValor();
            }
        }
        return faturamento;
    }
    
}
