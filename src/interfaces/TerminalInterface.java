package interfaces;

import java.util.InputMismatchException;
import java.util.Scanner;
import menus.Instancias;

/**
 * 
 * @author Gustavo
 */
public class TerminalInterface implements UserInterface {
    private final Instancias instancia;
    private final Scanner scanner;

    public TerminalInterface() {
        scanner = new Scanner(System.in);
        instancia = new Instancias(this);
        instancia.getPersistenciaIns().carregarDados("DadosEstacionamento.json");
        InicializacaoDados.inicializarClientes(instancia.getClienteIns());
        InicializacaoDados.inicializarTarifas(instancia.getTarifasIns());
        InicializacaoDados.inicializarVagas(instancia.getVagasIns());
        InicializacaoDados.inicializarVeiculos(instancia.getClienteIns());
        
    }

    @Override
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    @Override
    public String solicitarEntrada(String mensagem) {
        
        System.out.println(mensagem);
        scanner.next();
        return scanner.nextLine();
    }

    @Override
    public void exibirMenuPrincipal() {
        instancia.exibirMenuPrincipal();
    }
    
    @Override
    public int solicitarInt(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.println(mensagem);
                valor = scanner.nextInt();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }

        return valor;
    }
    
    @Override
    public void exibirErro (String erro){
        
        // Exibe a mensagem de erro no terminal
        System.err.println("Erro: " + erro);
    }
    
    @Override
    public void exibirSucesso(String sucesso){
        
        //Exibe a mensagem de sucesso no terminal
        System.out.println("Sucesso: " + sucesso);
    }
    
    @Override
    public double solicitarDouble(String mensagem) {
        double valor = 0.0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println(mensagem);
                valor = scanner.nextDouble();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.err.println("ENTRADA INVALIDA");
                scanner.next(); // Limpa o buffer do scanner
            }
        }
        return valor;
    }
    
    @Override
    public String solicitarEntradaMaior(String mensagem, String titulo, String[] opcoes, String opcaoPadrao) {
        // Exibir mensagem e título
        System.out.println(titulo);
        System.out.println(mensagem);

        // Exibir opções
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }

        // Solicitar entrada do usuário
        Scanner scanner = new Scanner(System.in);
        int escolha = -1;

        while (escolha < 1 || escolha > opcoes.length) {
            System.out.print("Escolha uma opção (1-" + opcoes.length + "): ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
                if (escolha < 1 || escolha > opcoes.length) {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.next(); // Limpa a entrada inválida
            }
        }

        // Retornar a opção escolhida
        return opcoes[escolha - 1];
    }
}
