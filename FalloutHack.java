import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class FalloutHack {//based on reddit daily challenge #238

	public static void main(String[] args) {
		FileReader fr=null;//read in file containing a LOT of words
		BufferedReader inp = null;
		String filename = "/Users/stevensawtelle/Documents/cse205ws/DailyProgrammer/enable1.txt";//fix this to be more narrow?
		try{
			fr = new FileReader(filename);
			inp = new BufferedReader(fr);
		}catch(Exception e){
			System.out.println(e);
		}
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader inp2 = new BufferedReader(isr);
		
		
		try{
			//int difficulty = Integer.parseInt(inp2.readLine().trim());//implement difficulty setting
		}catch(Exception e){}

		int wordLength=7;//base on difficulty
		int wordsNeeded=7;//base on difficulty, not necesarilly same
		String[] words = new String[wordsNeeded];//array holding words to be played using
		int wordsFound=0;//keeps track of how full array is
		Random rando = new Random();
		int randomInt;
		int desiredInt=5;//arbitrary, gives a target for randomInt to hit
		int lengthOfCurWord;//to make sure only words of right length are selected
		String curWord="";
		
		while(wordsFound<wordsNeeded){//until array is properly filled
			try{
				curWord = inp.readLine();//get next word from file
				if(curWord==null){//tricky! if it gets to end of file, start from beginning again
					fr = new FileReader(filename);//this is needed because of random nature
					inp = new BufferedReader(fr);
					curWord = inp.readLine();
				}
			}catch(Exception e){
				System.out.println(e);
			}
			lengthOfCurWord = curWord.length();
			randomInt = rando.nextInt(10000);/*lots of words that fit these criteria, this numbers needs to be high
												* to avoid them all starting with a
												*/
			if(desiredInt==randomInt && wordLength==lengthOfCurWord){//word must be right length and lucky!
				words[wordsFound]=curWord;//add word, update counter
				wordsFound++;
			}
		}
		
		int correctWordIndex = rando.nextInt(wordsNeeded);//randomly pick right word to be target
		String correctWord = words[correctWordIndex];
		
		char[] correctChars = new char[correctWord.length()];//set up char array of all letters of target word
		for(int i=0;i<correctChars.length;i++){
			correctChars[i]=correctWord.charAt(i);
		}
		
		
		for(int i =0;i<words.length;i++){//print out "menu" of words
			System.out.println(words[i]);
		}
		boolean gameOver=false;//controls if game is being played
		int guesses=4;//available guesses
		String guess="";//users guess
		
		while(!gameOver){
			System.out.println("\nYour guess? Guesses left: "+guesses);
			try{
				guess=inp2.readLine().trim();
			}catch(Exception e){
				System.out.println(e);
			}
			if(Arrays.asList(words).contains(guess.toLowerCase())){//if input is in the "menu"
				if(correctWord.equals(guess)){//word is right
					System.out.println("You won!");
					gameOver=true;
					break;
				}
				if(guesses>1){//wrong guess, but still playing
					int matchingLetters=0;//number of letters matching in guess
					for(int i =0;i<correctChars.length;i++){
						if(guess.charAt(i)==correctChars[i]){//check letters individually one by one
							matchingLetters++;//if they do match, reflect that
						}
					}
					System.out.println("Wrong. Try again. You got "+matchingLetters+"/"+correctWord.length()+" letters right.");
					guesses--;
				}else{//simple, you lose!
					System.out.println("Not right, no guesses remaining. You lose! Correct word was " + correctWord);
					gameOver=true;
				}
			}else{//if input is invalid
				System.out.println("Word not on screen. Try again.");
			}
		}
		
		
	}

}
