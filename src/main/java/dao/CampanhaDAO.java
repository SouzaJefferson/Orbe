package dao;

import model.Campanha;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CampanhaDAO {

    // Cria uma nova campanha (Para o Mestre usar depois)
    public boolean criarCampanha(Campanha c) {
        String sql = "INSERT INTO campanhas (mestre_id, nome, codigo) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getMestreId());
            stmt.setString(2, c.getNome());
            stmt.setString(3, c.getCodigo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao criar campanha: " + e.getMessage());
            return false;
        }
    }

    // Verifica se um código existe quando o jogador digita no Pop-up
    public Campanha buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM campanhas WHERE codigo = ?";
        try (Connection conn = ConexaoDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Campanha c = new Campanha();
                    c.setId(rs.getInt("id"));
                    c.setMestreId(rs.getInt("mestre_id"));
                    c.setNome(rs.getString("nome"));
                    c.setCodigo(rs.getString("codigo"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar código: " + e.getMessage());
        }
        return null; // Retorna nulo se o código for inválido
    }

    // Busca todas as campanhas que um Mestre criou
    public java.util.List<model.Campanha> buscarPorMestre(int mestreId) {
        java.util.List<model.Campanha> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM campanhas WHERE mestre_id = ?";
        try (Connection conn = ConexaoDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mestreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.Campanha c = new model.Campanha();
                    c.setId(rs.getInt("id"));
                    c.setMestreId(rs.getInt("mestre_id"));
                    c.setNome(rs.getString("nome"));
                    c.setCodigo(rs.getString("codigo"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
        }
        return lista;
    }
}