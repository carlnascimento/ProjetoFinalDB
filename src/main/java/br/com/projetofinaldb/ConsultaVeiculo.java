package br.com.projetofinaldb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsultaVeiculo {

	public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Informe o nome do cliente ou código do veículo: ");
            String entrada = sc.nextLine();

            String sql = "SELECT * FROM veiculos WHERE LOWER(marca) LIKE LOWER(?) OR LOWER(modelo) LIKE LOWER(?) OR codigo = ? OR codigo IN (SELECT codigo FROM veiculos WHERE LOWER(cliente) LIKE LOWER(?))";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + entrada + "%");
                preparedStatement.setString(2, "%" + entrada + "%");
                try {
                    int codigo = Integer.parseInt(entrada);
                    preparedStatement.setInt(3, codigo);
                } catch (NumberFormatException e) {
                    preparedStatement.setInt(3, 0); // Fornecendo um valor que não existe para código se não for um número
                }
                preparedStatement.setString(4, "%" + entrada + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("Nenhum veículo encontrado com o nome do cliente ou código fornecido.");
                }

                while (resultSet.next()) {
                    int codigo = resultSet.getInt("codigo");
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int ano = resultSet.getInt("ano");
                    int preco = resultSet.getInt("preco");
                    String placa = resultSet.getString("placa");
                    String cliente = resultSet.getString("cliente");

                    System.out.println("Código: " + codigo);
                    System.out.println("Marca: " + marca);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("Ano: " + ano);
                    System.out.println("Preço: " + preco);
                    System.out.println("Placa: " + placa);
                    System.out.println("Cliente: " + cliente);
                    System.out.println("---------------------------------");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao consultar veículo: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com Banco de Dados: " + e.getMessage());
        }
    }
}