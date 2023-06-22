import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * @author thucdx
 */
public class F_TachXau {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/F_Xau.inp";

    static Set<String> dict;
    static int[] start;
    static boolean[] isOK;

    static void solve(String phrase, BufferedWriter bw) throws IOException {
        int l = phrase.length();
        start = new int[l+1];
        isOK = new boolean[l+1];
        Arrays.fill(isOK, false);
        Arrays.fill(start, -2);

        debug("Phase", phrase);
        for (int i = 0; i < l; i++) {
            // isOK[i] ??
            String cur = "";
            for (int begin = i; begin >= 0; begin--) {
                cur = phrase.charAt(begin) + cur;

                if (dict.contains(cur)) {
                    if (begin == 0) {
                        isOK[i] = true;
                        start[i] = 0;
                    } else {
                        if (isOK[begin-1]) {
                            isOK[i] = true;
                            start[i] = begin;
                            break;
                        }
                    }
                }
            }
        }

        if (isOK[l-1]) {
            int cur = l-1;
            for (int i = 0; i < l; i++) {
               debug(i, start[i]);
            }
            List<String> res = new ArrayList<>();
            while (cur >= 0) {
                String s = phrase.substring(start[cur], cur+1);
                res.add(s);
                debug(" => s", s, cur);
                cur = start[cur]-1;
            }

            Collections.reverse(res);
            for(String r: res) {
                bw.write(r + " ");
            }
            bw.write("\n");
        } else {
            bw.write("-1\n");
        }
    }

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int nDict = ir.nextInt();
            dict = new HashSet<>();
            for (int i = 0; i < nDict; i++) {
                String word = ir.next();
                dict.add(word);
//                debug(word);
            }

            int nCase = ir.nextInt();
            for (int i = 0; i < nCase; i++) {
               String phrase = ir.next();
               solve(phrase, bw);
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