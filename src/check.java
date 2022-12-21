import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class check {

    private static void combinationUtil(int arr[], int n, int r, int index, int combo[], int i, List<int[]> l){
        if (index == r){
            l.add(Arrays.copyOf(combo, combo.length));
            return ;
        }
        if (i >= n)
            return;
        combo[index] = arr[i];
        combinationUtil(arr, n, r, index + 1, combo, i + 1,l);
        combinationUtil(arr, n, r, index, combo, i+1,l);
    }
   public static void combinationUtil2(int arr[], int n, int r, int index, int combo[], int i){
        if (index == r){
            System.out.println("{");
            for (int j = 0; j < r; j++)
                System.out.print( combo[j] );
            System.out.print("}\t");
            return;
        }
        if (i >= n)
            return;
        combo[index] = arr[i];
        combinationUtil2(arr, n, r, index + 1, combo, i + 1);
        combinationUtil2(arr, n, r, index, combo, i+1);
    }
    public static void main(String[] args) {

        int arr[] = {1, 2, 3, 4, 5};
        int r = 3;
        int n = 5;
        int combo[]=new int[r];
        List<int[]>l=new ArrayList<>();
        combinationUtil2(arr, n, r, 0, combo, 0);
        combinationUtil(arr, n, r, 0, combo, 0,l);
        int a=6;
    }
}
