package funcoesVisual;

import enums.EnumVagaStatus;
import enums.EnumTipoVeiculo;
import enums.EnumStatus;
import enums.EnumUsoEstacionamento;
import classes.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
/**
 * 
 * @author Gustavo
 */
public class FunVagasVisual {
    
    public List<Vagas> vagas;
    
    public FunVagasVisual() {
        this.vagas = new ArrayList<>();
    }
    
    public void cadastrarVaga(int numero, String rua, EnumTipoVeiculo tipoVeiculo) {
        if(buscarVaga(numero) == null){
            Vagas vaga = new Vagas(numero, rua, EnumVagaStatus.DISPONIVEL, tipoVeiculo);  
            vagas.add(vaga);
            JOptionPane.showMessageDialog(null, "Vaga cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar a vaga, vaga com numéro informado já existente", "Erro", JOptionPane.ERROR_MESSAGE);
        }
            
        
    }
    
    public Vagas buscarVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                return vaga;
            }
        }
        return null; // Vaga não encontrada
    }
    
    public boolean excluirVaga(int numeroVaga, FunTicketsVisual ticketIns) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && consultarTicketsVaga(vaga, ticketIns).isEmpty()) {
                vagas.remove(vaga);
                JOptionPane.showMessageDialog(null, "Vaga excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Vaga não encontrada ou não está disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    private List<Ticket> consultarTicketsVaga(Vagas vaga, FunTicketsVisual ticketIns) {
        List<Ticket> ticketsVaga = new ArrayList<>();
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket.getVaga().equals(vaga)) {
                ticketsVaga.add(ticket);
            }
        }
        return ticketsVaga;
    }

    public boolean editarVaga(int numeroVaga, String novaRua, EnumTipoVeiculo novoTipoVeiculo) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setRua(novaRua);
                vaga.setTipoVeiculo(novoTipoVeiculo);
                JOptionPane.showMessageDialog(null, "Vaga editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Vaga não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public boolean alterarDisponibilidadeVaga(int numeroVaga, EnumVagaStatus novoStatus) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() != EnumVagaStatus.OCUPADA) {
                vaga.setStatus(novoStatus);
                JOptionPane.showMessageDialog(null, "Disponibilidade da vaga alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Vaga não encontrada ou está ocupada.", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public List<Vagas> listarVagasDisponiveis() {
        List<Vagas> vagasDisponiveis = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == EnumVagaStatus.DISPONIVEL) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }
    
    public List<Vagas> listarVagasAlugadas() {
        List<Vagas> vagasAlugadas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == EnumVagaStatus.ALUGADA_MENSAL){
                vagasAlugadas.add(vaga);
            }
        }
        return vagasAlugadas;
    }

    public void estacionarVeiculoMensalista(FunTicketsVisual ticketIns, Veiculo veiculo, int numeroVaga, FunTarifasVisual tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && veiculo != null && (vaga.getStatus() == EnumVagaStatus.DISPONIVEL || vaga.getStatus() == EnumVagaStatus.ALUGADA_MENSAL) && veiculo.getTipo() == vaga.getTipoVeiculo()) {

            // Verifica se já existe um ticket mensalista válido para o veículo
            Ticket ticketExistente = buscarTicketMensalistaValido(veiculo, ticketIns);

            if (ticketExistente != null) {
                if (numeroVaga == ticketExistente.getVaga().getNumero()) {
                    // Ticket mensalista já existe e é válido, atualiza a vaga para ocupada
                    vaga.setStatus(EnumVagaStatus.OCUPADA);
                    JOptionPane.showMessageDialog(null, "Veículo estacionado com sucesso usando ticket mensalista existente!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Não foi possivel estacionar usando um ticket mensalista afinal a vaga não corresponde à vaga previamente cadastrada para esse ticket!", "Erro", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // Cria um novo ticket mensalista
                LocalDateTime inicio = LocalDateTime.now();
                LocalDateTime fim = inicio.plusDays(30); // Fim do período de 30 dias
                double valor = tarifaIns.encontrarTarifaMensalista().getValorMensal(veiculo.getTipo()); // Valor único da tarifa mensalista
                Ticket ticket = new TicketMensalista(inicio, fim, veiculo, valor, vaga, tarifaIns.encontrarTarifaMensalista());
                cadastrarTicket(ticket, ticketIns);
                vaga.setStatus(EnumVagaStatus.OCUPADA);
                JOptionPane.showMessageDialog(null, "Veículo estacionado com sucesso com novo ticket mensalista!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao estacionar veículo: vaga não correspondente ou indisponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*Método que estaciona um veículo na vaga específica e assim gerando um ticket novo com a inicio no dia atual e o fim nulo se a vaga não estiver ocupada e se o veículo seja compatível com a vaga*/
    public void estacionarVeiculo(FunTicketsVisual ticketIns, Veiculo veiculo, int numeroVaga, FunTarifasVisual tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && veiculo != null && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo()) {
            vaga.setStatus(EnumVagaStatus.OCUPADA);
            LocalDateTime inicio = LocalDateTime.now();
            Ticket ticket = new TicketHorista(inicio, null, veiculo, 0.0, vaga, tarifaIns.encontrarTarifaHorista(inicio.getDayOfWeek()));
            cadastrarTicket(ticket, ticketIns);
            JOptionPane.showMessageDialog(null, "Veículo estacionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao estacionar veículo: vaga não correspondente ou indisponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public double retirarVeiculo(FunTicketsVisual ticketIns, int numeroVaga, FunTarifasVisual tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == EnumVagaStatus.OCUPADA) {
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            if (ticket != null) {
                ticket.setFim(LocalDateTime.now());
                double valorTotal = ticketIns.calcularValorTicket(ticket, tarifaIns);
                if (valorTotal != -1.0) {
                    vaga.setStatus(EnumVagaStatus.DISPONIVEL);
                    ticket.setValor(valorTotal);
                    ticket.setStatus(EnumStatus.FINALIZADO);
                    JOptionPane.showMessageDialog(null, "Veículo retirado com sucesso! Valor total: " + valorTotal, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    return valorTotal;
                } else {
                    ticket.setFim(null);
                    JOptionPane.showMessageDialog(null, "Erro ao calcular o valor do ticket.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return -1.0;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum ticket encontrado para a vaga especificada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return -1.0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vaga não ocupada ou inexistente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }
    public double retirarVeiculoMensalista(FunTicketsVisual ticketIns, int numeroVaga, FunTarifasVisual tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == EnumVagaStatus.OCUPADA) {
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            if (ticket != null) {
                if (ticket instanceof TicketMensalista) {
                    // Ticket mensalista, apenas libera a vaga sem encerrar o ticket
                    vaga.setStatus(EnumVagaStatus.ALUGADA_MENSAL);
                    JOptionPane.showMessageDialog(null, "Veículo retirado com sucesso! Ticket mensalista ainda válido.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    return ticket.getValor();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum ticket encontrado para a vaga especificada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return -1.0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vaga não ocupada ou inexistente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
        
        return -1.0;
    }
    
    public TicketMensalista buscarTicketMensalistaValido(Veiculo veiculo, FunTicketsVisual ticketIns) {
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket instanceof TicketMensalista && ticket.getVeiculo().equals(veiculo) && ticket.getFim().isAfter(LocalDateTime.now())) {
                return (TicketMensalista) ticket; // Retorna o ticket mensalista válido encontrado
            }
        }
        return null; // Retorna null se nenhum ticket mensalista válido for encontrado
    }
    
    public void cadastrarTicket(Ticket ticket, FunTicketsVisual ticketIns) {
        ticketIns.tickets.add(ticket);
        JOptionPane.showMessageDialog(null, "Ticket cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


}