import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class H_SapXepKhoHang {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & true;
    static String INPUT_FILE = "input/H.inp";

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int n = ir.nextInt();
            int[] a, s;

            a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = ir.nextInt();
            }
            s = Arrays.copyOf(a, n);
            Arrays.sort(s);

            long[][] dp = new long[n][n];

            for (int i = 0; i < n; i++) {
                dp[i][0] = Math.abs(a[i] - s[0]) + (i > 0 ? dp[i-1][0] : 0);

                for (int j = 1; j < n; j++) {
                    dp[i][j] = Math.min(dp[i][j-1], (i > 0 ? dp[i-1][j] : 0) + Math.abs(a[i] - s[j]));
                }
            }

            long res = (long) 1e13;
            for (int i = 0; i < n; i++) {
                res = Math.min(res, dp[n-1][i]);
            }

            bw.write(res + "\n");
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    static void debug(Object... args) {
        if (DEBUG) {
            for (int i = 0; i < args.length; ++i) {
                System.out.print(args[i] + " ");
            }
            System.out.println();
        }
    }
}