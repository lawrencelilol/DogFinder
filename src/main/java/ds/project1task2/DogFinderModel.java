package ds.project1task2;

/**
 * @author Lawrence Li
 * Last Modified: Feb 10, 2022
 *
 *  DogFinderModel makes a request to https://dogtime.com/dog-breeds/profiles
 *  and then screen scraping the HTML that is returned in order to get breed's
 *  information:
 *  1. Friendly
 *  2. Intelligence
 *  3. Height
 *  4. Weight
 *  5. Lifespan
 *
 *  DogFinderModel also used https://dog.ceo/dog-api/ to get a JSON record that
 *  contains a number of URL's for pictures of dogs of the chosen breed.
 *  The model choose one of these URL's at random.
 *
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class DogFinderModel {

    public String[] doDogSearch (String searchTag) throws UnsupportedEncodingException {

        // get the user's input that is the breed of the dog
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        String response = "";

        // Store breed's information
        // 1. friendly 2. intelligence 3. Height 4. Weight
        // 5. Lifespan 6. pictureURL
        String[] res = new String[6];

        // Create a URL for the page to be screen scraped
        String dogURL = "https://dogtime.com/dog-breeds/"
                        + searchTag
                        +"#/slide/1";

        // Fetch the page
        response = fetch(dogURL);

        // Screen Scraping for Friendliness of the Breed
        int left  = response.indexOf("All Around Friendliness");
        char found = '0';

        // If the friendliness is found, use "star star-" to get
        // the rating of friendly
        if(left != -1) {
            int right = response.indexOf("star star-", left);
            int idx = right + 10;
            found = response.charAt(idx);
        }
        String friend = String.valueOf(found);
        res[0] = friend;

        //Screen Scraping for Intelligence of the Breed
        left = response.indexOf("Intelligence");

        // If the Intelligence is found, use "star star-" to get
        // the rating of intelligence
        if(left != -1) {
            int right = response.indexOf("star star-", left);
            int idx = right + 10;
            found = response.charAt(idx);
        }
        String intelligence = String.valueOf(found);
        res[1] = intelligence;

        //Screen Scraping for Height of the Breed
        left = response.indexOf("Height:</");
        String height = "";
        if(left != -1) {
            int right = response.indexOf(">", left);
            int end = response.indexOf("<",right);
            height = response.substring(right + 1,end);
        }
        res[2] = height;

        //Screen Scraping for Weight of the Breed
        left = response.indexOf("Weight:</");
        String weight = "";
        if(left != -1) {
            int right = response.indexOf(">", left);
            int end = response.indexOf("<",right);
            weight = response.substring(right + 1,end);

        }
        res[3] = weight;

        //Screen Scraping for Lifespan of the Breed
        left = response.indexOf("Life Span:</");
        String lifespan = "";
        if(left != -1) {
            int right = response.indexOf(">", left);
            int end = response.indexOf("<",right);
            lifespan = response.substring(right + 1,end);

        }
        res[4] = lifespan;

        //Getting the breed urls from Dog API
        String pictureURL = "https://dog.ceo/api/breed/"+ searchTag.toLowerCase(Locale.ROOT) + "/images";
        // fetch the urls of the breed
        String[] pictureResponse = fetchPicture(pictureURL);

        // Choose the random picture
        Random rand = new Random();
        int idx = rand.nextInt(pictureResponse.length);

        res[5] = pictureResponse[idx];
        return res;
    }

    /*
     * Make an HTTP request to a given URL
     * (Used the same method from InterestingPicture)
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }

    /*
     * Make an HTTP request to a Dog.api and fetch pictures from api.
     *
     * @param pictureURL The URL of the request
     * @return A array of string of the breed's pictures.
     */
    public String[] fetchPicture(String pictureURL) {
        String[] urls = new String[0];

        try {
            URL url = new URL(pictureURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();

            if(responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                String inline = "";

                // Read all the text returned by the server
                Scanner sc = new Scanner(url.openStream());
                // Read each line of "sc" until done, adding each to "inline"
                while(sc.hasNext()) {
                    inline += sc.nextLine();
                }

                // close the Scanner
                sc.close();

                // Used SimpleJSON to parse inline into JSON object
                // Followed instructions from this website:
                // https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl

                // Parse String to JSON object
                JSONParser parse = new JSONParser();
                JSONObject jsonObject = (JSONObject) parse.parse(inline);

                // Parse jsonObject to a jsonArray
                JSONArray jsonArr = (JSONArray) jsonObject.get("message");
                urls = new String[jsonArr.size()];

                // Put each JSON picture url into a string array called urls
                for (int i = 0; i < jsonArr.size(); i++) {
                    urls[i] = String.valueOf(jsonArr.get(i));
                }
            }

        } catch (IOException e) {
            System.out.println("Eeek, an exception");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return urls;
    }

}
