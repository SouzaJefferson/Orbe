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

        try {
            Connection conecta = ConexaoDB.conectar();
            PreparedStatement stmt = conecta.prepareStatement(sql);

            stmt.setInt(1, item.getFichaId());
            stmt.setString(2, item.getTituloItem());
            stmt.setString(3, item.getDescricao());
            stmt.setString(4, item.getImagem());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item no inventário: " + e.getMessage());
        }
    }

    public List<Inventario> buscarItensDaFicha(int fichaId) {
        List<Inventario> listaItens = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE ficha_id = ?";

        try {
            Connection conecta = ConexaoDB.conectar();
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, fichaId);

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Inventario item = new Inventario();
                item.setId(resultado.getInt("id"));
                item.setFichaId(resultado.getInt("ficha_id"));
                item.setTituloItem(resultado.getString("titulo_item"));
                item.setDescricao(resultado.getString("descricao"));
                item.setImagem(resultado.getString("imagem"));

                listaItens.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens do inventário: " + e.getMessage());
        }
        return listaItens;
    }
}