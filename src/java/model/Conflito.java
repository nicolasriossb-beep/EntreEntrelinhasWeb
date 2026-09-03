package model;

public class Conflito {
    private int id;
    private String nome;
    private String escala;
    private String lados;
    private String motivacao;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getLados() {
        return lados;
    }

    public void setLados(String lados) {
        this.lados = lados;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
