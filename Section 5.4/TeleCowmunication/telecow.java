/*
ID: bwang101
LANG: JAVA
TASK: telecow
 */
import java.util.*;
import java.io.*;
public class telecow {
	
	static boolean[] inSet;

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader("telecow.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("telecow.out")));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int c1 = Integer.parseInt(st.nextToken())-1;
		int c2 = Integer.parseInt(st.nextToken())-1;
		
		int[][] edges2 = new int[2*N][2*N];
		
		for(int i = 0; i < N; i++)
			edges2[2*i][2*i+1] = 1;
		
		for(int i = 0; i < M; i++)
		{
			st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			edges2[2*x+1][2*y] = 100;
			edges2[2*y+1][2*x] = 100;
		}
		
		ArrayList<Integer> list2 = minVertexCut(N, edges2, c1, c2);
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list2.size(); i++)
			sb.append(list2.get(i)/2+1 + " ");
		
		out.println(list2.size());
		out.println(sb.toString().trim());
		
		in.close();
		out.close();
	}
	
	public static ArrayList<Integer> minVertexCut(int N, int[][] edges2, int c1, int c2) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		int flow = maxFlowFF(edges2, 2*c1+1, 2*c2, list, false);
		
//		System.out.println("Flow = " + flow);
//		System.out.println("list = " + list);
		
		boolean[] valid = new boolean[2*N];
		
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) == -1)
				continue;
			boolean b = checkNodes(edges2, list.get(i), 2*c1+1, 2*c2, flow);
			valid[list.get(i)] = b;
		} 
		
		int least = Integer.MAX_VALUE;
		
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) == -1)
			{
				list2.add(least);
				least = Integer.MAX_VALUE;
				continue;
			}
			if(valid[list.get(i)])
				least = Math.min(list.get(i), least);
		}
		
		
		Collections.sort(list2);
		
		return list2;
	}
	
	public static boolean checkNodes(int[][] edges, int node, int s, int t, int flow) {
		
		int a = (node % 2 == 0)? node : node-1;
		int b = (a % 2 == 0)? a+1 : a-1;
		
		int temp = edges[a][b];
		
		edges[a][b] = 0;
		
		int flowTemp = maxFlowFF(edges, s, t, new ArrayList<Integer>(), true);
		
//		System.out.println("flowTemp for " + (node) + " = " + flowTemp);
		
		edges[a][b] = temp;
		
		return flowTemp != flow;
	}
	
	static boolean bfs(int rGraph[][], int s, int t, int parent[], boolean stdFF){
        boolean visited[] = new boolean[rGraph.length];
 
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]= -1;
 
        while(queue.size()!=0)
        {
            int u = queue.poll();
 
            for(int v=0; v<rGraph.length; v++)
            {
                if(!visited[v] && rGraph[u][v] > 0)
                {
                	if(stdFF || (!stdFF && !inSet[v]))
                	{
	                    queue.add(v);
	                    parent[v] = u;
	                    visited[v] = true;
                	}
                }
            }
        }
        
        return visited[t];
    } 
	
	static int maxFlowFF(int graph[][], int s, int t, ArrayList<Integer> list, boolean stdFF)
    {
        int u, v;
 
        inSet = new boolean[2*graph.length];
        
        int rGraph[][] = new int[graph.length][graph[0].length];
 
        for (u = 0; u < graph.length; u++)
            for (v = 0; v < graph[0].length; v++)
                rGraph[u][v] = graph[u][v];
 
        int parent[] = new int[graph.length];
 
        int max_flow = 0; 
 
        while (bfs(rGraph, s, t, parent, stdFF))
        {
            int path_flow = Integer.MAX_VALUE;
            for(v = t; v != s; v = parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }
 
            for(v = t; v != s; v = parent[v])
            {
            	if(v != t)
            	{
            		list.add(v);
            		inSet[v] = true;
            	}
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }
            
            list.add(-1);
            max_flow += path_flow;
        }
 
        return max_flow;
    }

}
/*
Submission #9!! 04/02/2018
3 2 1 2
1 3
2 3

1
3

20 26 1 2
1 9
1 17
1 19
2 6
2 9
2 14
3 15
3 16
3 18
4 8
5 6
5 7
5 12
5 17
6 7
6 19
7 13
7 14
7 15
9 12
9 14
10 14
11 12
12 17
13 15
15 17

3
6 7 9

40 76 1 2
1 6
1 11
1 12
1 19
1 31
1 33
1 35
2 3
2 4
2 17
2 20
2 33
3 4
3 11
3 36
4 37
5 9
6 8
6 9
6 21
6 22
6 27
6 32
6 35
6 36
7 19
7 24
8 10
8 11
8 20
8 25
8 28
9 10
10 12
11 14
11 20
12 14
12 15
12 28
12 39
13 21
13 36
14 15
14 18
14 36
14 39
15 30
15 33
16 17
16 21
16 37
17 18
17 26
17 28
17 36
19 26
21 28
21 37
22 24
22 29
22 31
23 24
24 30
27 29
27 31
27 40
29 31
30 32
30 40
32 34
32 37
34 39
35 40
36 37
36 40
38 39

5
3 4 17 20 33

20 77 1 2
1 14
1 17
1 20
2 3
2 11
2 12
2 13
2 16
3 5
3 6
3 8
3 9
3 11
3 14
3 15
3 20
4 5
4 6
4 12
4 13
4 14
4 15
4 18
5 11
5 12
5 13
5 15
5 16
5 19
5 20
6 7
6 13
6 14
6 15
6 16
6 17
7 9
7 10
7 13
7 14
7 15
7 20
8 9
8 10
8 11
8 17
8 19
8 20
9 10
9 11
9 12
9 13
9 14
9 17
9 18
9 19
9 20
10 11
10 13
10 15
10 18
11 13
11 14
11 15
11 17
11 18
11 19
12 18
13 14
13 16
14 18
15 18
15 20
16 17
16 18
17 18
18 19

3
14 17 20
*/
