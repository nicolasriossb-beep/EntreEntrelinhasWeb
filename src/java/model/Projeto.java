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

public class Projeto {

    private int id;
    private String nome;
    private String descricao;
    private String tipo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaEdicao;
    private int usuarioId;

    public Projeto() {
    }

    public Projeto(int id, String nome, String descricao, String tipo,
            LocalDateTime dataCriacao, LocalDateTime dataUltimaEdicao,
            int usuarioId) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
        this.dataUltimaEdicao = dataUltimaEdicao;
        this.usuarioId = usuarioId;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataUltimaEdicao() {
        return dataUltimaEdicao;
    }

    public void setDataUltimaEdicao(LocalDateTime dataUltimaEdicao) {
        this.dataUltimaEdicao = dataUltimaEdicao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
