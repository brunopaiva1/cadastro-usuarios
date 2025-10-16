package com.exemplo.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public UsuarioDAO() {
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        this.dbUser = System.getenv("DB_USER");
        this.dbPassword = System.getenv("DB_PASSWORD");

        this.dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

        criarTabelaSeNaoExistir();
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }

    private void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id SERIAL PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "senha TEXT NOT NULL)";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados para criar tabela. Tentando novamente em 5 segundos...");
            try {
                Thread.sleep(5000); 
                criarTabelaSeNaoExistir(); 
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void adicionar(Usuario u) {
        String sql = "INSERT INTO usuarios(nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}