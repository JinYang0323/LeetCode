import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Leetcode300 {

    public static void main(String[] arg0) {

        int[] nums = new int[] { 1, 2, 3, 0, 2 };
        String[][] tickets = new String[][] { { "JFK", "SFO" }, { "JFK", "ATL" }, { "SFO", "ATL" }, { "ATL", "JFK" },
                { "ATL", "SFO" } };
        System.out.println(findItinerary(tickets));
    }

    // 307 Range Sum Query - Mutable
    static class NumArray {

        class SegmentTreeNode {
            int start, end;
            SegmentTreeNode left, right;
            int sum;

            public SegmentTreeNode() {
            }

            public SegmentTreeNode(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        SegmentTreeNode root;

        public NumArray(int[] nums) {
            root = NumArrayHelper(nums, 0, nums.length - 1);
        }

        private SegmentTreeNode NumArrayHelper(int[] nums, int start, int end) {
            if (start > end)
                return null;
            SegmentTreeNode node = new SegmentTreeNode(start, end);
            if (start == end)
                node.sum = nums[start];
            else {
                int mid = start + (end - start) / 2;
                node.left = NumArrayHelper(nums, start, mid);
                node.right = NumArrayHelper(nums, mid + 1, end);
                node.sum = node.left.sum + node.right.sum;
            }
            return node;
        }

        public void update(int i, int val) {
            updateHelper(root, i, val);
        }

        private void updateHelper(SegmentTreeNode root, int i, int val) {
            if (root.start == root.end) {
                root.sum = val;
                return;
            }
            int mid = root.start + (root.end - root.start) / 2;
            if (mid < i)
                updateHelper(root.right, i, val);
            else
                updateHelper(root.left, i, val);
            root.sum = root.left.sum + root.right.sum;
        }

        public int sumRange(int i, int j) {
            return sumRangeHelper(root, i, j);
        }

        private int sumRangeHelper(SegmentTreeNode root, int i, int j) {
            if (root.start == i && root.end == j)
                return root.sum;
            int mid = root.start + (root.end - root.start) / 2;
            if (j <= mid)
                return sumRangeHelper(root.right, i, j);
            else if (i > mid)
                return sumRangeHelper(root.left, i, j);
            else
                return sumRangeHelper(root.left, i, mid) + sumRangeHelper(root.right, mid + 1, j);
        }
    }

    // 308
    public static int maxProfit(int[] prices) {
        int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE, prev_buy;
        for (int price : prices) {
            prev_buy = buy;
            buy = Math.max(prev_sell - price, prev_buy);
            prev_sell = sell;
            sell = Math.max(prev_buy + price, prev_sell);
            System.out
                    .println("prev_buy: " + prev_buy + " buy: " + buy + " prev_sell: " + prev_sell + " sell: " + sell);
        }
        return sell;
    }

    // 332. Reconstruct Itinerary
    public static List<String> findItinerary(String[][] tickets) {
        List<String> list = new ArrayList<>();
        if (tickets == null || tickets.length == 0)
            return list;
        HashMap<String, List<String>> map = new HashMap<>();
        int n = tickets.length;
        for (String[] ticket : tickets) {
            if (!map.containsKey(ticket[0]))
                map.put(ticket[0], new ArrayList<>());
            map.get(ticket[0]).add(ticket[1]);
        }
        for (String key : map.keySet()) {
            Collections.sort(map.get(key));
        }
        list.add("JFK");
        while (list.size() <= n) {
            String last = list.get(list.size() - 1);
            list.add(map.get(last).get(0));
            map.get(last).remove(0);
        }
        return list;
    }

    // 555. Split Concatenated Strings
    public static String splitLoopedString(String[] strs) {
        int n = strs.length;
        List<String> lst = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String s = strs[i];
            String reversed = new StringBuilder(s).reverse().toString();
            lst.add((s.compareTo(reversed) > 0) ? s : reversed);
        }
        String res = "";
        for (int i = 0; i < n; i++) {
            String[] temp = new String[] { strs[i], new StringBuilder(strs[i]).reverse().toString() };
            for (String start : temp) {
                for (int j = 0; j < start.length(); j++) {
                    StringBuilder loop = new StringBuilder();
                    loop.append(start.substring(j));
                    // join
                    for (int k = 0; k < n - 1; k++) {
                        loop.append(lst.get((i + 1 + k) % n));
                    }
                    loop.append(start.substring(0, j));
                    res = (res.compareTo(loop.toString()) > 0) ? res : loop.toString();
                }
            }
        }

        return res;
    }

}
