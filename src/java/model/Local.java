/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ryzen7RTX3050
 */
public class Local {

    private int id;
    private String nome;
    private String tipo;
    private String atmosfera;
    private String acontecimentos;
    private String descricao;
    private int projetoId;

    public Local() {
    }

    public Local(int id, String nome, String tipo,
            String atmosfera, String acontecimentos,
            String descricao, int projetoId) {

        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.atmosfera = atmosfera;
        this.acontecimentos = acontecimentos;
        this.descricao = descricao;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAtmosfera() {
        return atmosfera;
    }

    public void setAtmosfera(String atmosfera) {
        this.atmosfera = atmosfera;
    }

    public String getAcontecimentos() {
        return acontecimentos;
    }

    public void setAcontecimentos(String acontecimentos) {
        this.acontecimentos = acontecimentos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
}
