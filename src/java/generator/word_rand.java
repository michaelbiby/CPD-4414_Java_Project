package generator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

/**
 *
 * @author c0644696
 */
public class word_rand {

    public static final int random() {
        int randomInt = -1;
        log("Generating 10 random integers in range 0..99.");

        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(20) + 1;

        log("Done.");
        return randomInt;
    }
    
    public String newWord(String word)
	{
            String hiddenWord="";
		String w=word;
                for(int i=0;i<word.length();i++)
                {
                    hiddenWord=hiddenWord+"*";
                }
		return hiddenWord;
	}

    private static void log(String aMessage) {
        System.out.println(aMessage);
    }
}
