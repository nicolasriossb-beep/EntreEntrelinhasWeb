package model;

import java.time.LocalDateTime;

public class Nota {
    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataUltimaEdicao;

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
}
