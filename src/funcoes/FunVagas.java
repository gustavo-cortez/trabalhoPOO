package funcoes;

import classes.Ticket;
import classes.TipoVeiculo;
import classes.VagaStatus;
import classes.Vagas;
import classes.Veiculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void estacionarVeiculo(FunTickets ticketIns, Veiculo veiculo, int numeroVaga) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo()) {
            vaga.setStatus(VagaStatus.OCUPADA);
            ticketIns.cadastrarTicket(LocalDateTime.now(), null, veiculo, 0.0, vaga); // Adiciona o ticket à lista de tickets
            System.out.println("Veículo estacionado com sucesso");
        } else {
            System.out.println("Erro ao estacionar veículo, vaga não correspondente ou indisponível");
        }
    }

    public double retirarVeiculo(FunTickets ticketIns, int numeroVaga, FunTarifas tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.OCUPADA) {
            vaga.setStatus(VagaStatus.DISPONIVEL);
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            if (ticket != null) {
                ticket.setFim(LocalDateTime.now());
                double valorTotal = ticketIns.calcularValorTicket(ticket, tarifaIns);
                return valorTotal;
            } else {
                System.out.println("Nenhum ticket encontrado para a vaga especificada.");
                return -1.0;
            }
        } else {
            System.out.println("Vaga não ocupada ou inexistente");
            return -1.0;
        }
    }



}
