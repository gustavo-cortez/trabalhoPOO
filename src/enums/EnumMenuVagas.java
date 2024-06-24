package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuVagas {
    CADASTRAR_VAGA(1, "Cadastrar Vaga"),
    CONSULTAR_VAGA(2, "Consultar Vaga por Numero"),
    EXCLUIR_VAGA(3, "Excluir Vaga"),
    EDITAR_VAGA(4, "Editar Vaga"),
    ALTERAR_DISPONIBILIDADE(5, "Alterar Disponibilidade da Vaga"),
    VOLTAR(6, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuVagas(int numero, String opDesc) {
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
}
