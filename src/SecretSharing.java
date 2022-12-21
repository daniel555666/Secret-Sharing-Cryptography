import java.util.*;


public class SecretSharing {

    int n,k;
    static int groupsNumber;
    int mod;

    public SecretSharing(int n,int k){
        this.n=n;
        this.k=k;
        this.mod=11;
    }


    public Map<String,Integer> Share(int s){

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
        groupsNumber= allGrups.size();

        Map<String,Integer>shares=new HashMap<>();



        for(int i=0;i<allGrups.size();i++){
            int sum=0;

            for (int j = 0; j < this.k-1 ; j++) {
                int rand=random.nextInt(11);
                shares.put( i+","+allGrups.get(i)[j], rand );
                sum+=rand;
            }
            shares.put( i+","+allGrups.get(i)[k-1], modolo(s-sum) );
        }
        return shares;
    }

    public int Recover (int[] A, Map<String,Integer> shares ) {

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
        for (int i = 0; i <groupsNumber; i++) {
            s=0;flag=true;
            for (int j = 0; j < A.length; j++) {
                if( !shares.containsKey(i+","+A[j])) {
                    flag=false;
                    break;
                }
                s+=shares.get(i+","+A[j]);
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
        SecretSharing secretSharing= Init(12,6); //the first share is 0!
        Map<String,Integer> shares=secretSharing.Share(0);
        int[] A={0,2,8,4,11,3};
        System.out.println("the secret is: "+secretSharing.Recover(A,shares));
    }

}
