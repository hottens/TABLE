import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class TABLE {
    static List<Integer> primes = new ArrayList<>();

    // Calculates the position of a value in a m*n matrix
    public static long calculatePositionOf(long number, long m, long n){
        long position = 0;
        for(long i= 1; i <= Math.min(m, number); i++){
            for(long j = 1; j <= Math.min(n, number); j++){
                if(number >= i*j) position ++;
                else break;
            }
        }
        return position;
    }

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine();
        String[] temp = input.split(" ");

        long m = Long.parseLong(temp[0]);
        long n = Long.parseLong(temp[1]);
        long index = Long.parseLong(temp[2]);

        primes = sieveOfEratosthenes(m*n);

        long result = findNumberAtPosition(index, m*n, 1, m, n);
        long offset = calculatePositionOf(result, m, n) - index;

        // calculate the right number at the right index
        while(true) {
            while (calculateOccurencesOf(result, Math.min(m, n), Math.max(m, n)) == 0)
                result--;
            offset--;
            if(offset==0) break;
            result--;
        }
        System.out.println(result);
    }

    // returns the number on an index that is within 5 from the asked index
    public static long findNumberAtPosition(long index, long upperbound, long lowerbound, long m, long n){
        long currentIndex = calculatePositionOf((upperbound + lowerbound) / 2, m, n);
        //System.out.println(currentIndex + ": " + (upperbound+lowerbound)/2);
        if(currentIndex-index<5&&index<currentIndex) return (upperbound + lowerbound) / 2;
        if(index < currentIndex) return findNumberAtPosition(index, 1+(upperbound + lowerbound) / 2, lowerbound, m, n);
        else return findNumberAtPosition(index, upperbound, (upperbound + lowerbound) / 2, m, n);
    }

    // list all primes up to sqrt(n)
    public static List<Integer> sieveOfEratosthenes(long n) {
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

    // calculates how many times a value occurs in a m*n matrix
    private static int calculateOccurencesOf(long i, long min, long max){
        //primes.forEach(p -> System.out.println(p));
        ArrayList<Integer> pfactors = new ArrayList<>();

        for(long x = i; x>1;){
            long x1 = x;
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

    // Given a set of integers, compute all products which do not exceed max
    private static Set<Integer> permutate(List<Integer> list, long max) {
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
