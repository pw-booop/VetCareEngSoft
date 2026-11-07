import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final SistemaUsuarios sistema = new SistemaUsuarios();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("3 - Recuperar senha");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    recuperarSenha();
                    break;
                case 0:
                    System.out.println("Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrar() {
        System.out.println("\n--- Cadastro ---");
        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();
        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Telefone (só números): ");
        String telefone = sc.nextLine().trim();
        System.out.print("CPF (só números): ");
        String cpf = sc.nextLine().trim();

        System.out.println("Tipo de cadastro:");
        System.out.println("1 - Administração");
        System.out.println("2 - Recepcionista");
        System.out.println("3 - Médico");
        System.out.print("Escolha: ");

        TipoUsuario tipo = null;
        switch (lerInteiro()) {
            case 1:
                tipo = TipoUsuario.ADMINISTRACAO;
                break;
            case 2:
                tipo = TipoUsuario.RECEPCIONISTA;
                break;
            case 3:
                tipo = TipoUsuario.MEDICO;
                break;
        }
        if (tipo == null) {
            System.out.println("Tipo inválido. Cadastro cancelado.");
            return;
        }

        String erro = sistema.cadastrar(nome, email, senha, telefone, cpf, tipo);
        System.out.println(erro == null ? "Cadastro realizado com sucesso!" : "Erro: " + erro);
    }

    private static void login() {
        System.out.println("\n--- Login ---");
        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario u = sistema.login(email, senha);
        if (u == null) {
            System.out.println("E-mail ou senha incorretos.");
            return;
        }
        System.out.println("Bem-vindo(a), " + u.getNomeCompleto() + "!");
        mostrarAreaDoUsuario(u);
    }

    // Cada tipo de usuário vê uma área diferente
    private static void mostrarAreaDoUsuario(Usuario u) {
        switch (u.getTipo()) {
            case ADMINISTRACAO:
                System.out.println("[Painel da Administração]");
                break;
            case RECEPCIONISTA:
                System.out.println("[Painel da Recepção]");
                break;
            case MEDICO:
                System.out.println("[Painel do Médico]");
                break;
        }
    }

    private static void recuperarSenha() {
        System.out.println("\n--- Recuperar senha ---");
        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();
        System.out.print("CPF (para confirmar identidade): ");
        String cpf = sc.nextLine().trim();
        System.out.print("Nova senha: ");
        String novaSenha = sc.nextLine();

        String erro = sistema.recuperarSenha(email, cpf, novaSenha);
        System.out.println(erro == null ? "Senha alterada com sucesso!" : "Erro: " + erro);
    }

    private static int lerInteiro() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}