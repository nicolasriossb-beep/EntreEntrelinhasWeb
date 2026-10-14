/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ryzen7RTX3050
 */

public class Personagem {

    private int id;
    private String nome;
    private String idade;
    private String aparencia;
    private String personalidade;
    private String historia;
    private int projetoId;

    public Personagem() {
    }

    public Personagem(int id, String nome, String idade,
            String aparencia, String personalidade,
            String historia, int projetoId) {

        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.aparencia = aparencia;
        this.personalidade = personalidade;
        this.historia = historia;
        this.projetoId = projetoId;
    }

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

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
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

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
}