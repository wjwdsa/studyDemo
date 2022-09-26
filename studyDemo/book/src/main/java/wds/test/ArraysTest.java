package wds.test;

import java.util.Arrays;

/******************************
 * @Description 合并两个整型数组
 * @author Administrator
 * @date 2022-08-30 20:49
 ******************************/
public class ArraysTest {
    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
//        nums1 = IntStream.concat(Arrays.stream(nums1), Arrays.stream(nums2)).toArray();
//        System.out.println("nums1 = " + Arrays.toString(nums1));
//        nums1 = IntStream.concat(IntStream.of(nums1), IntStream.of(nums2)).toArray();
//        System.out.println("nums1 = " + Arrays.toString(nums1));
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
        System.out.println("nums1 = " + Arrays.toString(nums1));
    }
}
