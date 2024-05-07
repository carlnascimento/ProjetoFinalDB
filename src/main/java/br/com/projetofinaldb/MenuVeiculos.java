package br.com.projetofinaldb;
import java.util.Scanner;

public class MenuVeiculos {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----- MENU CONCESSIONÁRIA -----");
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Atualizar veículo");
            System.out.println("3. Excluir veículo");
            System.out.println("4. Consultar veículo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir nova linha pendente

            switch (opcao) {
                case 1:
                    System.out.println("\nCadastro de Veículo:");
                    Cadastro.main(null);
                    break;
                case 2:
                    System.out.println("\nAtualização de Veículo:");
                    AtualizacaoVeiculo.main(null);
                    break;
                case 3:
                    System.out.println("\nExclusão de Veículo:");
                    DelecaoVeiculo.main(null);
                    break;
                case 4:
                    System.out.println("\nConsulta de Veículo:");
                    ConsultaVeiculo.main(null);
                    break;
                case 5:
                    System.out.println("Saindo do programa. Até logo!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }
}
