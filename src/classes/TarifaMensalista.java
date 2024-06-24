package classes;
import enums.EnumTipoVeiculo;
import java.io.Serializable;
import java.time.LocalDate;
/**
 *
 * @author Gustavo
 */
/* Subclasse de Tarifa para tarifas mensalistas */
public class TarifaMensalista extends Tarifa implements Serializable {
    private double valorMensal;
    private static final long serialVersionUID = 1L;
    public TarifaMensalista(LocalDate inicio, double valorMensal) {
        super(inicio);
        this.valorMensal = valorMensal;
    }
    public double getValorMensal() {
        return valorMensal;
    }
    
}