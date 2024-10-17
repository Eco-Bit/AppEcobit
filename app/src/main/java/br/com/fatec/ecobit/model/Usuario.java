package br.com.fatec.ecobit.model;


public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String cep;
    private String endereco;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(" O nome não deve estar em branco");
        }else {
            this.nome = nome;
        }
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e_mail não deve estar em branco");
        } else {
            this.email = email;
        }
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("O telefone não deve estar em branco");
        } else {
            this.telefone = telefone;
        }
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha não deve estar em branco");
        } else {
            this.senha = senha;
        }
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new IllegalArgumentException("O cep não deve estar em branco");
        } else {
            this.cep = cep;
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("O endereço não deva estar em branco");
        } else {
            this.endereco = endereco;
        }
    }


    public Usuario(String nome, String email, String telefone, String senha, String cep, String endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.cep = cep;
        this.endereco = endereco;
    }
    public Usuario () {

    }
}