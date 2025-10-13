package com.exemplo.app;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            mostrarAjuda();
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        String comando = args[0];

        switch (comando) {
            case "add":
                if (args.length != 4) {
                    System.out.println("Erro: Para adicionar, use o formato: add <nome> <email> <senha>");
                    return;
                }
                String nome = args[1];
                String email = args[2];
                String senha = args[3];
                
                Usuario novoUsuario = new Usuario(nome, email, senha);
                dao.adicionar(novoUsuario);
                System.out.println("Usuário '" + nome + "' adicionado com sucesso!");
                break;

            case "list":
                System.out.println("--- Usuários Cadastrados ---");
                var usuarios = dao.listar();
                if (usuarios.isEmpty()) {
                    System.out.println("Nenhum usuário encontrado.");
                } else {
                    usuarios.forEach(u -> 
                        System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail()));
                }
                break;

            case "delete":
                if (args.length != 2) {
                    System.out.println("Erro: Para excluir, use o formato: delete <id>");
                    return;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    boolean sucesso = dao.excluir(id);
                    if (sucesso) {
                        System.out.println("Usuário com ID " + id + " foi excluído com sucesso!");
                    } else {
                        System.out.println("Usuário com ID " + id + " não encontrado.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: O ID fornecido não é um número válido.");
                }
                break;

            default:
                System.out.println("Erro: Comando '" + comando + "' não reconhecido.");
                mostrarAjuda();
                break;
        }
    }

    private static void mostrarAjuda() {
        System.out.println("Uso do programa:");
        System.out.println("  java -jar seu-app.jar add <nome> <email> <senha>");
        System.out.println("  java -jar seu-app.jar list");
        System.out.println("  java -jar seu-app.jar delete <id>");
    }
}
