package com.exemplo.app;

public class App {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        dao.adicionar(new Usuario("Bruno", "bruno@email.com", "1234"));

        System.out.println("Usuários cadastrados:");
        dao.listar().forEach(u -> 
            System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail()));
    }
}
