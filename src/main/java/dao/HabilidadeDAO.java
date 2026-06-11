package dao;

import model.Habilidade;
import util.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabilidadeDAO {

    public void cadastrar(Habilidade hab) {
        String sql = "INSERT INTO habilidades (ficha_id, titulo, tipo, descricao) VALUES (?, ?, ?, ?)";
        try (Connection c = ConexaoDB.conectar(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, hab.getFichaId());
            stmt.setString(2, hab.getTitulo());
            stmt.setString(3, hab.getTipo());
            stmt.setString(4, hab.getDescricao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar habilidade: " + e.getMessage());
        }
    }

    public List<Habilidade> buscarPorFicha(int fichaId) {
        List<Habilidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM habilidades WHERE ficha_id = ? ORDER BY id DESC";
        try (Connection c = ConexaoDB.conectar(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, fichaId);

            // Garantindo que o ResultSet também é fechado
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Habilidade h = new Habilidade();
                    h.setId(rs.getInt("id"));
                    h.setFichaId(rs.getInt("ficha_id"));
                    h.setTitulo(rs.getString("titulo"));
                    h.setTipo(rs.getString("tipo"));
                    h.setDescricao(rs.getString("descricao"));
                    lista.add(h);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar habilidades: " + e.getMessage());
        }
        return lista;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM habilidades WHERE id = ? ";
        try (Connection c = ConexaoDB.conectar(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir habilidade: " + e.getMessage());
        }
    }

    // ATUALIZAR HABILIDADE (Modo Edição)
    public void atualizar(Habilidade hab) {
        String sql = "UPDATE habilidades SET titulo = ?, tipo = ?, descricao = ? WHERE id = ?";
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {
            stmt.setString(1, hab.getTitulo());
            stmt.setString(2, hab.getTipo());
            stmt.setString(3, hab.getDescricao());
            stmt.setInt(4, hab.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar habilidade: " + e.getMessage());
        }
    }
}