package com.exemplo.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String URL = "jdbc:sqlite:data/usuarios.db";

    public UsuarioDAO() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "email TEXT UNIQUE NOT NULL," +
                    "senha TEXT NOT NULL)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionar(Usuario u) {
        String sql = "INSERT INTO usuarios(nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
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
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DriverManager.getConnection(URL);
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
        try (Connection conn = DriverManager.getConnection(URL);
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
