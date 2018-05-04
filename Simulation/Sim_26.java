import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Sim_26 {

	/* Written by: Thomas Hoffman 3/15/2017
	 *
	 * Description: This program generates n poisson variates
	 * just like in problem number 16 of the textbook and exports
	 * the values to a CSV file for Excel analysis
	 *
	 */

	public static void main(String[] args) throws FileNotFoundException{
		double rand, alpha = 2.5;
		int j, end, n = 2001;
		int[] poissonVariates = new int[n];

		for(int i = 0; i < n; i++){

			rand = Math.random(); //initialize these variables for each iteration
			j = 0;
			end = 0;

			while(end == 0){

				if(rand < Math.exp(-alpha)){ //if the random number is less than e^-Alpha
					poissonVariates[i] = j; //place count variable into array
					System.out.println("Random Variate "+i+" = "+j);
					end = 1; //break while loop

				}else{ //Reject
					rand = rand*Math.random(); //multiply current random number by a new one
					j++; //increment counting variable
				}
			}
		}

		File file = new File("data.csv");
		PrintWriter write = new PrintWriter(file);

		for(int i = 0; i < n-1; i++){
			write.print(poissonVariates[i]+"\n");
			write.flush();
		}
	}
}
