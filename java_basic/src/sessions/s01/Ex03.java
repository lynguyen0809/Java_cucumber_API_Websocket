package sessions.s01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex03 {
    public static void main(String[] args) {
        Integer[] arr = {1,2,77,8,55,10,33};
//        int[] arr = {0,-1};
//        int[] arr = {-5,-1,-6,8};
        topThreeLargest(arr);
    }

    public static List<Integer> topThreeLargest(Integer[] arr){
        int count = 1;
        int temp = 0;
        List<Integer> arrTopThreeLargest = new ArrayList<Integer>(){};
        int s = arr.length;

        // Check if array is valid
        if(arr.length<3){
            System.out.println("Invalid, array size is less than 3");
        }

        // Sort the arr from low to high
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        //Print out 3 highest values in the arr
        for(int i=1; i <= s; i++){
            if(count < 4){
                if(temp != arr[s - i]){
                    System.out.print(arr[s - i] + " ");
                    arrTopThreeLargest.add(arr[s-i]);
                    temp = arr[s - i];
                    count++;
                } else {
                    System.out.print(arr[s - i] + " ");
                    System.out.println("Oops! This is not a distinct array");
                    break;
                }
            }
        }
        return arrTopThreeLargest;
    }
}
