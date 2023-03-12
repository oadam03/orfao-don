package com.spamdetector.util;

import com.spamdetector.domain.TestFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: This class will be implemented by you
 * You may create more methods to help you organize you strategy and make you code more readable
 */
public class SpamDetector {
    public static String[] getWords(String line){
        String[] words = line.split("\\s");
        Pattern wordreg = Pattern.compile("(?<![A-Za-z])[A-Za-z]+(?![A-Za-z])");
        Matcher matcher = null;

        int valid = 0;
        for(int i = 0; i < words.length; i++){
            if((wordreg.matcher(words[i])).matches()){
                words[valid] = words[i];
                valid ++;
            }
        }

        String[] temp = new String[valid];
        for(int i = 0; i < valid; i++){
            temp[i] = words[i];
        }

        return temp;
    }

    public static List<TestFile> trainAndTest(File mainDirectory) {
//        TODO: main method of loading the directories and files, training and testing the model

        // load files from data/train/
        // count how many times each word appears in ham, in ham2, and in spam
        // make 2 frequency maps called trainHamFreq, trainSpamFreq, which maps the frequency of each words appearance to the words

        // create PrSW
        // PrSW = PrWS / (PrWS + PrWH)
        // PrWS = spam files containing word / number of spam files
        // PrWH = ham files containing word / number of ham files

        // put all PrSW into a treemap indexed by the word
        File[] ham = (new File(mainDirectory + "\\ham\\")).listFiles();
        File[] ham2 = (new File(mainDirectory + "\\ham2\\")).listFiles();
        File[] spam = (new File(mainDirectory + "\\spam\\")).listFiles();

        Map<String, Integer> trainHamFreq = new HashMap<>();
        Map<String, Integer> trainSpamFreq = new HashMap<>();

        Scanner scanner = null;
        BufferedReader reader = null;

        for(File hamchild : ham){
            try{
                reader = new BufferedReader(new FileReader(hamchild));
                String line = reader.readLine();

                while (line != null) {
                    line = reader.readLine();

                    System.out.println(line);

                    String[] words = getWords(line);

                    for(String word : words){
                        if(trainHamFreq.containsKey(word)){
                            trainHamFreq.put(word,trainHamFreq.get(word) + 1);
                        }else{
                            trainHamFreq.put(word,1);
                        }
                    }
                    /*
                    * TODO:
                    *  - split each line into words, checking if each word is valid (using regex)
                    *  -
                    * */
                }
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        return new ArrayList<TestFile>();
    }

    //main class for debugging
    public static void main(String[] args){
        File file = new File("D:\\SoftwareDev\\assignment1\\spamDetectorServer\\src\\main\\resources\\data\\train");
        trainAndTest(file);
    }
}

