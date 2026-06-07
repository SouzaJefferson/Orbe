package dao; // Definimos um pacote para organizar

import model.Usuario; //pega o usuario
import util.ConexaoDB; //pega  a conexão do banco

//tratamento e metodos sql
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // 1. CADASTRAR (Mudou de void para boolean!)
    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, email, senha, tipo) VALUES (? ,? , ?, ?)";

        // O jeito CERTO para Web: try-with-resources (fecha a porta do banco sozinho)
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement extrair = conecta.prepareStatement(sql)) {

            // Mudando os valores de (?, ?, ?, ?)
            extrair.setString(1, usuario.getUsername());
            extrair.setString(2, usuario.getEmail());
            extrair.setString(3, usuario.getSenha());
            extrair.setString(4, usuario.getTipo());

            // Fazendo o disparo pro MariaDB.
            extrair.executeUpdate();
            return true; // Retorna verdadeiro (O cadastro deu certo!)

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false; // Retorna falso (Geralmente porque o e-mail já existe)
        }
    }

    // 2. BUSCAR TODOS
    public List<Usuario> buscarTodos() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        // Protegendo contra travamentos
        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement extrair = conecta.prepareStatement(sql);
             ResultSet resultado = extrair.executeQuery()) {

            // Lendo toda a tabela
            while (resultado.next()) {
                Usuario novoUsuario = new Usuario();
                novoUsuario.setId(resultado.getInt("id"));
                novoUsuario.setUsername(resultado.getString("username"));
                novoUsuario.setEmail(resultado.getString("email"));
                novoUsuario.setSenha(resultado.getString("senha"));
                novoUsuario.setTipo(resultado.getString("tipo")); // Faltava carregar o tipo!
                listaUsuarios.add(novoUsuario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario (exceção de buscarTodos): " + e.getMessage());
        }
        return listaUsuarios;
    }

    // 3. AUTENTICAR (A FUNÇÃO NOVA PARA O LOGIN!)
    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conecta = ConexaoDB.conectar();
             PreparedStatement extrair = conecta.prepareStatement(sql)) {

            extrair.setString(1, email);
            extrair.setString(2, senha);

            try (ResultSet resultado = extrair.executeQuery()) {
                // Se o "next()" for verdadeiro, a pessoa acertou a senha!
                if (resultado.next()) {
                    Usuario u = new Usuario();
                    u.setId(resultado.getInt("id"));
                    u.setUsername(resultado.getString("username"));
                    u.setEmail(resultado.getString("email"));
                    u.setSenha(resultado.getString("senha"));
                    u.setTipo(resultado.getString("tipo"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na autenticação: " + e.getMessage());
        }
        return null; // Retorna "nulo" se errar o email ou a senha
    }
}