package controller;

import com.google.gson.Gson;
import dao.CampanhaDAO;
import model.Campanha;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/campanhas")
public class CampanhaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String codigo = request.getParameter("codigo");
        String mestreId = request.getParameter("mestreId");
        CampanhaDAO dao = new CampanhaDAO();

        // Se passar código, verifica para o Jogador. Se passar mestreId, lista as campanhas do Mestre!
        if (codigo != null) {
            Campanha c = dao.buscarPorCodigo(codigo);
            if (c != null) response.getWriter().write(new Gson().toJson(c));
            else { response.setStatus(404); response.getWriter().write("{\"erro\": \"Código inválido.\"}"); }

        } else if (mestreId != null) {
            List<Campanha> campanhas = dao.buscarPorMestre(Integer.parseInt(mestreId));
            response.getWriter().write(new Gson().toJson(campanhas));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            Campanha nova = new Gson().fromJson(request.getReader(), Campanha.class);
            if (new CampanhaDAO().criarCampanha(nova)) {
                response.setStatus(201);
                response.getWriter().write("{\"mensagem\": \"Campanha Forjada!\"}");
            } else {
                response.setStatus(400); // Falha se o código já existir no banco (é UNIQUE)
                response.getWriter().write("{\"erro\": \"Este Código Secreto já está em uso!\"}");
            }
        } catch (Exception e) { response.setStatus(500); }
    }
}