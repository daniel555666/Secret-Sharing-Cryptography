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
                    sum += modolo( poly[j] * ((int)Math.pow(i+1,j+1)) );
                    sum=modolo(sum);
                }
                shares[i]=sum;
            }
            return  shares;
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
            }
            return modolo(sum);
        }
        private int help1(int playerNumber,int[]A ){
            int sum=1;
            for( int otherPlayerNumber : A){
                if (otherPlayerNumber!=playerNumber){
                    sum*=(0-otherPlayerNumber)*modolo2(playerNumber-otherPlayerNumber);
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
            int p=N+1;
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

        Shamir shamir=Init(50,4);     // set N,K
        int[] shares=shamir.Share(0);    //Enter the secret
        System.out.println("players share:");
        for(int i=1;i<=shares.length;i++){
            System.out.print( "player "+i+": "+shares[i-1]+" , ");
        }
        System.out.println("\n");
        int [] players= {21,6,30,12,10,46,9,8,5,2,17,16,25,11,19,27};    // choose the players to recover the secret
        System.out.print("the secret is :");       //the first player is number 1!
        System.out.println(shamir.Recover(players,shares));

    }

 }