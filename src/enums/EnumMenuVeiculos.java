package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuVeiculos {
    CADASTRAR_VEICULO(1, "Cadastrar Veiculo"),
    CONSULTAR_VEICULO(2, "Consultar Veiculo"),
    EXCLUIR_VEICULO(3, "Excluir Veiculo"),
    VOLTAR(4, "Voltar");
    
    private final int num;
    private final String opDesc;

    EnumMenuVeiculos(int numero, String opDesc) {
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

    public static EnumMenuVeiculos porNumero(int numero) {
        for (EnumMenuVeiculos opcao : EnumMenuVeiculos.values()) {
            if (opcao.getNum() == numero) {
                return opcao;
            }
        }
        throw new IllegalArgumentException("Opção inválida: " + numero);
    }
}
