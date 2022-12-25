import java.util.*;


public class SecretSharing {

    int n,k;
    int mod;

    public SecretSharing(int n,int k){
        this.n=n;
        this.k=k;
        this.mod=2;
    }


    public int[][] Share(int s){

        if(s!=0 && s!=1){
            System.out.println(" error : b need to be 1 or 0");
            return null;
        }

        Random random=new Random();
        int [] arr=new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=i;
        }
        List<int[]> allGrups=new ArrayList<>();
        combinationUtil(arr, n, k, 0, new int[k], 0,allGrups);

        int[][] shares=new int[n][allGrups.size()];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < allGrups.size(); j++) {
                shares[i][j]=-1; // if we have share with -1 this mean that player i dont have a share of group j.
            }
        }

        for(int i=0;i<allGrups.size();i++){
            int sum=0;

            for (int j = 0; j < this.k-1 ; j++) {
                int rand=random.nextInt(this.mod); //allGrups.get(i)[j]][i]
                int playerNumber=allGrups.get(i)[j];
                shares[playerNumber][i]=rand ;
                sum+=rand;
            }
            shares [allGrups.get(i)[k-1]] [i] =modolo(s-sum);
        }
        return shares;
    }

    public int Recover (int[] A, int [][] shares ) {

        for (int i : A) {
            if (i < 0 || i >= this.n) {
                System.out.println("no such number player - " + i);
                return 0;
            }
        }
        if (A.length < k) {
            System.out.println("not enough players");
            return 0;
        }
        if (A.length > k) {
            A = Arrays.copyOfRange(A, 0, k);
        }
        boolean flag;
        int s=0;
        for (int i = 0; i <shares[0].length; i++) {
            s=0;flag=true;
            for (int player: A) {
                if( shares[player][i]==-1) {
                    flag=false;
                    break;
                }
                s+=shares[player][i];
            }
            if(flag) {
                return modolo(s);
            }
        }
        return -1;
    }
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
    private int modolo(int i){
        while(i<0) {
            i += this.mod;
        }
        return i%this.mod;
    }
    public static SecretSharing Init(int n, int k){

        SecretSharing secretSharing=new SecretSharing(n,k);
        return secretSharing;
    }

    public static void main(String[] args) {
        int n=5;
        int k=3;
        SecretSharing secretSharing= Init(n,k); //the first share is 0!
        int[][]shares=secretSharing.Share(0);
        int[] A={0,2,4};
        for (int i : A) {
            System.out.println("player "+i+" shares:");
            for (int j = 0; j < shares[0].length; j++) {
                if(shares[i][j]!=-1){
                    System.out.print("share of group "+j+": "+shares[i][j]+",  ");
                }
            }
            System.out.println("\n");
        }

        System.out.println("the secret is: "+secretSharing.Recover(A,shares));
    }

}
