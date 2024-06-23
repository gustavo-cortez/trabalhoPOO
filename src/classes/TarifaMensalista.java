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
    public double getValorMensal(EnumTipoVeiculo tipo) {
        
        if(tipo.equals(EnumTipoVeiculo.ÔNIBUS)){
           return valorMensal * 1.5; 
        }
        else{
            if(tipo.equals(EnumTipoVeiculo.MOTO)){
                return valorMensal / 2; 
            }
        }
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }
    
}