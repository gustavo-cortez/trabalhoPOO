package classes;
import funcoes.FunTarifas;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalDate;
/**
 *
 * @author Gustavo
 */
/* Subclasse específica para representar tickets mensalistas */
public class TicketMensalista extends Ticket implements Serializable  {
    /* Construtor */
    TarifaMensalista tarifaMensal;
    public TicketMensalista(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga, TarifaMensalista tarifaMensal) {
        super(inicio, fim, veiculo, valor, vaga);
        this.tarifaMensal = tarifaMensal;
    }
    
    public TarifaMensalista getTarifaMensal() {
        return tarifaMensal;
    }

    public void setTarifaMensal(TarifaMensalista tarifaMensal) {
        this.tarifaMensal = tarifaMensal;
    }
    
    @Override
    public double calcularValor(FunTarifas tarifaIns) {
        double valorTotal;
        valorTotal = tarifaMensal.getValorMensal(this.getVeiculo().getTipo());
        if (LocalDateTime.now().isAfter(this.getFim())){
            valorTotal *= 1.5;
        }
        
        return valorTotal;
    }
}

