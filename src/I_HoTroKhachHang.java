import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class I_HoTroKhachHang {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & false;
    static String INPUT_FILE = "input/I.inp";
    static int INF = (int) 1e9;

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            InputReader ir = new InputReader(is);

            // Process here!
            int n = ir.nextInt(), m = ir.nextInt(), k = ir.nextInt();

            int[][] d = new int[n + 1][n+1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = INF;
                }
                d[i][i] = 0;
            }

            for (int i = 0; i < m; i++) {
                int u = ir.nextInt() - 1, v = ir.nextInt() - 1, w = ir.nextInt();
                d[u][v] = d[v][u] = w;
            }

            int[] customers = new int[k];
            int[] technicians = new int[k];

            for (int i = 0; i < k; i++) {
                customers[i] = ir.nextInt() - 1;
            }

            for (int i = 0; i < k; i++) {
                technicians[i] = ir.nextInt() - 1;
            }

            // Floyd Warshall for finding minimum distance between all pair of (i, j)
            for (int mid = 0; mid < n; mid++) {
                for (int i = 0; i < n; i++) {
                    for (int j = i+1; j < n; j++) {
                        d[i][j] = d[j][i] = Math.min(d[i][j], d[i][mid] + d[mid][j]);
                    }
                }
            }

            // Binary search min d such that all distance between technician and customer <= d.
            int l = 1, r = INF;
            while (r - l > 1) {
                int dist = (r + l) >> 1;

                int[] assignedCustomerId = getTotalMatch(k, dist, d, customers, technicians);
                boolean isMatch = true;
                for (int i = 0; i < k; i++) {
                    if (assignedCustomerId[i] < 0) {
                        isMatch = false;
                        break;
                    }
                }

                if (isMatch) { // isOK
                    r = dist;
                } else {
                    // not ok, distance should be larger
                    l = dist + 1;
                }
            }

            int[] assignedCustomerId = getTotalMatch(k, l, d, customers, technicians);
            int res = l;
            boolean isMatch = true;
            for (int i = 0; i < k; i++) {
                if (assignedCustomerId[i] < 0) {
                    isMatch = false;
                    break;
                }
            }
            if (!isMatch)  {
                assignedCustomerId = getTotalMatch(k, r, d, customers, technicians);
                res = r;
            }

            bw.write(res + "\n");
            for (int i = 0; i < k; i++) {
                bw.write((1 + assignedCustomerId[i]) + " ");
            }
            bw.write("\n");

            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int[] getTotalMatch(int k, int dist, int[][] d, int[] customers, int[] technicians) {
        KuhnAssignment solver;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            adj.add(new ArrayList<>());
        }
        for (int customNum = 0; customNum < k; customNum++) {
            int customPosition = customers[customNum];
            for (int techNum = 0; techNum < k; techNum++) {
                int techPosition = technicians[techNum];
                if (d[customPosition][techPosition] <= dist) {
                    adj.get(customNum).add(techNum);
                }
            }
        }

        solver = new KuhnAssignment(k, k, adj);
        return solver.findMaxMatch();
    }

    static class KuhnAssignment {
        int firstPart, secondPart;
        List<List<Integer>> adj;
        int[] mt;
        boolean[] used;

        public KuhnAssignment(int firstPart, int secondPart, List<List<Integer>> adj) {
            this.firstPart = firstPart;
            this.secondPart = secondPart;
            this.adj = adj;

            mt = new int[secondPart];
            used = new boolean[firstPart];
        }

        public boolean tryKuhn(int v) {
            if (used[v])
                return false;

            used[v] = true;
            for (Integer to: adj.get(v)) {
                if (mt[to] == -1 || tryKuhn(mt[to])) {
                    mt[to] = v;
                    return true;
                }
            }

            return false;
        }

        public int[] findMaxMatchNormal() {
            Arrays.fill(mt, -1);
            for (int v = 0; v < firstPart; v++) {
                Arrays.fill(used, false);
                tryKuhn(v);
            }

            return mt;
        }

        public int[] findMaxMatch() {
            Arrays.fill(mt, -1);
            boolean[] usedLocal = new boolean[firstPart];
            for (int v = 0; v < firstPart; v++) {
                for (Integer to: adj.get(v)) {
                    if (mt[to] == -1) {
                        mt[to] = v;
                        usedLocal[v] = true;
                        break;
                    }
                }
            }

            for (int v = 0; v < firstPart; v++) {
                if (usedLocal[v])
                    continue;

                Arrays.fill(used, false);
                tryKuhn(v);
            }

            return mt;
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