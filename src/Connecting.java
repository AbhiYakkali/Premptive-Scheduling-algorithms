import java.sql.*;
import javax.swing.JOptionPane;
public class Connecting {
    Connection conn=null;
    public static Connection ConnerDb(){
        try{
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/os","root","root");
            return conn;
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null,e);
    }
        return null;
    }
    
}
