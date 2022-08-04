package sessions.s02;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static sessions.s01.Ex03.topThreeLargest;

public class EX05_extra {
    static List<String> zeroToNineTeen = Arrays.asList("zero","one","two","three","four","five","six","seven","eight","nine","ten", "eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen");
    static List<String> tens = Arrays.asList("twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety");
    static List<String> moreTens = Arrays.asList("hundred","thousand","million","billion","trillion","quadrillion","quintillion");
    static String negative = "negative";


    public static String convertNumberToWords(int n) {
        if (n < 0) {
            return negative + " " + convertNumberToWords(abs(n));
        }

        if (n < 20) {
            return zeroToNineTeen.get(n);
        }

        if (n < 100) {
            return tens.get(n / 10 - 2) + ((n % 10 != 0) ? " " + zeroToNineTeen.get(n % 10) : "");
        }

        if (n < 1000) {
            return zeroToNineTeen.get(n / 100) + " " + moreTens.get(0) + ((n % 100 != 0) ? " and " + convertNumberToWords(n % 100) : "");
        }

        if (n < 1000000) {
            return convertNumberToWords(n / 1000) + " " + moreTens.get(1) + ((n % 1000 != 0) ? " " + convertNumberToWords(n % 1000) : "");
        }

        if (n < 1000000000) {
            return convertNumberToWords(n / 1000000) + " " + moreTens.get(2) + ((n % 1000000 != 0) ? " " + convertNumberToWords(n % 1000000) : "");
        }

        return convertNumberToWords(n / 1000000000) + " " + moreTens.get(3)  + ((n % 1000000000 != 0) ? " " + convertNumberToWords(n % 1000000000) : "");
    }

    public static String convertAnyNumberToWords(long number){

        String currentString = " ";
        String tempString = "";
        int index = 0;

        do {
            if(index > moreTens.size()-1){
                return currentString = "This " + number + " is out of range, system has not support yet";
            }

            int n = (int) (number % 1000);
            if (n != 0){
                tempString = convertNumberToWords(n);
                currentString = tempString + " " + ((index >=1) ? moreTens.get(index) + " " + currentString : " " + currentString);
            }
            index++;
            number /= 1000;
        } while (abs(number) > 0);

        return (currentString).trim();
    }

    public static void readNumberInList (List<Integer> arr){
        System.out.printf("%n");
        for(Integer n : arr){
            System.out.printf("%d = '%s'%n", n, convertNumberToWords(n));
        }
    }

    public static void main(final String[] args) {
//        Integer[] arr = {1,2,77,8,55,10,33};
//        List<Integer> arrTopThreeLargest = topThreeLargest(arr);
//        readNumberInList(arrTopThreeLargest);

        int n;
        n = 0;
        System.out.printf("%10d = '%s'%n", n, convertNumberToWords(n));
        n = 100;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));
        n = 19;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));
        n = 1000000;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));
        n = 999999999;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));
        n = -4560;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));
        n = Integer.MAX_VALUE;
        System.out.printf("%10d = '%s'%n", n, convertAnyNumberToWords(n));

        long a = 123456789098765432L;
        System.out.println(a + " : " + convertAnyNumberToWords(a));
    }
}
