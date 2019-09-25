/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 3/9/2017
 * Class: COSC 314
 * Project: #2, Part 1
 */

import java.util.*;
import java.io.*;

public class Main {
    
    //Declaring random variable outside of main so it can be used by all methods
    public static Random randGen;
    
    //main method for Project 2 
    public static void main(String[] args) throws IOException{
        
        //declaring variable "n" to be used as a universal "size" variable
        int n;
        
        //FOR COSC 314 PROJECT #2, PART: 2
        //creating file locations to be used to locate external files and also
        //creating a location for the output file to be saved to.
        File inFileOne = new File("file1.txt"); 
        File inFileTwo = new File("file2.txt"); 
        File outFile = new File("OutputFile.txt");
        
        //creating a printwriter for the output file
        //(passed: outFile= out file location to be saved)
        PrintWriter pWriter = new PrintWriter(outFile);
        
        //scanner created for file external reading
        Scanner fIn = new Scanner(inFileOne); 
        
	
		
		//Label at top of output
		pWriter.println("COSC 314 Project #2, Part 1:");
		pWriter.println("");
		
        //read size of matrix from file
        n = fIn.nextInt(); 
		
        //create & populate matrix with external file data
        //(passed: n= size, fIn= scanner linked to external file)
        int[][] fMatrixOne = popFileMatrix(n, fIn);
		
        //Execute floyd's algorithm and assign it to fMatrixOne.
        //(passed: fMatrixOne= file populated matrix, n= size)
        fMatrixOne = floydsAlgo(fMatrixOne, n);
		
        //writing descriptor of data to file
        pWriter.println("Floyd's Algorithm file1.txt Output: ");
		
        //writing copy of matrix to file
        //(passed: fMatrixOne= floyd's algo output, n= size, pWriter= printwriter)
        printMatrix(fMatrixOne, n, pWriter);
		
        //closing scanner to ready for next iteration
        fIn.close();
        
		
		
        //scanner re-initialized with new value for file external reading
        fIn = new Scanner(inFileTwo);
		
        //read size of matrix from file
        n = fIn.nextInt();
		
        //create & populate matrix with external file data
        //(passed: n= size, fIn= scanner linked to external file)
        int[][] fMatrixTwo = popFileMatrix(n, fIn);
		
        //Execute floyd's algorithm and assign it to fMatrixOne.
        //(passed: fMatrixOne= file populated matrix, n= size)
        fMatrixTwo = floydsAlgo(fMatrixTwo, n);
		
        //writing descriptor of data to file
        pWriter.println("Floyd's Algorithm file2.txt Output: ");
		
        //writing copy of matrix to file
        //(passed: fMatrixOne= floyd's algo output, n= size, pWriter= printwriter)
        printMatrix(fMatrixTwo, n, pWriter);
		
        //closing scanner to maintain data integrity
        fIn.close();
        
		
        
        /*
        //Inneficiency testing for COSC 314 Project #2, part 1:
        //Each section of code does the following:
		//changes the value of n to the new desired array size to be tested.
		//declares a new matrix.
		//creates a "for loop" that runs ten times, each time refreshing the values in the matrix to maintain
		//original continuity between trials and make sure variability amongst trials is based on runtime 
		//and not from differences in the matrix itself.
		//runs the timedFloydsAlgo method which calculates time it takes to complete finding all shortest paths from
		//all potential vertices.
		//this section is disabled due to it just being used to gather data.
        n = 100;
        int[][] rndMatrixOne;
        for (int q = 0; q < 10; q++){
			rndMatrixOne = randPopMatrix(n, 13);
			timedFloydsAlgo(rndMatrixOne, n, 13);
        }
		
        n = 200;
        int[][] rndMatrixTwo;
        for (int w = 0; w < 10; w++){
			rndMatrixTwo = randPopMatrix(n, 13);
			timedFloydsAlgo(rndMatrixTwo, n, 13);
		}
		
        n = 500;
        int[][] rndMatrixThree;
        for (int e = 0; e < 10; e++){
			rndMatrixThree = randPopMatrix(n, 13);
			timedFloydsAlgo(rndMatrixThree, n, 13);
		}
		
        n = 1000;
        int[][] rndMatrixFour;
        for (int r = 0; r < 10; r++){
			rndMatrixFour = randPopMatrix(n, 13);
			timedFloydsAlgo(rndMatrixFour, n, 13);
        }
		*/
		
		//closing printwriter to maintain data integrity
        pWriter.close();
        
	
    }
    
	
	
    //Floyd's algorithm
    //uses iterative self-referential comparisons to create a matrix one can use to determine
	//the most efficient path to get from one graph vertex to another (if a path exists)
    //(passed: R= matrix, nElements= length of one side of matrix)
    public static int[][] floydsAlgo(int[][] R, int nElements){
        
        //creating matrix pointer, assigning passed matrix to new pointer 
        int[][] A = R;
		
        //creating & initializing a matrix of equal size to matrix A,
        //(passed: nElements= length of one side of matrix)
        int[][] B = popMatrix(nElements, -1);
        
        //3 nested for loops that compare values in a matrix in a specific order
        //to create a matrix detailing the most efficient transversals
		//one can make through a graphed data set, a matrix that gets more accurate
		//as iterations are made and recompiled with past iterations.
        for (int k = 0; k < nElements; k++){
			
            for (int i = 0; i < nElements; i++){
				
                for (int j = 0; j < nElements; j++){
					
                    B[i][j] = minVal(A[i][j], (A[i][k] + A[k][j]));
				}
			}
			
            //copying the values of matrix B to matrix  A so loop can reset and 
            //go onto next iteration of calculations (if it hasn't reached the last iteration yet
            for (int i = 0; i < nElements; i++){
                for (int j = 0; j < nElements; j++){
					
                    A[i][j] = B[i][j];
				}
			}
        }
        
        //return matrix A with completed Floyd's algorithm
        return A;
    }
    
	
	
