package funcoes;

import enums.EnumStatus;
import classes.Ticket;
import classes.TicketHorista;
import classes.TicketMensalista;
import enums.EnumTipoVeiculo;
import enums.EnumVagaStatus;
import classes.Vagas;
import classes.Veiculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
        try {
            if(buscarVaga(numero) == null){
                Vagas vaga = new Vagas(numero, rua, EnumVagaStatus.DISPONIVEL, tipoVeiculo);  
                vagas.add(vaga);
                instancias.getInterface().exibirSucesso("Vaga cadastrada com sucesso!");
            }
            else{
                throw new Exception("Erro ao cadastrar a vaga, vaga com numéro informado já existente");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao cadastrar vaga: " + e.getMessage());
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
    public boolean excluirVaga(int numeroVaga) {
        try {
            for (Vagas vaga : vagas) {
                if (vaga.getNumero() == numeroVaga && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && consultarTicketsVaga(vaga).isEmpty()) {
                    vagas.remove(vaga);
                    instancias.getInterface().exibirSucesso("Vaga excluída com sucesso!");
                    return true;
                }
            }
            throw new Exception("Vaga não encontrada ou está sendo utilizada.");
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao excluir vaga: " + e.getMessage());
            return false;
        }
    }
    
    private List<Ticket> consultarTicketsVaga(Vagas vaga) {
        List<Ticket> ticketsVaga = new ArrayList<>();
        for (Ticket ticket : instancias.getTicketsIns().tickets) {
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
        try {
            for (Vagas vaga : vagas) {
                if (vaga.getNumero() == numeroVaga && vaga.getStatus() != EnumVagaStatus.OCUPADA && vaga.getStatus() != EnumVagaStatus.ALUGADA_MENSAL) {
                    if(novoStatus == EnumVagaStatus.DISPONIVEL){
                        vaga.desocupar();
                        return true; /*True simbolizando que a disponibilidade da vaga foi alterada com sucesso*/  
                    }
                    vaga.indisponibilizar();
                    return true;
                }
            }
            throw new Exception("Vaga não encontrada.");
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao alterar disponibilidade da vaga: " + e.getMessage());
            return false;
        }
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
    public void estacionarVeiculoMensalista(Veiculo veiculo, int numeroVaga) {
        try {
            Vagas vaga = buscarVaga(numeroVaga);
            if (vaga != null && veiculo != null && (vaga.getStatus() == EnumVagaStatus.DISPONIVEL || vaga.getStatus() == EnumVagaStatus.ALUGADA_MENSAL) && veiculo.getTipo() == vaga.getTipoVeiculo()) {

                // Verifica se já existe um ticket mensalista válido para o veículo
                Ticket ticketExistente = buscarTicketMensalistaValido(veiculo);

                if (ticketExistente != null) {
                    // Ticket mensalista já existe e é válido, atualiza a vaga para ocupada
                    if (numeroVaga == ticketExistente.getVaga().getNumero()) {
                        vaga.ocupar();
                        instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso usando ticket mensalista existente!");
                    }
                    else{
                        throw new Exception("Não foi possivel estacionar usando um ticket mensalista afinal a vaga não corresponde à vaga previamente cadastrada para esse ticket!");
                    }
                } else {
                    // Cria um novo ticket mensalista
                    LocalDateTime inicio = LocalDateTime.now();
                    LocalDateTime fim = inicio.plusDays(30); // Fim do período de 30 dias
                    double valor = instancias.getTarifasIns().encontrarTarifaMensalista().getValorMensal(); // Valor único da tarifa mensalista
                    Ticket ticket = new TicketMensalista(inicio, fim, veiculo, valor, vaga, instancias.getTarifasIns().encontrarTarifaMensalista());
                    cadastrarTicket(ticket);
                    vaga.ocupar();
                    instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso com novo ticket mensalista!");
                }
            }else {
                throw new Exception("Vaga não correspondente ou indisponível.");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao estacionar veículo mensalista: " + e.getMessage());
        }
    }

    /*Método que estaciona um veículo na vaga específica e assim gerando um ticket novo com a inicio no dia atual e o fim nulo se a vaga não estiver ocupada e se o veículo seja compatível com a vaga*/
    public void estacionarVeiculo(Veiculo veiculo, int numeroVaga) {
        try{
            Vagas vaga = buscarVaga(numeroVaga);
            if (vaga != null && veiculo != null && vaga.getStatus() == EnumVagaStatus.DISPONIVEL && veiculo.getTipo() == vaga.getTipoVeiculo() && buscarTicketMensalistaValido(veiculo) == null) {
                vaga.ocupar();
                LocalDateTime inicio = LocalDateTime.now();
                Ticket ticket = new TicketHorista(inicio, null, veiculo, 0.0, vaga, instancias.getTarifasIns().encontrarTarifaHorista(inicio.getDayOfWeek()));
                cadastrarTicket(ticket);
                instancias.getInterface().exibirSucesso("Veículo estacionado com sucesso!");
            } else {
                throw new Exception("Vaga não correspondente ou indisponível.");
            }
        }catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao retirar veículo: " + e.getMessage());
        }
    }

    public void retirarVeiculo(int numeroVaga) {
        try {
            Vagas vaga = buscarVaga(numeroVaga);
            if (vaga != null && vaga.getStatus() == EnumVagaStatus.OCUPADA) {
                Ticket ticket = instancias.getTicketsIns().buscarTicketPorVaga(numeroVaga);
                if (ticket != null) {
                    ticket.setFim(LocalDateTime.now());
                    double valorTotal = instancias.getTicketsIns().calcularValorTicket(ticket);
                    if (valorTotal != -1.0) {
                        vaga.desocupar();
                        ticket.setValor(valorTotal);
                        ticket.setStatus(EnumStatus.FINALIZADO);
                        instancias.getInterface().exibirSucesso("Veículo retirado com sucesso! Valor total: " + valorTotal);
                    } else {
                        ticket.setFim(null);
                        throw new Exception("Erro ao calcular o valor do ticket.");
                    }
                } else {
                    throw new Exception("Nenhum ticket encontrado para a vaga especificada.");
                }
            } else {
                throw new Exception("Vaga não ocupada ou inexistente.");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao retirar veículo: " + e.getMessage());
        }
    }
    public void retirarVeiculoMensalista(int numeroVaga) {
        try{
            Vagas vaga = buscarVaga(numeroVaga);
            if (vaga != null && vaga.getStatus() == EnumVagaStatus.OCUPADA) {
                Ticket ticket = instancias.getTicketsIns().buscarTicketPorVaga(numeroVaga);
                if (ticket != null) {
                    if (ticket instanceof TicketMensalista) {
                        // Ticket mensalista, apenas libera a vaga sem encerrar o ticket
                        vaga.alugar();
                        instancias.getInterface().exibirSucesso("Veículo retirado com sucesso! Ticket mensalista ainda válido.");
                    }
                } else {
                    throw new Exception("Nenhum ticket encontrado para a vaga especificada.");
                }
            } else {
                throw new Exception("Vaga não ocupada ou inexistente.");
            }
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao retirar veículo: " + e.getMessage());
        }
    }
    
    public TicketMensalista buscarTicketMensalistaValido(Veiculo veiculo) {
        for (Ticket ticket : instancias.getTicketsIns().tickets) {
            if (ticket instanceof TicketMensalista && ticket.getVeiculo().equals(veiculo) && ticket.getFim().isAfter(LocalDateTime.now())) {
                return (TicketMensalista) ticket; // Retorna o ticket mensalista válido encontrado
            }
        }
        return null; // Retorna null se nenhum ticket mensalista válido for encontrado
    }
    
    public void cadastrarTicket(Ticket ticket) {
        instancias.getTicketsIns().tickets.add(ticket);
        instancias.getInterface().exibirMensagem("Ticket cadastrado com sucesso!");
    }



}
