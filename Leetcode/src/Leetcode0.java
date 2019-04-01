import java.util.*;

public class Leetcode0 {
    public static void main(String[] arg0){

        int[] num = new int[]{5, 2, 1, 3, 6};
        String[] words = {"practice", "makes", "perfect", "coding", "makes"};
        int[][] nums = new int[][]{{0,1},{1,2},{0,2},{3,4}};
        wallsAndGates(nums);
        System.out.println();
    }

    //
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
            System.out.println("i : " + i + " ans: " + ans + " j : " + j);
        }
        return ans;
    }

    //5 Longest Palindromic Substring
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
                System.out.println("start: "  + start + " end: "  + end);
            }
            System.out.println("len: "  + len);
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    //17
    public static List<String> letterCombinations(String digits) {
        String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        LinkedList<String> list = new LinkedList<>();
        for(char c : digits.toCharArray()){
            System.out.println(c);
            LinkedList<String> temp = new LinkedList<>();
            while(!list.isEmpty()){
                String curr = list.poll();
                for(char chr : map[c - '0'].toCharArray())
                    temp.add(curr + String.valueOf(chr));
            }
            list = temp;
            System.out.println(temp);
        }
        return list;
    }

    //84 Largest Rectangle in Histogram
    public static int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            if(s.isEmpty() || h >= height[s.peek()]){
                s.push(i);
                System.out.println("Stack push: " + i);
            }
            else{
                int tp = s.pop();
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                System.out.println("tp: " + tp + ", maxArea = " + maxArea + ", i = " + i + ", width = " + (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

    //163 Missing Ranges
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> list = new ArrayList<>();
        int start = lower;
        if(lower == Integer.MAX_VALUE) return list;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] < start) continue;
            if(nums[i] == start) {
                start++;
                continue;
            }
            list.add(generateString(start, nums[i] - 1));
            if(nums[i] == upper) return list;
            start = nums[i] + 1;
        }
        if(start <= upper)
            list.add(generateString(start, upper));
        return list;
    }
    private String generateString(long start, long end){
        return (start == end) ? String.valueOf(start) : String.valueOf(start) + "->" + String.valueOf(end);
    }

    //245
    public static int shortestWordDistance(String[] words, String word1, String word2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word1)) list1.add(i);
            if(words[i].equals(word2)) list2.add(i);
        }
        int result = Integer.MAX_VALUE;
        for(int i1 : list1){
            for(int i2 : list2){
                if(i1 != i2)
                    result = Math.min(result, Math.abs(i1 - i2));
            }
        }
        return result;
    }

    //249 Group Shifted Strings
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strings){
            if(!map.containsKey(shiftToOrigin(str))){
                List<String> temp = new ArrayList<>();
                temp.add(str);
                map.put(shiftToOrigin(str), temp);
            }
            else{
                map.get(shiftToOrigin(str)).add(str);
            }
        }
        return new ArrayList<List<String>>(map.values());
    }
    private static String shiftToOrigin(String str){
        if(str.startsWith("a")) return str;
        char[] chrArray = str.toCharArray();
        int diff = 0;
        for(int i = 0; i < chrArray.length; i++){
            if(i == 0) diff = chrArray[i] - 'a';
            chrArray[i] -= diff;
        }
        return new String(chrArray);
    }

    //254. Factor Combinations
    public static List<List<Integer>> getFactors(int n) {
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 2; i < n; i++){
            if(n % i == 0) helper(list, new ArrayList<>(), n, i);
        }
        return list;
    }
    private static void helper(List<List<Integer>> list, List<Integer> tempList, int n, int startFactor){
        System.out.println("n: " + n + " startFactor: " + startFactor);
        if(n == 1) {
            System.out.println(tempList);
            list.add(new ArrayList<>(tempList));
            return;
        }
        tempList.add(startFactor);
        n /= startFactor;
        for(int i = startFactor; i <= n ; i++){
            if(n % i == 0) {
                tempList.add(i);
                helper(list, tempList, n / i, startFactor);
                tempList.remove(tempList.size() - 1);
            }
        }
        tempList.remove(tempList.size() - 1);
    }

    //255 Verify Preorder Sequence in Binary Search Tree
    public static boolean verifyPreorder(int[] preorder) {
        int low = Integer.MIN_VALUE;
        int i = -1;
        for(int p : preorder){
            if(p < low)
                return false;
            while(i >= 0 && p > preorder[i])
                low = preorder[i--];
            System.out.println(" p: " + p + " i: " + i + " low: " + low);
            preorder[++i] = p;
        }
        return true;
    }

    //286
    public static void  wallsAndGates(int[][] rooms) {
        if(rooms == null || rooms.length == 0) return;
        for(int i = 0; i < rooms.length; i++) {
            for(int j = 0; j < rooms[0].length; j++) {
                if(rooms[i][j] == 0) setDistance(rooms, i, j, 0);
            }
        }
    }
    private static void setDistance(int[][] rooms, int row, int col, int distance){
        if(row < 0 || row >= rooms.length || col < 0 || col >= rooms[0].length || rooms[row][col] == -1) return;
        int[][] add = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        if(rooms[row][col] == Integer.MAX_VALUE) rooms[row][col] = distance;
        for(int i = 0; i < 8; i++) {
            setDistance(rooms, row + add[i][0], col + add[i][1], distance + 1);
        }
    }
    //323
    public static int countComponents(int n, int[][] edges) {
        int count = 0;
        int[] parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
        for(int[] edge : edges) {
            int id1 = findParent(parent, edge[0]), id2 = findParent(parent, edge[1]);
            parent[id1] = id2;
        }
        Set<Integer> set = new HashSet<>();
        for(int i : parent)
            set.add(i);
        return set.size();
    }
    private static int findParent(int[] parent, int index) {
        while(index != parent[index]) {
            parent[index] = parent[parent[index]];
            index = parent[index];
        }
        return index;
    }

}

