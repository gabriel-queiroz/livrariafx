package application;

import javafx.scene.Group;




import java.io.IOException;

import br.com.gabriel.livraria.produtos.Produto;
import dao.ProdutoDAO;
import io.Exportador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;


public class Main extends View {
	@Override
	public void start(Stage primaryStage) {

		// a classe group recebera os elementos do pagina


		Group group = new Group();

		// Criando o cenario da tela do Javafx com parametro de tamanho

		Scene scene = new Scene(group, 690, 510);

		// configurando scene para edita-la por arquivo css

		scene.getStylesheets().add(getClass()
				.getResource("application.css").toExternalForm());

		// criando tipo de Lista especial do Javafx

		ObservableList<Produto> p = FXCollections.observableArrayList();

		ObservableList<Produto> produtos =  p; 

		// criando a tabela no 

		TableView<Produto> tableview = new TableView<Produto>(produtos);

		// criando os rótulos da tela

		Label progresso = new Label();
		progresso.setId("pro-label");


		Label progresso2 = new Label();
		progresso2.setId("label-progresso2");


		Label labelFooter = new Label();
		labelFooter.setId("label-footer");


		Label label = new Label("Listagem de Livros");
		label.setId("rotulo-nome");



		// Criando objeto TableColumn a partir de classe criarColumn


		TableColumn<Produto,String> nomeColumn = new CriarColumn(
				"Nome",180,"nome").criarColuna();


		TableColumn<Produto,String> descColumn = new CriarColumn(
				"Descrição",230,"descricao").criarColuna();

		TableColumn<Produto,String> valorColumn = new CriarColumn(
				"Valor",60,"Valor").criarColuna();											



		TableColumn<Produto,String> isbnColumn = new CriarColumn(
				"ISBN",180,"isbn").criarColuna();

		tableview.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);


		// adicionar nossa tabela em uma vBox

		VBox vbox = new VBox(tableview);

		vbox.setId("vbox");

		// criando um botar para exportar um arquivo tipo csv

		Button button = new Button("Exportar CSV");

		Button button2 = new Button("Mostrar Tabela");
		button2.setId("button-fechar");

		
		int i = 0;
		
		i = Integer.MAX_VALUE;
		
		
		// criando as ações dos botões

		// usando expressao lambda para criar nosso acao do botao

		button.setOnAction(event -> {
				
			
			
			
			Task<Void> task = new Task	<Void>(){

				@Override
				protected Void call() throws Exception {
					
					
					
					
					//dormirPor10Seg();
					paraCSV(produtos);

					return null;
				}
			};

			new Thread(task).start();

			task.setOnRunning(e -> progresso.setText("Exportando...."));

			task.setOnSucceeded(e -> progresso.setText("Concluído!"));
			
		});

		
		
		button2.setOnAction(event ->{

			Task<Void> task = new Task<Void>(){

				@Override
				protected Void call() throws Exception {

					//dormirPor10Seg();
					produtos.addAll(new ProdutoDAO().lista());

					return null;
				}
			};

			new Thread(task).start();

			task.setOnRunning(l -> {

				progresso2.setId("label-carregando");	
				progresso2.setText("Carregando Produtos...");

			});

			task.setOnSucceeded(l-> {


				progresso2.setId("label-carregado");	
				progresso2.setText("Concluído!");

				labelFooter.setText(String.format("Você tem R$%.2f em estoque, "
						+ "com um total de %d produtos " , 
						calcularLista(produtos), produtos.size()));
			});

		});
		// adicionando o rotulo, o butao e no vbox( dentro ira nossa tableView)a group

		group.getChildren().addAll(
				label,progresso2, vbox ,button2, button,progresso,labelFooter);

		// adicionando toda a scene em primaryStage(containner)

		primaryStage.setScene(scene);

		primaryStage.setTitle("Sistema de Livraria com JavaFX");

		primaryStage.show();

	}

	public void paraCSV(ObservableList<Produto> p){

		try{
			new Exportador().paraCSV(p);
		}catch(IOException c){
			c.printStackTrace();

		}

	}

	public double calcularLista(ObservableList<Produto> produtos){


		return produtos.stream().mapToDouble(Produto::getValor).sum();
	}

	public void dormirPor10Seg(){

		try{

			Thread.sleep(10000);
		}catch(InterruptedException C){
			System.out.println("Ocorreu um erro na Thread");

		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
