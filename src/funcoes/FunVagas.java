package funcoes;

import classes.Ticket;
import classes.TipoVeiculo;
import classes.VagaStatus;
import classes.Vagas;
import classes.Veiculo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class FunVagas {
    
    public List<Vagas> vagas;
    
    public FunVagas() {
        this.vagas = new ArrayList<>();
    }
    
    // Método para cadastrar uma nova vaga
    public void cadastrarVaga(int numero, String rua, TipoVeiculo tipoVeiculo) {
        Vagas vaga = new Vagas(numero, rua, VagaStatus.DISPONIVEL, tipoVeiculo);  
        vagas.add(vaga);
    }
    
    // Método para buscar uma vaga pelo número
    public Vagas buscarVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                return vaga;
            }
        }
        return null; // Vaga não encontrada
    }
    
    // Método para excluir uma vaga pelo número
    public boolean excluirVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vagas.remove(vaga);
                return true; // Vaga removida com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para editar uma vaga pelo número
    public boolean editarVaga(int numeroVaga, String novaRua, TipoVeiculo novoTipoVeiculo) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setRua(novaRua);
                vaga.setTipoVeiculo(novoTipoVeiculo);
                return true; // Vaga editada com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para alterar a disponibilidade de uma vaga pelo número
    public boolean alterarDisponibilidadeVaga(int numeroVaga, VagaStatus novoStatus) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setStatus(novoStatus);
                return true; // Disponibilidade da vaga alterada com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para listar todas as vagas disponíveis
    public List<Vagas> listarVagasDisponiveis() {
        List<Vagas> vagasDisponiveis = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == VagaStatus.DISPONIVEL) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }
    // Método para estacionar um veículo
    public void estacionarVeiculo(Veiculo veiculo, int numeroVaga) {
        FunTickets ticketIns = new FunTickets();
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo()) {
            vaga.setStatus(VagaStatus.OCUPADA);
            Ticket ticket = new Ticket(new Date(), new Date(), veiculo, 0.0, vaga);
            ticketIns.tickets.add(ticket);
            System.out.println("Veículo estacionado com sucesso");
        } else {
            
            System.out.println("Erro ao estacionar veículo, vaga não correspondente ou indisponível");
            
        }
    }
    
    public double retirarVeiculo(int numeroVaga) {
        FunTickets ticketIns = new FunTickets();
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.OCUPADA) {
            vaga.setStatus(VagaStatus.DISPONIVEL);
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            ticket.setFim(new Date());

            double valorTotal = ticketIns.calcularValorTicket(ticket);

            ticketIns.tickets.remove(ticket); // Remover ticket da lista de tickets

            return valorTotal;
        }
        return -1; // Vaga não ocupada ou inexistente
    }
}
