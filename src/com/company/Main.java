package com.company;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.List;
public class Main {


    public static void main(String[] args) throws IOException {
        // write your code here
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
        String name;
        ArrayList aryLst = new ArrayList();
        ListIterator iter = aryLst.listIterator();
        File storage = new File("transactions.txt");
        storage.createNewFile();
        PrintWriter writer = new PrintWriter(storage);
        ArrayList holding = new ArrayList();
        int listNumber = 0;
        int whosAcount = 0;
        int howMuch = 0;


        do {
            Scanner kbReader = new Scanner(System.in);
            System.out.print("Please enter the name to whom the account belongs. (\"Exit\" to abort) ");
            name = kbReader.nextLine();

            if (!name.equalsIgnoreCase("exit")) {
                listNumber = listNumber + 1;

                writer.print("New account " + name + "[" + listNumber + "] ");
            }


            if (!name.equalsIgnoreCase("EXIT") && !name.equalsIgnoreCase("debug")) {
                System.out.print("Please enter the amount of the deposit. ");
                double amount = kbReader.nextDouble();
                holding.add(0, 0);
                holding.add(listNumber, amount);

                writer.println("amount inside account: " + amount);

                System.out.println(" "); // gives an eye pleasing blank line
                // between accounts
                bankAccount theAccount = new bankAccount(name, amount);
                iter.add(theAccount);
            }
        } while (!name.equalsIgnoreCase("EXIT") && !name.equalsIgnoreCase("debug"));


        bankAccount ba = (bankAccount) iter.previous();
        double maxBalance = ba.balance; // set last account as the winner so far
        String maxName = ba.name;
        while (iter.hasPrevious()) {
            ba = (bankAccount) iter.previous();
            if (ba.balance > maxBalance) {
                // We have a new winner, chicken dinner
                maxBalance = ba.balance;
                maxName = ba.name;
            }
        }
        System.out.println(" ");
        System.out.println("The account with the largest balance belongs to "
                + maxName + ".");
        System.out.println("The amount is $" + fmt.format(maxBalance) + ".");

        writer.println(" ");
        writer.print("Most valuable account is: " + maxName + ":   worth currently: " + maxBalance);

        writer.flush();
        writer.close();

        if (name.equalsIgnoreCase("debug")) {
            Scanner steal = new Scanner(System.in);

            do {
                System.out.println("Which account would you like to steal from (enter number)");
                whosAcount = steal.nextInt();
                System.out.println("How much would you like to steal?");
                howMuch = steal.nextInt();

                writer.println("Trying to steal from " + whosAcount + "'s account for the value of " + howMuch);

                System.out.println(holding.get(whosAcount));



                if (holding.get(whosAcount) > howMuch) {

                    double temp2 = holding.get(whosAcount) - howMuch;

                    writer.println(whosAcount + "'s account is now worth " + temp2);
                    writer.println("New stolen account is now worth " + howMuch);


                } else {
                    System.out.println("Can't, not enough funds");
                    writer.println("Job unsuccessful, not enough funds in target account!");

                }

                writer.flush();
                writer.close();
                return;

            } while (true);


        }
    }
}
