package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	// abrindo conexao com banco de dados

	public Connection getConnection() {

		// endereco do banco

		String url = "jdbc:mysql://localhost/livraria";

		try {

			// passando endereco , login e senha para logar no banco

			return DriverManager.getConnection(url, "root", "gq2308");
				
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
