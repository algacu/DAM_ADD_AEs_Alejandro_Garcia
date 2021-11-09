package es.add.ae4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Principal {

		public static void main(String[] args) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
				Vista vista = new Vista();
				Biblioteca biblioteca = new Biblioteca();
				Controlador controlador = new Controlador(vista, biblioteca, con);
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
}
