import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;

/*
This algorithm will find the k'th position of all sorted numbers in a m*n table
where (m_i,n_j) = m_i*n_j.
It does so by iterating over some of the numbers between 1 and m*n.
For each of these numbers the amount of occurrences in the m*n table is calculated.
If the addition of all these occurrences exceeds our k, we found the right number.
 */
public class Main2 {
    static List<Integer> primes = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine();
        String[] temp = input.split(" ");

        int m = Integer.parseInt(temp[0]);
        int n = Integer.parseInt(temp[1]);
        int index = Integer.parseInt(temp[2])-1;

        int max = Math.max(m,n);
        int min = Math.min(m,n);

        primes = sieveOfEratosthenes(m*n);
        //primes.forEach(p->System.out.println(p));

        // Choose best position to start counting
        // This could either be (1,1), (m,n) or some fixed position p,
        // which we can calculate from a N*N matrix, and also its corresponding value
        int p = min*(min+1)/2;
        int dir = 1;
        int startpoint = 1;
        if(index < p) {
            if (index > p - index){
                dir = -1;
                startpoint = dir + (p%min == 0 ? (p/min)*(p/min) : (((p/min)+1)*((p/min)+1) - (p/min)+1));
                index = p - index;
            }
        } else {
            if(m*n - index < index - p){
                startpoint = m*n;
                dir = -1;
                index = m*n - index;
            } else {
                startpoint = dir + (p%min == 0 ? (p/min)*(p/min) : (((p/min)+1)*((p/min)+1) - (p/min)+1));
                index = index - p;
            }
        }

        //System.out.println("startpoint: " + startpoint + " index: " + index);
        int i = startpoint;

        Instant before = Instant.now();
        int index1 = index;
        while(i >= 1 && i <= m*n){

            //System.out.println(index + " : " + i);
            index1 -= calculateDivisors2Of(i,min,max);
            if(index1 <= 0) break;

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
        System.out.println("primes:" + (after.getEpochSecond() - before.getEpochSecond()));

        before = Instant.now();
        int index2 = index;
        i = startpoint;
        while(i >= 1 && i <= m*n){

            //System.out.println(index + " : " + i);
            index2 -= calculateDivisorsOf(i,min,max);
            if(index2 <= 0) break;

            i+=dir;
        }
        System.out.println(i);
        after = Instant.now();
        System.out.println("no primes:" + (after.getEpochSecond() - before.getEpochSecond()));
    }

    private static int calculateDivisorsOf(int i, int min, int max) {
        int nDivisors = 0;
        int incrementer = 1+i%2;
        for(int n_i = 1; 2*n_i<=Math.min(i,2*max); n_i+=incrementer){
            int m_i = i/n_i;
            if(i%n_i==0 && m_i <= max){

                //System.out.println(i + " % " + n_i + " = " + i%n_i);
                if(n_i==m_i) nDivisors++;
                else {
                    if(n_i <= min) nDivisors++;
                    if(m_i <= min) nDivisors++;
                }
            }
        }
        return nDivisors;
    }

    private static int calculateDivisors2Of(int i, int min, int max){
        //primes.forEach(p -> System.out.println(p));
        ArrayList<Integer> pfactors = new ArrayList<>();

        for(int x = i; x>1;){
            int x1 = x;
            Optional<Integer> curPrime = primes.stream().filter(p -> x1%p==0).findFirst();
            if(curPrime.isPresent()){
                pfactors.add(curPrime.get());
                x /= curPrime.get();
            } else break;
        }
        //pfactors.forEach(p -> System.out.println(p));
        Set<Integer> factors = permutate(pfactors, max);
        int result = i<=max ? 1 : 0;
        for(int f : factors)
            if(i/f <= min) result ++;

        return result;
    }

    public static List<Integer> sieveOfEratosthenes(int n) {
        int max = (int) Math.sqrt(n);
        boolean prime[] = new boolean[max + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= max; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= max; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= max; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private static Set<Integer> permutate(List<Integer> list, int max) {
        Set<Integer> result = new HashSet<>();
        for (int i : list) {
            List<Integer> add = new ArrayList<>();
            for(int r : result)
                if(r*i <= max) add.add(r*i);

            result.addAll(add);
            result.add(i);
        }
        return result;
    }
}
