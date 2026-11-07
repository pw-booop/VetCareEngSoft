import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class SistemaUsuarios {

    private final List<Usuario> usuarios = new ArrayList<>();

    private String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    private Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    private boolean cpfJaCadastrado(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) return true;
        }
        return false;
    }

    public String cadastrar(String nome, String email, String senha, String telefone, String cpf, TipoUsuario tipo) {
        if (nome.trim().isEmpty()) return "O nome não pode ficar vazio.";
        if (!email.contains("@") || !email.contains(".")) return "E-mail inválido.";
        if (senha.length() < 6) return "A senha deve ter pelo menos 6 caracteres.";
        if (!telefone.matches("\\d{10,11}")) return "Telefone deve ter 10 ou 11 dígitos (só números).";
        if (!cpf.matches("\\d{11}")) return "CPF deve ter 11 dígitos (só números).";
        if (buscarPorEmail(email) != null) return "Já existe um usuário com esse e-mail.";
        if (cpfJaCadastrado(cpf)) return "Já existe um usuário com esse CPF.";

        usuarios.add(new Usuario(nome, email, gerarHash(senha), telefone, cpf, tipo));
        return null;
    }

    public Usuario login(String email, String senha) {
        Usuario u = buscarPorEmail(email);
        if (u != null && u.getSenhaHash().equals(gerarHash(senha))) {
            return u;
        }
        return null;
    }

    public String recuperarSenha(String email, String cpf, String novaSenha) {
        Usuario u = buscarPorEmail(email);
        if (u == null || !u.getCpf().equals(cpf)) {
            return "E-mail ou CPF não conferem.";
        }
        if (novaSenha.length() < 6) return "A senha deve ter pelo menos 6 caracteres.";

        u.setSenhaHash(gerarHash(novaSenha));
        return null;
    }
}