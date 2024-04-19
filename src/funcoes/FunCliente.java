package funcoes;
import classes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class FunCliente {
    
    public List<Cliente> clientes;
    
    public FunCliente() {
        this.clientes = new ArrayList<>();
    }
    // Método para cadastrar um novo cliente
    public void cadastrarCliente(String nome, String documento) {
        Cliente cliente = new Cliente(nome, documento);
        clientes.add(cliente);
    }

    // Método para consultar um cliente pelo documento
    public Cliente consultarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null; // Cliente não encontrado
    }

    // Método para excluir um cliente
    public boolean excluirCliente(String documento) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            clientes.remove(cliente);
            return true; // Cliente removido com sucesso
        } else {
            return false; // Cliente não encontrado
        }
    }

    // Método para editar os dados de um cliente
    public boolean editarCliente(String documento, String novoNome) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            cliente.setNome(novoNome);
            return true; // Cliente editado com sucesso
        } else {
            return false; // Cliente não encontrado
        }
    }
    
    // Método para listar todos os clientes cadastrados
    public void listarClientes() {
        System.out.println("\nLista de todos os clientes cadastrados:");
        for (Cliente cliente : clientes) {
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Documento: " + cliente.getDocumento());
            System.out.println("Veículos:");
            List<Veiculo> veiculos = cliente.getVeiculos();
            if (veiculos.isEmpty()) {
                System.out.println("Nenhum veículo cadastrado para este cliente.");
            } else {
                for (Veiculo veiculo : veiculos) {
                    System.out.println("- Placa: " + veiculo.getPlaca() + ", Tipo: " + veiculo.getTipo());
                }
            }
            System.out.println();
        }
    }
    
        // Método para adicionar um veículo a um cliente
    public void adicionarVeiculoCliente(String placa, String tipo, String documentoCliente) {
        String nomeCliente;
        TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(tipo.toUpperCase());
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            Veiculo veiculo = new Veiculo(placa, consultarCliente(documentoCliente) ,tipoVeiculo);
            cliente.adicionarVeiculo(veiculo);
            nomeCliente = cliente.getNome();
            System.out.println("Veículo adicionado com sucesso ao cliente " + nomeCliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    // Método para consultar os veículos de um cliente por documento
    public List<Veiculo> consultarVeiculo(String documentoCliente) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            if (veiculosCliente.isEmpty()) {
                System.out.println("O cliente não possui veículos cadastrados.");
            } else {
                System.out.println("Veículos do cliente:");
                for (Veiculo veiculo : veiculosCliente) {
                    System.out.println("Placa: " + veiculo.getPlaca() + ", Tipo: " + veiculo.getTipo());
                }
            }
            return veiculosCliente;
        } else {
            System.out.println("Cliente não encontrado.");
            return null;
        }
    }
    
    // Método para consultar veículo por placa
    public Veiculo consultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return veiculo;
                }
            }
        }
        return null; // Retorna null se nenhum veículo com a placa especificada for encontrado
    }
}
