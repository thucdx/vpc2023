import java.io.*;
import java.nio.file.Files;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author thucdx
 */
public class J_RobotThongMinh {
    static boolean IS_LOCAL = System.getenv("LOCAL_JUDGE") != null;
    static boolean DEBUG = IS_LOCAL & true;
    static String base = "LRUD";
    static int dx[] = {0, 0, -1, 1};
    static int dy[] = {-1, 1, 0, 0};
    static int n, m;

    static boolean inside(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    static int find(String[] maze, String dir, int sx, int sy) {
        boolean[][] vs = new boolean[n][m];
        int cX = sx, cY = sy;
        int l = dir.length();
        int total  = 0;
        for (int i = 0; i < l; i++) {
            char c = dir.charAt(i);
            int id = 0;
            for (int j = 0; j < base.length(); j++) {
                if (base.charAt(j) == c) {
                    id = j;
                    break;
                }
            }

            while (true) {
                int nx = cX + dx[id];
                int ny = cY + dy[id];
                if (inside(nx, ny) && maze[nx].charAt(ny) != '#') {
                    cX = nx;
                    cY = ny;
                    if (!vs[nx][ny]) {
                        total++;
                        vs[nx][ny] = true;
                    }
                } else {
                    break;
                }
            }
        }
       return total;
    }

    public static void main(String[] args) {
        try {
            String INPUT_FILE = "i_vcc23/j/input_%d.txt";
            String OUT_FILE = "i_vcc23/out/output_%d.txt" ;

            for (int nCase = 0; nCase <= 9; nCase++) {
                String actualFile = String.format(INPUT_FILE, nCase);
                String actualOut = String.format(OUT_FILE, nCase);

                InputStream is = Files.newInputStream(new File(actualFile).toPath());
                OutputStream os = Files.newOutputStream(new File(actualOut).toPath()) ;

                OutputStreamWriter ow = new OutputStreamWriter(os);
                InputReader ir = new InputReader(is);

                n = ir.nextInt();
                m = ir.nextInt();
                int k = ir.nextInt();
                String maze[] = new String[n];

                int sx = -1, sy = -1;
                for (int j = 0; j < n; j++) {
                    maze[j] = ir.next();
                    for (int i = 0; i < m; i++) {
                        if (maze[j].charAt(i) == 'O') {
                            sx = j;
                            sy = i;
                            debug("FOUND ", sx, sy);
                        }
                    }
                }

                Random rand = new Random(System.currentTimeMillis());

                String best = "";
                int totalMax = -1;
                int totalTry = 0;
                if (nCase <= 3) {
                    totalTry = 1000000;
                } else if (nCase <= 6) {
                    totalTry = 200000;
                } else {
                    totalTry = 100000;
                }
                for (int i = 0; i < totalTry; i++) {
                    StringBuilder dirs = new StringBuilder("");

                    for (int j = 0; j < k; j++) {
                        int r = rand.nextInt(4);
                        dirs.append(base.charAt(r));
                    }

                    int total = find(maze, dirs.toString(), sx, sy);
                    if (total > totalMax) {
                        totalMax = total;
                        best = dirs.toString();
                    }
                }

                ow.write(best + "\n");
                ow.close();
            }

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