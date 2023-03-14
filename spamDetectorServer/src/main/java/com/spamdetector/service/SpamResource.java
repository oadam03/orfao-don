package com.spamdetector.service;

import com.spamdetector.domain.TestFile;
import com.spamdetector.util.SpamDetector;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/spam")
public class SpamResource {
//    your SpamDetector Class responsible for all the SpamDetecting logic
    SpamDetector detector = new SpamDetector();
    List<TestFile> data;

    SpamResource() throws IOException, URISyntaxException {
        System.out.print("Training and testing the model, please wait");
        File maindirectory = new File("D:\\SoftwareDev\\assignment1\\spamDetectorServer\\src\\main\\resources\\data");
        this.data = this.trainAndTest(maindirectory);
    }

    @GET
    @Produces("application/json")
    public Response getSpamResults() {
        JSONArray json = new JSONArray(data);
        Response resp = Response.status(200)
                .header("Content-Type","application/json")
                .entity(json)
                .build();
        return resp;
    }

    @GET
    @Path("/accuracy")
    @Produces("application/json")
    public Response getAccuracy() {
//      TODO: return the accuracy of the detector, return in a Response object
        // number of correct guesses/number of guesses
        // (number of true positives + number of true negatives) / number of files
        double truepos = 0;
        double trueneg = 0;
        for(TestFile file : data){
            if(file.getSpamProbability() > 0.5 && file.getActualClass() == "spam") {
                trueneg += 1.0;
            }
            else if(file.getSpamProbability() < 0.5 && file.getActualClass() == "ham"){
                truepos += 1.0;
            }
        }

        double accuracy = (truepos + trueneg) / data.size();

        Response resp = Response.status(200)
                .header("Content-Type","application/json")
                .entity(accuracy)
                .build();
        return resp;
    }

    @GET
    @Path("/precision")
    @Produces("application/json")
    public Response getPrecision() {
       //      TODO: return the precision of the detector, return in a Response object
        // number of true positives / (number of false positives + number of true positives)
        double truepos = 0;
        for(TestFile file : data){
            if(file.getSpamProbability() < 0.5 && file.getActualClass() == "ham"){
                truepos += 1.0;
            }
        }

        double precision = truepos / data.size();

        Response resp = Response.status(200)
                .header("Content-Type","application/json")
                .entity(precision)
                .build();
        return resp;
    }

    private List<TestFile> trainAndTest(File maindirectory)  {
        if (this.detector==null){
            this.detector = new SpamDetector();
        }

//        TODO: load the main directory "data" here from the Resources folder
        File mainDirectory = null;
        return this.detector.trainAndTest(mainDirectory);
    }
}