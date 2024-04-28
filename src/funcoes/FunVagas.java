package funcoes;

import classes.Ticket;
import classes.TipoVeiculo;
import classes.VagaStatus;
import classes.Vagas;
import classes.Veiculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Gustavo
 */
public class FunVagas {
    
    public List<Vagas> vagas;
    
    public FunVagas() {
        this.vagas = new ArrayList<>();
    }
    
    /*Método para cadastrar uma nova vaga, não entendi muito bem por que da rua na vaga, muito sem sentido pra mim*/
    public void cadastrarVaga(int numero, String rua, TipoVeiculo tipoVeiculo) {
        Vagas vaga = new Vagas(numero, rua, VagaStatus.DISPONIVEL, tipoVeiculo);  
        vagas.add(vaga);
    }
    
    /*Método para buscar uma vaga pelo número, que retorna a vaga ou nulo*/
    public Vagas buscarVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                return vaga;
            }
        }
        return null; // Vaga não encontrada
    }
    
    /*Método para excluir uma vaga pelo número apenas é excluido se a vaga está disponivel*/
    public boolean excluirVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() == VagaStatus.DISPONIVEL) {
                vagas.remove(vaga);
                return true; /*True simbolizando que a vaga foi removida com sucesso*/
            }
        }
        return false; /* False simbolizando que a vaga não foi encontrada*/
    }

    /*Método para editar uma vaga pelo número*/
    public boolean editarVaga(int numeroVaga, String novaRua, TipoVeiculo novoTipoVeiculo) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setRua(novaRua);
                vaga.setTipoVeiculo(novoTipoVeiculo);
                return true; /* True simbolizando que a vaga foi editada com sucesso*/
            }
        }
        return false; /* False simbolizando que a vaga não foi encontrada*/
    }

    /*Método para alterar a disponibilidade de uma vaga pelo número apenas se a vaga estiver disponivel e indisponivel, 
    nunca ocupada, afinal não faz sentido mudar sua disponibilidade se ela estiver ocupada ainda*/
    public boolean alterarDisponibilidadeVaga(int numeroVaga, VagaStatus novoStatus) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() != VagaStatus.OCUPADA) {
                vaga.setStatus(novoStatus);
                return true; /*True simbolizando que a disponibilidade da vaga foi alterada com sucesso*/
            }
        }
        return false; /*False simbolizando que a vaga não foi encontrada */
    }

    /*Método para listar todas as vagas disponíveis*/
    public List<Vagas> listarVagasDisponiveis() {
        List<Vagas> vagasDisponiveis = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == VagaStatus.DISPONIVEL) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }
    /*Método que estaciona um veículo na vaga específica e assim gerando um ticket novo com a inicio no dia atual e o fim nulo se a vaga não estiver ocupada e se o veículo seja compatível com a vaga*/
    public void estacionarVeiculo(FunTickets ticketIns, Veiculo veiculo, int numeroVaga) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && veiculo != null && vaga.getStatus() == VagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo()) {
            vaga.setStatus(VagaStatus.OCUPADA);
            ticketIns.cadastrarTicket(LocalDateTime.now(), null, veiculo, 0.0, vaga); // Adiciona o ticket à lista de tickets
            System.out.println("Veículo estacionado com sucesso");
        } else {
            System.out.println("Erro ao estacionar veículo, vaga não correspondente ou indisponível");
        }
    }
    /*Método que retira o veiculo da vaga especifica e faz com que o ticket relacionado a vaga tenha seu fim alterado para a data atual da retirada do veículo*/
    public double retirarVeiculo(FunTickets ticketIns, int numeroVaga, FunTarifas tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.OCUPADA) {
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            if (ticket != null) {
                
                ticket.setFim(LocalDateTime.now());
                double valorTotal = ticketIns.calcularValorTicket(ticket, tarifaIns);
                if(valorTotal != -1.0) {
                    vaga.setStatus(VagaStatus.DISPONIVEL);
                    ticket.setValor(valorTotal);
                    return valorTotal;
                }
                else{
                    ticket.setFim(null);
                    return -1.0;
                }
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
