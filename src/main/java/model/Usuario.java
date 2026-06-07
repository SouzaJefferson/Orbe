package model; // Definimos um pacote para organizar

public class Usuario {
    // Atributos privados (Encapsulamento)
    private int id;
    private String username;
    private String email;
    private String senha;
    private String tipo;

    // Construtor Vazio: Necessário para muitas tecnologias Java Web
    public Usuario() {
    }

    // Construtor Completo: Facilita a criação do objeto com dados vindo do banco
    public Usuario(int id, String username, String email, String senha) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters (As portas de acesso aos dados)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
