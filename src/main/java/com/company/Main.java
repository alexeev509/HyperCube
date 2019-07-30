package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static Deque<Integer> queueOfIndexexOfDifferentBytes;
    private static Deque<Integer> queueOfIndexexOfSameBytes;
    public static String input;
    public static String[] massOfInputBytes;
    public static String inputStartByte;
    public static String inputDestinationByte;
    public static StringBuilder result;

    public static void main(String[] args) {
        findOfDifferentTracks(scanner);
    }

    public static void initFields(Scanner scanner) {
        input = scanner.nextLine();
        massOfInputBytes = input.trim().split(" ");
        inputStartByte = massOfInputBytes[0];
        inputDestinationByte = massOfInputBytes[1];
        queueOfIndexexOfDifferentBytes = new ArrayDeque<>();
        queueOfIndexexOfSameBytes = new ArrayDeque<>();
        result = new StringBuilder();

        for (int i = 0; i < inputStartByte.length(); i++) {
            if (inputStartByte.charAt(i) != inputDestinationByte.charAt(i)) {
                queueOfIndexexOfDifferentBytes.add(i);
            } else {
                queueOfIndexexOfSameBytes.add(i);
            }
        }
    }

    public static void findOfDifferentTracks(Scanner scanner) {
        initFields(scanner);

        StringBuilder temp;
        //Меняю неправильные биты без замен правильных
        for (int i = 0; i < queueOfIndexexOfDifferentBytes.size(); i++) {
            temp = new StringBuilder(inputStartByte);
            result.append(temp.toString());
            replacingDifferentBytes(temp);
            result.append("\n");
            queueOfIndexexOfDifferentBytes.add(queueOfIndexexOfDifferentBytes.remove());
        }

        //Меняю неправильные биты с заменами правильных на неправильные
        for (int i = 0; i < queueOfIndexexOfSameBytes.size(); i++) {

            temp = new StringBuilder(inputStartByte);
            result.append(temp.toString());
            //Меняю правильный бит на неверный
            temp = replaceChar(queueOfIndexexOfSameBytes, temp);
            //Добавляю в очередь неправильных элементов - это новый неправильный бит
            queueOfIndexexOfDifferentBytes.add(queueOfIndexexOfSameBytes.peek());
            result.append(" " + temp);
            //Меняю неверные биты на правильные
            replacingDifferentBytes(temp);

            //Удаляю бит, который был правильный и я его изменил на неверный
            queueOfIndexexOfDifferentBytes.removeLast();
            result.append("\n");
            //Делаю круговые сдвиги у очередей
            queueOfIndexexOfDifferentBytes.add(queueOfIndexexOfDifferentBytes.remove());
            queueOfIndexexOfSameBytes.add(queueOfIndexexOfSameBytes.remove());
        }

        System.out.println(result.toString());
    }

    public static void replacingDifferentBytes(StringBuilder temp) {
        for (int q = 0; q < queueOfIndexexOfDifferentBytes.size(); q++) {
            temp = replaceChar(queueOfIndexexOfDifferentBytes, temp);
            result.append(" " + temp);
            queueOfIndexexOfDifferentBytes.add(queueOfIndexexOfDifferentBytes.remove());
        }
    }

    public static StringBuilder replaceChar(Deque<Integer> queueForGettingChar, StringBuilder temp) {
        char replaceChar = temp.charAt(queueForGettingChar.peek()) == '0' ? '1' : '0';
        temp = new StringBuilder(temp.replace(queueForGettingChar.peek(),
                queueForGettingChar.peek() + 1, String.valueOf(replaceChar)));
        return temp;
    }
}


//Алгоритм
//        Нужно составить k способов превратить одну последовательность бит в другую, изменяя за каждый шаг ровно 1 бит, то есть придумать алгоритм перебора таких способов.
//        Без ограничения общности предположим, что нам любое число надо превратить в 1111...11 (k единиц, 2^k - 1).
//
//        Чтобы из некоторой вершины, обозначенной числом из n единиц и k-n нулей, перейти в вершину 2^k - 1, нужно флипнуть как минимум n-k бит (нули превратить в единицы).
//
//        Если вы каждый бит меняете только 1 раз за проход, то все вершины в этом проходе гарантированно разные.
//        0101 -> 0111 -> 1111
//
//        Если Вы зафиксируете некоторую последовательность из k-n позиций, зациклите ее в смысле графа и будете менять биты в порядке, указанном циклом, при этом каждый раз начинать с новой вершины цикла, то все вершины в каждом проходе и между проходами тоже будут гарантированно разные, за исключением начальной и конечной:
//        01000110 - > 11111111
//        Позиции различных бит 1, 3, 4, 5, 8. Зафиксируем эту последовательность и будем менять биты в указанном порядке:
//        01000110 -> 11000110 -> 11100110 -> 11110110 -> 11111110 -> 11111111
//        Теперь начнем с позиции 3, дальше 4, 5, 8 и только потом 1:
//        01000110 -> 01100110 -> 01110110 -> 01111110 -> 01111111 -> 11111111
//        Теперь 4, 5, 8, 1, 3:
//        01000110 -> 01010110 -> 01011110 -> 01011111 -> 11011111 -> 11111111
//        и т.д.
//
//        Так получим k-n вершинно непересекающихся путей, надо сделать еще n. Как?
//        Мы сначала флипнем какой-нибудь уже "правильный" бит ("испортим", 1 превратим в 0), флипнем остальные, а потом вернем единицу на место. Флипнем другой "правильный" бит, потом "неправильные" по очереди, вернем бит на место. Правильных битов по условию n, поэтому их нам как раз хватит.
//        Пример: пусть нужно найти пути из 00111 в 11111. Кратчайшие пути - флипнуть 2 бита:
//        00111 -> 01111 -> 11111
//        00111 -> 10111 -> 11111
//        Всё, кратчайшие варианты закончились.
//        Портим первый "правильный" бит:
//        00111 -> 00110 -> 01110 -> 11110 -> 11111
//        Портим второй:
//        00111 -> 00101 -> 01101 -> 11101 -> 11111
//        Портим третий:
//        00111 -> 00011 -> 01011 -> 11011 -> 11111
//
//        С двумя произвольными узлами принцип такой же, делим биты на "правильные" и "неправильные", просто "правильный" не обязательно 1, а "неправильный" не обязательно 0.
//        Варианты не надо хранить в памяти! Иначе ее не хватит. Надо тут же превращать обозначение узла в битовый формат и выводить на печать.