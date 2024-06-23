package funcoes;
import enums.EnumVagaStatus;
import enums.EnumStatus;
import enums.EnumUsoEstacionamento;
import classes.*;
import interfaces.UserInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import menus.Instancias;

/**
 *
 * @author Gustavo
 */
public class FunTickets {
    
    public List<Ticket> tickets;
    private Instancias instancias;
    public FunTickets() {
        this.tickets = new ArrayList<>();
    }
    
    /*Método para cadastrar um novo ticket, o valor de fim nesses cadastros será sempre nulo e o valor 0.0*/
    public void cadastrarTicket(LocalDateTime inicio, Veiculo veiculo, double valor, Vagas vaga) {
        Ticket ticket;
        if(veiculo.getTipoUso().equals(EnumUsoEstacionamento.HORISTA)){
            ticket = new TicketHorista(inicio, null, veiculo, 0.0, vaga, null);
            instancias.getInterface().exibirSucesso("Ticket cadastrado com sucesso!");
        }
        else{
            LocalDateTime fim = inicio.plusDays(30); // Definir o final 30 dias após o início
            ticket = new TicketMensalista(inicio, fim, veiculo, 0.0, vaga, null);
            instancias.getInterface().exibirSucesso("Ticket cadastrado com sucesso!");
        }
        tickets.add(ticket);
    }
    
    public Ticket buscarTicketPorVaga(int numeroVaga) {
        for (Ticket ticket : tickets) {
            if (ticket.getVaga().getNumero() == numeroVaga && ticket.getStatus().equals(EnumStatus.EM_ABERTO)) {
                return ticket;
            }
        }
        return null; /*Ticket não encontrado para a vaga especificada ou já foi encerrado*/
    }
    
    /*Método para listar todos tickets gerados*/
    public void listarTickets() {
        for (Ticket ticket : tickets) {
            instancias.getInterface().exibirMensagem("Inicio: " + ticket.getInicio() + ", Fim: " + ticket.getFim() + ", Valor: " + ticket.getValor() + ", Veiculo: " + ticket.getVeiculo().getPlaca() + ", Vaga: " + ticket.getVaga().getNumero() + ", Tipo de uso: " + ticket.getTipo());
        }
    }
    
    /*Método para calcular o valor total do ticket com base nas tarifas disponivel antes da data atual e do dia da semana atual*/
    public double calcularValorTicket(Ticket ticket, FunTarifas tarifaIns) {
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
    
    /*Método para consultar o faturamento em um período específico, buscando todos tickets que 
    foram gerados nesse meio tempo e fazendo a soma dos valores*/
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
    
    public void verificarTicketsMensalistas(FunVagas vagasIns) {
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

}
