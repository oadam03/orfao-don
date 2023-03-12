package com.spamdetector.util;

import com.spamdetector.domain.TestFile;

import java.io.*;
import java.util.*;


/**
 * TODO: This class will be implemented by you
 * You may create more methods to help you organize you strategy and make you code more readable
 */
public class SpamDetector {

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

        Scanner scanner = null;
        BufferedReader reader = null;

        for(File hamchild : ham){
            try{
                reader = new BufferedReader(new FileReader(hamchild));
                String line = reader.readLine();

                while (line != null) {
                    line = reader.readLine();
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

