package controller;

import com.google.gson.Gson;
import dao.FichaDAO;
import model.Ficha;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// A rota na internet será /api/fichas
@WebServlet("/api/fichas")
public class FichaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 1. Captura o parâmetro da URL (ex: ?usuarioId=1)
            String usuarioIdParam = request.getParameter("usuarioId");

            // Se o front-end esquecer de mandar o ID, devolvemos um erro avisando
            if (usuarioIdParam == null || usuarioIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\": \"É necessário informar o usuarioId na URL.\"}");
                return;
            }

            // Converte o texto da URL para número inteiro
            int usuarioId = Integer.parseInt(usuarioIdParam);

            // 2. Aciona o DAO para buscar as fichas desse utilizador específico
            FichaDAO dao = new FichaDAO();
            List<Ficha> fichasDoUsuario = dao.buscarPorUsuario(usuarioId);

            // 3. Traduz para JSON e envia para a tela
            Gson tradutor = new Gson();
            String json = tradutor.toJson(fichasDoUsuario);
            response.getWriter().write(json);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro interno ao buscar as fichas.\"}");
            e.printStackTrace();
        }
    }
}