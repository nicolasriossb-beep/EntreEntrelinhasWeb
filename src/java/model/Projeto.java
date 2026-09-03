package model;

import java.time.LocalDateTime;
import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private String tipo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaEdicao;
    private List<Capitulo> capitulos;
    private List<Personagem> personagens;
    private List<Local> locais;
    private List<Conflito> conflitos;
    private List<Nota> notas;
    private List<Cronologia> cronologias;

    @Override
    public String toString() {
        return "{id: " + id + 
            ", nome: " + nome +
            ", descricao: " + descricao +
            ", tipo: " + tipo +
            ", dataCriacao: " + dataCriacao +
            ", dataUltimaEdicao: " + dataUltimaEdicao +
            "}";
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

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public List<Personagem> getPersonagens() {
        return personagens;
    }

    public void setPersonagens(List<Personagem> personagens) {
        this.personagens = personagens;
    }

    public List<Local> getLocais() {
        return locais;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    public List<Conflito> getConflitos() {
        return conflitos;
    }

    public void setConflitos(List<Conflito> conflitos) {
        this.conflitos = conflitos;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Cronologia> getCronologias() {
        return cronologias;
    }

    public void setCronologias(List<Cronologia> cronologias) {
        this.cronologias = cronologias;
    }
}

