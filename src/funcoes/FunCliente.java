package funcoes;
import classes.*;
import enums.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    /*Método para cadastrar um novo cliente*/
    public void cadastrarCliente(String nome, String documento) {
        Cliente cliente = new Cliente(nome, documento);
        clientes.add(cliente);
    }

    /*Método para consultar um cliente pelo documento*/
    public Cliente consultarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null; /*Cliente com o documento informado não foi encontrado*/
    }

    /*Método para excluir um cliente se ele não possuir veiculos*/
    public boolean excluirCliente(String documento, FunTickets ticketIns) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null && cliente.getVeiculos().isEmpty() && consultarTicketsCliente(cliente, ticketIns).isEmpty()) {
            clientes.remove(cliente);
            return true; /* Cliente removido com sucesso */
        } else {
            return false; /* Cliente não encontrado ou possui veículos/tickets */
        }
    }

    /*Método para consultar os tickets de um cliente, precisa ser implementado de acordo com a estrutura do seu sistema*/
    private List<Ticket> consultarTicketsCliente(Cliente cliente, FunTickets ticketIns) {
        List<Ticket> ticketsDoCliente = new ArrayList<>();
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket.getVeiculo().getProprietario().equals(cliente)) {
                ticketsDoCliente.add(ticket);
            }
        }
        return ticketsDoCliente;
    }

    
    /*Método para excluir um veiculo do cliente*/
    public boolean excluirVeiculo(String documento, String placa, FunTickets ticketIns) {
        Cliente cliente = consultarCliente(documento);
        Veiculo veiculo = consultarPlaca(placa);
        if (cliente != null && veiculo != null) {
            // Obtém a lista de veículos do cliente
            List<Veiculo> veiculosCliente = cliente.getVeiculos();

            // Itera sobre a lista de veículos do cliente
            Iterator<Veiculo> iterator = veiculosCliente.iterator();
            while (iterator.hasNext()) {
                Veiculo v = iterator.next();
                // Verifica se o veículo atual é o veículo que queremos remover
                if (v.getPlaca().equals(placa) && consultarTicketsCliente(cliente, ticketIns).isEmpty()) {
                    // Remove o veículo da lista
                    iterator.remove();
                    // Retorna verdadeiro indicando que o veículo foi removido com sucesso
                    return true;
                }
            }
        }
        // Retorna falso se o cliente ou o veículo não foram encontrados ou há tickets com o veículo cadastrados
        return false;
    }


    /*Método para editar os dados de um cliente*/
    public boolean editarCliente(String documento, String novoNome) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            cliente.setNome(novoNome);
            return true; /*Cliente editado com sucesso*/
        } else {
            return false; /*Cliente não encontrado*/
        }
    }
    
    /*Método para listar todos os clientes cadastrados e seus respectivos veículos*/
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
                    System.out.println("- Placa: " + veiculo.getPlaca() + "- Modelo: " + veiculo.getModelo() + "- Cor: " + veiculo.getCor() + ", Tipo: " + veiculo.getTipo());
                }
            }
            System.out.println();
        }
    }

    
    /*Método para adicionar um veículo a um cliente*/
    public void adicionarVeiculoCliente(String placa, EnumTipoVeiculo tipoVeiculo, String documentoCliente, String cor, String modelo, EnumUsoEstacionamento tipoUso) {
        String nomeCliente;
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            Veiculo veiculo = new Veiculo(placa, cliente ,tipoVeiculo, cor, modelo, tipoUso);
            cliente.adicionarVeiculo(veiculo);
            nomeCliente = cliente.getNome();
            System.out.println("Veículo adicionado com sucesso ao cliente " + nomeCliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    /*Método para consultar os veículos de um cliente por documento, esse métodos lista TODOS os veículos*/
    public void consultarVeiculo(String documentoCliente) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            if (veiculosCliente.isEmpty()) {
                System.out.println("O cliente não possui veículos cadastrados.");
            } else {
                System.out.println("Veículos do cliente:");
                for (Veiculo veiculo : veiculosCliente) {
                    System.out.println("- Placa: " + veiculo.getPlaca() + "- Modelo: " + veiculo.getModelo() + "- Cor: " + veiculo.getCor() + ", Tipo: " + veiculo.getTipo());
                }
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    /*Método para consultar veículo por placa, retorna o veículo com a placa informada*/
    public Veiculo consultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return veiculo;
                }
            }
        }
        return null; /*Retorna null se nenhum veículo com a placa especificada for encontrado*/
    }
    
    /*Método para consultar veículo por placa, esse método retorno o cliente que possui o veículo não o veículo em si*/
    public Cliente clienteconsultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return cliente;
                }
            }
        }
        return null; /*Retorna null se nenhum veículo com a placa especificada for encontrado*/
    }

}
