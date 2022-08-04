package sessions.s01;

import java.util.ArrayList;

public class My_Stuff {
    public static void main(String[] args) {

        // Lambda
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(4);
        numbers.add(15);
        numbers.add(27);

        numbers.forEach(System.out::println);

        // Throw exception
        int age = 17;
        if(age < 18){
            throw new RuntimeException("Damn, not enough age to ...");
        } else
            System.out.println("Geez, welcome babe!");

        // Try..catch exception
        Integer[] arr = {3,4,5};
        System.out.println(sumArr(arr));
        try{
            System.out.println(arr[2]);
        }catch(Exception e){
            System.out.println("error: " + e);
        }
        finally {
            System.out.println("R.I.P");
        }

        // call functions
        int a = sumArr1(1,2,3);
        System.out.println(a);

        // change while -> for
//        int i = 1;
//        while (i<= 100){
//            System.out.println("number"+i%2);
//            ++i;
//        }

        for(int i = 1;i <=100;i++ ){
            System.out.println("number "+i%2);
        }
    }

    public static Integer sumArr(Integer[] arr){
        Integer sum = 0;
        for(Integer a : arr){
            sum += a;
        }
        return sum;
    }

    // the same with sumArr but we can input the array directly to the function without initial
    public static Integer sumArr1(Integer ... arr){
        Integer sum = 0;
        for(Integer a : arr){
            sum += a;
        }
        return sum;
    }
}
