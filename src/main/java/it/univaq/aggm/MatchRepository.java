package it.univaq.aggm;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.xml.ws.AsyncHandler;

import org.apache.cxf.jaxws.ServerAsyncResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.concurrent.Future;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import javax.xml.ws.AsyncHandler;
import org.apache.cxf.jaxws.ServerAsyncResponse;

@Path("matches")
@Produces("text/xml")

public class MatchRepository {	
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@GET @Path("/")
    public ArrayList<Match> getMatches() throws IOException, JSONException {
		ArrayList<Match> result = new ArrayList<Match>();
		JSONArray data = getMatchesData();
	    int dataLength = data.length();
	    for(int i=0; i<dataLength; i++) {
	    	JSONObject fetchedMatch = data.getJSONObject(i);
	    	
	    	// convert XML data into match object
	    	Team localTeam = createTeam(fetchedMatch, "local");
	    	Team visitorTeam = createTeam(fetchedMatch, "visitor");
	    	int localTeamScore = fetchedMatch.getJSONObject("scores").getInt("localteam_score");
	    	int visitorTeamScore = fetchedMatch.getJSONObject("scores").getInt("visitorteam_score");
	    	
	    	Match m = new Match(localTeam, localTeamScore, visitorTeam, visitorTeamScore, getCoordinates(fetchedMatch));
	    	result.add(m);
	    }
        return result;
    }
	
	private JSONArray getMatchesData() throws IOException, JSONException {
		String todayDate = "2021-07-18";
		String apiToken = "EuWlXXgur6j6aoUZwll7mWFGeU3bQcudww5AcQ9pz7AnUDb4Ed96KtJdUZQa";
		String url = "https://soccer.sportmonks.com/api/v2.0/fixtures/date/" + todayDate + "?api_token=" + apiToken + "&include=localTeam,visitorTeam";
		OkHttpClient client = new OkHttpClient();
		// Sportmonks API call
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		String jsonData = response.body().string(); // get body as string
	    JSONObject Jobject = new JSONObject(jsonData); // create JSON Object from string
	    JSONArray data = Jobject.getJSONArray("data"); // get data array from API
	    return data;
	}
	
	private Team createTeam(JSONObject actualMatch, String type) throws JSONException {
		// type is local or visitor
    	int id = actualMatch.getInt(type + "team_id");
    	String name = actualMatch.getJSONObject(type + "Team").getJSONObject("data").getString("name");
    	return new Team(id, name);
	}
	
	private String getCoordinates(JSONObject actualMatch) throws JSONException {
		// get latitude and longitude where the match will take place
		return actualMatch.getJSONObject("weather_report").getJSONObject("coordinates").getString("lat") + ',' + actualMatch.getJSONObject("weather_report").getJSONObject("coordinates").getString("lon");
	}
}
