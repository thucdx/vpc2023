import java.io.*;
import java.nio.file.Files;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class E_NoiVongTayLon {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/E_TDX.inp";

    static int simulate(int[] arr, long p, int q) {
        if (p <= q) {
            return 0;
        }

        int step = 0;
        boolean end = false;
        long cur = p;

        while (!end) {
            for (int i = 0; i < arr.length; i++) {
                step++;

                if (arr[i] < 0) {
                    cur -= Math.min(cur, -arr[i]);
                } else {
                    cur += arr[i];
                }

                if (cur <= q) {
                    end = true;
                    break;
                }
            }
        }

        return step;
    }

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int n = ir.nextInt(), p = ir.nextInt(), q = ir.nextInt();
            long step = 0;
            int[] a = new int[n];
            long s = 0;

            // minP is minimum value of p such that we can play full circle 1 -> n
            long minP = q + 1;

            for (int i = 0; i < n; i++) {
                a[i] = ir.nextInt();
                if (a[i] < 0) {
                    s -= -a[i];
                } else {
                    s += a[i];
                }

                minP = Math.max(minP, 1 + q - s);
                debug(i, a[i], s, minP);
            }

            debug("minP", minP, "s", s);

            if (p >= minP) {
                if (s >= 0) {
                    // Never end
                    bw.write("-1\n");
                } else {
                    // s < 0 => value of p decreases after each circle
                    long fullCircle = ((p - minP) / -s);
                    long newP = (p - fullCircle * -s);

                    debug("fullCircle", fullCircle, "newP", newP);
                    step += n * fullCircle;
                    step += simulate(a, newP, q);

                    bw.write(step + "\n");
                }
            } else {
                // p < minP => simulate now
                step += simulate(a, p, q);
                bw.write(step + "\n");
            }

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