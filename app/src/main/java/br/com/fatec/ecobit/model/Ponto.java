package br.com.fatec.ecobit.model;

public class Ponto {
    private String id;
    private String nomePonto;
    private String endererecoPonto;
    private String numeroPonto;
    //private List <String> materiasPonto;
    private String materiasPonto;

    public String getAbertoSabado() {
        return abertoSabado;
    }

    public void setAbertoSabado(String abertoSabado) {
        this.abertoSabado = abertoSabado;
    }

    private String abertoSabado;

    public Ponto(){

    }

    public Ponto(String nomePonto, String endererecoPonto, String numeroPonto, String materiasPonto, String abertoSabado) {
        this.nomePonto = nomePonto;
        this.endererecoPonto = endererecoPonto;
        this.numeroPonto = numeroPonto;
        this.materiasPonto = materiasPonto;
        this.abertoSabado = abertoSabado;
    }

    public String getMateriasPonto() {
        return materiasPonto;
    }

    public void setMateriasPonto(String materiasPonto) {
        this.materiasPonto = materiasPonto;
    }

    public String getNumeroPonto() {
        return numeroPonto;
    }

    public void setNumeroPonto(String numeroPonto) {
        if(numeroPonto.isBlank()){
            throw new IllegalArgumentException("Campo obrigatorio");
        }else{
            this.numeroPonto = numeroPonto;
        }
    }

    public String getEndererecoPonto() {
        return endererecoPonto;
    }

    public void setEndererecoPonto(String endererecoPonto) {
        if(endererecoPonto.isBlank()){
            throw  new IllegalArgumentException("Campo Obrigatorio");
        }
        else {
            this.endererecoPonto = endererecoPonto;
        }
    }

    public String getNomePonto() {
        return nomePonto;
    }

    public void setNomePonto(String nomePonto) {
        if(nomePonto.isBlank()){
            throw new IllegalArgumentException("Campo Obrigatorio");
        }
        else{
            this.nomePonto = nomePonto;
        }
    }
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

}
