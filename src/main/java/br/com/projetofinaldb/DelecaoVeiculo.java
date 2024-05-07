package br.com.projetofinaldb;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DelecaoVeiculo {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Informe o código do veículo ou nome do cliente: ");
            String entrada = sc.nextLine();

            String sql = "SELECT * FROM veiculos WHERE codigo = ? OR cliente LIKE ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                try {
                    int codigo = Integer.parseInt(entrada);
                    preparedStatement.setInt(1, codigo);
                    preparedStatement.setString(2, "%" + entrada + "%");
                } catch (NumberFormatException e) {
                    preparedStatement.setInt(1, 0); // Fornecendo um valor que não existe para código se não for um número
                    preparedStatement.setString(2, "%" + entrada + "%");
                }

                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("Nenhum veículo encontrado com o código ou nome do cliente fornecido.");
                    return;
                }

                // Armazenar os resultados da consulta em objetos Java
                while (resultSet.next()) {
                    int codigoVeiculo = resultSet.getInt("codigo");
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    int ano = resultSet.getInt("ano");
                    int preco = resultSet.getInt("preco");
                    String placa = resultSet.getString("placa");
                    String cliente = resultSet.getString("cliente");

                    System.out.println("---------------------------------");
                    System.out.println("Código: " + codigoVeiculo);
                    System.out.println("Marca: " + marca);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("Ano: " + ano);
                    System.out.println("Preço: " + preco);
                    System.out.println("Placa: " + placa);
                    System.out.println("Cliente: " + cliente);
                    System.out.println("---------------------------------");

                    System.out.println("Deseja realmente excluir este veículo? (S/N)");
                    String resposta = sc.nextLine().toUpperCase();

                    if (resposta.equals("S")) {
                        // Excluir o veículo
                        String sqlDelete = "DELETE FROM veiculos WHERE codigo = ?";
                        try (PreparedStatement preparedStatementDelete = conn.prepareStatement(sqlDelete)) {
                            preparedStatementDelete.setInt(1, codigoVeiculo);

                            int rowsAffected = preparedStatementDelete.executeUpdate();
                            System.out.println(rowsAffected + " veículo(s) excluído(s) com sucesso.");
                        } catch (SQLException ex) {
                            System.err.println("Erro ao excluir veículo: " + ex.getMessage());
                        }
                    } else {
                        System.out.println("Operação de exclusão cancelada.");
                    }
                }

            } catch (SQLException e) {
                System.err.println("Erro ao consultar veículo: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com Banco de Dados: " + e.getMessage());
        }
    }
}
