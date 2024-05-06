package br.com.projetofinaldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Cadastro {
	public static void main(String[] args) {
	    String url = "jdbc:postgresql://localhost:5432/postgres";
	    String username = "postgres";
	    String password = "123";

	    try (Connection conn = DriverManager.getConnection(url, username, password)) {
	        Scanner sc = new Scanner(System.in);

	        // Solicitar dados do usuário
	        System.out.println("Informe o código do veículo: ");
	        int codigo = sc.nextInt();
	        sc.nextLine(); // Consumir nova linha pendente
	        
	        System.out.println("Informe a marca do carro: ");
	        String marca = sc.nextLine();
	        
	        System.out.println("Informe o modelo do carro: ");
	        String modelo = sc.nextLine();
	        
	        System.out.println("Informe o ano de fabricação do veículo: ");
	        int ano = sc.nextInt();
	        
	        System.out.println("Informe o preço do veículo: ");
	        int preco = sc.nextInt();
	        sc.nextLine(); // Consumir nova linha pendente
	        
	        System.out.println("Informe a placa do veículo: ");
	        String placa = sc.nextLine();
	        
	        
	        System.out.println("Informe o cliente do veículo: ");
	        System.out.println("Se o carro não tiver sido vendido, cadastre como loja");
	        String cliente = sc.nextLine();

	        // Preparar e executar a query SQL para inserir dados
	        String sql = "INSERT INTO veiculos(codigo, marca, modelo, ano, preco, placa,cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	            preparedStatement.setInt(1, codigo);
	            preparedStatement.setString(2, marca);
	            preparedStatement.setString(3, modelo);
	            preparedStatement.setInt(4, ano);
	            preparedStatement.setInt(5, preco);
	            preparedStatement.setString(6, placa);
	            preparedStatement.setString(7, cliente);

	            // Executar a query
	            int rowsAffected = preparedStatement.executeUpdate();
	            System.out.println(rowsAffected + " linha inserida no banco de dados com sucesso");
	        } catch (SQLException e) {
	            System.err.println("Erro ao gravar no banco de dados: " + e.getMessage());
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao estabelecer conexão com Banco de Dados: " + e.getMessage());
	    }
	}
}
           
           

               
