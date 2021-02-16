package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<String> myString = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        new Operator().start();
        new Mashine().start();
    }

    static class Operator extends Thread{
        public void run(){
            Scanner scanner = new Scanner(System.in);
            while (true){
                synchronized (myString){
                    myString.add(scanner.nextLine());
                    myString.notify();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Mashine extends Thread{
        public void  run(){
            while (myString.isEmpty()){
                synchronized (myString){
                    try {
                        myString.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(myString.remove(0));
                }
            }
        }
    }

}
