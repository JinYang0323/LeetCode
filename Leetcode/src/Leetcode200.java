public class Leetcode200 {

    public static void main(String[] arg0) {
        int[] nums = new int[] { 3,12,7,5,6,4 };
        System.out.println(findKthLargest(nums, 3));
    }
    public static int findKthLargest(int[] nums, int k) {
        return partition(nums, 0, nums.length - 1, nums.length - k);
    }
    private static int partition(int[] nums, int start, int end, int k){
        if(start >= end) return nums[k];
        int mid = nums[(start + end) / 2];
        int left = start, right = end;
        while(left <= right){
            while(left <= right && mid > nums[left]) left++;
            while(left <= right && mid < nums[right]) right--;
            if(left <= right){
                swap (nums, left++, right--);
            }
        }
        System.out.println("nums: ");
        for(int num : nums) System.out.print(num + ", ");
        if(k <= right)  partition(nums, start, right, k);
        if(k >= left)  partition(nums, left, end, k);
        return nums[k];
    }
    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
