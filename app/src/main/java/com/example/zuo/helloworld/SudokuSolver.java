package com.example.zuo.helloworld;

/**
 * Created by zuo on 2016/3/7.
 */

import java.util.Arrays;
import java.util.LinkedList;

public class SudokuSolver {
    //public static int count=1;
    @SuppressWarnings("unchecked")
    public static boolean solve(String[][] board) {
        if(!checkSudokuValid(board)) return false;
        LinkedList<Character> possibleNums[][] = (LinkedList<Character>[][]) new LinkedList[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == null || board[i][j].length() == 0) board[i][j] = "0";
                possibleNums[i][j] = new LinkedList<Character>();
            }
        caculatePossibleNums(board, possibleNums);
        return solve(board, possibleNums);
    }

    private static void caculatePossibleNums(String[][] board, LinkedList<Character> possibleNums[][]) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j].charAt(0) > '9' || board[i][j].charAt(0) < '1') {
                    possibleNums[i][j].clear();
                    for (char ch = '1'; ch <= '9'; ch++) {
                        if (check(board, i, j, ch))
                            possibleNums[i][j].addLast(ch);
                    }
                }
    }


    private static boolean check(String[][] board, int i, int j, char ch) {
        for (int row = 0; row < 9; row++)
            if (board[row][j].charAt(0) == ch) return false;
        for (int cloum = 0; cloum < 9; cloum++)
            if (board[i][cloum].charAt(0) == ch) return false;
        int m = i / 3, n = j / 3;
        for (int p = 0; p < 3; p++)
            for (int q = 0; q < 3; q++) {
                if (board[m * 3 + p][n * 3 + q].charAt(0) == ch) return false;
            }
        return true;
    }

    private static int findMin(String[][] board, LinkedList<Character> possibleNums[][]) {
        int minIndex = 100, min = 10;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if ((board[i][j].charAt(0) > '9' || board[i][j].charAt(0) < '1') && possibleNums[i][j].size() < min) {
                    if (possibleNums[i][j].size() == 1) return i * 9 + j;
                    else {
                        min = possibleNums[i][j].size();
                        minIndex = i * 9 + j;
                    }
                }
            }
        return minIndex;
    }

    private static boolean solve(String[][] board, LinkedList<Character> possibleNums[][]) {
        int minIndex = findMin(board, possibleNums);
        if (minIndex > 80) return true;
        board[minIndex / 9][minIndex % 9] = "0";
        for (Character ch : possibleNums[minIndex / 9][minIndex % 9]) {
            if (check(board, minIndex / 9, minIndex % 9, ch)) {
                board[minIndex / 9][minIndex % 9] = ch + "";
                caculatePossibleNums(board, possibleNums);
                //System.out.println((count++)+":在"+(minIndex/9+1)+"行"+(minIndex%9+1)+"列填入"+ch);
                if (solve(board, possibleNums)) return true;
                else {
                    //System.out.println((count++)+":在"+(minIndex/9+1)+"行"+(minIndex%9+1)+"列去掉"+ch);
                    board[minIndex / 9][minIndex % 9] = "0";
                }
            }
        }
        board[minIndex / 9][minIndex % 9] = "0";
        return false;
    }
    private static  boolean checkSudokuValid(String[][] board)
    {
        boolean marked[] = new boolean[9];
        boolean temp[] = Arrays.copyOf(marked,9);
        //检查9个行
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
                if(board[i][j] != null && board[i][j].length() > 0&&board[i][j].charAt(0) <= '9' && board[i][j].charAt(0) > '0')
                {
                    if(marked[board[i][j].charAt(0)-'1']) return false;
                    marked[board[i][j].charAt(0)-'1'] = true;
                }
            System.arraycopy(temp,0,marked,0,9);
        }
        //检查9个列
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
                if(board[j][i] != null && board[j][i].length() > 0&&board[j][i].charAt(0) <= '9' && board[j][i].charAt(0) > '0')
                {
                    if(marked[board[j][i].charAt(0)-'1']) return false;
                    marked[board[j][i].charAt(0)-'1'] = true;
                }
            System.arraycopy(temp,0,marked,0,9);
        }
        //检查9个九宫格
        for (int p = 0; p < 3; p++)
            for (int q = 0; q < 3; q++) {
                //对第p行q列九格检查
                for(int i=0;i<3;i++)
                {
                    for(int j=0;j<3;j++)
                        if(board[p*3+i][q*3+j] != null && board[p*3+i][q*3+j].length() > 0&&board[p*3+i][q*3+j].charAt(0) <= '9' && board[p*3+i][q*3+j].charAt(0) > '0')
                        {
                            if(marked[board[p*3+i][q*3+j].charAt(0)-'1']) return false;
                            marked[board[p*3+i][q*3+j].charAt(0)-'1'] = true;
                        }
                }
                System.arraycopy(temp,0,marked,0,9);
            }
        return true;
    }
}
