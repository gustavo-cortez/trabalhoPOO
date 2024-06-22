package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuEstacionamento {
    ESTACIONAR(1, "Estacionar veiculo"),
    RETIRAR(2, "Retirar Veiculo"),
    LISTAR_VAGAS_DISPONIVEIS(3, "Listar vaagas disponiveis"),
    lISTAR_VAGAS_ALUGADAS(4, "Listar vagas alugadas"),
    GERENCIAR_TARIFAS(5, "Gerenciar tarifas"),
    VOLTAR(6, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuEstacionamento(int numero, String opDesc) {
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

    public static EnumMenuEstacionamento porNumero(int numero) {
        for (EnumMenuEstacionamento opcao : EnumMenuEstacionamento.values()) {
            if (opcao.getNum() == numero) {
                return opcao;
            }
        }
        throw new IllegalArgumentException("Opção inválida: " + numero);
    }
}
