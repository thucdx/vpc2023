import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class B_VanChuyenGao {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/B.inp";

    static class Node implements Comparable<Node> {
        int pos;
        int type;
        int total;

        public Node(int pos, int type, int total) {
            this.pos = pos;
            this.type = type;
            this.total = total;
        }

        public Node(int pos, int type) {
            this.pos = pos;
            this.type = type;
            this.total = -1;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(pos, o.pos);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "pos=" + pos +
                    ", type=" + type +
                    ", total=" + total +
                    '}';
        }
    }

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int ntest = ir.nextInt();
            for (int t = 0; t < ntest; t++) {
                int c = ir.nextInt(), m = ir.nextInt(), n = ir.nextInt();
                List<Node> pos = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    int shop = ir.nextInt();
                    pos.add(new Node(shop, 1));
                }

                for (int i = 0; i < n; i++) {
                    int location = ir.nextInt();
                    int order = ir.nextInt();
                    pos.add(new Node(location, 2, order));
                }

                Collections.sort(pos);

                long total = 0;
                int car = c;
                for (int i = 0; i < pos.size(); i++) {
                    Node cur = pos.get(i);
                    if (cur.type == 2) {
                        // order
                        if (car >= cur.total) {
                            car -= cur.total;
                            total += cur.total;
                        } else {
                        }
                    } else {
                        //
                        // fill up
                        car = c;
                    }
                }

                bw.write(total + "\n");
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