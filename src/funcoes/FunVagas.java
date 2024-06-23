package funcoes;

import enums.EnumStatus;
import classes.Ticket;
import classes.TicketHorista;
import classes.TicketMensalista;
import enums.EnumUsoEstacionamento;
import enums.EnumTipoVeiculo;
import enums.EnumVagaStatus;
import classes.Vagas;
import classes.Veiculo;
import interfaces.UserInterface;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import menus.Instancias;

/**
 *
 * @author Gustavo
 */
public class FunVagas {
    
    public List<Vagas> vagas;
    private Instancias instancias;
    public FunVagas(Instancias instancias) {
        this.vagas = new ArrayList<>();
        this.instancias = instancias;
    }
    
    /*Método para cadastrar uma nova vaga, não entendi muito bem por que da rua na vaga, muito sem sentido pra mim*/
    public void cadastrarVaga(int numero, String rua, EnumTipoVeiculo tipoVeiculo) {
        if(buscarVaga(numero) == null){
            Vagas vaga = new Vagas(numero, rua, EnumVagaStatus.DISPONIVEL, tipoVeiculo);  
            vagas.add(vaga);
            instancias.getInterface().exibirSucesso("Vaga cadastrada com sucesso!");
        }
        else{
            instancias.getInterface().exibirErro("Erro ao cadastrar a vaga, vaga com numéro informado já existente");
        }
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
    public boolean excluirVaga(int numeroVaga, FunTickets ticketIns) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && consultarTicketsVaga(vaga, ticketIns).isEmpty()) {
                vagas.remove(vaga);
                instancias.getInterface().exibirSucesso("Vaga excluída com sucesso!");
                return true;
            }
        }
        instancias.getInterface().exibirMensagem("Vaga não encontrada ou não está disponível.");
        return false;
    }
    
    private List<Ticket> consultarTicketsVaga(Vagas vaga, FunTickets ticketIns) {
        List<Ticket> ticketsVaga = new ArrayList<>();
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket.getVaga().equals(vaga)) {
                ticketsVaga.add(ticket);
            }
        }
        return ticketsVaga;
    }

    /*Método para editar uma vaga pelo número*/
    public boolean editarVaga(int numeroVaga, String novaRua, EnumTipoVeiculo novoTipoVeiculo) {
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
    public boolean alterarDisponibilidadeVaga(int numeroVaga, EnumVagaStatus novoStatus) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga && vaga.getStatus() != EnumVagaStatus.OCUPADA) {
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
            if (vaga.getStatus() == EnumVagaStatus.DISPONIVEL) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }
    /*Método para listar todas as vagas alugadas*/
    public List<Vagas> listarVagasAlugadas() {
        List<Vagas> vagasAlugadas = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == EnumVagaStatus.ALUGADA_MENSAL) {
                vagasAlugadas.add(vaga);
            }
        }
        return vagasAlugadas;
    }
    /*Método que estaciona um veículo na vaga específica e assim gerando um ticket novo com a inicio no dia atual e o fim nulo se a vaga não estiver ocupada e se o veículo seja compatível com a vaga*/
    public void estacionarVeiculoMensalista(FunTickets ticketIns, Veiculo veiculo, int numeroVaga, FunTarifas tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && veiculo != null && (vaga.getStatus() == EnumVagaStatus.DISPONIVEL || vaga.getStatus() == EnumVagaStatus.ALUGADA_MENSAL) && veiculo.getTipo() == vaga.getTipoVeiculo()) {

            // Verifica se já existe um ticket mensalista válido para o veículo
            Ticket ticketExistente = buscarTicketMensalistaValido(veiculo, ticketIns);

            if (ticketExistente != null) {
                // Ticket mensalista já existe e é válido, atualiza a vaga para ocupada
                if (numeroVaga == ticketExistente.getVaga().getNumero()) {
                    vaga.setStatus(EnumVagaStatus.OCUPADA);
                    instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso usando ticket mensalista existente!");
                }
                else{
                    instancias.getInterface().exibirErro("Não foi possivel estacionar usando um ticket mensalista afinal a vaga não corresponde à vaga previamente cadastrada para esse ticket!");
                }
            } else {
                // Cria um novo ticket mensalista
                LocalDateTime inicio = LocalDateTime.now();
                LocalDateTime fim = inicio.plusDays(30); // Fim do período de 30 dias
                double valor = tarifaIns.encontrarTarifaMensalista().getValorMensal(veiculo.getTipo()); // Valor único da tarifa mensalista
                Ticket ticket = new TicketMensalista(inicio, fim, veiculo, valor, vaga, tarifaIns.encontrarTarifaMensalista());
                cadastrarTicket(ticket, ticketIns);
                vaga.setStatus(EnumVagaStatus.OCUPADA);
                instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso com novo ticket mensalista!");
            }
        }else {
            instancias.getInterface().exibirErro("Erro ao estacionar veículo: vaga não correspondente ou indisponível.");
        }
    }

    /*Método que estaciona um veículo na vaga específica e assim gerando um ticket novo com a inicio no dia atual e o fim nulo se a vaga não estiver ocupada e se o veículo seja compatível com a vaga*/
    public void estacionarVeiculo(FunTickets ticketIns, Veiculo veiculo, int numeroVaga, FunTarifas tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && veiculo != null && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo()) {
            vaga.setStatus(EnumVagaStatus.OCUPADA);
            LocalDateTime inicio = LocalDateTime.now();
            Ticket ticket = new TicketHorista(inicio, null, veiculo, 0.0, vaga, tarifaIns.encontrarTarifaHorista(inicio.getDayOfWeek()));
            cadastrarTicket(ticket, ticketIns);
            instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso!");
        } else {
            instancias.getInterface().exibirErro("Erro ao estacionar veículo: vaga não correspondente ou indisponível.");
        }
    }

    public double retirarVeiculo(FunTickets ticketIns, int numeroVaga, FunTarifas tarifaIns) {
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
                    instancias.getInterface().exibirSucesso("Veículo retirado com sucesso! Valor total: " + valorTotal);
                    return valorTotal;
                } else {
                    ticket.setFim(null);
                    instancias.getInterface().exibirErro("Erro ao calcular o valor do ticket.");
                    return -1.0;
                }
            } else {
                instancias.getInterface().exibirMensagem("Nenhum ticket encontrado para a vaga especificada.");
                return -1.0;
            }
        } else {
            instancias.getInterface().exibirMensagem("Vaga não ocupada ou inexistente.");
            return -1.0;
        }
    }
    public double retirarVeiculoMensalista(FunTickets ticketIns, int numeroVaga, FunTarifas tarifaIns) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == EnumVagaStatus.OCUPADA) {
            Ticket ticket = ticketIns.buscarTicketPorVaga(numeroVaga);
            if (ticket != null) {
                if (ticket instanceof TicketMensalista) {
                    // Ticket mensalista, apenas libera a vaga sem encerrar o ticket
                    vaga.setStatus(EnumVagaStatus.ALUGADA_MENSAL);
                    instancias.getInterface().exibirSucesso("Veículo retirado com sucesso! Ticket mensalista ainda válido.");
                    return ticket.getValor();
                }
            } else {
                instancias.getInterface().exibirMensagem("Nenhum ticket encontrado para a vaga especificada.");
                return -1.0;
            }
        } else {
            instancias.getInterface().exibirMensagem("Vaga não ocupada ou inexistente.");
            return -1.0;
        }
        
        return -1.0;
    }
    
    public TicketMensalista buscarTicketMensalistaValido(Veiculo veiculo, FunTickets ticketIns) {
        for (Ticket ticket : ticketIns.tickets) {
            if (ticket instanceof TicketMensalista && ticket.getVeiculo().equals(veiculo) && ticket.getFim().isAfter(LocalDateTime.now())) {
                return (TicketMensalista) ticket; // Retorna o ticket mensalista válido encontrado
            }
        }
        return null; // Retorna null se nenhum ticket mensalista válido for encontrado
    }
    
    public void cadastrarTicket(Ticket ticket, FunTickets ticketIns) {
        ticketIns.tickets.add(ticket);
        instancias.getInterface().exibirMensagem("Ticket cadastrado com sucesso!");
    }



}
