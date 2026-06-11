package controller;

import com.google.gson.Gson;
import dao.HabilidadeDAO;
import model.Habilidade;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/habilidades")
public class HabilidadeServlet extends HttpServlet {

    // CARREGAR (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int fichaId = Integer.parseInt(request.getParameter("fichaId"));
        response.getWriter().write(new Gson().toJson(new HabilidadeDAO().buscarPorFicha(fichaId)));
    }

    // CADASTRAR (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Habilidade hab = new Gson().fromJson(request.getReader(), Habilidade.class);
        new HabilidadeDAO().cadastrar(hab);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            Habilidade habEditada = new Gson().fromJson(request.getReader(), Habilidade.class);
            HabilidadeDAO dao = new HabilidadeDAO();
            dao.atualizar(habEditada);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\": \"Habilidade editada com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro ao editar habilidade.\"}");
        }
    }

    // EXCLUIR (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new HabilidadeDAO().excluir(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}