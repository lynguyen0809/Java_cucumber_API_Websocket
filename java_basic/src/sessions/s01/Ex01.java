package sessions.s01;

import java.util.Arrays;

public class Ex01 {
    public static void main(String[] args) {
        String[] arr = new String[100];
        for(int i = 0; i<arr.length; i++)
        {
            int a = i%3;
            int b = i%5;
            if(a == 0 && b == 0){
                arr[i] = "FizzBuzz";
            } else if(a == 0){
                arr[i] = "Fizz";
            } else if(b == 0){
                arr[i] = "Buzz";
            } else arr[i] = String.valueOf(i);
        }
        System.out.println("Final Arrays is: " + Arrays.toString(arr));
    }
}
