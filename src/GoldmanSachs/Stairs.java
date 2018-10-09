package GoldmanSachs;

/*
https://www.geeksforgeeks.org/count-ways-reach-nth-stair-using-step-1-2-3/
stairs , 1,2,3  how many ways to reach the top
for example:
    input 4, results: 7
    input 3, results: 4
solution:
1. Recursive Method
2. Dynamic Programming
 */
public class Stairs {

    public static void main(String args[]) {
        int n = 4;
        int ways = countWays(n);
        System.out.println(ways);
        n = 3;
        ways = countWays(n);
        System.out.println(ways);
        n = 5;
        ways = countWays(n);
        System.out.println(ways);

        n = 4;
        ways = dpSolution(n);
        System.out.println(ways);
        n = 3;
        ways = dpSolution(n);
        System.out.println(ways);
        n = 5;
        ways = dpSolution(n);
        System.out.println(ways);
    }

    /*
       recursion
     */
    private static int countWays(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) return 2;
        return countWays(n-1) + countWays(n-2) + 1;
    }

    /*
       d[1] = 1
       d[2] = d[1] + 1 = 2
       d[3] = d[1] + 1 + d[2] = 4
       d[4] = d[2] + d[3] + 1 = 7
       d[5] = d[4] + d[3] + 1 = 12
     */
    private static int dpSolution(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        if (n <= 2) return dp[n-1];
        for (int i = 3; i <= n; i++) {
            dp[i-1] = dp[i-3] + dp[i-2] + 1;
        }
        return dp[n-1];
    }
}
