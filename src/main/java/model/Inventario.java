package model;

public class Inventario {
    // Atributos privados correspondentes à tabela inventario
    private int id;
    private int fichaId;
    private String tituloItem;
    private String descricao;
    private String imagem;

    // Construtor Vazio
    public Inventario() {
    }

    // Construtor Completo
    public Inventario(int id, int fichaId, String tituloItem, String descricao, String imagem) {
        this.id = id;
        this.fichaId = fichaId;
        this.tituloItem = tituloItem;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFichaId() { return fichaId; }
    public void setFichaId(int fichaId) { this.fichaId = fichaId; }

    public String getTituloItem() { return tituloItem; }
    public void setTituloItem(String tituloItem) { this.tituloItem = tituloItem; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}