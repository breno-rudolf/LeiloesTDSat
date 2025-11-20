/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        conn = new conectaDAO().connectDB();
        pstm = conn.prepareStatement(sql);

        pstm.setString(1, produto.getNome());
        pstm.setInt(2, produto.getValor());
        pstm.setString(3, produto.getStatus());

        pstm.execute();
        pstm.close();

         JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        String sql = "SELECT * FROM produtos";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        try{
            conn = new conectaDAO().connectDB();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                ProdutosDTO item = new ProdutosDTO();

                item.setId(rs.getInt("id"));
                item.setNome(rs.getString("nome"));
                item.setValor(rs.getInt("valor"));
                item.setStatus(rs.getString("status"));

                lista.add(item);
            }

        }catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
        }
        
        return lista;
    }
    
        public void venderProduto(int id) {
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
        try {
            conn = new conectaDAO().connectDB();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            int linhasAfetadas = pstm.executeUpdate();
                pstm.close();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            }else{
                JOptionPane.showMessageDialog(null, "ID não encontrado.");
            }

        }catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + ex.getMessage());
        }
    }
    
        public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {

                ProdutosDTO item = new ProdutosDTO();
                item.setId(rs.getInt("id"));
                item.setNome(rs.getString("nome"));
                item.setValor(rs.getInt("valor"));
                item.setStatus(rs.getString("status"));

                listaVendidos.add(item);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + ex.getMessage());
        }

        return listaVendidos;
    }
}

