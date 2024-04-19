package funcoes;
import classes.Tarifa;
import classes.Ticket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class FunTarifas {
    
    public List<Tarifa> tarifas;
    
    public FunTarifas() {
        this.tarifas = new ArrayList<>();
    }
    
    // Métodos para gerenciar tarifas
    public void cadastrarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }
    
    // Método para listar todas as tarifas cadastradas
    public List<Tarifa> listarTarifas() {
        return tarifas;
    }
    
    // Métodos para gerenciar tarifas de acordo com períodos específicos
    public void cadastrarTarifaPeriodo(Tarifa tarifa) {
        tarifas.add(tarifa);
    }

    public Tarifa buscarTarifaPorPeriodo(Date inicioPeriodo, Date fimPeriodo) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getInicio().equals(inicioPeriodo) && tarifa.getInicio().equals(fimPeriodo)) {
                return tarifa;
            }
        }
        return null; // Tarifa não encontrada para o período especificado
    }

    // Método para consultar o faturamento em um período específico
    public double consultarFaturamentoPeriodo(Date inicioPeriodo, Date fimPeriodo) {
        FunTickets ticketsIns = new FunTickets();
        double faturamento = 0;
        for (Ticket ticket : ticketsIns.tickets) {
            Date inicioTicket = ticket.getInicio();
            Date fimTicket = ticket.getFim();
            if (inicioTicket.after(inicioPeriodo) && fimTicket.before(fimPeriodo)) {
                double valorTicket = ticketsIns.calcularValorTicket(ticket);
                faturamento += valorTicket;
            }
        }
        return faturamento;
    }
    
}
