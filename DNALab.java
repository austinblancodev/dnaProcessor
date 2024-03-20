/* DNA LAB
 * Austin Blanco
 * 
 * In this lab, we will write a variety of methods in order to assign and match proteins
 * with their respective DNA. 
 * 
 * The program will use four file readers to consume data from external text files, 
 * initialize and normalize that data, and then sort through it in order to match and
 * eventually output a fully created hash table.
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class hashTableDecypher
{
	private String[] hashTable;
	private String protein;
	private String geneticCode;
	
	//pre: information read in from the files is inputed into the parameters.
	//post: initializes and normalizes read in data.
	public hashTableDecypher(String newprotein, String newgeneticCode)
	{
		hashTable = new String[64];
		for(int pos= 0; pos < 64; pos ++)
		{
			hashTable[pos] = " ";
		}
		
		protein = newprotein;
		geneticCode = newgeneticCode;
		
		normalize();
	}
	
	//pre:
	//post: returns a formatted string with neccesary information
	public String toString()
	{
		return protein + ", " + geneticCode;
	}
	
	//pre
	//post: removes any white spaces from the geneticCode variable.
	public void normalize()
	{
		for(int pos = 0; pos < geneticCode.length(); pos++)
		{
			if(pos == 0 && geneticCode.substring(0,1).equals(" "))
			{
				geneticCode = geneticCode.substring(1);
			}
			else if(pos == geneticCode.length() - 1 && geneticCode.substring(geneticCode.length() - 1).equals(" "))
			{
				geneticCode = geneticCode.substring(0,geneticCode.length() - 1);
			}
			else
			{
				if(geneticCode.substring(pos, pos + 1).equals(" "))
				{
					geneticCode = geneticCode.substring(0, pos) + geneticCode.substring(pos + 1);
				}
			}
		}
	}
	
	//pre: Valid sequence of characters is inputed into the parameters
	//post: integer is returned via hashing the sequence of characters using a formula.
	public int hashFunction(String aminoAcidSequence)
	{
		int returnInt = 0;
		for(int pos = 0; pos < aminoAcidSequence.length(); pos++)
		{
			int codeDiget = 0;
			if(aminoAcidSequence.charAt(pos) == 'a')
			{
				
			}
			else if(aminoAcidSequence.charAt(pos) == 'c')
			{
				codeDiget = 1;
			}
			else if(aminoAcidSequence.charAt(pos) == 'g')
			{
				codeDiget = 2;
			}
			else if(aminoAcidSequence.charAt(pos) == 't')
			{
				codeDiget = 3;
			}
			returnInt += codeDiget * Math.pow(4, pos);

		}
		return returnInt;
		
	}
	
	//pre: Value between 0 and 63 is inputted.
	//post: returns the string of a hash number in the table, serving as the reverse to the previous method.
	public String unHashFunction(int hashNumber)
	{
		int num = hashNumber;
		String returnString = "";
		
		for(int pos = 0; pos < 3; pos++)
		{
			if(num%4 == 0)
				returnString += "a";
			else if(num%4 == 1)
				returnString += "c";
			else if(num%4 == 2)
				returnString += "g";
			else
				returnString += "t";
			num = num/4;
		}
		
		
		
		return returnString;
	}
	
	//pre: 
	//post: clears the entire hash
	public void eraseHash()
	{
		for(int pos = 0; pos < hashTable.length; pos ++)
		{
			hashTable[pos] = " ";
		}
	}
	
	//pre: Startpos is a in range of the geneticSequence
	//Post: returns a boolean value based onn if a match is found, and reports a collision detection if one appears.
	public boolean findGeneticMatch(int startpos)
	{
		String geneticSequence = geneticCode.substring(startpos);
		
		for(int pos = 0; pos < protein.length(); pos++)
		{
			String geneSegment = geneticSequence.substring(pos * 3, (pos + 1) * 3);
			
			int hashPosition = hashFunction(geneSegment);
			
			if(hashTable[hashPosition].equals(" "))
			{
				hashTable[hashPosition] = protein.substring(pos,pos+1)
;			}
			else if(!hashTable[hashPosition].equals(protein.substring(pos, pos + 1)))
			{
				System.out.println("Detection: " + hashPosition);
				return false;
			}
			else
			{
				
			}
		}
		return true;
	}
	
	//pre:
	//post: Runs a loop that executes findGeneticMatch in order to search the entire variable.
	public void searchGeneticCode()
	{
		for(int pos = 0; pos < geneticCode.length() - (protein.length() * 3); pos++)
		{
			if(findGeneticMatch(pos))
			{
				System.out.println("Match found at " + pos);
				return;
			}
			else
			{
				eraseHash();
			}
		}
		
		
	}
	
	//pre
	//post: returns a string of the gene sequences matched with the proteins.
	public String getAminoAcidCode()
	{
		String returnString = "";
			
		for(int pos = 0; pos < hashTable.length; pos++)
		{
			if(!hashTable[pos].equals(" "))
			{	
				returnString += hashTable[pos] + "(" + unHashFunction(pos) + ") ";
			}
		}
		
		return returnString;
	}
}


public class DNALab {

	//pre:
	//post: Code is run
	public static void main(String[] args) throws FileNotFoundException 
	{
		//reading in and initialing information
		
		String geneticCode = ""; 
		String proteinSequence = "";
		File geneticFile = new File("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/geneticSequence1.txt");
		File proteinFile = new File("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/protein1.txt");
		Scanner inScanner = new Scanner(geneticFile);
		Scanner inScanner1 = new Scanner(proteinFile);
		
		while(inScanner.hasNextLine())
		{
			geneticCode += inScanner.nextLine();
		}
		
		inScanner.close();
		
		while(inScanner1.hasNextLine())
		{
			proteinSequence += inScanner1.nextLine();
		}
		
		inScanner1.close();
		
		String geneticCode2 = ""; 
		String proteinSequence2 = "";
		File geneticFile2 = new File("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/geneticSequence2.txt");
		File proteinFile2 = new File("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/protein2.txt");
		Scanner inScanner2 = new Scanner(geneticFile2);
		Scanner inScanner3 = new Scanner(proteinFile2);
		
		while(inScanner2.hasNextLine())
		{
			geneticCode2 += inScanner2.nextLine();
		}
		
		inScanner2.close();
		
		while(inScanner3.hasNextLine())
		{
			proteinSequence2 += inScanner3.nextLine();
		}
		
		inScanner3.close();
		
		//decyphering and printing information
		
		hashTableDecypher myCypher = new hashTableDecypher(proteinSequence, geneticCode);
		System.out.println(myCypher.toString());
		myCypher.searchGeneticCode();
		
		String aminoAcidResult = (myCypher.getAminoAcidCode());
		
		myCypher.searchGeneticCode();
		System.out.println(aminoAcidResult);
	
		
		hashTableDecypher myCypher2 = new hashTableDecypher(proteinSequence2, geneticCode2);
		System.out.println(myCypher2.toString());
		myCypher2.searchGeneticCode();
		
		String aminoAcidResult2 = (myCypher2.getAminoAcidCode());
		
		myCypher2.searchGeneticCode();
		System.out.println(aminoAcidResult2);
		
	
		
		
		
			
		// TODO Auto-generated method stub
		PrintWriter printWriter2 = new PrintWriter("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/austinSolution2.txt");
		PrintWriter printWriter1 = new PrintWriter("/Users/austinblanco/My Drive/AustinBAPCSAworkspace/DNALab/src/austinSolution1.txt");
		
		printWriter1.println(aminoAcidResult);
		printWriter1.close();
		
		printWriter2.println(aminoAcidResult2);
		printWriter2.close();
		
		
		
		System.out.println("It works");
		
	}

}
