package controller;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import model.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            // O Front-end manda um JSON com email e senha
            Usuario credenciais = new Gson().fromJson(request.getReader(), Usuario.class);
            UsuarioDAO dao = new UsuarioDAO();

            // O DAO verifica no MariaDB
            Usuario usuarioLogado = dao.autenticar(credenciais.getEmail(), credenciais.getSenha());

            if (usuarioLogado != null) {
                // Por segurança, apagamos a senha antes de devolver os dados para o Front-end
                usuarioLogado.setSenha(null);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(new Gson().toJson(usuarioLogado));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"erro\": \"E-mail ou senha incorretos!\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro no servidor.\"}");
        }
    }
}