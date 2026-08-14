class Solution {
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    for (int k = 0; k < board.length; k++) {
                        if (k != i && board[k][j] == board[i][j]){
                            System.out.println("ver");
                            return false;
                        }
                    }
                    for (int k = 0; k < board[i].length; k++) {
                        if (k != j && board[i][k] == board[i][j]) {
                            System.out.println("hor");
                            return false;
                        }
                    }

                    if (i <= 2 && i >= 0 && j <= 2 && j >= 0) {
                        for(int a =0; a< 3; a++) {
                            for(int b = 0; b < 3; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("1");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 2 && i >= 0 && j <= 5 && j >= 3) {
                        for(int a =0; a< 3; a++) {
                            for(int b = 3; b < 6; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("2");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 2 && i >= 0 && j <= 8 && j >= 6) {
                        for(int a =0; a< 3; a++) {
                            for(int b = 6; b < 9; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("3");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 5 && i >= 3 && j <= 2 && j >= 0) {
                        for(int a =3; a< 6; a++) {
                            for(int b = 0; b < 3; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("4");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 5 && i >= 3 && j <= 5 && j >= 3) {
                        for(int a =3; a< 6; a++) {
                            for(int b = 3; b < 6; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("5");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 5 && i >= 3 && j <= 8 && j >= 6) {
                        for(int a =3; a< 6; a++) {
                            for(int b = 6; b < 9; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("6");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 8 && i >= 6 && j <= 2 && j >= 0) {
                        for(int a =6; a< 9; a++) {
                            for(int b = 0; b < 3; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("7");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 8 && i >= 6 && j <= 5 && j >= 3) {
                        for(int a =6; a< 9; a++) {
                            for(int b = 3; b < 6; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("8");
                                    return false;
                                }
                            }
                        }
                    } else if (i <= 8 && i >= 6 && j <= 8 && j >= 6) {
                        for(int a =6; a< 9; a++) {
                            for(int b = 6; b < 9; b++) {
                                if(a != i && b != j && board[a][b] == board[i][j]) {
                                    System.out.println("9");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}