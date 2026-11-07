public class Usuario {
    private final String nomeCompleto;
    private final String email;
    private String senhaHash;
    private final String telefone;
    private final String cpf;
    private final TipoUsuario tipo;

    public Usuario(String nomeCompleto, String email, String senhaHash, String telefone, String cpf, TipoUsuario tipo) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.cpf = cpf;
        this.tipo = tipo;
    }

    public String getNomeCompleto() { 
        return nomeCompleto; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getSenhaHash() { 
        return senhaHash; 
    }
    public String getTelefone() { 
        return telefone; 
    }
    public String getCpf() { 
        return cpf; 
    }
    public TipoUsuario getTipo() { 
        return tipo; 
    }

    public void setSenhaHash(String senhaHash) { 
        this.senhaHash = senhaHash; 
    }
}
