package com.github.qcyin;


public class Test {
    public static void main(String[] args) {
        String s = "LEETCODEISHIRING";
        System.out.println(convert(s, 4));
    }

    private static int find(int[] nums1, int start1, int end1,
                     int[] nums2, int start2, int end2,
                     int cnt){
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        if (len1 > len2) {
            return find(nums2, start2, end2, nums1, start1, end1, cnt);
        }
        if (len1 == 0){
            return nums2[start2 + cnt - 1];
        }
        if (cnt == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        int half = cnt >> 1;
        int pos1 = start1 + Math.min(half, len1) - 1;
        int pos2 = start2 + half - 1;
        if (nums1[pos1] > nums2[pos2]) {
            return find(nums1, start1, end1, nums2, pos2 + 1, end2, cnt - half);
        }else {
            return find(nums1, pos1 + 1, end1, nums2, start2, end2, cnt - Math.min(half, len1));
        }
    }

    private static String convert(String s, int numRows){
        if (numRows == 0) {
            return "";
        }
        int len = 0;
        if (numRows == 1 || s == null || (len = s.length()) <= 2) {
            return s;
        }

        // ==========================

        StringBuilder sbd = new StringBuilder();

        int offset = (numRows - 2) << 1 | 1;
        for (int i = 0; i < numRows; i++) {
            sbd.append(s.charAt(1));
            sbd.append(s.charAt(1 + offset));

        }
        return "";
    }
}
