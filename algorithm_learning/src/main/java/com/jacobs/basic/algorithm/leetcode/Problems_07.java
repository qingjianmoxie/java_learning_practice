package com.jacobs.basic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author lichao
 * @date 2018/2/8
 */
public class Problems_07 {

  public static void main(String[] args) {
//    int[] A = new int[]{-2, -1};
//    System.out.println(maxSubArray_101(A));
//    System.out.println(canJump_099(A));
//    System.out.println(
//        spiralOrder_100(
//            new int[][]{{1, 11}, {2, 12}, {3, 13}, {4, 14}, {5, 15}, {6, 16}, {7, 17}, {8, 18},
//                {9, 19}, {10, 20}}));
    //System.out.println(solveNQueens_102(8));

    //System.out.println(permute_105(new int[]{1, 2, 3, 4}));
    //System.out.println(multiply("123", "456"));
    System.out.println(combinationSum2_112(new int[]{1, 1}, 2));
  }

  //  Given an array of non-negative integers, you are initially positioned at the first index of the array.
//
//  Each element in the array represents your maximum jump length at that position.
//
//      Determine if you are able to reach the last index.
//
//  For example:
//  A =[2,3,1,1,4], returntrue.
//
//      A =[3,2,1,0,4], returnfalse.
  //动态规划，第一个设为true，其他看情况

  public static boolean canJump_099(int[] A) {
    if (A == null || A.length == 0) {
      return true;
    }

    int length = A.length;
    boolean[] dp = new boolean[length];
    dp[0] = true;
    for (int i = 1; i < length; i++) {
      dp[i] = false;
    }

    for (int i = 0; i < length && dp[i]; i++) {
      for (int j = 0; j <= A[i] && (i + j < length); j++) {
        dp[i + j] = true;
      }
    }

    return dp[length - 1];
  }


  //  Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
//
//  For example,
//  Given the following matrix:
//
//      [
//      [ 1, 2, 3,4 ],
//      [ 5, 6,7,8 ],
//      [ 9, 10 ,11,12 ]
  //    [13, 14, 15, 16]
//      ]
//  You should return[1,2,3,6,9,8,7,4,5].
  public static ArrayList<Integer> spiralOrder_100(int[][] matrix) {
    ArrayList<Integer> resultList = new ArrayList<>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return resultList;
    }

    if (matrix.length == 1 && matrix[0].length == 1) {
      resultList.add(matrix[0][0]);
      return resultList;
    }

    int starti = 0;
    int startj = 0;
    int endi = matrix.length - 1;
    int endj = matrix[0].length - 1;
    while (starti <= endi && startj <= endj) {
      //当只有一行一列的情况
      if (starti == endi) {
        for (int j = startj; j <= endj; j++) {
          resultList.add(matrix[starti][j]);
        }
        return resultList;
      } else if (startj == endj) {
        for (int i = starti; i <= endi; i++) {
          resultList.add(matrix[i][startj]);
        }
        return resultList;
      } else {
        int i = starti;
        int j = startj;
        //上边
        while (j < endj) {
          resultList.add(matrix[i][j++]);
        }

        //右边
        while (i < endi) {
          resultList.add(matrix[i++][j]);
        }

        //下边
        while (j > startj) {
          resultList.add(matrix[i][j--]);
        }

        //上边,加一的目的是不需要重复的加，只需要等待下一次循环就好
        while (i > starti) {
          resultList.add(matrix[i--][j]);
        }

        starti++;
        startj++;
        endi--;
        endj--;
      }
    }

    return resultList;
  }


  //  Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
//
//  For example, given the array[−2,1,−3,4,−1,2,1,−5,4],
//  the contiguous subarray[4,−1,2,1]has the largest sum =6.
//
// 思路: sum[i,j]=sum[0,j]-sum[0-i]
  public static int maxSubArray_101(int[] A) {
    if (A == null || A.length == 0) {
      return -1;//非法值
    }

    if (A.length == 1) {
      return A[0];
    }

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int sum = A[0];

    for (int i = 0; i < A.length; i++) {
      min = Math.min(min, sum);
      sum += A[i];
      max = Math.max(max, sum - min);
    }

    return max;
  }


  //  Given an integer n, return all distinct solutions to the n-queens puzzle.
