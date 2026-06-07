package controller;

import com.google.gson.Gson;
import dao.FichaDAO;
import model.Ficha;

import strategy.CalculadoraDeEvolucao;

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
            String usuarioIdParam = request.getParameter("usuarioId");
            String campanhaIdParam = request.getParameter("campanhaId");
            String idParam = request.getParameter("id"); // NOVO: Captura a busca por ID

            dao.FichaDAO dao = new dao.FichaDAO();
            List<model.Ficha> lista = new java.util.ArrayList<>();

            // Decide qual é o tipo de busca que o Front-end pediu
            if (idParam != null && !idParam.isEmpty()) {
                model.Ficha ficha = dao.buscarPorId(Integer.parseInt(idParam));
                if (ficha != null) lista.add(ficha);
            } else if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                lista = dao.buscarPorUsuario(Integer.parseInt(usuarioIdParam));
            } else if (campanhaIdParam != null && !campanhaIdParam.isEmpty()) {
                lista = dao.buscarPorCampanha(Integer.parseInt(campanhaIdParam));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            response.getWriter().write(new Gson().toJson(lista));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 1. Pega o JSON do front-end e transforma na Ficha
            Ficha novaFicha = new Gson().fromJson(request.getReader(), Ficha.class);

            // 2. CRAVANDO TODOS OS ATRIBUTOS EM 20 (Como planeado para esta fase)
            novaFicha.setCorpo(20); novaFicha.setSentidos(20); novaFicha.setMente(20); novaFicha.setSorte(20);
            novaFicha.setForca(20); novaFicha.setVelocidade(20); novaFicha.setDestreza(20); novaFicha.setVigor(20);
            novaFicha.setSabedoria(20); novaFicha.setInteligencia(20);

            // Cálculos automáticos baseados nos 20
            novaFicha.setVida((20 * 5) + (20 * 0.5f)); // Fica 110
            novaFicha.setSagrada(20 + (20 * 2)); // Fica 60
            novaFicha.setAmaldicoada(20 + (20 * 2)); // Fica 60
            novaFicha.setPesquisa(20);
            novaFicha.setConhecimento(20);
            novaFicha.setNivel(1);
            novaFicha.setExp(0);

            // 3. Manda o DAO salvar no banco
            FichaDAO dao = new FichaDAO();
            dao.cadastrar(novaFicha);

            // 4. Devolve o sinal de Sucesso pro JavaScript!
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\": \"Ficha criada com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Erro ao criar ficha.\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 1. Pega o JSON que veio do front-end e transforma num objeto Ficha
            Gson tradutor = new Gson();
            Ficha fichaRecebida = tradutor.fromJson(request.getReader(), Ficha.class);

            // 2. A MÁGICA DO STRATEGY: Recalcula o nível com base no XP que o jogador digitou!
            CalculadoraDeEvolucao calculadora = new CalculadoraDeEvolucao();
            int novoNivel = calculadora.subirDeNivel(fichaRecebida.getRaca(), fichaRecebida.getExp());
            fichaRecebida.setNivel(novoNivel);

            // 3. Manda o DAO atualizar o banco
            FichaDAO dao = new FichaDAO();
            boolean sucesso = dao.atualizarAtributos(fichaRecebida);

            if (sucesso) {
                response.getWriter().write("{\"mensagem\": \"Ficha atualizada e nível recalculado!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"erro\": \"Falha ao salvar no banco de dados.\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"erro\": \"Erro ao processar os dados.\"}");
            e.printStackTrace();
        }
    }
}