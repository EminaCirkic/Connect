package com.company;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connection {

    public static void main(String[] args) throws Exception{
        Connection http = new Connection();
        System.out.println("Send http GET request");
        http.sendGet();
        System.out.println("Send http POST request");
        http.sendPost("http://demo.edument.se/api/highscore"
               ,"{\"Name\":\"Spindel\",\"Score\":300}");
        http.geoNames();
        http.geneticsGet();


    }

    private void sendGet() throws Exception {
        String url = "http://demo.edument.se/api/highscores";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();

        // request header
        con.setRequestMethod("GET");
        int reponseCode = con.getResponseCode();
        System.out.println("Sending GET request to URL :"+ url);
        System.out.println("Response code :"+ reponseCode);

        // request body

        BufferedReader d=new BufferedReader(new InputStreamReader(con.getInputStream()));

        System.out.println(d.readLine());

    }

    private void geneticsGet() throws Exception {
        //sjukdomar måste ha ett streck istället för mellanslag, t.ex. alzheimer-disease, crohn-disease, breast-cancer osv
        String diseaseToSearch="lung-cancer";
        String uri = "https://ghr.nlm.nih.gov/condition/"+diseaseToSearch+"?report=json";
        URL obj = new URL(uri);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();

        // request header
        con.setRequestMethod("GET");
        int reponseCode = con.getResponseCode();
        System.out.println("Sending GET request to URL :"+ uri);
        System.out.println("Response code :"+ reponseCode);

        // request body

        BufferedReader d=new BufferedReader(new InputStreamReader(con.getInputStream()));

        System.out.println(d.readLine());

    }
    private void geoNames() throws Exception{

        WebService.setUserName("emci");
        String city="zurich";
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setQ(city);
        ToponymSearchResult searchResult = WebService.search(searchCriteria);
        System.out.println("GEONAMES:");
        System.out.println("Countries where the city "+city+" is found:");
        for (Toponym toponym : searchResult.getToponyms()) {
            System.out.println(toponym.getName()+" "+ toponym.getCountryName());
        }

    }

    private void sendPost(String targetURL, String urlParameters) throws Exception{

        URL url=new URL(targetURL);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());

        out.writeBytes(urlParameters);

        int response=connection.getResponseCode();
        System.out.println("Sending POST request to URL :"+ url);
        System.out.println("Response code :"+ response);
        System.out.println("Player sent");
        out.flush();
        out.close();
        os.close();

    }


}
