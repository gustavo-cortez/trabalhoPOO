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
    private static FunPersistenciaDados persistenciaIns;

    private static MenuPrincipal menuPrincipal;
    private static MenuClientes menuClientes;
    private static MenuVagas menuVagas;
    private static MenuEstacionamento menuEstacionamento;
    private static MenuFuncoesGerais menuFuncoesGerais;
    private static MenuVeiculos menuVeiculos;
    
    public static UserInterface Interface;
    private static Instancias instance;
    
    public Instancias(UserInterface Interface) {
        Instancias.Interface = Interface;
        inicializarFuncoes();
        inicializarMenus();
    }

    private void inicializarFuncoes() {
        clienteIns = new FunCliente(this);
        tarifasIns = new FunTarifas(this);
        ticketsIns = new FunTickets(this);
        vagasIns = new FunVagas(this);
        persistenciaIns = new FunPersistenciaDados(this);

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

    public FunPersistenciaDados getPersistenciaIns() {
        return persistenciaIns;
    }

    public MenuPrincipal getMenuPrincipal() {
        return menuPrincipal;
    }

    public UserInterface getInterface() {
        return Interface;
    }
    
    
}
