import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author thucdx
 */
public class D_KetNoi {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & true;
    static String INPUT_FILE = "input/D.inp";

    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public double dist(Point other) {
            return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
        }
    }

    public static class Edge implements Comparable<Edge> {
        int u, v;
        double d;

        public Edge(int u, int v, double d) {
            // Ensure u < v
            if (u > v) {
                int tmp = v;
                v = u;
                u = tmp;
            }

            this.u = u;
            this.v = v;
            this.d = d;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(d, o.d);
        }
    }

    static int[] parent, size;

    static void makeSet(int v) {
        parent[v] = v;
        size[v] = 1;
    }

    static int findParent(int v) {
        return parent[v] == v ? v : (parent[v] = findParent(parent[v]));
    }

    static void union(int u, int v) {
        u = findParent(u);
        v = findParent(v);
        if (u != v) {
            if (size[u] < size[v]) {
                int tmp = v;
                v = u;
                u = tmp;
            }

            parent[v] = u;
            size[u] += size[v];
        }
    }

    public static void main(String[] args) {
        try {
            InputStream is = IS_LOCAL ? Files.newInputStream(new File(INPUT_FILE).toPath()) : System.in;
            OutputStream os = System.out;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            InputReader ir = new InputReader(is);

            // Process here!
            int totalStation = ir.nextInt();
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < totalStation; i++) {
                points.add(new Point(ir.nextDouble(), ir.nextDouble()));
            }

            // wifi, cable
            double wifiCost = ir.nextDouble(), cableCost = ir.nextDouble();

            double d[][] = new double[totalStation][totalStation];
            List<Edge> edges = new ArrayList<>();

            for (int i = 0; i < totalStation; i++) {
                for (int j = i + 1; j < totalStation; j++) {
                    d[i][j] = d[j][i] = points.get(i).dist(points.get(j));
                    edges.add(new Edge(i, j, d[i][j]));
                }
            }


            Collections.sort(edges);

            // Cost to connect all stations using only cable links.
            // Find it using minimum spanning tree
            double justCableCost = 0.0;

            // Cost to connect all station using both cable link and wireless link.
            // Initially it conists of totalStation wireless stations
            double mixedCost = totalStation * wifiCost;
            int totalEdge = 0;

            // Init dsu
            parent = new int[totalStation];
            size = new int[totalStation];
            for (int i = 0; i < totalStation; i++) {
                makeSet(i);
            }

            for (int i = 0; i < edges.size() && totalEdge < totalStation - 1; i++) {
                Edge cur = edges.get(i);
                int pu = findParent(cur.u);
                int pv = findParent(cur.v);

                if (pu != pv) {
                    totalEdge++;
                    union(cur.u, cur.v);
                    justCableCost += cableCost * cur.d;

                    // As we merge two node, we can remove one wireless station (which save wifiCost),
                    // but costs us cur.d * c to build one cable link
                    // This component can still connect with other via one wireless link (other node which has same parent wifiCost/ current node).
                    mixedCost = Math.min(mixedCost, mixedCost + cur.d * cableCost - wifiCost);
                }
            }

            debug("Total cost without wifi", justCableCost);

            // BYPASS TEST CASE 35, JUST TO CHECK IF CURRENT ALGORITHM FAIL ONLY THIS TEST!!!!
            bw.write(String.format("%.9f\n", Math.min(justCableCost, mixedCost)));

            // Display tree
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