//
//  Each solution contains a distinct board configuration of the n-queens' placement, where'Q'and'.'both indicate a queen and an empty space respectively.
  public static ArrayList<String[]> solveNQueens_102(int n) {
    ArrayList<String[]> resultList = new ArrayList<>();
    if (n <= 0) {
      return resultList;
    }

    //cols[i]表示第i个皇后所在的列
    int[] cols = new int[n];
    //循环n列
    for (int i = 0; i < n; i++) {
      cols[0] = i;//先放置第一个确定的皇后,之后从第二行开始
      queensHelper(resultList, cols, 1);
    }

    return resultList;
  }

  public static void queensHelper(ArrayList<String[]> resultList, int[] cols, int row) {
    if (row == cols.length) {
      String[] resultStrs = new String[cols.length];
      for (int i = 0; i < resultStrs.length; i++) {
        resultStrs[i] = "";
        for (int j = 0; j < resultStrs.length; j++) {
          if (j == cols[i]) {
            resultStrs[i] += "Q";
          } else {
            resultStrs[i] += ".";
          }
        }
      }
      resultList.add(resultStrs);
      return;
    }

    //递归放置其他皇后
    for (int i = 0; i < cols.length; i++) {
      if (isValid(cols, row, i)) {
        cols[row] = i;
        queensHelper(resultList, cols, row + 1);
      }
    }
  }

  public static boolean isValid(int[] cols, int row, int col) {
    if (cols == null) {
      return false;
    }

    //与之前的对比
    for (int i = 0; i < row; i++) {
      if (cols[i] == col || Math.abs(cols[i] - col) == Math.abs(row - i)) {
        return false;
      }
    }

    return true;
  }

  //  Follow up for N-Queens problem.
//
//  Now, instead outputting board configurations, return the total number of distinct solutions.
  static int sum = 0;

  public static int totalNQueens_103(int n) {
    if (n <= 0) {
      return 0;
    }

    //cols[i]表示第i个皇后所在的列
    int[] cols = new int[n];
    //循环n列
    for (int i = 0; i < n; i++) {
      cols[0] = i;//先放置第一个确定的皇后,确定了行与列
      queensHelper2(cols, 1);
    }

    return sum;
  }

  public static void queensHelper2(int[] cols, int row) {
    if (row == cols.length) {
      sum = sum + 1;
      return;
    }

    //递归放置其他皇后
    for (int i = 0; i < cols.length; i++) {
      if (isValid(cols, row, i)) {
        cols[row] = i;
        queensHelper2(cols, row + 1);
      }
    }
  }

  //Implement pow(x, n).
  public static double pow_104(double x, int n) {
    if (n == 0) {
      return 1;
    }

    if (n < 0) {
      return 1 / x * pow_104(1 / x, -(n + 1));
    }
    if (n % 2 == 0) {
      return pow_104(x * x, n / 2);
    } else {
      return pow_104(x * x, n / 2) * x;
    }
  }


  //  Given a collection of numbers, return all possible permutations.
//
//      For example,
//  [1,2,3]have the following permutations:
//      [1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2], and[3,2,1].
  public static ArrayList<ArrayList<Integer>> permute_105(int[] num) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0) {
      return resultList;
    }

    permute_helper(resultList, new ArrayList<>(), num);

    return resultList;
  }

  public static void permute_helper(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] nums) {
    if (result.size() == nums.length) {
      resultList.add(new ArrayList<>(result));
    }

    for (int i = 0; i < nums.length; i++) {
      if (!result.contains(nums[i])) {
        result.add(nums[i]);
        permute_helper(resultList, result, nums);
        result.remove(result.size() - 1);
      }
    }
  }

  //  Given a collection of numbers that might contain duplicates, return all possible unique permutations.
