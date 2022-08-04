package sessions.s01;

public class Ex02 {
    public static void main(String[] args) {
        int[] arr1 = {1,2,4,5,6,3,10,20,22,10,2,2,3};
        int[] arr2 = {2,4,6,33,55};
        boolean result = checkUniqueValues(arr1);
        if(!result){
            System.out.println("false - elements in array are not unique");
        } else System.out.println("true - elements in array are unique");
    }

    // Function
    public static boolean checkUniqueValues(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
