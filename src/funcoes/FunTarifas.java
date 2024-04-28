package funcoes;
import classes.DiaSemana;
import classes.Tarifa;
import classes.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Gustavo
 */
public class FunTarifas {
    
    public List<Tarifa> tarifas;
    
    public FunTarifas() {
        this.tarifas = new ArrayList<>();
    }
    
    /*Método para cadastrar uma nova tarifa, é nescessário para cadastrar a tarifa informar os dias da semana 
    atráves de número do ENUM: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC..., numero abaixo de 1 e maior que 7 para a leitura dos dias*/;
    public void cadastrarTarifa(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente) {
        Scanner scanner = new Scanner(System.in);
        List<DiaSemana> diasSemana = new ArrayList<>();
        /*Solicitar os dias da semana ao usuário atráves de números do ENUM*/
        int dia;
        System.out.println("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC...):");
        dia = scanner.nextInt();
        while(dia <= 7 && dia > 0){
            DiaSemana diaSemana = DiaSemana.getByOpcao(dia); /*Obtém o enum correspondente ao dia informado*/
            diasSemana.add(diaSemana); /*Adiciona o dia à lista*/
            System.out.println("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC...):");
            dia = scanner.nextInt();
        }
        Tarifa tarifa = new Tarifa(inicio, valorPrimeiraHora, valorHoraSubsequente, diasSemana.toArray(new DiaSemana[0]));
        tarifas.add(tarifa);
    }
    
    /*Métodos para gerenciar tarifas de acordo com períodos específicos*/
    public void cadastrarTarifaPeriodo(Tarifa tarifa) {
        tarifas.add(tarifa);
    }
    
    /*Método para listar todas as tarifas*/
    public void listarTarifas() {
        for (Tarifa tarifa : tarifas) {
            System.out.println("Inicio: " + tarifa.getInicio() + ", Valor primeira hora: " + tarifa.getValorPrimeiraHora() + ", Valor hora subsequente: " + tarifa.getValorHoraSubsequente() + ", Dias da semana: ");
            /*Itera sobre os dias da semana da tarifa para mostrar todos os dias que a tarifa opera*/
            for (DiaSemana dia : tarifa.getDiasSemana()) {
                System.out.print(dia.getDescricao() + " ");
            }
            System.out.println(" ");
        }
    }
    /*Métodos para buscar tarifas de acordo com os períodos específicos*/
    public Tarifa buscarTarifaPorPeriodo(LocalDate inicioPeriodo, LocalDate fimPeriodo) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getInicio().equals(inicioPeriodo) && tarifa.getInicio().equals(fimPeriodo)) {
                return tarifa;
            }
        }
        return null; /*Tarifa não encontrada para o período especificado*/
    }
    /*Método para consultar o faturamento em um período específico, buscando todos tickets que 
    foram gerados nesse meio tempo e fazendo a soma dos valores*/
    public double consultarFaturamentoPeriodo(LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo, FunTarifas tarifaIns, FunTickets ticketsIns) {
        double faturamento = 0;
        for (Ticket ticket : ticketsIns.tickets) {
            LocalDateTime fimTicket = ticket.getFim();
            if (fimTicket.isAfter(inicioPeriodo) && fimTicket.isBefore(fimPeriodo) && fimTicket != null) {
                double valorTicket = ticket.getValor();
                faturamento += valorTicket;
            }
        }
        return faturamento;
    }

    
}