//
//  For example,
//  [1,1,2]have the following unique permutations:
//      [1,1,2],[1,2,1], and[2,1,1].
  public static ArrayList<ArrayList<Integer>> permuteUnique_106(int[] num) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0) {
      return resultList;
    }

    Arrays.sort(num);
    boolean[] used = new boolean[num.length];
    permute_helper2(resultList, new ArrayList<>(), num, used);

    return resultList;
  }

  public static void permute_helper2(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] nums, boolean[] used) {
    if (result.size() == nums.length) {
      resultList.add(new ArrayList<>(result));
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) {
        continue;
      }
      if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
        continue;
      }

      result.add(nums[i]);
      used[i] = true;
      permute_helper2(resultList, result, nums, used);
      used[i] = false;
      result.remove(result.size() - 1);
    }
  }


  //  Given an array of non-negative integers, you are initially positioned at the first index of the array.
//
//  Each element in the array represents your maximum jump length at that position.
//
//  Your goal is to reach the last index in the minimum number of jumps.
//
//  For example:
//  Given array A =[2,3,1,1,4]
//
//  The minimum number of jumps to reach the last index is2. (Jump1step from index 0 to 1, then3steps to the last index.)
  public static int jump_107(int[] A) {
    if (A == null || A.length == 0) {
      return 0;
    }

    //dp[i]表示到达i点的最小步数
    int[] dp = new int[A.length];
    dp[0] = 0;
    for (int i = 0; i < A.length; i++) {
      int maxPosition = Math.min(i + A[i], A.length - 1);
      for (int j = i + 1; j <= maxPosition; j++) {
        if (dp[j] == 0) {
          dp[j] = dp[i] + 1;
        } else {
          dp[j] = Math.min(dp[j], dp[i] + 1);
        }
      }
    }

    return dp[A.length - 1];
  }

  //  Implement wildcard pattern matching with support for'?'and'*'.
//
//      '?' Matches any single character.
//      '*' Matches any sequence of characters (including the empty sequence).
//
//  The matching should cover the entire input string (not partial).
  public static boolean isMatch_108(String s, String p) {
    if ((s == null && p == null) || (s.length() == 0 && p.length() == 0)) {
      return true;
    }
    if (p.length() == 0 && s.length() != 0) {
      return false;
    }

    boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
    dp[0][0] = true;

    //匹配空字符
    for (int j = 1; j <= p.length(); j++) {
      if (p.charAt(j - 1) == '*') {
        dp[0][j] = dp[0][j - 1];
      }
    }

    for (int i = 1; i <= s.length(); i++) {
      for (int j = 1; j <= p.length(); j++) {
        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
          dp[i][j] = dp[i - 1][j - 1];
        }

        if (p.charAt(j - 1) == '*') {
          dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
        }
      }
    }

    return dp[s.length()][p.length()];
  }

  //  Given two numbers represented as strings, return multiplication of the numbers as a string.
