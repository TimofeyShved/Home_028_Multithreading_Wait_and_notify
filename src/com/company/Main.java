package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<String> myString = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        new Operator().start();
        new Mashine().start();

        //2 пример, когда делаем действие до ответа команд потока
        MyPotok myPotok = new MyPotok();
        myPotok.start();
        synchronized (myPotok){
            myPotok.wait();
        }
        System.out.println(myPotok.sum);
    }

    static class Operator extends Thread{ // то что мы вводим руками в командной строке, для потока
        public void run(){
            Scanner scanner = new Scanner(System.in); // то что будет считывать
            while (true){
                synchronized (myString){ // синхронизация String
                    myString.add(scanner.nextLine()); // ввод данных в массив
                    myString.notify();
                }
                try {
                    Thread.sleep(500); // заставить уснуть на некоторое время
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Mashine extends Thread{ // то что выводит пк, во время 2 потока
        public void  run(){
            while (myString.isEmpty()){
                synchronized (myString){ // синхронизация String
                    try {
                        myString.wait(); // выход из потока на время
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(myString.remove(0)); // вывести из данные из коллекции String
                }
            }
        }
    }

}

class MyPotok extends Thread{
    int sum;
    public void run(){
        synchronized (this){
            for (int i=0; i<5; i++){
                sum+=i;
            }
            notify(); // передает системе, что всё завершино и можно не ждать
        }
    }
}
