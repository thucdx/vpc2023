import java.io.*;
import java.nio.file.Files;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class A_XepChoNgoi {

    /**
     * Ensure: IS_LOCAL = false before submit
     */
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & true;
    static String INPUT_FILE = "input/A.inp";

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int n = ir.nextInt(), m = ir.nextInt();
            String[] b = new String[n];
            for (int i = 0; i < n; i++) {
                b[i] = ir.next();
            }

            boolean found = false;
            for (int i = 0; i < n && !found; i++) {
                for (int j = 0; j < m && !found; j++) {
                   if (b[i].charAt(j) == '.'
                   && (j + 1 < m) && b[i].charAt(j + 1) == '.'
                   && (j + 2 < m) && b[i].charAt(j + 2) == '.') {
                       bw.write((i + 1) + " " + (j + 1) + "\n");
                       found = true;
                   }
                }
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