import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;


    public class Shamir {
        int [] poly;
        int k,n,mod;

        public Shamir(){}
        private Shamir(int polyLength,int k,int n){
            poly=new int[polyLength];
            this.k=k;
            this.n=n;
            this.mod=findPrime(n);
        }
        public static Shamir Init(int n, int k){

            Shamir shamir=new Shamir(k-1,k,n);
            shamir.poly=new Random().ints(1, n+1)
                        .distinct().limit(shamir.poly.length).toArray();
            return shamir;
        }
        public int[] Share (int b){

            if(b!=0 && b!=1){
                System.out.println(" error : b need to be 1 or 0");
                return null;
            }

            int [] shares= new int[this.n];

            for (int i = 0; i <this.n ; i++) {
                int sum=b;
                for (int j = 0; j < poly.length; j++) {
                    sum += ( poly[j] * (myPow(i+1,j+1)) );
                    sum=modolo(sum);
                }
                shares[i]=sum;
            }
            return  shares;
        }
        private int myPow (int n,int pow){
            int sum=n;
            for (int i = 1; i < pow; i++) {
                sum=sum*n;
                sum=modolo(sum);
            }
            return sum;
        }

        public int Recover (int[] A, int[] shares ){

            for (int i:A) {
                if(i<=0||i>this.n){
                    System.out.println("no such number player - "+i);
                    return 0;
                }
            }
            if(A.length<k) {
                System.out.println("not enough players");
                return 0;
            }
            if(A.length>k){
                A= Arrays.copyOfRange(A,0,k);
            }

            int sum=0;
            for(int playerNumber : A){
                sum+=shares[playerNumber-1]*help1(playerNumber,A);
                sum=modolo(sum);

            }
            return sum;
        }
        private int help1(int playerNumber,int[]A ){
            int sum=1;
            for( int otherPlayerNumber : A){
                if (otherPlayerNumber!=playerNumber){
                    sum*=modolo((0-otherPlayerNumber)*modolo2(playerNumber-otherPlayerNumber));
                    sum=modolo(sum);
                }
            }
            return sum;
        }
        private int modolo(int i){
            while(i<0) {
                i += this.mod;
            }
           return i%this.mod;
        }
        private int modolo2(int i){
            int count=1;
            while(modolo(i*count)!=1){
                count++;
            }
            return count;
        }

        public static int findPrime(int N){
            int p=(N+1);
            while( ! isPrime(p)){
                p++;
            }
            System.out.println("\nWe use modulo "+p+"\n");
            return p;
        }
        public static boolean isPrime (int n){
            if(n==2)
                return true;
            if(n%2==0)
                return false;
            for( int i = 3; i <=Math.sqrt(n) ; i=i+2) {
                if(n%i==0)
                    return false;
            }
            return true;
        }




    public static void main(String[] args) {

        int n=8;  // choose n,k
        int k=4;

        Shamir shamir=Init(n,k);     // set N,K
        int[] shares=shamir.Share(1);    //Enter the secret array with the shares of every player

        System.out.println("players share:"); // print all the players share
        for(int i=1;i<=shares.length;i++){
            System.out.print( "player "+i+": "+shares[i-1]+" , ");
        }
        System.out.println("\n");

        int [] players= {1,3,5,2};    // choose the players to recover the secret. the first player is number 1!
        //the first player is number 1! ,  [1,n] .

        System.out.print("the secret is :");
        System.out.println(shamir.Recover(players,shares)); // recover the secret

    }

 }