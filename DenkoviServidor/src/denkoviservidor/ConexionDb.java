package denkoviservidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDb {
      
    public static void GetConnection(String consulta) throws SQLException, ClassNotFoundException
    {
        Connection conexion;
        
        Class.forName("com.mysql.jdbc.Driver");
        String servidor = "jdbc:mysql://localhost:3306/smart";
        String usuarioDB="root";
        String passwordDB="";
        conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
        try (Statement st = conexion.createStatement()) {
            st.executeUpdate(consulta);
            conexion.close();
        }        
    }
    
    public static boolean validar_sesion(String usuario, String clave)throws SQLException, ClassNotFoundException
    {
        boolean valido=false;
        int resultado=0;
        
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver");
        String servidor = "jdbc:mysql://localhost:3306/smart";
        String usuarioDB="root";
        String passwordDB="";
        conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM usuario WHERE usuario ='"+usuario+"' and clave ='"+clave+"';");
        
        if (rs != null) 
        {
            while (rs.next()) 
            {
                resultado=(int) rs.getObject("id");
            }
            rs.close();
        }
        st.close();
           
        if(resultado==1)
        {
            valido=true;
        }
        
        conexion.close();
        return valido;
        
    } 
}