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

    // Método para Salvar a Ficha no Banco
    public void cadastrar(Ficha ficha) {
        // SQL gigante com 21 pontos de interrogação (um para cada atributo além do ID)
        String sql = "INSERT INTO fichas (usuario_id, nome_personagem, estilos, corpo, sentidos, mente, sorte, forca, velocidade, destreza, vigor, sabedoria, inteligencia, vida, sagrada, amaldicoada, pesquisa, conhecimento, nivel, exp, raca) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conecta = ConexaoDB.conectar();
            PreparedStatement stmt = conecta.prepareStatement(sql);

            stmt.setInt(1, ficha.getUsuarioId());
            stmt.setString(2, ficha.getNomePersonagem());
            stmt.setString(3, ficha.getEstilos());
            stmt.setInt(4, ficha.getCorpo());
            stmt.setInt(5, ficha.getSentidos());
            stmt.setInt(6, ficha.getMente());
            stmt.setInt(7, ficha.getSorte());
            stmt.setInt(8, ficha.getForca());
            stmt.setInt(9, ficha.getVelocidade());
            stmt.setInt(10, ficha.getDestreza());
            stmt.setInt(11, ficha.getVigor());
            stmt.setInt(12, ficha.getSabedoria());
            stmt.setInt(13, ficha.getInteligencia());
            stmt.setFloat(14, ficha.getVida());
            stmt.setInt(15, ficha.getSagrada());
            stmt.setInt(16, ficha.getAmaldicoada());
            stmt.setInt(17, ficha.getPesquisa());
            stmt.setInt(18, ficha.getConhecimento());

            // Novos campos do sistema de evolução
            stmt.setInt(19, ficha.getNivel());
            stmt.setInt(20, ficha.getExp());
            stmt.setString(21, ficha.getRaca());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar ficha: " + e.getMessage());
        }
    }

    // Método para buscar apenas as fichas de um jogador específico
    public List<Ficha> buscarPorUsuario(int usuarioId) {
        List<Ficha> listaFichas = new ArrayList<>();
        String sql = "SELECT * FROM fichas WHERE usuario_id = ?"; // O WHERE filtra pelo dono da ficha

        try {
            Connection conecta = ConexaoDB.conectar();
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, usuarioId); // Trocamos o ? pelo ID do usuário que queremos buscar

            ResultSet resultado = stmt.executeQuery();

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

                // Novos campos
                ficha.setNivel(resultado.getInt("nivel"));
                ficha.setExp(resultado.getInt("exp"));
                ficha.setRaca(resultado.getString("raca"));

                listaFichas.add(ficha);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fichas: " + e.getMessage());
        }
        return listaFichas;
    }
}