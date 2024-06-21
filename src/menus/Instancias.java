package menus;

import classes.*;
import funcoes.*;
import interfaces.*;
import menus.*;
/**
 *
 * @author Gustavo
 */
public class Instancias {
    private static FunCliente clienteIns;
    private static FunTarifas tarifasIns;
    private static FunTickets ticketsIns;
    private static FunVagas vagasIns;

    private static MenuPrincipal menuPrincipal;
    private static MenuClientes menuClientes;
    private static MenuVagas menuVagas;
    private static MenuEstacionamento menuEstacionamento;
    private static MenuFuncoesGerais menuFuncoesGerais;
    private static MenuVeiculos menuVeiculos;
    
    public static UserInterface Interface;
    
    public Instancias(UserInterface Interface) {
        inicializarFuncoes();
        inicializarMenus();
        Instancias.Interface = Interface;
    }

    private void inicializarFuncoes() {
        clienteIns = new FunCliente();
        tarifasIns = new FunTarifas();
        ticketsIns = new FunTickets();
        vagasIns = new FunVagas();

    }

    private void inicializarMenus() {
        menuClientes = new MenuClientes();
        menuVagas = new MenuVagas();
        menuEstacionamento = new MenuEstacionamento();
        menuFuncoesGerais = new MenuFuncoesGerais();
        menuVeiculos = new MenuVeiculos();

        menuPrincipal = new MenuPrincipal();
    }

    public void exibirMenuPrincipal() {
        menuPrincipal.exibir(Interface, this);
    }

    public MenuClientes getMenuClientes() {
        return menuClientes;
    }

    public MenuVagas getMenuVagas() {
        return menuVagas;
    }

    public MenuEstacionamento getMenuEstacionamento() {
        return menuEstacionamento;
    }

    public MenuFuncoesGerais getMenuFuncoesGerais() {
        return menuFuncoesGerais;
    }

    public MenuVeiculos getMenuVeiculos() {
        return menuVeiculos;
    }

    public FunCliente getClienteIns() {
        return clienteIns;
    }

    public FunTarifas getTarifasIns() {
        return tarifasIns;
    }

    public FunTickets getTicketsIns() {
        return ticketsIns;
    }

    public FunVagas getVagasIns() {
        return vagasIns;
    }

    public MenuPrincipal getMenuPrincipal() {
        return menuPrincipal;
    }

    public UserInterface getInterface() {
        return Interface;
    }
    
    
}
