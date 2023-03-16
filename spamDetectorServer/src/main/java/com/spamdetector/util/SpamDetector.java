package com.spamdetector.util;

import com.spamdetector.domain.TestFile;
import org.json.JSONArray;

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
    public static Map<String,Double> buildProbs(int len, Map<String,Integer> map){
        double size = (double)len;
        Map<String,Double> Pr = new HashMap<>();
        double prob = 0.0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();
            prob = freq / size;
            Pr.put(word,prob);
        }
        return Pr;
    }
    public static File[] concatenate(File[] a, File[] b) {
        int aLen = a.length;
        int bLen = b.length;
        File[] c = new File[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
    public static Map<String,Integer> getFreq(File[] file){
        if(file == null){
            return null;
        }
        BufferedReader reader = null;
        Map<String, Integer> frequency = new HashMap<>();

        for(File hamchild : file) {
            try {
                reader = new BufferedReader(new FileReader(hamchild));
                String line = reader.readLine();
                while (line != null) {
                    String[] words = getWords(line);

                    for (String word : words) {
                        if (frequency.containsKey(word)) {
                            frequency.put(word, frequency.get(word) + 1);
                        } else {
                            frequency.put(word, 1);
                        }
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return frequency;
    }
    public static Map<String, Double> getPrSW(Map<String,Double> PrWS, Map<String,Double>PrWH){
        Map<String, Double> denom = new HashMap<>();
        Map<String, Double> PrSW = new HashMap<>();

        // Iterate through the keys in hashMap1
        for (String key : PrWS.keySet()) {
            // If the key is also in hashMap2, add its value to the sharedHashMap
            if (PrWH.containsKey(key)) {
                double value1 = PrWS.get(key);
                double value2 = PrWH.get(key);
                double totalValue = value1 + value2;
                PrSW.put(key, PrWS.get(key) / totalValue);
            }
        }
        return PrSW;
    }
    public static TestFile isFileSpam(Map<String,Double> PrSW, File file, String type){//type = spam or ham
        if(file == null){
            return null;
        }
        double n = 0.0;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] words = getWords(line);

                for (String word : words) {
                    if(PrSW.get(word) != null) {
                        n += Math.log(1 - PrSW.get(word)) - Math.log(PrSW.get(word));
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double PrSF = 1/(1+(Math.pow(Math.E,n)));
        TestFile testfile = new TestFile(file.getName(),PrSF,type);
        return testfile;
    }

//    public static String createJSON()
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
        File[] ham1 = (new File(mainDirectory + "\\train\\ham")).listFiles();
        File[] ham2 = (new File(mainDirectory + "\\train\\ham2")).listFiles();

        File[] ham = concatenate(ham1,ham2);
        File[] spam = (new File(mainDirectory + "\\train\\spam")).listFiles();

        Map<String, Integer> trainHamFreq = getFreq(ham);
        Map<String, Integer> trainSpamFreq = getFreq(spam);

        Map<String, Double> PrWS = buildProbs(spam.length, trainSpamFreq);
        Map<String, Double> PrWH = buildProbs(ham.length, trainHamFreq);
        Map<String, Double> PrSW = getPrSW(PrWS, PrWH);

        File[] testham = (new File(mainDirectory + "\\test\\ham")).listFiles();
        File[] testspam = (new File(mainDirectory + "\\test\\spam")).listFiles();

        ArrayList<TestFile> testfiles = new ArrayList<>();

        for(File hamfile : testham){
            testfiles.add(isFileSpam(PrSW,hamfile,"ham"));
        }
        for(File spamfile : testspam){
            testfiles.add(isFileSpam(PrSW,spamfile,"spam"));
        }
        for(TestFile guy : testfiles){
            System.out.println(guy.getSpamProbability());
        }

        return testfiles;
    }
}

