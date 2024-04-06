package classes;
import java.util.Date;
/**
 *
 * @author Gustavo
 */
// Classe Ticket para representar um ticket de estacionamento
public class Ticket{
    private Date inicio;
    private Date fim;
    private Veiculo veiculo;
    private double valor;
    private Vagas vaga;

    // Métodos getters e setters

    public Ticket(Date inicio, Date fim, Veiculo veiculo, double valor, Vagas vaga) {
        this.inicio = inicio;
        this.fim = fim;
        this.veiculo = veiculo;
        this.valor = valor;
        this.vaga = vaga;
    }
    
    public Date getInicio() {
        return inicio;
    }

    public Date getFim() {
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


    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public void setFim(Date fim) {
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


    
}