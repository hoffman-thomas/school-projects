import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Sim_24 {

	/* Written by: Thomas Hoffman 3/15/2017
	 *
	 * Description: This program generates n random numbers and
	 * n random standard normal variates using congruential
	 * generators and the Direct Transformation technique. Values
	 * for the congruential generator are chosen to maximize the
	 * period of repetition for the generator.The program then writes
	 * generated values to a CSV file for further analysis.
	 *
	 */

	static int n = 10000, a = 16807, c = 0, m = 2147483647, seed = 123457;

	public static void main(String[] args) throws FileNotFoundException{

		double[]stdNormVars = genStdNormVars();
		File file = new File("data.csv");
		PrintWriter write = new PrintWriter(file);

		for(int i = 0; i < n; i++){
			write.print(stdNormVars[i]+"\n");
			write.flush();
		}
	}

	public static double[] genRandNums(){	 //generates n random numbers and returns that array
		double[] randNums = new double[n];
		double[] genNums = new double[n];
		genNums[0] = (double)seed;

		for(int i = 1; i < n; i++){
			genNums[i] = ((a*genNums[i-1] + c) % m);
			randNums[i-1] = genNums[i]/m;
		}
		return randNums;
	}

	//
	public static double[] genStdNormVars(){	//evaluates an array random numbers to generate standard normal variates
		double[] randNums = genRandNums();
		double[] stdNormVars = new double[n];
		double z1, z2;

		for(int i = 0; i < n-1; i++){
			if(i % 2 != 0){		//only look at 2 Random numbers at a time
				z1 = Math.pow((-2*Math.log(randNums[i])),0.5)*Math.cos(2*Math.PI*randNums[i+1]);
				z2 = Math.pow((-2*Math.log(randNums[i])),0.5)*Math.sin(2*Math.PI*randNums[i+1]);
				stdNormVars[i] = z1;
				stdNormVars[i+1] = z2;
			}
		}
		return stdNormVars;
	}
}
