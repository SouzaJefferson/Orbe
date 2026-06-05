package dao;

import model.Inventario;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public void adicionarItem(Inventario item) {
        String sql = "INSERT INTO inventario (ficha_id, titulo_item, descricao, imagem) VALUES (?, ?, ?, ?)";

        // Protegendo a conexão com try-with-resources
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, item.getFichaId());
            stmt.setString(2, item.getTituloItem());
            stmt.setString(3, item.getDescricao());
            stmt.setString(4, item.getImagem());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item: " + e.getMessage());
        }
    }

    // EXCLUIR ITEM
    public void excluirItem(int id) {
        String sql = "DELETE FROM inventario WHERE id = ?";
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir item: " + e.getMessage());
        }
    }

    // ATUALIZAR ITEM
    public void atualizarItem(Inventario item) {
        String sql = "UPDATE inventario SET titulo_item = ?, descricao = ?, imagem = ? WHERE id = ?";
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {
            stmt.setString(1, item.getTituloItem());
            stmt.setString(2, item.getDescricao());
            stmt.setString(3, item.getImagem());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item: " + e.getMessage());
        }
    }

    public List<Inventario> buscarItensDaFicha(int fichaId) {
        List<Inventario> listaItens = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE ficha_id = ? ORDER BY id DESC";

        // Protegendo a conexão de busca
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement stmt = conecta.prepareStatement(sql)) {

            stmt.setInt(1, fichaId);

            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    Inventario item = new Inventario();
                    item.setId(resultado.getInt("id"));
                    item.setFichaId(resultado.getInt("ficha_id"));
                    item.setTituloItem(resultado.getString("titulo_item"));
                    item.setDescricao(resultado.getString("descricao"));
                    item.setImagem(resultado.getString("imagem"));

                    listaItens.add(item);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar inventário: " + e.getMessage());
        }

        return listaItens;
    }
}