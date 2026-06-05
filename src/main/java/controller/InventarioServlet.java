package controller;

import com.google.gson.Gson;
import dao.InventarioDAO;
import model.Inventario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// A rota na internet será /api/inventario
@WebServlet("/api/inventario")
public class InventarioServlet extends HttpServlet {


    // ---------------------------------    GET (envia os itens)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 1. Captura o parâmetro da URL (ex: ?fichaId=1)
            String fichaIdParam = request.getParameter("fichaId");

            if (fichaIdParam == null || fichaIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\": \"É necessário informar o fichaId na URL.\"}");
                return;
            }

            int fichaId = Integer.parseInt(fichaIdParam);

            // 2. Aciona o DAO para buscar os itens pendurados nesta ficha
            InventarioDAO dao = new InventarioDAO();
            List<Inventario> itens = dao.buscarItensDaFicha(fichaId);

            // 3. Traduz para JSON e envia
            Gson tradutor = new Gson();
            String json = tradutor.toJson(itens);
            response.getWriter().write(json);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro interno ao buscar o inventário.\"}");
            e.printStackTrace();
        }
    }
// ---------------------------------    POST (carrega os itens)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            Gson tradutor = new Gson();
            // Lê o JSON enviado pelo JavaScript e transforma num objeto Inventario
            Inventario novoItem = tradutor.fromJson(request.getReader(), Inventario.class);

            InventarioDAO dao = new InventarioDAO();
            dao.adicionarItem(novoItem); // Guarda no banco!

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\": \"Item criado com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro ao criar item.\"}");
        }
    }


    // ATUALIZAR ITEM NO INVENTÁRIO (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            Inventario itemAtualizado = new Gson().fromJson(request.getReader(), Inventario.class);
            InventarioDAO dao = new InventarioDAO();
            dao.atualizarItem(itemAtualizado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\": \"Item atualizado com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro ao atualizar item.\"}");
        }
    }

    // EXCLUIR ITEM DO INVENTÁRIO (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Pega o ID que vem na URL (ex: ?id=5)
            int id = Integer.parseInt(request.getParameter("id"));
            InventarioDAO dao = new InventarioDAO();
            dao.excluirItem(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\": \"Item excluído com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro ao excluir item.\"}");
        }
    }
}