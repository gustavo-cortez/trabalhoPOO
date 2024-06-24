package funcoes;
import classes.*;
import enums.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import menus.Instancias;

/**
 *
 * @author Gustavo
 */
public class FunCliente {
    
    public List<Cliente> clientes;
    private Instancias instancias;
    public FunCliente(Instancias instancias){
        this.clientes = new ArrayList<>();
        this.instancias = instancias;
    }
    
    /*Método para cadastrar um novo cliente*/
    public void cadastrarCliente(String nome, String documento) {
        try {
            if(consultarCliente(documento) == null){
                Cliente cliente = new Cliente(nome, documento);
                clientes.add(cliente);
            } else {
                throw new Exception("Cliente já existente no sistema!");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro(e.getMessage());
        }
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
        try {
            Cliente cliente = consultarCliente(documento);
            if (cliente!= null && cliente.getVeiculos().isEmpty() && consultarTicketsCliente(cliente, ticketIns).isEmpty()) {
                clientes.remove(cliente);
                return true; /* Cliente removido com sucesso */
            } else {
                throw new Exception("Cliente não encontrado ou possui veículos/tickets");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro(e.getMessage());
            return false;
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
        try {
            Cliente cliente = consultarCliente(documento);
            Veiculo veiculo = consultarPlaca(placa);
            if (cliente!= null && veiculo!= null) {
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
            } else {
                throw new Exception("Cliente ou veículo não encontrado");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro(e.getMessage());
            return false;
        } 
        // Retorna falso se o cliente ou o veículo não foram encontrados ou há tickets com o veículo cadastrados
        return false;
    }


    /*Método para editar os dados de um cliente*/
    public void editarCliente(String documento, String novoNome) {
        try{
            Cliente cliente = consultarCliente(documento);
            if (cliente != null) {
                cliente.setNome(novoNome);
                instancias.getInterface().exibirSucesso("Cliente editado com sucesso!");
            } else {
                throw new Exception("Não foi possível editar o cliente!");
            }
        }catch (Exception e) {
            instancias.getInterface().exibirErro(e.getMessage());
        } 
       
    }
    
    /*Método para listar todos os clientes cadastrados e seus respectivos veículos*/
    public void listarClientes() {
        if(clientes.isEmpty()){
            instancias.getInterface().exibirMensagem("Não há clientes cadastrados!");
        }else{ 
            StringBuilder mensagem = new StringBuilder("\nLista de todos os clientes cadastrados:\n");
            for (Cliente cliente : clientes) {
                mensagem.append("Nome: ").append(cliente.getNome()).append("\n");
                mensagem.append("Documento: ").append(cliente.getDocumento()).append("\n");
                mensagem.append("Veículos:\n");
                List<Veiculo> veiculos = cliente.getVeiculos();
                if (veiculos.isEmpty()) {
                    mensagem.append("Nenhum veículo cadastrado para este cliente.\n");
                } else {
                    for (Veiculo veiculo : veiculos) {
                        mensagem.append("- Placa: ").append(veiculo.getPlaca()).append(" - Modelo: ").append(veiculo.getModelo().getModelo()).append(" - Cor: ").append(veiculo.getCor().getNome()).append(", Tipo: ").append(veiculo.getTipo()).append("\n");
                    }
                }
                mensagem.append("\n");
                instancias.getInterface().exibirMensagem(mensagem.toString());
                mensagem.delete(0 , mensagem.length());
            }
            
        }
    }

    
    /*Método para adicionar um veículo a um cliente*/
    public void adicionarVeiculoCliente(String placa, EnumTipoVeiculo tipoVeiculo, String documentoCliente, String cor_veiculo, String modelo_veiculo, EnumUsoEstacionamento tipoUso) {
        String nomeCliente;
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            if(consultarPlaca(placa) == null){
                Cor cor = new Cor(cor_veiculo);
                Modelo modelo = new Modelo(modelo_veiculo);
                Veiculo veiculo = new Veiculo(placa, cliente ,tipoVeiculo, modelo, cor, tipoUso);
                cliente.adicionarVeiculo(veiculo);
                nomeCliente = cliente.getNome();
                instancias.getInterface().exibirSucesso("Veículo adicionado com sucesso ao cliente " + nomeCliente);
            }
            else{
                instancias.getInterface().exibirErro("Veículo com placa identica já existente");
            }
        } else {
            instancias.getInterface().exibirErro("Cliente não encontrado");
        }
    }
    
    /*Método para consultar os veículos de um cliente por documento, esse métodos lista TODOS os veículos*/
    public void consultarVeiculo(String documentoCliente) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            if (veiculosCliente.isEmpty()) {
                instancias.getInterface().exibirMensagem("O cliente não possui veículos cadastrados.");
            } else {
                instancias.getInterface().exibirMensagem("Veículos do cliente:");
                for (Veiculo veiculo : veiculosCliente) {
                    instancias.getInterface().exibirMensagem("- Placa: " + veiculo.getPlaca() + "- Modelo: " + veiculo.getModelo().getModelo() + "- Cor: " + veiculo.getCor().getNome() + ", Tipo: " + veiculo.getTipo());
                }
            }
        } else {
            instancias.getInterface().exibirErro("Cliente não encontrado.");
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
