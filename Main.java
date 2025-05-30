package org.example;

import javax.swing.*;
import static org.example.BuscaFilme.buscarFilme; //Importação das funções pré-programadas nas outras classes.
import static org.example.BuscaSerie.buscarSerie;

public class Main {
    public static void main(String[] args) {


        JOptionPane.showMessageDialog(null, "Seja bem vindo ao software de pesquisa de filmes e séries! ");

        int opcao = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite [1] para filmes. " +
                " Digite [2] para séries.\n"));


        if (opcao == 1){
            String filme = (JOptionPane.showInputDialog(null,"Digite o nome do filme: "));
            buscarFilme(filme);

        } else {
            String serie = (JOptionPane.showInputDialog(null, "Digite o nome da série: "));
            buscarSerie(serie);
        }
    }
}
