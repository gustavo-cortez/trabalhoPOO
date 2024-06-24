package funcoes;
import enums.EnumVagaStatus;
import enums.EnumStatus;
import enums.EnumUsoEstacionamento;
import classes.*;
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
    public FunTickets(Instancias instancias) {
        this.tickets = new ArrayList<>();
        this.instancias = instancias;
    }
    
    /*Método para cadastrar um novo ticket, o valor de fim nesses cadastros será sempre nulo e o valor 0.0*/
    public void cadastrarTicket(LocalDateTime inicio, Veiculo veiculo, double valor, Vagas vaga) {
        try {
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
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao cadastrar ticket: " + e.getMessage());
        }
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
        if(!tickets.isEmpty()){
            for (Ticket ticket : tickets) {
                instancias.getInterface().exibirMensagem("Inicio: " + ticket.getInicio() + ", Fim: " + ticket.getFim() + ", Valor: " + ticket.getValor() + ", Veiculo: " + ticket.getVeiculo().getPlaca() + ", Vaga: " + ticket.getVaga().getNumero() + ", Tipo de uso: " + ticket.getTipo());
            }
        }
        else{
            instancias.getInterface().exibirErro("Nenhum ticket gerado!");
        }

    }
    
    /*Método para calcular o valor total do ticket com base nas tarifas disponivel antes da data atual e do dia da semana atual*/
    public double calcularValorTicket(Ticket ticket) {
        try {
            double valorTotal;
            if(ticket instanceof TicketHorista){
                valorTotal = ticket.calcularValor(instancias.getTarifasIns());
            }
            else{
                if(instancias.getVagasIns().buscarTicketMensalistaValido(ticket.getVeiculo()) == null){
                    valorTotal = ticket.calcularValor(instancias.getTarifasIns());
                    return valorTotal; 
                }
                else{
                    return instancias.getVagasIns().buscarTicketMensalistaValido(ticket.getVeiculo()).getValor();
                }
            }
            return valorTotal; 
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao calcular valor do ticket: " + e.getMessage());
            return -1; // Indica que ocorreu um erro
        }
    }
        
    /*Método usado para verificar todos os tickets mensalistas existentes e atualiza-los conforme a data de hoje */
    public void verificarTicketsMensalistas() {
        try {
            LocalDateTime hoje = LocalDateTime.now();
            for (Ticket ticket : tickets) {
                if (ticket instanceof TicketMensalista && ticket.getFim().isBefore(hoje)) {
                    ticket.setStatus(EnumStatus.FINALIZADO);
                    Vagas vaga = instancias.getVagasIns().buscarVaga(ticket.getVaga().getNumero());
                    if (vaga != null) {
                        vaga.desocupar();
                    }
                }
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao verificar tickets mensalistas: " + e.getMessage());
        }
    }

}
