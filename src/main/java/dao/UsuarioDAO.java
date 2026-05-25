package dao; // Definimos um pacote para organizar

import model.Usuario; //pega o usuario
import util.ConexaoDB; //pega  a conexão do banco

//tratamento e metodos sql
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import java.util.ArrayList;

import java.sql.ResultSet;

//--------------------------------------------------------------------


public class UsuarioDAO {


    public void cadastrar(Usuario usuario){
        try {
            Connection conecta= ConexaoDB.conectar();
            String sql = "INSERT INTO usuarios (username, email, senha) VALUES (? ,? ,?)";

            /*  está errado , fica aqui como aprendizado. -------------------------------------------
            conecta= prepareStatement(sql);
            jeito certo embaixo
             */

            PreparedStatement extrair = conecta.prepareStatement(sql); //geralmente é stmt , mas coloquei extrair que é pra pegar a ideia do stmt.

           //mudando os valores de (?, ? , ?)
            extrair.setString( 1, usuario.getUsername());
            extrair.setString( 2, usuario.getEmail());
            extrair.setString( 3, usuario.getSenha());


            //fazendo o disparo pro MariaDB.
            extrair.executeUpdate();

        }

        catch (SQLException e) { System.out.println("Erro ao cadastrar: " + e.getMessage()); } //tem que estudar os CATCHS ...
    }

    public List<Usuario> buscarTodos(){
        //lista vazia que será preenchida
        List<Usuario> listaUsuarios = new ArrayList<>();
        try{
            Connection conecta= ConexaoDB.conectar();
            String sql=  "SELECT * FROM usuarios";

            PreparedStatement extrair = conecta.prepareStatement(sql);

            ResultSet resultado = extrair.executeQuery();


            //lendo toda a tabela
            while (resultado.next()) {
              Usuario novoUsuario = new Usuario();
                novoUsuario.setId(resultado.getInt("id"));
                novoUsuario.setUsername(resultado.getString("username"));
                novoUsuario.setEmail(resultado.getString("email"));
                novoUsuario.setSenha(resultado.getString("senha"));
                listaUsuarios.add(novoUsuario);
            }


        }

        catch (SQLException e) { System.out.println("Erro ao buscar usuario (exeção de buscarTodos): " + e.getMessage()); }
        return listaUsuarios;
    }


}