//
//      Note: The numbers can be arbitrarily large and are non-negative.
  public static String multiply(String num1, String num2) {
    char[] chars1 = num1.length() > num2.length() ? num2.toCharArray() : num1.toCharArray();
    char[] chars2 = num1.length() > num2.length() ? num1.toCharArray() : num2.toCharArray();

    LinkedList<Integer> resultList = new LinkedList<>();

    int zeroNum = 0;
    for (int i = chars1.length - 1; i >= 0; i--) {
      LinkedList<Integer> tempResultList = getTempResultList(chars1[i], chars2);
      setResultList(resultList, zeroNum, tempResultList);
      zeroNum++;
    }

    StringBuilder resultBuilder = new StringBuilder();
    for (Integer temp : resultList) {
      resultBuilder.append(temp);
    }

    return resultBuilder.toString();
  }

  private static LinkedList<Integer> getTempResultList(char c, char[] chars2) {
    int up = 0;
    int curr = 0;
    LinkedList<Integer> tempResultList = new LinkedList<>();
    for (int j = chars2.length - 1; j >= 0; j--) {
      curr =
          Integer.valueOf(String.valueOf(c)) * Integer.valueOf(String.valueOf(chars2[j]))
              + up;
      if (curr >= 10) {
        up = curr / 10;
        curr = curr % 10;
      } else {
        //注意up每次都得变
        up = 0;
      }
      //加入当前位的值
      tempResultList.addFirst(curr);
    }
    //一次计算完成判断最后是否应该进位
    if (up != 0) {
      tempResultList.addFirst(up);
    }
    return tempResultList;
  }

  private static void setResultList(LinkedList<Integer> resultList, int index,
      LinkedList<Integer> tempResultList) {
    //每层先补0
    for (int k = 0; k < index; k++) {
      tempResultList.addLast(0);
    }
    //里层循环结束，将和加起来
    int tempi = resultList.size() - 1;
    int tempj = tempResultList.size() - 1;
    int tempUp = 0;
    int tempCurr = 0;
    while (tempi >= 0 && tempj >= 0) {
      tempCurr = resultList.get(tempi) + tempResultList.get(tempj) + tempUp;
      if (tempCurr >= 10) {
        tempUp = tempCurr / 10;
        tempCurr = tempCurr % 10;
      } else {
        tempUp = 0;
      }
      resultList.set(tempi, tempCurr);
      tempi--;
      tempj--;
    }
    while (tempj >= 0) {
      if (tempUp != 0) {
        resultList.addFirst(tempResultList.get(tempj) + tempUp);
        tempUp = 0;
      } else {
        resultList.addFirst(tempResultList.get(tempj));
      }
      tempj--;
    }
    if (tempUp != 0) {
      resultList.addFirst(tempUp);
    }
  }

  //easy solution
  public static String multiply_108(String num1, String num2) {
    int n1 = num1.length();
    int n2 = num2.length();

    StringBuilder resultBuilder = new StringBuilder();
    int[] tmp = new int[n1 + n2];

    for (int i = n1 - 1; i >= 0; i--) {
      for (int j = n2 - 1; j >= 0; j--) {
        tmp[i + j + 1] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
      }
    }

    int carrybit = 0;
    for (int i = tmp.length - 1; i >= 0; i--) {
      tmp[i] += carrybit;
      carrybit = tmp[i] / 10;
      tmp[i] = tmp[i] % 10;
    }

    int left = 0;
    while (left < -1 && tmp[left] == 0) {
      left++;
    }

    for (; left < tmp.length - 1; left++) {
      resultBuilder.append(tmp[left]);
    }
    return resultBuilder.toString();
  }


  //  Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
//
//      For example,
//  Given[0,1,0,2,1,0,1,3,2,1,2,1], return6.
  public static int trap_109(int[] A) {
    if (A == null || A.length == 0) {
      return 0;
    }

    int maxHigh = 0;
    int left = 0;
    int right = 0;
    //先找到最高的点
    for (int i = 0; i < A.length; i++) {
      if (A[i] > A[maxHigh]) {
        maxHigh = i;
      }
    }

    int sum = 0;
    //计算左边的容量
    for (int i = 0; i < maxHigh; i++) {
      if (A[i] < left) {
        sum += left - A[i];
      } else {
        left = A[i];
      }
    }

    //右边的容量
    for (int i = A.length - 1; i > maxHigh; i++) {
      if (A[i] < right) {
        sum += right - A[i];
      } else {
        right = A[i];
      }
    }

    return sum;
  }


  //  Given an unsorted integer array, find the first missing positive integer.
