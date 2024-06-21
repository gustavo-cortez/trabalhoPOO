package funcoesVisual;

import enums.EnumTipoVeiculo;
import enums.EnumUsoEstacionamento;
import funcoes.*;
import classes.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * 
 * @author Gustavo
 */
public class FunClienteVisual {

    public List<Cliente> clientes;

    public FunClienteVisual() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(String nome, String documento) {
        Cliente cliente = new Cliente(nome, documento);
        clientes.add(cliente);
    }

    public Cliente consultarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean excluirCliente(String documento, FunTicketsVisual ticketIns) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null && cliente.getVeiculos().isEmpty() && consultarTicketsCliente(cliente, ticketIns).isEmpty()) {
            clientes.remove(cliente);
            return true;
        } else {
            return false;
        }
    }

    private List<Ticket> consultarTicketsCliente(Cliente cliente, FunTicketsVisual ticketIns) {
        List<Ticket> ticketsDoCliente = new ArrayList<>();
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket.getVeiculo().getProprietario().equals(cliente)) {
                ticketsDoCliente.add(ticket);
            }
        }
        return ticketsDoCliente;
    }

    public boolean excluirVeiculo(String documento, String placa, FunTicketsVisual ticketIns) {
        Cliente cliente = consultarCliente(documento);
        Veiculo veiculo = consultarPlaca(placa);
        if (cliente != null && veiculo != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            Iterator<Veiculo> iterator = veiculosCliente.iterator();
            while (iterator.hasNext()) {
                Veiculo v = iterator.next();
                if (v.getPlaca().equals(placa) && consultarTicketsCliente(cliente, ticketIns).isEmpty()) {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean editarCliente(String documento, String novoNome) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            cliente.setNome(novoNome);
            return true;
        } else {
            return false;
        }
    }

    public boolean editarCliente(String documento, EnumUsoEstacionamento tipo) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            return true;
        } else {
            return false;
        }
    }

    public void listarClientesVisual() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Lista de todos os clientes cadastrados:\n\n");
        for (Cliente cliente : clientes) {
            mensagem.append("Nome: ").append(cliente.getNome()).append("\n");
            mensagem.append("Documento: ").append(cliente.getDocumento()).append("\n");
            mensagem.append("Veículos:\n");
            List<Veiculo> veiculos = cliente.getVeiculos();
            if (veiculos.isEmpty()) {
                mensagem.append("Nenhum veículo cadastrado para este cliente.\n");
            } else {
                for (Veiculo veiculo : veiculos) {
                    mensagem.append("- Placa: ").append(veiculo.getPlaca())
                            .append(", Modelo: ").append(veiculo.getModelo())
                            .append(", Cor: ").append(veiculo.getCor())
                            .append(", Tipo: ").append(veiculo.getTipo())
                            .append("\n");
                }
            }
            mensagem.append("\n");
        }
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void adicionarVeiculoCliente(String placa, EnumTipoVeiculo tipo, String documentoCliente, String cor, String modelo, EnumUsoEstacionamento tipoUso) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            Veiculo veiculo = new Veiculo(placa, cliente, tipo, cor, modelo, tipoUso);
            cliente.adicionarVeiculo(veiculo);
            JOptionPane.showMessageDialog(null, "Veículo adicionado com sucesso ao cliente " + cliente.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void consultarVeiculoVisual(String documentoCliente) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            if (veiculosCliente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O cliente não possui veículos cadastrados.", "Veículos do Cliente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Veículos do cliente:\n");
                for (Veiculo veiculo : veiculosCliente) {
                    mensagem.append("- Placa: ").append(veiculo.getPlaca())
                            .append(", Modelo: ").append(veiculo.getModelo())
                            .append(", Cor: ").append(veiculo.getCor())
                            .append(", Tipo: ").append(veiculo.getTipo())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, mensagem.toString(), "Veículos do Cliente", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Veículos do Cliente", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Veiculo consultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return veiculo;
                }
            }
        }
        return null;
    }

    public Cliente clienteconsultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return cliente;
                }
            }
        }
        return null;
    }
}