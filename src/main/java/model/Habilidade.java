package model;

public class Habilidade {
    private int id;
    private int fichaId;
    private String titulo;
    private String tipo;
    private String descricao;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFichaId() { return fichaId; }
    public void setFichaId(int fichaId) { this.fichaId = fichaId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}