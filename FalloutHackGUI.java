import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;


public class FalloutHackGUI extends JApplet{
	
	JButton d1;
	JButton d2;
	JButton d3;
	JButton d4;
	JButton d5;
	JLabel difQ;
	ButtonListener lis;
	int difficulty;
	int wordLength;
	int wordsNeeded;
	int guesses;
	JPanel dPanel;
	FileReader fr=null;//read in file containing a LOT of words
	BufferedReader inp = null;
	String filename;
	String[] words;
	int wordsFound;
	Random rando;
	int randomInt;
	int desiredInt;//arbitrary, gives a target for randomInt to hit
	int lengthOfCurWord;//to make sure only words of right length are selected
	String curWord;
	int correctWordIndex;
	String correctWord;
	JPanel p3, p4, p5, p6;
	JTextField entry;
	char[] correctChars;
	EnterListener lis2;
	
	public void init(){
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(700,500));
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
		
		difficulty=0;
		wordLength=0;
		wordsNeeded=0;
		guesses=4;//available guesses
		
		difQ = new JLabel("Diffculty? 1-5", SwingConstants.CENTER);
		this.add(difQ, BorderLayout.CENTER);
		
		dPanel = new JPanel();
		dPanel.setLayout(new FlowLayout());
		d1=new JButton("1");
		d2=new JButton("2");
		d3=new JButton("3");
		d4=new JButton("4");
		d5=new JButton("5");
		lis=new ButtonListener();
		dPanel.add(d1);
		dPanel.add(d2);
		dPanel.add(d3);
		dPanel.add(d4);
		dPanel.add(d5);

		d1.addActionListener(lis);
		d2.addActionListener(lis);
		d3.addActionListener(lis);
		d4.addActionListener(lis);
		d5.addActionListener(lis);
		
		this.add(dPanel, BorderLayout.SOUTH);
		
		

	}
	public void moveToStage2(){
		char[] rightChars = generateWordList();
		displayStuff();
	}
	public char[] generateWordList(){
		filename = "/Users/stevensawtelle/Documents/cse205ws/DailyProgrammer/enable1.txt";
		try{
			fr = new FileReader(filename);
			inp = new BufferedReader(fr);
		}catch(Exception e){
			System.out.println(e);
		}
		words = new String[wordsNeeded];//array holding words to be played using
		wordsFound=0;//keeps track of how full array is
		rando = new Random();
		desiredInt=5;//arbitrary, gives a target for randomInt to hit
		curWord="";
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
			randomInt = rando.nextInt(10000);
			if(desiredInt==randomInt && wordLength==lengthOfCurWord){//word must be right length and lucky!
				words[wordsFound]=curWord;//add word, update counter
				wordsFound++;
			}
		}
		
		correctWordIndex = rando.nextInt(wordsNeeded);//randomly pick right word to be target
		correctWord = words[correctWordIndex];
		
		correctChars = new char[correctWord.length()];//set up char array of all letters of target word
		for(int i=0;i<correctChars.length;i++){
			correctChars[i]=correctWord.charAt(i);
		}
		return correctChars;
	}
	public void displayStuff(){
		getContentPane().setLayout(new GridLayout(1,2));
		int counter=0;
		p5=new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
		//JTextArea allW = new JTextArea();
		while(counter<words.length){
			String toAdd = words[counter];
			JLabel toAdd2 = new JLabel(toAdd);
			toAdd2.setAlignmentX(Component.CENTER_ALIGNMENT);
			p5.add(new JLabel(toAdd));
			//allW.setText(allW.getText()+"\nEARERT"+toAdd+"ERDFSER");
			counter++;
		}
		this.add(p5);
		p6 = new JPanel();
		p6.setLayout(new BorderLayout());
		entry = new JTextField();
		p6.add(entry, BorderLayout.SOUTH);
		lis2 = new EnterListener();
		entry.addKeyListener(lis2);
		this.add(p6);
		
	}
	public void processEntry(String guess){
		JLabel winner = new JLabel("");
		JLabel denied = new JLabel("");
		JLabel wrong = new JLabel("");
		p6.add(winner, BorderLayout.NORTH);
		p6.add(denied, BorderLayout.NORTH);
		p6.add(wrong, BorderLayout.NORTH);
		
		
		boolean replacable=false;
		boolean wrongWord=false;
		if(Arrays.asList(words).contains(guess)){
			if(guess.equals(correctWord)){
				p6.removeAll();
				p6.add(entry);
				p6.add(new JLabel("You won!"));
			}
			else if(guesses>1){
				int matchingLetters=0;//number of letters matching in guess
				for(int i =0;i<correctChars.length;i++){
					if(guess.charAt(i)==correctChars[i]){//check letters individually one by one
						matchingLetters++;//if they do match, reflect that
					}
				}
				p6.removeAll();
				p6.add(entry);
				p6.add(new JLabel("Denied. Likeness="+matchingLetters));
			}else{
				p6.removeAll();
				p6.add(new JLabel("You lose! Correct word was "+correctWord));
			}
			
		}else{
			p6.removeAll();
			p6.add(entry);
			p6.add(new JLabel("Invalid word. Try again"));
		}
		entry.setText("");
		revalidate();
		repaint();
		entry.requestFocus();
	}
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==d1){
				difficulty=1;
				wordLength = 5;
				wordsNeeded = 5;
			}
			if(e.getSource()==d2){
				difficulty=2;
				wordLength = 8;
				wordsNeeded = 8;
			}
			if(e.getSource()==d3){
				difficulty=3;
				wordLength = 11;
				wordsNeeded = 11;
			}
			if(e.getSource()==d4){
				difficulty=4;
				wordLength = 13;
				wordsNeeded = 13;
			}
			if(e.getSource()==d5){
				difficulty=5;
				wordLength = 15;
				wordsNeeded = 15;
			}
			difQ.setText("alone");
			
			dPanel.remove(d1);
			dPanel.remove(d2);
			dPanel.remove(d3);
			dPanel.remove(d4);
			dPanel.remove(d5);
			remove(difQ);
			remove(dPanel);
			revalidate();
			repaint();
			moveToStage2();
		}
	}
	public class EnterListener implements KeyListener{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				processEntry(entry.getText());
			}
		}
		
	}
}
