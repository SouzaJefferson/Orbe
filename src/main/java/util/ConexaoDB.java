package util;

// Importamos as ferramentas originais do Java para lidar com Banco de Dados (o JDBC)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // 1. AS COORDENADAS DO COFRE (Constantes que nunca mudam)
    // Aqui nós passamos o idioma (mysql), o endereço (localhost), a porta do Docker (3308) e o nome do banco
    private static final String URL = "jdbc:mysql://localhost:3308/void";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    private static  Connection conectado;

    //construtor
    private ConexaoDB(){};

    // 2. A CHAVE DO COFRE (O método que abre a ponte)
    // Esse método devolve um objeto do tipo "Connection", que é a estrada aberta.
    public static Connection conectar() {
        if (conectado==null){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Este comando pega as coordenadas e bate na porta do banco de dados pedindo para entrar
            conectado= DriverManager.getConnection(URL, USUARIO, SENHA);



        } catch (Exception e) {
            // 3. O PLANO DE EMERGÊNCIA (Try / Catch)
            // Se o Docker estiver desligado ou a senha errada, o programa não "explode".
            // Ele cai aqui e lança um aviso no terminal explicando o erro.
            throw new RuntimeException("Erro ao conectar com o banco de dados. O Docker está rodando? Erro: " + e.getMessage());
        }


        }
        //retorno aqui fora do if para evitar problemas de sintaxe
        return conectado;

        }
}