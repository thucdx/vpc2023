import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class C_KhaoSatDanCu {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/C.inp";

    static class X implements Comparable<X> {
        int id;
        long c;
        long p;

        public X(int id, long c, long p) {
            this.id = id;
            this.c = c;
            this.p = p;
        }

        @Override
        public String toString() {
            return "X{" +
                    ", id=" + id +
                    ", c=" + c +
                    ", p=" + p +
                    '}';
        }

        @Override
        public int compareTo(X o) {
            long s = c * o.p - p * o.c;
            if (s == 0) {
                return 0;
            } else {
                if (s > 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            List<X> all = new ArrayList<>();
            int n = ir.nextInt();
            long[] c = new long[n];
            long[] p = new long[n];

            for (int i = 0; i < n; i++) {
                p[i] = ir.nextInt();
            }

            for (int i = 0; i < n; i++) {
                c[i] = ir.nextInt();
            }

            for (int i = 0; i < n; i++) {
                all.add(new X(i+1, c[i], p[i]));
            }
            Collections.sort(all);
            for (int i = 0; i < n; i++) {
                debug(all.get(i));
            }

            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    res.add(all.get(i).id);
                } else {
                    if (all.get(i).compareTo(all.get(i-1)) == 0) {
                        res.add(all.get(i).id);
                    } else {
                        break;
                    }
                }
            }

            bw.write(res.size() + "\n");
            Collections.sort(res);
            for (Integer id: res) {
                bw.write(id + " ");
            }
            bw.write("\n");
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