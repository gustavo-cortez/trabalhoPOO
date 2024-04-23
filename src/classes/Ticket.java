package classes;
import java.time.LocalDateTime;
/**
 *
 * @author Gustavo
 */
// Classe Ticket para representar um ticket de estacionamento
public class Ticket{
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Veiculo veiculo;
    private double valor;
    private Vagas vaga;

    // Métodos getters e setters

    public Ticket(LocalDateTime inicio, LocalDateTime fim, Veiculo veiculo, double valor, Vagas vaga) {
        this.inicio = inicio;
        this.fim = fim;
        this.veiculo = veiculo;
        this.valor = valor;
        this.vaga = vaga;
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


    
}