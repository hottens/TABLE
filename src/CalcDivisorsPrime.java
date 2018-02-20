import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class CalcDivisorsPrime {
    public static List<Integer> sieveOfEratosthenes(int n) {
        int max = (int)Math.sqrt(n);
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
            if (prime[i] && n%i==0) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private static Set<Integer> permutate(List<Integer> list, int max) {
        Set<Integer> result = new HashSet<>();
        result.add(1);

        for (int i : list) {
            List<Integer> add = new ArrayList<>();
            for(int r : result)
                if(r*i <= max) add.add(r*i);

            result.addAll(add);
            result.add(i);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        String input = in.readLine();

        int n = Integer.parseInt(input);

        List<Integer> primes = sieveOfEratosthenes(n);
        //primes.forEach(p -> System.out.println(p));
        ArrayList<Integer> pfactors = new ArrayList<>();

        for(int x = n; x>1;){
            int x1 = x;
            int curPrime = primes.stream().filter(p -> x1%p==0).findFirst().get();
            pfactors.add(curPrime);
            x /= curPrime;
        }
        //pfactors.forEach(p -> System.out.println(p));
        Set<Integer> factors = permutate(pfactors, (int)Math.sqrt(n));
        factors.forEach(p -> System.out.println(p));
    }
}
