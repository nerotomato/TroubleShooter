package com.nerotomato.leetcode;

/**
 * @Created by nerotomato on 2021/8/19.
 */
public class MaxConsecutiveOnes3 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        System.out.println(longestOnes(nums, k));
    }

    /**
     * 重点：题意转换。把「最多可以把 K 个 0 变成 1，求仅包含 1 的最长子数组的长度」转换为 「找出一个最长的子数组，该子数组内最多允许有 K 个 0 」。
     * 经过上面的题意转换，我们可知本题是求最大连续子区间，可以使用滑动窗口方法。滑动窗口的限制条件是：窗口内最多有 K 个 0。
     * <p>
     * 代码思路：
     * <p>
     * 使用 leftleft 和 rightright 两个指针，分别指向滑动窗口的左右边界。
     * rightright 主动右移：rightright 指针每次移动一步。当 A[right]A[right] 为 00，说明滑动窗口内增加了一个 00；
     * leftleft 被动右移：判断此时窗口内 00 的个数，如果超过了 KK，则 leftleft 指针被迫右移，直至窗口内的 00 的个数小于等于 KK 为止。
     * 滑动窗口长度的最大值就是所求。
     * 时间复杂度：O(N)O(N)，因为每个元素只遍历了一次。
     * 空间复杂度：O(1)O(1)，因为使用了常数个空间。
     */
    public static int longestOnes(int[] nums, int k) {
        int length = nums.length;
        int sumLength = 0;
        int left = 0, right = 0;
        int zeros = 0;
        while (right < length) {
            if (nums[right] == 0)
                zeros++;
            while (zeros > k) {
                if (nums[left++] == 0) {
                    zeros--;
                }
            }
            sumLength = Math.max(sumLength, right - left + 1);
            right++;
        }
        return sumLength;
    }
}
