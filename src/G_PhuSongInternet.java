import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class G_PhuSongInternet {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/G.inp";

    static class Range implements Comparable<Range> {
        int l, r;

        public Range(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "l=" + l +
                    ", r=" + r +
                    '}';
        }

        @Override
        public int compareTo(Range o) {
            if (l == o.l) {
                return Integer.compare(r, o.r);
            } else {
                return Integer.compare(l, o.l);
            }
        }
    }

    static int countHoles(List<Range> ranges, int max) {
        Collections.sort(ranges);
        int last = 0;
        int res = 0;
        for (Range r: ranges) {
            debug(r);
            if (r.l > last) {
                res += r.l - last - 1;
            }
            last = Math.max(last, r.r);
        }

        if (max > last) {
            res += (max - last);
        }

        return res;
    }
    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int k = ir.nextInt(), m = ir.nextInt(), n = ir.nextInt();
            List<Range> rangeX = new ArrayList<>();
            List<Range> rangeY = new ArrayList<>();

            for (int i = 0; i < k; i++) {
                int x = ir.nextInt(), y = ir.nextInt(), w = ir.nextInt();
                int lx = Math.max(x - w, 1);
                int rx = Math.min(x + w, m);

                int ly = Math.max(y - w, 1);
                int ry = Math.min(y + w, n);
                rangeX.add(new Range(lx, rx));
                rangeY.add(new Range(ly, ry));
            }

            int hx = countHoles(rangeX, m);
            int hy = countHoles(rangeY, n);
            long res = (long) m * n - (long)hx * hy;
            debug("hx", hx);
            debug("hy", hy);
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