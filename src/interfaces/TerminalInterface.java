package interfaces;

import java.util.InputMismatchException;
import java.util.Scanner;
import menus.Instancias;

/**
 * 
 * @author Gustavo
 */
public class TerminalInterface implements UserInterface {
    private final Instancias instancia = new Instancias(this);
    private final Scanner scanner;

    public TerminalInterface() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    @Override
    public String solicitarEntrada(String mensagem) {
        System.out.println(mensagem);
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
    
    @Override//CORRIGIR **************************
     public String solicitarEntradaMaior(String mensagem, String[] opcoes, String opcaoPadrao) {
        // Imprime a mensagem para o usuário
        System.out.println(mensagem);

        // Imprime as opções disponíveis
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }

        // Solicita que o usuário digite a opção desejada
        System.out.print("Escolha uma opção (1-" + opcoes.length + ", default: " + opcaoPadrao + "): ");
        String escolha = scanner.nextLine().trim();

        // Verifica se a entrada do usuário é vazia (usa a opção padrão) ou uma escolha válida
        int indiceEscolhido;
        try {
            indiceEscolhido = Integer.parseInt(escolha) - 1;
            if (indiceEscolhido < 0 || indiceEscolhido >= opcoes.length) {
                indiceEscolhido = Integer.parseInt(opcaoPadrao) - 1;
            }
        } catch (NumberFormatException e) {
            indiceEscolhido = Integer.parseInt(opcaoPadrao) - 1;
        }

        // Retorna a opção selecionada pelo usuário
        return opcoes[indiceEscolhido];
    }
     
    @Override
    public void exibirMensagemVaga (String numero, String rua, String status, String tipo, String mensagem){
        // Concatena todas as informações em uma única string formatada
        String mensagemCompleta = String.format("Número: %s\nRua: %s\nStatus: %s\nTipo: %s\nMensagem: %s", numero, rua, status, tipo, mensagem);
        
        // Exibe a mensagem no terminal
        System.out.println(mensagemCompleta);
    }
}
