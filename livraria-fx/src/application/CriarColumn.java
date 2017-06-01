package application;



import br.com.gabriel.livraria.produtos.Produto;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CriarColumn {
	
	private String titulo;
	private int largura;
	private String atributo;
	
	

	
	public  CriarColumn(String titulo, int largura, String atributo){
		
		this.titulo = titulo;
		this.largura = largura;
		this.atributo = atributo;
		
	}
	
	 public	TableColumn<Produto,String> criarColuna(){
			
		 
			TableColumn<Produto,String> column = new TableColumn<Produto, String>(this.titulo);
			
			
			// metodo setMinWidth -> defini qual tamanho min da coluna
			
			column.setMinWidth(this.largura); 
				
			//metodo setCellValueFactory -> recebe o atributo da classe deve conter em sua coluna
			
			column.setCellValueFactory(
							new PropertyValueFactory<Produto,String>(this.atributo));
			
			return column;
			
		}
		
	}

