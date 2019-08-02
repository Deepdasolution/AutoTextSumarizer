package summarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AccessSummary{

	public static void main(String args[])
	{
		
		
	/*	Summarizer summarizer= new Summarizer();
		
		GenerateSummary gs= new GenerateSummary();
	
	
		int[] a=gs.findhighest(4);
	System.out.println(a[1]);
	*/
		
	/*
		String[] myword;
		String s="HashMap is a Map based collection class that is"
				+ " used for storing Key & value"
				+ " pairs, it is denoted as HashMap<Key, Value> "
				+ "or HashMap<K, V>. This class makes no guarantees as to the order of the map."
				+ " It is similar to the Hashtable class except that it is unsynchronized an"
			+ "d permits nulls(null values and null key).";
		
		myword=s.split("\\W+");
		
		  for (int i = 0; i < myword.length; i++) {
			  
		  myword[i] = myword[i].toLowerCase();
			  System.out.println(myword[i]);
	        }
		
		*/
		 String[] stopWords = {"i", "me", "my", "myself", "we", "us", "our", "ours", "ourselves",
		            "you", "your", "yours", "yourself", "yourselves", "he", "him", "his",
		            "himself", "she", "her", "hers", "herself", "it", "its", "itself",
		            "they", "them", "their", "theirs", "themselves", "what", "which",
		            "who", "whom", "this", "that", "these", "those", "am", "is", "are",
		            "was", "were", "be", "been", "being", "have", "has", "had", "having",
		            "do", "does", "did", "doing", "would", "should", "could", "ought",
		            "i'm", "you're", "he's", "she's", "it's", "we're", "they've", "i've",
		            "you've", "we've", "they've", "i'd", "you'd", "he'd", "she'd", "we'd",
		            "they'd", "i'll", "you'll", "he'll", "she'll", "we'll", "they'll",
		            "isn't", "aren't", "wasn't", "weren't", "hasn't", "haven't", "hadn't",
		            "doesn't", "don't", "didn't", "won't", "wouldn't", "can't", "cannot",
		            "couldn't", "mustn't", "let's", "that's", "who's", "what's", "here's",
		            "there's", "when's", "where's", "why's", "how's", "a", "an", "the",
		            "and", "but", "if", "or", "because", "as", "until", "while", "of", "at",
		            "by", "for", "with", "about", "against", "between", "into", "through",
		            "during", "before", "after", "above", "below", "to", "from", "up", "down",
		            "in", "out", "on", "off", "over", "under", "again", "further", "then",
		            "once", "here", "there", "when", "where", "why", "how", "all", "any",
		            "both", "each", "few", "more", "most", "other", "some", "such", "no",
		            "nor", "not", "only", "own", "same", "so", "than", "too", "very"};
		 HashMap<String, Integer> wordFrequency= new HashMap<>();
		 String[] words;
		String a="HashMap is a Map based collection class that is"
				+ " used for storing Key & value"
				+ " pairs, it is denoted as HashMap<Key, Value> "
				+ "or HashMap<K, V>. This class makes no guarantees as to the order of the map."
				+ " It is similar to the Hashtable class except that it is unsynchronized an"
			+ "d permits nulls(null values and null key).";
		
		 String[] ignoreWords = {"Dr", "Mr", "Mrs", "Ms", "Sr", "Jr"};
		String currentSentence;
		 ArrayList<String> sentences = new ArrayList<>();
		 int currentChar = 0;
	        int previousStop = 0;
	        while (currentChar < a.length() - 1) {
	            if (a.charAt(currentChar) == '?' || a.charAt(currentChar) == '!') {
	                // end of sentence
	                currentSentence = a.substring(previousStop, currentChar + 1);
	                sentences.add(currentSentence);
	                currentChar++;
	                previousStop = currentChar;
	                
	            }

	            // Check to see if sentence if over when we see a period
	            else if (a.charAt(currentChar) == '.') {
	                if (currentChar - previousStop <= 2) {
	                    // sentence only has two characters so skip there are no one character sentences??
	                    // This would be one letter and a "."
	                    currentChar++;
	                }
	                else if (currentChar > 2) {
	                    String twoLetterAbbrev = a.substring(currentChar - 2, currentChar);
	                    String threeLetterAbbrev = a.substring(currentChar - 3, currentChar);

	                    // ignore words like Mr, Mrs, Dr, Etc
	                    if (Arrays.asList(ignoreWords).contains(twoLetterAbbrev) ||
	                            Arrays.asList(ignoreWords).contains(threeLetterAbbrev)) {
	                        currentChar++;
	                    }

	                    // end of sentence
	                    else {
	                        currentSentence = a.substring(previousStop, currentChar + 1);
	                        sentences.add(currentSentence);
	                        currentChar++;
	                        previousStop = currentChar;
	                    }
	                }
	            }
	            currentChar++;
	        }

	        System.out.println();
	        System.out.println("Number of Sentences: " + sentences.size());
	        for(String x: sentences)
                System.out.println(x);
		 
    }
}

