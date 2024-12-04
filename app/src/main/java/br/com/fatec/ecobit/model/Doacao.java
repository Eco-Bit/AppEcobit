package br.com.fatec.ecobit.model;

import java.util.List;

public class Doacao {
    private String id;
    private String titulo;
    private String descricao;
    private String quantidade;
    private String categoria;
    private String condicao;
    private String disponibilidade;
    private String telefone;
    private List<String> imagensBase64; // Lista de imagens em Base64
    private String userId;

    public Doacao() {
    }

    public Doacao(String titulo, String descricao, String quantidade, String categoria, String condicao, String disponibilidade, List<String> imagensBase64, String userId, String telefone) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.condicao = condicao;
        this.disponibilidade = disponibilidade;
        this.imagensBase64 = imagensBase64;
        this.userId = userId;
        this.telefone = telefone;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public List<String> getImagensBase64() {
        return imagensBase64;
    }

    public void setImagensBase64(List<String> imagensBase64) {
        this.imagensBase64 = imagensBase64;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
