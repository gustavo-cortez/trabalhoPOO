package classes;
import enums.EnumStatus;
import funcoes.FunTarifas;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 *
 * @author Gustavo
 */
/*Classe Ticket para representar um ticket de estacionamento*/
public abstract class Ticket  implements Serializable {
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Veiculo veiculo;
    private double valor;
    private Vagas vaga;
    private EnumStatus status;
    
    public Ticket(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga) {
        this.inicio = inicio;
        this.fim = fim;
        this.veiculo = veiculo;
        this.valor = valor;
        this.vaga = vaga;
        this.status = EnumStatus.EM_ABERTO;
    }
    public Ticket() {

    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public double getValor() {
        return valor;
    }

    public Vagas getVaga() {
        return vaga;
    }

    public EnumStatus getStatus() {
        return status;
    }
    
    public String getTipo() {
        
        if(this instanceof TicketMensalista){
            return "Mensalista";
        }
        else{
            return "Horista";
        }
        
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setVaga(Vagas vaga) {
        this.vaga = vaga;
    }
    
    public void setStatus(EnumStatus status) {
        this.status = status;
    }
    
    // Método abstrato para calcular o valor do ticket
    public abstract double calcularValor(FunTarifas tarifaIns);
}