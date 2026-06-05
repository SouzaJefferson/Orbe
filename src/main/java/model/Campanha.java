package model;

public class Campanha {
    private int id;
    private int mestreId;
    private String nome;
    private String codigo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMestreId() { return mestreId; }
    public void setMestreId(int mestreId) { this.mestreId = mestreId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}