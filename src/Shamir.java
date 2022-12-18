import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;


    public class Shamir {
        int [] poly;
        int k,n;

        public Shamir(){}
        private Shamir(int polyLength,int k,int n){
            poly=new int[polyLength];
            this.k=k;
            this.n=n;
        }
        public Shamir Init(int n, int k){

            Shamir shamir=new Shamir(k-1,k,n);
//
//            Random rand = new Random();
//            for (int i = 0; i < k-1; i++) {
//                shamir.poly[i]=(rand.nextInt(11));
//            }
        //}
            for (int i = 0; i < 3; i++) {
                shamir.poly[i]=i;
            }
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
                    sum += modolo( poly[j] * ((int)Math.pow(i,j+1)) );
                    sum=modolo(sum);
                }
                shares[i]=sum;
            }
            return  shares;
        }

        public double Recover (int[] A, int[] shares ){

            if(A.length<k) {
                System.out.println("not enough players");
                return 0;
            }
            if(A.length>k){
                A= Arrays.copyOfRange(A,0,k);
            }
            double sum=0;
            for(int playerNumber : A){
                sum+=modolo(shares[playerNumber]*help1(playerNumber,A));
            }
            return sum;
        }
        private int help1(int playerNumber,int[]A ){
            int sum=1;
            for( int otherPlayerNumber : A){
                if (otherPlayerNumber!=playerNumber){
                    sum*=modolo((0-otherPlayerNumber))*modolo2((playerNumber-otherPlayerNumber));
                    sum=modolo(sum);
                }
            }
            return sum;
        }
        private int modolo(int i){
            while(i<0)
                i+=11;
           return i%11;
        }
        private int modolo2(int i){
            int count=0;
            int temp=modolo(i);
            while(temp!=1){
                count++;
                temp=modolo(temp+i);
            }
            return count;
        }




    public static void main(String[] args) {

        Shamir shamir=new Shamir();
        shamir=shamir.Init(10,4);
        int[] shares=shamir.Share(1);
        for (int i = 0; i < shares.length; i++) {
            System.out.println(shares[i]);
        }
        int [] players= {1,2,5,6,7};
        System.out.print("the secret is :");
        System.out.println(shamir.Recover(players,shares));

    }

 }