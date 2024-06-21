package classes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo
 */

/*Classe Cliente para representar um cliente do estacionamento*/
public class Cliente {
    private String nome;
    private String documento;
    private List<Veiculo> veiculos; /*Lista de veículos associados ao cliente*/
    /*Construtor e métodos getters e setters do cliente*/

    public Cliente(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
        this.veiculos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    // Método para adicionar um veículo à lista de veículos do cliente
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }
    
    
    
}