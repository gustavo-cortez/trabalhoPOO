package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuClientes {
    CADASTRAR_CLIENTE(1, "Cadastrar cliente"),
    CONSULTAR_CLIENTE(2, "Consultar cliente por documento"),
    EXCLUIR_CLIENTE(3, "Excluir cliente"),
    EDITAR_CLIENTE(4, "Editar cliente"),
    GERENCIAR_VEICULOS(5, "Gerenciar veículos"),
    LISTAR_CADASTROS(6, "Listar todos os cadastros"),
    VOLTAR(7, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuClientes(int numero, String opDesc) {
        this.num = numero;
        this.opDesc = opDesc;
    }

    public int getNum() {
        return num;
    }

    public String getOpcao() {
        return opDesc;
    }

    @Override
    public String toString() {
        return num + " - " + opDesc;
    }

    public static EnumMenuClientes porNumero(int numero) {
        for (EnumMenuClientes opcao : EnumMenuClientes.values()) {
            if (opcao.getNum() == numero) {
                return opcao;
            }
        }
        throw new IllegalArgumentException("Opção inválida: " + numero);
    }
}
