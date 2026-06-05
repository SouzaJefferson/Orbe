package model;

public class Ficha {
    // Atributos que mapeiam a tabela do banco de dados
    private int id;
    private Integer campanhaId; // Usamos Integer (com I maiúsculo) porque pode ser nulo caso ele não tenha campanha ainda
    private int usuarioId;
    private String nomePersonagem;
    private String estilos;

    //destino
    private int corpo;
    private int sentidos;
    private int mente;
    private int sorte;

    //status
    private int forca;
    private int velocidade;
    private int destreza;
    private int vigor;
    private int sabedoria;
    private int inteligencia;

    //sub status
    private float vida;
    private int sagrada;
    private int amaldicoada;
    private int pesquisa;
    private int conhecimento;

    // Atributos adicionados para a regra de evolução
    private int nivel;
    private int exp;
    private String raca;

    // Construtor Vazio obrigatório
    public Ficha() {
    }

    // Construtor Completo para facilitar a leitura do DAO
    public Ficha(int id, int usuarioId, String nomePersonagem, String estilos, int corpo, int sentidos,
                 int mente, int sorte, int forca, int velocidade, int destreza, int vigor, int sabedoria,
                 int inteligencia, float vida, int sagrada, int amaldicoada, int pesquisa, int conhecimento,
                 int nivel, int exp, String raca) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nomePersonagem = nomePersonagem;
        this.estilos = estilos;
        this.corpo = corpo;
        this.sentidos = sentidos;
        this.mente = mente;
        this.sorte = sorte;
        this.forca = forca;
        this.velocidade = velocidade;
        this.destreza = destreza;
        this.vigor = vigor;
        this.sabedoria = sabedoria;
        this.inteligencia = inteligencia;
        this.vida = vida;
        this.sagrada = sagrada;
        this.amaldicoada = amaldicoada;
        this.pesquisa = pesquisa;
        this.conhecimento = conhecimento;
        this.nivel = nivel;
        this.exp = exp;
        this.raca = raca;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getCampanhaId() { return campanhaId; }
    public void setCampanhaId(Integer campanhaId) { this.campanhaId = campanhaId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getNomePersonagem() { return nomePersonagem; }
    public void setNomePersonagem(String nomePersonagem) { this.nomePersonagem = nomePersonagem; }

    public String getEstilos() { return estilos; }
    public void setEstilos(String estilos) { this.estilos = estilos; }

    public int getCorpo() { return corpo; }
    public void setCorpo(int corpo) { this.corpo = corpo; }

    public int getSentidos() { return sentidos; }
    public void setSentidos(int sentidos) { this.sentidos = sentidos; }

    public int getMente() { return mente; }
    public void setMente(int mente) { this.mente = mente; }

    public int getSorte() { return sorte; }
    public void setSorte(int sorte) { this.sorte = sorte; }

    public int getForca() { return forca; }
    public void setForca(int forca) { this.forca = forca; }

    public int getVelocidade() { return velocidade; }
    public void setVelocidade(int velocidade) { this.velocidade = velocidade; }

    public int getDestreza() { return destreza; }
    public void setDestreza(int destreza) { this.destreza = destreza; }

    public int getVigor() { return vigor; }
    public void setVigor(int vigor) { this.vigor = vigor; }

    public int getSabedoria() { return sabedoria; }
    public void setSabedoria(int sabedoria) { this.sabedoria = sabedoria; }

    public int getInteligencia() { return inteligencia; }
    public void setInteligencia(int inteligencia) { this.inteligencia = inteligencia; }

    public float getVida() { return vida; }
    public void setVida(float vida) { this.vida = vida; }

    public int getSagrada() { return sagrada; }
    public void setSagrada(int sagrada) { this.sagrada = sagrada; }

    public int getAmaldicoada() { return amaldicoada; }
    public void setAmaldicoada(int amaldicoada) { this.amaldicoada = amaldicoada; }

    public int getPesquisa() { return pesquisa; }
    public void setPesquisa(int pesquisa) { this.pesquisa = pesquisa; }

    public int getConhecimento() { return conhecimento; }
    public void setConhecimento(int conhecimento) { this.conhecimento = conhecimento; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
}