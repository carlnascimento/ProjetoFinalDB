package br.com.projetofinaldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AtualizacaoVeiculo {

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

                    // Solicitar novos dados ao usuário
                    System.out.println("Informe a nova marca do veículo: ");
                    String novaMarca = sc.nextLine();

                    System.out.println("Informe o novo modelo do veículo: ");
                    String novoModelo = sc.nextLine();

                    System.out.println("Informe o novo ano do veículo: ");
                    int novoAno = sc.nextInt();
                    sc.nextLine(); // Consumir nova linha pendente

                    System.out.println("Informe o novo preço do veículo: ");
                    int novoPreco = sc.nextInt();
                    sc.nextLine(); // Consumir nova linha pendente

                    System.out.println("Informe a nova placa do veículo: ");
                    String novaPlaca = sc.nextLine();

                    System.out.println("Informe o novo cliente do veículo: ");
                    String novoCliente = sc.nextLine();

                    // Atualizar o veículo
                    String sqlUpdate = "UPDATE veiculos SET marca = ?, modelo = ?, ano = ?, preco = ?, placa = ?, cliente = ? WHERE codigo = ?";
                    try (PreparedStatement preparedStatementUpdate = conn.prepareStatement(sqlUpdate)) {
                        preparedStatementUpdate.setString(1, novaMarca);
                        preparedStatementUpdate.setString(2, novoModelo);
                        preparedStatementUpdate.setInt(3, novoAno);
                        preparedStatementUpdate.setInt(4, novoPreco);
                        preparedStatementUpdate.setString(5, novaPlaca);
                        preparedStatementUpdate.setString(6, novoCliente);
                        preparedStatementUpdate.setInt(7, codigoVeiculo);

                        int rowsAffected = preparedStatementUpdate.executeUpdate();
                        System.out.println(rowsAffected + " veículo(s) atualizado(s) com sucesso.");

                    } catch (SQLException ex) {
                        System.err.println("Erro ao atualizar veículo: " + ex.getMessage());
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


