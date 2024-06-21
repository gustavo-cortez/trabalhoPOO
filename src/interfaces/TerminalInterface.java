package interfaces;

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
}
