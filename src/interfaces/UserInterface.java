package interfaces;
/**
 *
 * @author Gustavo
 */
public interface UserInterface {
    void exibirMensagem(String mensagem);
    String solicitarEntrada(String mensagem);
    void exibirMenuPrincipal();
    int solicitarInt(String mensagem);
    void exibirErro(String erro);
    void exibirSucesso(String sucesso);
    double solicitarDouble(String mensagem);
    String solicitarEntradaMaior(String mensagem, String titulo, String[] opcoes, String opcaoPadrao);
    void exibirMensagemVaga (String numero, String rua, String status, String tipo, String mensagem);
}

