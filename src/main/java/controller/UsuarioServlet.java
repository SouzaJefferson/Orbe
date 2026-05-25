package controller;
import dao.UsuarioDAO;
import model.Usuario;
import com.google.gson.Gson; // Importação da biblioteca do Google

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


// Esta anotação define a URL (o endereço) que o seu front-end vai chamar
@WebServlet("/api/usuarios")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1. Configurando a resposta para o formato da Web (JSON e UTF-8)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 2. Acionando o seu DAO para buscar os dados no MariaDB
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> listaDeUsuarios = dao.buscarTodos();

            // 3. Traduzindo a Lista do Java para o formato JSON
            Gson tradutor = new Gson();
            String json = tradutor.toJson(listaDeUsuarios);

            // 4. Enviando o JSON pronto de volta para quem pediu (o navegador)
            response.getWriter().write(json);

        } catch (Exception e) {
            // 5. Tratamento de erro elegante: se o banco cair, devolvemos um erro 500 em JSON


            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro interno ao buscar usuários do Sistema Orbe.\"}");
        }
    }
}