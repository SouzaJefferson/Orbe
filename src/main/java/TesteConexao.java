import dao.FichaDAO;
import dao.InventarioDAO;
import dao.UsuarioDAO;
import model.Ficha;
import model.Inventario;
import model.Usuario;
import strategy.CalculadoraDeEvolucao;

import java.util.List;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO O GRANDE TESTE DO SISTEMA ORBE ===");

        try {
            // ---------------------------------------------------------
            // 1. TESTE: CADASTRAR UTILIZADOR
            // ---------------------------------------------------------
            System.out.println("\n1. A cadastrar Utilizador...");
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario novoUsuario = new Usuario();
            novoUsuario.setUsername("MestreDosMagos");
            novoUsuario.setEmail("mestre@cavernadodragao.com");
            novoUsuario.setSenha("segredo123");

            usuarioDAO.cadastrar(novoUsuario);

            // Vamos descobrir qual foi o ID que o MariaDB gerou para este utilizador!
            List<Usuario> todosUsuarios = usuarioDAO.buscarTodos();
            Usuario usuarioInserido = todosUsuarios.get(todosUsuarios.size() - 1); // Pega o último da lista
            int idUsuarioReal = usuarioInserido.getId();

            System.out.println("[OK] Utilizador gravado! O MariaDB deu-lhe o ID: " + idUsuarioReal);


            // ---------------------------------------------------------
            // 2. TESTE: PADRÃO STRATEGY (CÁLCULO DE EVOLUÇÃO)
            // ---------------------------------------------------------
            System.out.println("\n2. A testar o Padrão Strategy...");
            CalculadoraDeEvolucao calculadora = new CalculadoraDeEvolucao();
            String racaEscolhida = "Elfo";
            int xpGanhoNaSessao = 3600;

            int nivelCalculado = calculadora.subirDeNivel(racaEscolhida, xpGanhoNaSessao);
            System.out.println("[OK] Nível calculado para o Elfo com 3600 XP: Nível " + nivelCalculado);


            // ---------------------------------------------------------
            // 3. TESTE: CADASTRAR FICHA (Ligada ao Utilizador Real)
            // ---------------------------------------------------------
            System.out.println("\n3. A cadastrar Ficha do Personagem...");
            FichaDAO fichaDAO = new FichaDAO();
            Ficha novaFicha = new Ficha();

            novaFicha.setUsuarioId(idUsuarioReal); // Usa o ID verdadeiro que fomos buscar acima!
            novaFicha.setNomePersonagem("Legolas");
            novaFicha.setEstilos("Arqueiro");
            novaFicha.setRaca(racaEscolhida);
            novaFicha.setExp(xpGanhoNaSessao);
            novaFicha.setNivel(nivelCalculado);

            novaFicha.setSentidos(20);
            novaFicha.setDestreza(18);
            novaFicha.setCorpo(10);

            fichaDAO.cadastrar(novaFicha);

            // Vamos descobrir qual foi o ID que o MariaDB gerou para esta Ficha!
            List<Ficha> fichasDoUsuario = fichaDAO.buscarPorUsuario(idUsuarioReal);
            Ficha fichaInserida = fichasDoUsuario.get(fichasDoUsuario.size() - 1);
            int idFichaReal = fichaInserida.getId();

            System.out.println("[OK] Ficha gravada! O MariaDB deu-lhe o ID: " + idFichaReal);


            // ---------------------------------------------------------
            // 4. TESTE BÓNUS: INVENTÁRIO (Ligado à Ficha Real)
            // ---------------------------------------------------------
            System.out.println("\n4. A adicionar item ao Inventário...");
            InventarioDAO inventarioDAO = new InventarioDAO();
            Inventario arco = new Inventario();

            arco.setFichaId(idFichaReal); // Usa o ID verdadeiro da ficha!
            arco.setTituloItem("Arco Élfico de Lothlórien");
            arco.setDescricao("Um arco longo que nunca erra o alvo.");
            arco.setImagem("arco_elfico.png");

            inventarioDAO.adicionarItem(arco);
            System.out.println("[OK] Item adicionado à ficha com sucesso!");

            System.out.println("\n=== TODOS OS TESTES PASSARAM COM SUCESSO! ===");

        } catch (Exception e) { // Aqui está o catch que estava faltando!
            System.out.println("\n[ERRO] O teste falhou: " + e.getMessage());
            e.printStackTrace();
        }
    }
} // E aqui está a chave final do arquivo!