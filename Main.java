// Hosung Kim
// 2/10/2022

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Main
{

    public static int[][] CountNeighbor(char[][]GameBoard, int[][]ScoreBoard, int xRange, int yRange, int rows, int columns)
    {
        for(int y=0; y<columns; y++)
        {
            for(int x=0; x<rows; x++)
            {
                boolean alive = false;

                if(x-1 >= 0) // checks cell diretly left if in range
                {
                    if(GameBoard[y][x-1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }

                if(x+1 <= xRange) // checks cell directly right if in range
                {
                    if(GameBoard[y][x+1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }

                if(y-1 >= 0) // checks cell directly above if in range
                {
                    if(GameBoard[y-1][x] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }                               
                }

                if(y+1 <= yRange) // checks cell directly below if in range
                {
                    if(GameBoard[y+1][x] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }   
                }

                if(x-1 >= 0 && y-1 >= 0) // Checks diagnol left up
                {
                    if(GameBoard[y-1][x-1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }

                if(x+1 <= xRange && y+1 <= yRange) // Checks diagnol right down
                {
                    if(GameBoard[y+1][x+1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }

                if(x+1 <= xRange && y-1 >= 0) // Checks diagnol right up
                {
                    if(GameBoard[y-1][x+1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }

                if(x-1 >= 0 && y+1 <= yRange) // Checks diagnol left down
                {
                    if(GameBoard[y+1][x-1] == 'X')
                    {
                        ScoreBoard[y][x] = ScoreBoard[y][x] + 1;
                    }
                }


            }
        }

        return ScoreBoard;
    }

    public static char[][]DeadOrAlive(char[][]GameBoard, int[][]ScoreBoard, int xRange, int yRange, int rows, int columns)
    {
        for(int y=0; y<columns; y++)
        {
            for(int x=0; x<rows; x++)
            {
                if(GameBoard[y][x] == 'X')
                {
                    if(ScoreBoard[y][x] < 2)
                    {
                        GameBoard[y][x] = '.';
                    }
                    else if(ScoreBoard[y][x] > 3)
                    {
                        GameBoard[y][x] = '.';
                    }
                    else if(ScoreBoard[y][x] == 2 || ScoreBoard[y][x] == 3)
                    {
                        GameBoard[y][x] = 'X';
                    }
                }
                else
                {
                    if(ScoreBoard[y][x] == 3)
                    {
                        GameBoard[y][x] = 'X';
                    }
                }
            }
        }

        return GameBoard;

    }

    public static int[][] ClearScoreBoard(int[][]ScoreBoard, int rows, int columns)
    {
        for(int x=0; x<rows; x++)
        {
            for(int y=0; y<columns; y++)
            {
                ScoreBoard[y][x] = 0;
            }
        }

        return ScoreBoard;
    }


    public static void main(String[] args) 
    {
        
        Scanner user = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = user.nextInt();

        System.out.print("\nEnter the number of columns: ");
        int columns = user.nextInt();

        char[][] GameBoard = new char[columns][rows];
        int [][] ScoreBoard = new int[columns][rows];
        char[][] PreviousGameBoard = new char[columns][rows];

        for(int x=0; x< rows; x++)
        {
            for(int y=0; y<columns; y++)
            {
                ScoreBoard[y][x] = 0;
            }
        }

        Random random = new Random();

        for(int i=0; i < columns; i++)
        {
            for(int a=0; a < rows; a++)
            {
                int seed = random.nextInt(10 + 1) + 1;

                if(seed < 3)
                {
                    GameBoard[i][a] = 'X';
                }

                else
                {
                    GameBoard[i][a] = '.';
                }

            }
        }

        GameBoard[0][0] = 'X'; // garuntees one live cel

        int generation = 1;

        System.out.println("\nGeneration: " + generation);

        for (char[] x : GameBoard)
        {
            for (char y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        System.out.println();

        int run = 1;
        int neighbors;

        while(run == 1)
        {
            if(run == 1)
            {
                int xRange = rows - 1;
                int yRange = columns - 1;

                PreviousGameBoard = GameBoard;

                CountNeighbor(GameBoard, ScoreBoard, xRange, yRange, rows, columns);
                DeadOrAlive(GameBoard, ScoreBoard, xRange, yRange, rows, columns);
                ClearScoreBoard(ScoreBoard, rows, columns);

                for (char[] x : GameBoard)
                {
                    for (char y : x)
                    {
                        System.out.print(y + " ");
                    }
                    System.out.println();
                }


                if(!(Arrays.deepEquals(PreviousGameBoard, GameBoard)))
                {
                    run = 2;
                    System.out.println("Arrays are equal");
                }
            }
            
            System.out.println("Generation: " + generation);

            generation = generation + 1;


            try 
            {
                Thread.sleep(750);
            } 
            catch(InterruptedException e)
            {
            }
            
            System.out.println();
            
        }

        System.out.println("Simulation does not change and has ended on generation " + generation);
    }
}