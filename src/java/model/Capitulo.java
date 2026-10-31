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

public class Capitulo {

    private int id;
    private int numero;
    private String titulo;
    private String texto;
    private String json;
    private String html;
    private Integer quantidadePalavras;
    private LocalDateTime dataUltimaEdicao;
    private int projetoId;
    private boolean publico;

    public Capitulo() {
    }

    public Capitulo(int id, int numero, String titulo, String texto,
            String json, String html, Integer quantidadePalavras, 
            LocalDateTime dataUltimaEdicao, int projetoId, boolean publico) {

        this.id = id;
        this.numero = numero;
        this.titulo = titulo;
        this.texto = texto;
        this.quantidadePalavras = quantidadePalavras;
        this.dataUltimaEdicao = dataUltimaEdicao;
        this.projetoId = projetoId;
        this.publico = publico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getQuantidadePalavras() {
        return quantidadePalavras;
    }

    public void setQuantidadePalavras(Integer quantidadePalavras) {
        this.quantidadePalavras = quantidadePalavras;
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

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }
}
