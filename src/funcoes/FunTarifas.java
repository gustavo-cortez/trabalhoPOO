package funcoes;
import classes.DiaSemana;
import classes.Tarifa;
import classes.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Gustavo
 */
public class FunTarifas {
    
    public List<Tarifa> tarifas;
    
    public FunTarifas() {
        this.tarifas = new ArrayList<>();
    }
    
    // Método para cadastrar uma nova tarifa
    public void cadastrarTarifa(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente, DiaSemana[] diasSemana) {
        Tarifa tarifa = new Tarifa(inicio, valorPrimeiraHora, valorHoraSubsequente, diasSemana);
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

    public Tarifa buscarTarifaPorPeriodo(LocalDate inicioPeriodo, LocalDate fimPeriodo) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getInicio().equals(inicioPeriodo) && tarifa.getInicio().equals(fimPeriodo)) {
                return tarifa;
            }
        }
        return null; // Tarifa não encontrada para o período especificado
    }
    // Método para consultar o faturamento em um período específico
    public double consultarFaturamentoPeriodo(LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo) {
        FunTickets ticketsIns = new FunTickets();
        double faturamento = 0;
        for (Ticket ticket : ticketsIns.tickets) {
            LocalDateTime inicioTicket = ticket.getInicio();
            LocalDateTime fimTicket = ticket.getFim();
            if (inicioTicket.isAfter(inicioPeriodo) && fimTicket.isBefore(fimPeriodo)) {
                double valorTicket = ticketsIns.calcularValorTicket(ticket);
                faturamento += valorTicket;
            }
        }
        return faturamento;
    }

    
}
