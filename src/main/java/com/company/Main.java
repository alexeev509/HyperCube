package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static List<Integer> listOfIdexexOfDifferentBytes=new ArrayList<>();
    private static List<Integer> listOfIdexexOfSameBytes=new ArrayList<>();
    public static void main(String[] args) {
        findOfDifferentTracks(scanner);
    }
    public static void findOfDifferentTracks(Scanner scanner){
        String input=scanner.nextLine();
        String[] massOfInputBytes = input.trim().split(" ");
        String startByte=massOfInputBytes[0];
        String destinationByte=massOfInputBytes[1];

        for (int i = 0; i < startByte.length(); i++) {
            if(startByte.charAt(i)!=destinationByte.charAt(i)){
                listOfIdexexOfDifferentBytes.add(i);
            }else {
                listOfIdexexOfSameBytes.add(i);
            }
        }

//        System.out.println(new StringBuilder(startByte).replace(listOfIdexexOfDifferentBytes.get(0),
//                listOfIdexexOfDifferentBytes.get(0)+1, String.valueOf('0').toString()));


        //code for different combinations
        int counter=0;
        while (counter<listOfIdexexOfDifferentBytes.size()) {
            int j=counter;
            int starter=0;
            System.out.print(startByte.toString()+" ");
            StringBuilder startByteCopy=new StringBuilder(startByte);
            //cyclic move
            differentCombinationsReplace(counter,j,starter,startByteCopy);
            System.out.println();
            counter++;
        }


        counter=0;
        while (counter<listOfIdexexOfSameBytes.size()) {
            int j=counter;
            int starter=0;
            System.out.print(startByte.toString()+" ");
            StringBuilder startByteCopy=new StringBuilder(startByte);
            //Replace of one correct char
            char replaceCorrenctChar = startByte.charAt(listOfIdexexOfSameBytes.get(j))=='0' ? '1':'0';
            startByteCopy.replace(listOfIdexexOfSameBytes.get(j),
                    listOfIdexexOfSameBytes.get(j) + 1,
                    String.valueOf(replaceCorrenctChar));
           // listOfIdexexOfDifferentBytes.add(listOfIdexexOfSameBytes.get(j));
            System.out.print(startByteCopy.toString()+" ");
            //cyclic move
            for (int i = counter; i < listOfIdexexOfDifferentBytes.size()+counter; i++) {
                
                j= (i>=listOfIdexexOfDifferentBytes.size()) ? starter++ :i;

                startByteCopy = new StringBuilder(startByteCopy.toString());
                replaceBits(startByteCopy,j);
                System.out.print(startByteCopy.toString() + " ");
            }
          //  listOfIdexexOfDifferentBytes.remove(listOfIdexexOfDifferentBytes.size()-1);
            replaceCorrenctChar = replaceCorrenctChar=='0' ? '1':'0';
            startByteCopy.replace(listOfIdexexOfSameBytes.get(counter),
                    listOfIdexexOfSameBytes.get(counter) + 1,
                    String.valueOf(replaceCorrenctChar));
            System.out.print(startByteCopy.toString() + " ");
            System.out.println();
            counter++;
        }
    }
    public static void differentCombinationsReplace(int counter,int j,int starter,StringBuilder startByteCopy){
        for (int i = counter; i < listOfIdexexOfDifferentBytes.size()+counter; i++) {
            j= (i>=listOfIdexexOfDifferentBytes.size()) ? starter++ :i;
            startByteCopy = new StringBuilder(startByteCopy.toString());
            replaceBits(startByteCopy,j);
            System.out.print(startByteCopy.toString() + " ");
        }
    }

    public static void replaceBits(StringBuilder startByteCopy,int j){
        char charForReplace = startByteCopy.charAt(j) == '1'? '0' :'1';

        startByteCopy.replace(listOfIdexexOfDifferentBytes.get(j),
                listOfIdexexOfDifferentBytes.get(j) + 1,
                String.valueOf(charForReplace));
    }
}