	//min value function
	//takes two values, compares values and returns the minimum
	public static int minVal(int c, int x){
		
		if (c < x)
			return c;
		else
			return x;
	}
    
    
    
    //creates a matrix populated by all zero's based on input parameter value, returns created matrix
    //(passed: nPop= size of on side of desired matrix to be created
	//(passed (cont): zPop= 1:populate with all zeroes, -1:populate with all 9999999)
    public static int[][] popMatrix(int nPop, int zPop){
        
        //create new matrix based on input param size
        int[][] zeroMatrix = new int[nPop][nPop];
        
		if (zPop == -1){
			
			//populate new matrix with all 9999999's for easier "min" comparison later
			for (int t = 0; t < nPop; t++){
				
				for (int y = 0; y < nPop; y++){
					zeroMatrix[t][y] = 9999999;
				}
			}
        }
		
		else if (zPop != -1){
			
			//populate new matrix with all zero's
			for (int t = 0; t < nPop; t++){
				
				for (int y = 0; y < nPop; y++){
					zeroMatrix[t][y] = zPop;
				}
			}
		}
		
        //returns new matrix filled with all zero's
        return zeroMatrix;
    }
    
    
	
    //Makes a new matrix based off of properly formatted external file input, returns matrix
    //(passed: nPopFile= matrix dimension size based on external file
    //(passed(cont): keyInF = Scanner with file location attached to it)
    public static int[][] popFileMatrix(int nPopFile, Scanner keyInF){
        
		//creating temp variable to store read value for checking
		int pathExist = 0;
		
        //create new matrix based on read size in external file
        int[][] fileMatrix = new int[nPopFile][nPopFile];
        
        //copies all external file values to comparable size matrix
        for (int d = 0; d < nPopFile; d++){
            for (int f = 0; f < nPopFile; f++){
				
				//created variable to check if no path exists in matrix
				pathExist = keyInF.nextInt();
				
				//if the vert's destination is itself, mark as zero
				if (d == f){
					fileMatrix[d][f] = 0;
				}
				
				//if there is no path, mark as 9999999(to signify infinite distance between two verts)
				else if (pathExist == 0){
					fileMatrix[d][f] = 9999999;
				}
				
				//else, put distance value in new matrix
				else{
					fileMatrix[d][f] = pathExist;
				}
            }
        }
        
        //returns file populated matrix
        return fileMatrix;
    }
    
    
	
    //Creates a matrix based off of supplied matrix dimension size,
    //seed value and the user supplied probability of getting a connection
    //returns the random matrix
    //(passed: n= matrix size dimension, seedVal= seedvalue, pcnt= probability)
    public static int[][] randPopMatrix(int n, int seedVal){
        
        //create new random  generator based on suplied seed value
        randGen = new Random(seedVal);
        
        //creating matrix based on supplied size value
        int[][] randMatrix = new int[n][n];
        
        //populates matrix with approprate values at appropriate places based on probability parameter
        for (int t = 0; t < n; t++){
            for (int y = 0; y < n; y++){
				
				//fills matrix with values between 1 and 1000 randomly	
				randMatrix[t][y] = 1 + randGen.nextInt(1000);
				
            }
        }
        
        //return the populated matrix
        return randMatrix;
    }
    

    
    //prints the supplied matrix to an external file
    //(passed: toBePrinted= matrix to be printed, nPrint= length of matrix side,
    //(passed(cont): pW= printwriter with preconfigured file location attached.
    public static void printMatrix(int[][] toBePrinted, int nPrnt, PrintWriter pW){
		
		//prints the matrix dimension to the external file then goes to newline
        pW.println(nPrnt);
		
        //iterates through matrix, and prints matrix in proper form
        for (int d = 0; d < nPrnt; d++){
			
			//prints matrix elements with padding so everything lines up
            for (int f = 0; f < nPrnt; f++){
                pW.printf("%2d ", toBePrinted[d][f]);
            }
			
			//goes to new row when current row is completed printing
            pW.println("");
        }
		
		//adds space at end of printing to make room for next print job
        pW.println("\n");
    }
    
	
    
    //Executes the floydsAlgo method and keeps track 
    //of the time it takes for the method to execute
    //passed: R= supplied matrix for use, nVal= length of matrix side, seedVal= seed value for rnd #)
    public static void timedFloydsAlgo(int[][] R, int nVal, int seedVal){
        
        //makes note of initial time before Floyd's begins
        long startTime = System.currentTimeMillis();
		
        //executes Floyd's algorithm on supplied data
        //(passed: R= matrix to be used for floyd's algorithm, nVal= length of matrix side)
        R = floydsAlgo(R, nVal);
		
        //makes note of end time after Floyd's has completed
        long endTime = System.currentTimeMillis();
		
        //prints out properly formatted data to describe timing results of Floyd's algorithm
        System.out.println("N Value: " + nVal + " Seed Value: " + 
        seedVal + " Time taken to calculate Matrix Using Floyd's Algorithm: " +
        (endTime - startTime) + " Milliseconds");
    }
}