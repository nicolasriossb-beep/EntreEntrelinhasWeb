/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ryzen7RTX3050
 */

import java.time.LocalDateTime;

public class Nota {

    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataUltimaEdicao;
    private int projetoId;

    public Nota() {
    }

    public Nota(int id, String titulo, String conteudo,
            LocalDateTime dataUltimaEdicao, int projetoId) {

        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataUltimaEdicao = dataUltimaEdicao;
        this.projetoId = projetoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataUltimaEdicao() {
        return dataUltimaEdicao;
    }

    public void setDataUltimaEdicao(LocalDateTime dataUltimaEdicao) {
        this.dataUltimaEdicao = dataUltimaEdicao;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
}
