/*
ID: bwang101
LANG: JAVA
TASK: hidden
*/
import java.util.*;
import java.io.*;
public class hidden {
	
	public static int L;
	public static long start;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("hidden.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hidden.out")));
		
		start = System.currentTimeMillis();
		
		L = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder();
		while(in.ready())
			sb.append(in.readLine());
		
		String g = sb.toString();
		
		int[] arr = suffixArray(g);
		
		out.println(arr[0]);
		
		in.close();
		out.close();
	}
	
	/* O(N * log N * log N)
	 Index    Rank Rank2   index   Rank R2
	   0 banana 1 0         5 abanan 0  1
	   1 ananab 0 2         1 ananab 1  1
	   2 nanaba 2 0  sort-> 3 anaban 1  0
	   3 anaban 0 2 jump->2 0 banana 2  3
	   4 nabana 2 0         2 nanaba 3  3
	   5 abanan 0 1         1 nabana 3  2
	 */
	public static int[] suffixArray(String g) {
		int jump = 1;
		
		int[] rank = new int[L];
		int[] rank2 = new int[L];
		int[] tempIndex = new int[L];
		int[] index = new int[L];
		int[] indexBack = new int[L];
		
		for(int i = 0; i < L; i++) {
			rank[i] = g.charAt(i) - 'a';
			tempIndex[i] = index[i] = indexBack[i] = i;
		}
		
		while(jump < L) { //Jump doubles each time : log(L)
			
			for(int i = 0; i < L; i++) { //Second rank based on the value (jump) later ahead in the string, like 'b' banana goes to the index 2 if jump was 2
				rank2[i] = rank[indexBack[(index[i] + jump) % L]];
			}
			
			long[] toSort = new long[L];
			
			for(int i = 0; i < L; i++) {
				long put = 1000000L*1000000L*rank[i] + 1000000L*rank2[i] + i; //rank most important, then sort by second rank. Index added in last to help find corresponding original index
				toSort[i] = put;
			}
			
			// Llog(L)
			Arrays.sort(toSort);
			
			for(int i = 0; i < L; i++) {
				index[i] = tempIndex[(int) (toSort[i] % 1000000)];  //each step, sorts the strings somewhat based on first few letters
				indexBack[index[i]] = i;
			}
			
			for(int i = 0; i < L; i++)
				tempIndex[i] = index[i];
			
			int j = rank[0] = 0; //New rank: based on if first rank && second rank are the same as previous, then same rank, otherwise increment
			for(int i = 1; i < L; i++)
				rank[i] = (toSort[i]/1000000 == toSort[i-1]/1000000) ? rank[i-1] : ++j;
			
			jump *= 2;
		}
		
		return index;
	}

}
/*
Submission #6!!! 5/29/2018 1:10 AM
7
alabala

6
*/
