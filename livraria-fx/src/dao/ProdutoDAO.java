package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.gabriel.livraria.Autor;
import br.com.gabriel.livraria.produtos.Livro;
import br.com.gabriel.livraria.produtos.LivroFisico;
import br.com.gabriel.livraria.produtos.Produto;
import db.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoDAO {

	// metodo consulta o banco de dados e retorna uma List(List especial do
	// javafx)

	public ObservableList<Produto> lista() {

		ObservableList<Produto> produtos = FXCollections.observableArrayList();

		// criando List(Tipo especial de lista do JAVAFX)

		// query

		String sql = "select * from produtos";

		// abriando conexao com banco de dados

		// criando a conexao dentro do try ela se fechara automaticamente depois

		try (Connection conn = new ConnectionFactory().getConnection()) {

			/*
			 * preparedStatement e uma interface de connection ela ajudara a
			 * executar manipulacoes no banco
			 */

			PreparedStatement ps = conn.prepareStatement(sql);

			/*
			 * ps.excecute retorna um objeto do tipo ResultSet com os dados da
			 * consulta
			 * 
			 */

			ResultSet resultSet = ps.executeQuery();

			// enquanto houver registros disponiveis resultSet.next retornara
			// True

			while (resultSet.next()) {

				// passando o nome da coluna no metodo .getTipodado para
				// recuperar valor

				String nome = resultSet.getString("nome");
				String descricao = resultSet.getString("descricao");
				double valor = resultSet.getDouble("valor");
				String isbn = resultSet.getString("isbn");

				// instanciando um objeto do tipo livro e adicinando seus
				// atributos

				Livro livro = new LivroFisico(nome, descricao, valor, isbn);

				// adicionando o livro na "List"

				produtos.add(livro);

			}

			// fechando conexoes

			resultSet.close();
			ps.close();

			

		} catch (SQLException c) {
			c.printStackTrace();
			throw new RuntimeException(c);
			
			// return FXCollections.observableArrayList(produtos);
		}
		return produtos;
	}

	// adicionando o produto no banco de dados

	public void adicionarProduto(Produto produto) {

		// abrindo conexao com banco

		try (Connection conn = new ConnectionFactory().getConnection();) {

			/*
			 * preparedStatement e uma interface de connection ela ajudara a
			 * executar manipulacoes no banco
			 */
			// passando o insert no construtor do PreparedStatament

			// os valores sao omitidos evitando confunsao no codigo

			PreparedStatement ps = conn
					.prepareStatement("insert into produtos (nome, " + "descricao , valor , isbn )  values (?,?,?,?)");

			// adicionando os valores no insert atraves dos metodos de "ps"

			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setDouble(3, produto.getValor());
			ps.setString(4, produto.getIsbn());

			// fechando conexoes

			ps.execute();
			ps.close();

		} catch (SQLException c) {
			throw new RuntimeException(c);
		}

	}
	
	

}
