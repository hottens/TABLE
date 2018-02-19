import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine();
        String[] temp = input.split(" ");

        int m = Integer.parseInt(temp[0]);
        int n = Integer.parseInt(temp[1]);
        int index = Integer.parseInt(temp[2])-1;
        int iCounter = 0;

        Map<Integer,Integer> dict = new HashMap<>();

        int max = Math.max(m,n);
        int min = Math.min(m,n);

        Instant before = Instant.now();

//        for(int i = 1; i <= max; i++) {
//            for (int j = 1; j <= Math.min(i,min); j++) {
//                int value = dict.get(i * j) == null ? 0 : dict.get(i * j);
//                if (i == j || i > min) dict.put(i * j, 1 + value);
//                else dict.put(i * j, 2 + value);
//            }
//        }
        int p = min*(min+1)/2;
        int dir = 1;
        int startpoint = 1;
        if(index < p) {
            if (index > p - index){
                dir = -1;
                startpoint = dir + (p%min == 0 ? (p/min)*(p/min) : (((p+1)/min)*((p+1)/min) - (p+1)));
                index = p - index;
            }
        } else {
            if(m*n - index < index - p){
                startpoint = m*n;
                dir = -1;
                index = m*n - index;
            } else {
                startpoint = dir + (p%min == 0 ? (p/min)*(p/min) : (((p+1)/min)*((p+1)/min) - (p+1)));
                index = index - p;
            }
        }

        System.out.println("startpoint: " + startpoint + " index: " + index);
        int i = startpoint;

        while(i >= 1 && i <= m*n){

            System.out.println(index + " : " + i);
            for(int n_i = 1; 2*n_i<Math.min(i,2*max); n_i++){
                int m_i = i/n_i;
                if(i%n_i==0 && m_i <= max){

                    System.out.println(i + " % " + n_i + " = " + i%n_i);
                    if(n_i==m_i) index --;
                    else {
                        if(n_i <= min) index--;
                        if(m_i <= min) index--;
                    }
                }
            }
            if(index <= 0) break;

            i+=dir;
        }
        System.out.println(i);

//        for(int i = 1; i <= min; i++) {
//            for (int j = i; j/i <= Math.min(max,index); j+=1) {
//                int value = dict.get(i * j) == null ? 0 : dict.get(i * j);
//                if (i == j || j > min) dict.put(i * j, 1 + value);
//                else dict.put(i * j, 2 + value);
//                //System.out.println("" + i + " * " + j + " = " + i*j);
//            }
//        }
//        for(int i = 1; i <= m*n; i++){
//            iCounter+= dict.get(i) == null ? 0 : dict.get(i);
//            //System.out.println("" + i + ": " + (dict.get(i) == null ? 0 : dict.get(i)));
//            if(iCounter>=index) {
//                System.out.println(i);
//                break;
//            }
//        }
        Instant after = Instant.now();
        System.out.println(after.getEpochSecond() - before.getEpochSecond());
    }

    public static List<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 0; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}
