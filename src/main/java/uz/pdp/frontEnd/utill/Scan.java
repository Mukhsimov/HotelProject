package uz.pdp.frontEnd.utill;

import java.util.Scanner;

public class Scan {
    private static final Scanner scanInt = new Scanner(System.in);
    private static final Scanner scanStr = new Scanner(System.in);

    public static String getStr(String hint){
        System.out.print(hint + ": ");
        return scanStr.nextLine();
    }
    public static int getInt(String hint){
        System.out.print(hint + ": ");
        return scanInt.nextInt();
    }
    public static double getDouble(String hint){
        System.out.print(hint + ": ");
        return scanInt.nextDouble();
    }
}