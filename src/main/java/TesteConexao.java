import dao.FichaDAO;
import model.Ficha;
import java.util.List;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("Iniciando o teste da Ficha...");

        // 1. Criar uma Ficha fictícia para teste
        Ficha novaFicha = new Ficha();

        // ATENÇÃO: Este ID precisa existir na tabela 'usuarios' do seu MariaDB!
        // Se o MagoImplacavel for o ID 1, usamos o 1.
        novaFicha.setUsuarioId(1);

        novaFicha.setNomePersonagem("Elara, a Patrulheira");
        novaFicha.setEstilos("Arco e Flecha");

        // Preenchendo alguns atributos (os outros vão como 0 por padrão)
        novaFicha.setCorpo(12);
        novaFicha.setSentidos(15);
        novaFicha.setMente(10);

        // Os novos atributos de evolução
        novaFicha.setNivel(1);
        novaFicha.setExp(0);
        novaFicha.setRaca("Elfo");

        // 2. Chamar o DAO
        FichaDAO dao = new FichaDAO();

        try {
            // Teste A: Tentar gravar a ficha no banco
            System.out.println("A gravar a ficha no MariaDB...");
            dao.cadastrar(novaFicha);
            System.out.println("Ficha gravada com sucesso!");

            // Teste B: Tentar ler as fichas desse utilizador
            System.out.println("\nA buscar fichas do utilizador ID 1...");
            List<Ficha> fichasEncontradas = dao.buscarPorUsuario(1);

            for (Ficha f : fichasEncontradas) {
                System.out.println("-> Personagem: " + f.getNomePersonagem() +
                        " | Raça: " + f.getRaca() +
                        " | Sentidos: " + f.getSentidos());
            }

        } catch (Exception e) {
            System.out.println("Deu algum erro: " + e.getMessage());
        }
    }
}