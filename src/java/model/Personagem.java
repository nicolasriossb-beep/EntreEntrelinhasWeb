package model;

public class Personagem {
    private int id;
    private String nome;
    private int idade;
    private String aparencia;
    private String personalidade;
    private String historia;

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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getAparencia() {
        return aparencia;
    }

    public void setAparencia(String aparencia) {
        this.aparencia = aparencia;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
}
