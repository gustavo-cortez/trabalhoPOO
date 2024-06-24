package funcoes;

import classes.Ticket;
import classes.Veiculo;
import enums.EnumStatus;
import java.time.LocalDateTime;
import menus.Instancias;

/**
 *
 * @author Gustavo
 */
public class FunFaturamento {
    
    private Instancias instancias;
    
    public FunFaturamento(Instancias instancias) {
        this.instancias = instancias;
    }
    /*Método para consultar o faturamento em um período específico, buscando todos tickets que 
    foram gerados nesse meio tempo e fazendo a soma dos valores*/
    public double consultarFaturamentoPeriodo(LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo) {
        try {
            double faturamento = 0;
            for (Ticket ticket : instancias.getTicketsIns().tickets) {
                LocalDateTime fimTicket = ticket.getFim();
                if (fimTicket != null && fimTicket.isAfter(inicioPeriodo) && fimTicket.isBefore(fimPeriodo)) {
                    faturamento += ticket.getValor();
                }
            }
            return faturamento;
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao consultar faturamento do período: " + e.getMessage());
            return -1; // Indica que ocorreu um erro
        }
    }
    
    public double consultarFaturamentoVeiculo(String placaVeiculo) {
        try {
            double faturamento = 0;
            for (Ticket ticket : instancias.getTicketsIns().tickets) {
                Veiculo veiculo = ticket.getVeiculo();
                if (veiculo != null && veiculo.getPlaca().equals(placaVeiculo) && ticket.getStatus().FINALIZADO.equals(EnumStatus.FINALIZADO)) {
                    faturamento += ticket.getValor();
                }
            }
            return faturamento;
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao consultar faturamento do veículo: " + e.getMessage());
            return -1; // Indica que ocorreu um erro
        }
    }
}
