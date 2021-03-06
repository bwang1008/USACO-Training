/*
ID: bwang101
LANG: JAVA
TASK: tour
*/
import java.util.*;
import java.io.*;

public class tour {
	
	static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	static boolean[][] edges;
	
	static long start;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("tour.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("tour.out")));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int V = Integer.parseInt(st.nextToken());
		
		edges = new boolean[N][N];
		
		for(int i = 0; i < N; i++)
			hm.put(in.readLine().trim(), i);
		
		for(int i = 0; i < V; i++)
		{
			st = new StringTokenizer(in.readLine());
			int a = hm.get(st.nextToken());
			int b = hm.get(st.nextToken());
			edges[a][b] = true;
			edges[b][a] = true;
		}
		
		start = System.currentTimeMillis();
		
		int[][] table = genTable(N, edges);
		
		int ans = table[N-1][N-1];
		out.println(ans < 0 ? "1" : ans);
		
//		System.out.println("Took " + (System.currentTimeMillis()-start) + " milliseconds");
		
		in.close();
		out.close();
	}
	
	/*
	 * DP table: Start with having 1 city from the very start.
	 *  DP[i][j] represents max cities visited given two paths,
	 *  one from city 0 to city i, one from city 0 to city j. 
	 *  We seek DP[N-1][N-1]. 
	 *  
	 *  For each DP[i][j] (== DP[j][i]), found by looking for max of all
	 *  previous DP[i][k] (if edge[k] to [j] is true) and adding an
	 *  additional 1 city if valid.
	 *  
	 *  O(N^3) to build subcases, O(N) to build final answer
	 */
	public static int[][] genTable(int N, boolean[][] edges) {
		int[][] dp = new int[N][N];
		for(int i = 0; i < dp.length; i++)
			Arrays.fill(dp[i], -10);
		
		dp[0][0] = 1;
		
		edges[N-1][N-1] = true;
		
		for(int i = 0; i < N; i++)
			for(int j = i+1; j < N; j++)
				for(int k = 0; k < j; k++)
					if(edges[k][j] && dp[i][k] != -10)
						dp[i][j] = dp[j][i] = Math.max(dp[i][k] + 1, dp[i][j]);
		
		for(int k = 0; k < N-1; k++)
			if(edges[k][N-1])
				dp[N-1][N-1] = Math.max(dp[N-1][k], dp[N-1][N-1]);
		
		return dp;
	}
	
}
/*
Submission #5!!! 5/15/2015 10:15 PM
8 9	
Vancouver		
Yellowknife	
Edmonton
Calgary
Winnipeg
Toronto	
Montreal
Halifax	
Vancouver Edmonton
Vancouver Calgary	
Calgary Winnipeg
Winnipeg Toronto
Toronto Halifax
Montreal Halifax
Edmonton Montreal
Edmonton Yellowknife
Edmonton Calgary

7

5 5
C1
C2
C3
C4
C5
C5 C4
C2 C3
C3 C1
C4 C1
C5 C2

1

8 12
A
B
C
D
E
F
G
H
A B
A C
C B
D C
D B
D E
D F
E F
E G
D H
H F
H G

1

8 13
A
B
C
D
E
F
G
H
A B
A C
C B
D C
D B
D E
D F
E F
E G
D H
H F
H G
A G

8

40 419
0
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
18 31
17 0
21 7
18 32
37 21
20 21
4 29
3 6
1 28
25 5
1 2
9 39
8 24
16 32
39 37
24 17
29 1
18 10
9 36
2 7
18 23
29 22
13 33
29 14
21 15
20 23
18 30
22 26
14 39
18 13
37 3
31 26
5 10
36 14
6 39
22 24
22 11
7 36
4 37
10 26
12 31
9 30
21 32
17 36
31 36
10 28
39 2
14 4
12 11
10 1
3 33
29 17
9 0
3 21
31 13
11 13
6 29
9 37
25 20
26 24
22 1
29 35
12 8
14 22
9 18
15 22
29 5
0 38
5 3
20 37
17 32
10 23
21 6
1 8
31 23
9 20
18 22
28 32
5 38
11 21
20 0
26 20
39 31
24 20
29 2
12 39
26 33
20 7
0 21
12 24
17 13
39 28
34 20
29 20
12 26
9 1
29 21
1 15
23 16
4 39
8 9
22 10
6 22
2 27
21 4
16 33
31 25
35 20
7 37
35 22
19 18
38 23
18 6
27 26
37 10
32 36
4 35
39 1
20 30
26 15
11 34
13 6
25 15
16 3
21 9
7 9
36 4
19 33
8 11
30 13
7 14
15 19
37 2
35 8
36 8
15 13
28 18
4 10
27 12
19 24
17 39
18 26
10 8
39 18
22 39
24 38
19 22
0 15
31 37
24 7
10 25
8 38
3 13
9 31
25 28
15 3
27 33
29 38
16 24
23 9
21 8
23 32
34 31
31 38
5 0
29 31
34 17
39 24
16 1
14 33
25 4
35 9
13 19
33 38
36 15
7 19
7 1
11 39
39 29
8 29
35 3
6 34
27 23
35 2
16 21
6 11
31 24
39 25
8 7
27 19
6 27
36 6
17 5
35 12
8 2
25 3
38 2
14 16
25 6
0 3
34 39
7 16
22 13
4 19
19 21
24 15
34 32
18 2
28 4
5 26
6 31
7 17
27 7
21 22
7 11
24 14
28 7
26 8
14 10
23 8
3 2
11 31
6 17
18 12
34 15
28 21
3 30
4 9
33 24
24 36
26 36
5 21
37 6
16 25
6 10
32 8
24 25
32 9
12 19
1 35
17 38
1 38
24 28
16 38
3 23
31 1
19 16
27 11
26 9
31 32
20 3
11 38
21 12
11 35
37 29
18 29
12 29
32 4
30 11
21 17
23 7
27 14
39 7
17 11
5 39
23 39
38 34
34 36
24 11
27 16
16 18
28 37
5 23
26 4
30 4
3 39
34 30
37 18
39 16
21 38
15 6
9 3
23 17
23 28
33 31
14 9
28 17
19 2
15 17
21 14
34 3
12 9
9 22
12 32
8 33
3 32
20 11
37 14
3 11
29 32
16 9
35 31
26 16
5 20
20 18
30 29
0 2
36 25
12 22
19 10
36 22
27 4
34 7
16 15
1 18
23 14
16 29
36 13
2 21
25 9
17 27
35 34
28 36
9 13
2 26
15 4
14 12
18 17
33 20
38 7
1 34
1 4
31 2
26 19
39 35
32 1
22 5
10 34
12 3
9 19
34 21
15 7
1 6
10 30
25 34
16 20
1 23
31 8
23 13
1 26
13 27
8 27
24 29
36 1
10 13
28 29
29 11
17 25
8 20
33 15
4 7
26 35
8 30
36 33
16 13
35 23
38 3
0 24
14 19
20 10
13 14
33 35
8 15
3 28
11 33
36 21
15 11
19 28
19 1
20 14
33 4
25 18
27 0
28 13
17 19
19 35
3 22
30 21
32 27
33 32
0 8
20 38
27 37
33 22
8 25
28 27
35 21
12 2
30 1
38 32
5 13
21 33
7 10
3 19
25 1
3 17
13 25
24 27
4 24
4 2
34 26
2 25
15 30
5 19
11 1
10 35
29 15
1 24
26 0

33
*/
