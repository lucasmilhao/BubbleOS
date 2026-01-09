package Controle;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas
 * 
 */
public class ConexaoDAO {
    
    public Connection conectaBancoDados () {
        Connection conexao = null;
        
        String driveJBC = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbwindows";
        String user = "root";
        String senha = "";
        
    try {
        Class.forName(driveJBC);
        
    } catch (Exception e){
        JOptionPane.showMessageDialog(null, "Nome não encontrado");
    }
    
    try {
        
        conexao = DriverManager.getConnection(url,user,senha);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Falha na Conexão:" + e.getMessage());
    }
    return conexao;
    }
}

