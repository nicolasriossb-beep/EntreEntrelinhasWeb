package model;

public class Local {
    private int id;
    private String nome;
    private String tipo;
    private String atmosfera;
    private String acontecimentos;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAcontecimentos() {
        return acontecimentos;
    }

    public void setAcontecimentos(String acontecimentos) {
        this.acontecimentos = acontecimentos;
    }

    public String getAtmosfera() {
        return atmosfera;
    }

    public void setAtmosfera(String atmosfera) {
        this.atmosfera = atmosfera;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
