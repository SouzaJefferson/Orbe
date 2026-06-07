package dao;

import model.Ficha;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FichaDAO {

    public void cadastrar(Ficha ficha) {
        // Adicionamos o campanha_id no SQL
        String sql = "INSERT INTO fichas (usuario_id, campanha_id, nome_personagem, estilos, corpo, sentidos, mente, sorte, forca, velocidade, destreza, vigor, sabedoria, inteligencia, vida, sagrada, amaldicoada, pesquisa, conhecimento, nivel, exp, raca) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, ficha.getUsuarioId());

            // Lógica para permitir Fichas sem campanha (nulo)
            if (ficha.getCampanhaId() != null) {
                stmt.setInt(2, ficha.getCampanhaId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setString(3, ficha.getNomePersonagem());
            stmt.setString(4, ficha.getEstilos());
            // ... os índices dos próximos stmts agora vão do 5 ao 22. Ajuste a numeração sequencial deles:
            stmt.setInt(5, ficha.getCorpo());
            stmt.setInt(6, ficha.getSentidos());
            stmt.setInt(7, ficha.getMente());
            stmt.setInt(8, ficha.getSorte());
            stmt.setInt(9, ficha.getForca());
            stmt.setInt(10, ficha.getVelocidade());
            stmt.setInt(11, ficha.getDestreza());
            stmt.setInt(12, ficha.getVigor());
            stmt.setInt(13, ficha.getSabedoria());
            stmt.setInt(14, ficha.getInteligencia());
            stmt.setFloat(15, ficha.getVida());
            stmt.setInt(16, ficha.getSagrada());
            stmt.setInt(17, ficha.getAmaldicoada());
            stmt.setInt(18, ficha.getPesquisa());
            stmt.setInt(19, ficha.getConhecimento());
            stmt.setInt(20, ficha.getNivel());
            stmt.setInt(21, ficha.getExp());
            stmt.setString(22, ficha.getRaca());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar ficha: " + e.getMessage());
        }
    }

    public List<Ficha> buscarPorUsuario(int usuarioId) {
        List<Ficha> listaFichas = new ArrayList<>();
        String sql = "SELECT * FROM fichas WHERE usuario_id = ?";

        // Protegendo a conexão de busca
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setId(resultado.getInt("id"));
                    ficha.setUsuarioId(resultado.getInt("usuario_id"));
                    ficha.setNomePersonagem(resultado.getString("nome_personagem"));
                    ficha.setEstilos(resultado.getString("estilos"));
                    ficha.setCorpo(resultado.getInt("corpo"));
                    ficha.setSentidos(resultado.getInt("sentidos"));
                    ficha.setMente(resultado.getInt("mente"));
                    ficha.setSorte(resultado.getInt("sorte"));
                    ficha.setForca(resultado.getInt("forca"));
                    ficha.setVelocidade(resultado.getInt("velocidade"));
                    ficha.setDestreza(resultado.getInt("destreza"));
                    ficha.setVigor(resultado.getInt("vigor"));
                    ficha.setSabedoria(resultado.getInt("sabedoria"));
                    ficha.setInteligencia(resultado.getInt("inteligencia"));
                    ficha.setVida(resultado.getFloat("vida"));
                    ficha.setSagrada(resultado.getInt("sagrada"));
                    ficha.setAmaldicoada(resultado.getInt("amaldicoada"));
                    ficha.setPesquisa(resultado.getInt("pesquisa"));
                    ficha.setConhecimento(resultado.getInt("conhecimento"));
                    ficha.setNivel(resultado.getInt("nivel"));
                    ficha.setExp(resultado.getInt("exp"));
                    ficha.setRaca(resultado.getString("raca"));

                    listaFichas.add(ficha);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fichas: " + e.getMessage());
        }

        return listaFichas;
    }

    public boolean atualizarAtributos(Ficha ficha) {
        String sql = "UPDATE fichas SET forca=?, velocidade=?, destreza=?, vigor=?, sabedoria=?, inteligencia=?, exp=?, nivel=? WHERE id=?";

        // Protegendo a conexão de atualização
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, ficha.getForca());
            stmt.setInt(2, ficha.getVelocidade());
            stmt.setInt(3, ficha.getDestreza());
            stmt.setInt(4, ficha.getVigor());
            stmt.setInt(5, ficha.getSabedoria());
            stmt.setInt(6, ficha.getInteligencia());
            stmt.setInt(7, ficha.getExp());
            stmt.setInt(8, ficha.getNivel());
            stmt.setInt(9, ficha.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ficha: " + e.getMessage());
            return false;
        }
    }



    // Puxa as fichas dos jogadores que usaram o código da campanha
    public List<Ficha> buscarPorCampanha(int campanhaId) {
        List<Ficha> listaFichas = new ArrayList<>();
        String sql = "SELECT * FROM fichas WHERE campanha_id = ?";
        try (Connection conecta = ConexaoDB.conectar(); PreparedStatement stmt = conecta.prepareStatement(sql)) {
            stmt.setInt(1, campanhaId);
            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setId(resultado.getInt("id"));
                    ficha.setNomePersonagem(resultado.getString("nome_personagem"));
                    ficha.setEstilos(resultado.getString("estilos"));
                    ficha.setRaca(resultado.getString("raca"));
                    ficha.setNivel(resultado.getInt("nivel"));
                    ficha.setExp(resultado.getInt("exp"));
                    listaFichas.add(ficha);
                }
            }
        } catch (SQLException e) { }
        return listaFichas;
    }

    // NOVO: Busca uma ficha específica diretamente pelo ID dela (Para o Mestre usar)
    public Ficha buscarPorId(int id) {
        String sql = "SELECT * FROM fichas WHERE id = ?";
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    Ficha ficha = new Ficha();
                    ficha.setId(resultado.getInt("id"));
                    ficha.setUsuarioId(resultado.getInt("usuario_id"));
                    ficha.setNomePersonagem(resultado.getString("nome_personagem"));
                    ficha.setEstilos(resultado.getString("estilos"));
                    ficha.setCorpo(resultado.getInt("corpo"));
                    ficha.setSentidos(resultado.getInt("sentidos"));
                    ficha.setMente(resultado.getInt("mente"));
                    ficha.setSorte(resultado.getInt("sorte"));
                    ficha.setForca(resultado.getInt("forca"));
                    ficha.setVelocidade(resultado.getInt("velocidade"));
                    ficha.setDestreza(resultado.getInt("destreza"));
                    ficha.setVigor(resultado.getInt("vigor"));
                    ficha.setSabedoria(resultado.getInt("sabedoria"));
                    ficha.setInteligencia(resultado.getInt("inteligencia"));
                    ficha.setVida(resultado.getFloat("vida"));
                    ficha.setSagrada(resultado.getInt("sagrada"));
                    ficha.setAmaldicoada(resultado.getInt("amaldicoada"));
                    ficha.setPesquisa(resultado.getInt("pesquisa"));
                    ficha.setConhecimento(resultado.getInt("conhecimento"));
                    ficha.setNivel(resultado.getInt("nivel"));
                    ficha.setExp(resultado.getInt("exp"));
                    ficha.setRaca(resultado.getString("raca"));
                    return ficha;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ficha por ID: " + e.getMessage());
        }
        return null;
    }
}