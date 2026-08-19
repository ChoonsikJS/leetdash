import java.util.Scanner;
public class Soultion {
    public static void main(String args[]) throws Exception
	{
		
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
			
			int M = sc.nextInt();
			int K = sc.nextInt();	
			char[][] field = new char[M][K];
			 int tankI = 0;
			 int tankJ = 0;
			for (int i = 0; i < M; i++) {
				String a = sc.next();
				for (int j = 0; j < K; j++) {
					field[i][j]=a.charAt(j);
					 if(field[i][j] == '^' ||field[i][j] == 'v' ||field[i][j] == '<' ||  field[i][j] == '>') {
		                        tankI = i;
		                        tankJ = j;
					 }
				}
			}
			int N = sc.nextInt();
			String command = sc.next();
				 for(int c = 0; c < N; c++) {
		                char cmd = command.charAt(c);
		                int i = tankI;
		                int j = tankJ;
		                if(cmd == 'U') {
		                    field[i][j] = '^';
		                    if(i - 1 >= 0 && field[i - 1][j] == '.') {
		                        field[i - 1][j] = '^';
		                       field[i][j] = '.';
		                        tankI = i - 1;
		                    }
		                }
		                else if(cmd == 'D') {
		                    field[i][j] = 'v';
		                    if(i + 1 < M && field[i + 1][j] == '.') {
		                        field[i + 1][j] = 'v';
		                        field[i][j] = '.';
		                        tankI = i + 1;
		                    }
		                }
		                else if(cmd == 'L') {
		                    field[i][j] = '<';
		                    if(j - 1 >= 0 && field[i][j - 1] == '.') {
		                        field[i][j - 1] = '<';
		                        field[i][j] = '.';
		                        tankJ = j - 1;
		                    }
		                }
		                else if(cmd == 'R') {
		                    field[i][j] = '>';
		                    if(j + 1 < K && field[i][j + 1] == '.') {
		                    	field[i][j + 1] = '>';
		                        field[i][j] = '.';
		                        tankJ = j + 1;
		                    }
		                }
		                else if(cmd == 'S') {
		                    if(field[i][j] == '^') {
		                        for(int k = i - 1; k >= 0; k--) {
		                            if(field[k][j] == '#') {
		                                break;
		                            }
		                            if(field[k][j] == '*') {
		                                field[k][j] = '.';
		                                break;
		                            }
		                        }
		                    }
		                    else if(field[i][j] == 'v') {
		                        for(int k = i + 1; k < M; k++) {
		                            if(field[k][j] == '#') {
		                                break;
		                            }
		                            if(field[k][j] == '*') {
		                                field[k][j] = '.';
		                                break;
		                            }
		                        }
		                    }
		                    else if(field[i][j] == '<') {
		                        for(int k = j - 1; k >= 0; k--) {
		                            if(field[i][k] == '#') {
		                                break;
		                            }
		                            if(field[i][k] == '*') {
		                                field[i][k] = '.';
		                                break;
		                            }
		                        }
		                    }

		                    else if(field[i][j] == '>') {
		                        for(int k = j + 1; k < K; k++) {
		                            if(field[i][k] == '#') {
		                                break;
		                            }
		                            if(field[i][k] == '*') {
		                                field[i][k] = '.';
		                                break;
		                            }
		                        }
		                    }
		                }
		            }
			System.out.print("#"+test_case+" ");
		for (int l = 0; l < M; l++) {
			for (int l2 = 0; l2 < K; l2++) {
				System.out.print(field[l][l2]);
			}System.out.println();
		}

		}
		
	}
}

