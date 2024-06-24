package interfaces;

import java.util.List;

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
    <T> int exibirMenus(String mensagem, List<T> opcoes);
}

