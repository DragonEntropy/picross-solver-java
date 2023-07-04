public class Util {
    public static int fact(int x) {
        return x > 1 ? x * fact(x - 1) : 1;
    }

    public static int binomial(int n, int k) {
        return fact(n) / (fact(k) * fact(n - k));
    }
}