//
//  For example,
//  Given[1,2,0]return3,
//  and[3,4,-1,1]return2.
//
//  Your algorithm should run in O(n) time and uses constant space.
  public static int firstMissingPositive_110(int[] A) {
    if (A == null || A.length == 0) {
      return -1;//非法值
    }

    //每一个数都调整到i-1的位置
    for (int i = 0; i < A.length; i++) {
      if (A[i] >= 1 && A[i] <= A.length && A[i] != A[A[i] - 1]) {
        swap(A, i, A[i] - 1);
        //继续判断
        i--;
      }
    }

    //从头开始扫描，第一个出现A[i]!=i+1;的即是所缺的
    for (int i = 0; i < A.length; i++) {
      if (A[i] != i + 1) {
        return i + 1;
      }
    }

    return A.length + 1;
  }

  public static void swap(int[] arr, int n1, int n2) {
    int temp = arr[n1];
    arr[n1] = arr[n2];
    arr[n2] = temp;
  }


  //  Given a set of candidate numbers ( C ) and a target number ( T ), find all unique combinations in C where the candidate numbers sums to T .
//
//  The same repeated number may be chosen from C unlimited number of times.
//
//      Note:
//
//  All numbers (including target) will be positive integers.
//  Elements in a combination (a 1, a 2, … , a k) must be in non-descending order. (ie, a 1 ≤ a 2 ≤ … ≤ a k).
//  The solution set must not contain duplicate combinations.
//
//  For example, given candidate set2,3,6,7and target7,
//  A solution set is:
//      [7]
//      [2, 2, 3]
  public static ArrayList<ArrayList<Integer>> combinationSum_111(int[] candidates, int target) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (candidates == null || candidates.length == 0 || target < 0) {
      return resultList;
    }
    Arrays.sort(candidates);
    combinationSumHelper(resultList, new ArrayList<>(), candidates, 0, target);
    return resultList;
  }

  private static void combinationSumHelper(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] candidates, int start, int target) {
    if (target < 0) {
      return;
    }

    if (target == 0) {
      resultList.add(new ArrayList<>(result));
    }

    int index = start - 1 < 0 ? 0 : start - 1;
    for (int i = index; i < candidates.length; i++) {
      result.add(candidates[i]);
      combinationSumHelper(resultList, result, candidates, i + 1, target - candidates[i]);
      result.remove(result.size() - 1);
    }
  }


  //Each number in C may only be used once in the combination.
  public static ArrayList<ArrayList<Integer>> combinationSum2_112(int[] num, int target) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0 || target < 0) {
      return resultList;
    }
    Arrays.sort(num);
    combinationSumHelper2(resultList, new ArrayList<>(), num, 0, target);
    return resultList;
  }

  private static void combinationSumHelper2(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] candidates, int start, int target) {
    if (target < 0) {
      return;
    }

    if (target == 0) {
      if (!resultList.contains(result)) {
        resultList.add(new ArrayList<>(result));
      }
    }

    for (int i = start; i < candidates.length; i++) {
      result.add(candidates[i]);
      combinationSumHelper2(resultList, result, candidates, i + 1, target - candidates[i]);
      result.remove(result.size() - 1);
    }
  }


  //  The count-and-say sequence is the sequence of integers beginning as follows:
//      1, 11, 21, 1211, 111221, ...
//
//      1is read off as"one 1"or11.
//  11is read off as"two 1s"or21.
//  21is read off as"one 2, thenone 1"or1211.
//
//  Given an integer n, generate the n th sequence.
//
//  Note: The sequence of integers will be represented as a string.
  public static String countAndSay_113(int n) {
    return "";
  }


  //  Write a program to solve a Sudoku puzzle by filling the empty cells.
//
//  Empty cells are indicated by the character'.'.
//
//  You may assume that there will be only one unique solution.
  public static void solveSudoku_114(char[][] board) {
    return;
  }

  public int searchInsert_115(int[] A, int target) {
    // 二分查找
    int low = 0;
    int high = A.length - 1;
    while (low <= high) {
      int mid = (low + high) / 2;
      if (A[mid] == target) {
        return mid;
      } else if (A[mid] < target) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    // 没找到返回low,即插入位置
    return low;
  }
}