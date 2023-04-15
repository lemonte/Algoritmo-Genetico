/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import models.ItemMochila;

/**
 *
 * @author geandersonlemonte
 */
public class LeituraArquivo extends ILeituraArquivo {

    @Override
    public LinkedList<ItemMochila> lerLista1() {
        final String fileName = "Problema da Mochila.csv";
        LinkedList<ItemMochila> lado1 = buscarDadosNoArquivo(fileName);
        return lado1;
    }

    private LinkedList<ItemMochila> buscarDadosNoArquivo(String fileName) {
        final LinkedList<ItemMochila> list = new LinkedList();
        try {
            final FileInputStream fil = new FileInputStream(fileName);
            try ( Scanner scan = new Scanner(fil)) {
                System.out.println("LISTA " + fileName);
                System.out.println("");
                if (scan.hasNextLine()) {
                    scan.nextLine();
                }
                do {
                    final ItemMochila valorInput = ItemMochila.fromString(scan.next());
                    list.add(valorInput);
                    if (scan.hasNextLine()) {
                        scan.nextLine();
                    }
                } while (scan.hasNextLine());
            }
            System.out.println("");
            System.out.println("");
        } catch (FileNotFoundException erro) {
            list.clear();
            System.out.println("");
            System.out.println("Arquivo não encontrado -> " + fileName);
            System.out.println("");
        } catch (NoSuchElementException erro) {
            list.clear();
            System.out.println("");
            System.out.println("Erro ao carregar os elementos do arquivo -> " + fileName);
            System.out.println("");
        } catch (Exception e) {
            list.clear();
            System.out.println("");
            System.out.println("Erro ao consultar o arquivo -> " + fileName);
            System.out.println("Erro -> " + e.toString());
            System.out.println("");
        }
        return list;

    }
}
