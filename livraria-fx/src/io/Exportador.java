package io;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import br.com.gabriel.livraria.produtos.Produto;

public class Exportador {

	// metodo para parCSV recebe uma List e criar um arquivo dessa lista

	public void paraCSV(List<Produto> produtos) throws IOException {

		PrintStream ps = new PrintStream("produtos.csv");

		ps.println("Nome, Descrição, Valor, ISBN");

		for (Produto p : produtos) {

			ps.println(String.format("%s, %s, %s, %s",

					p.getNome(), p.getDescricao(), p.getNome(), p.getIsbn()));

		}

		ps.close();
	}
}
