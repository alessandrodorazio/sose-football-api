package it.univaq.aggm;
import javax.ws.rs.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Path("matches")
@Produces("text/xml")
public class MatchRepository {
	@GET
    @Path("today")
    public ArrayList<Match> getCourse() throws IOException, JSONException {
		ArrayList<Match> result = new ArrayList<Match>();
		// get fixtures from a date
		// -> https://soccer.sportmonks.com/api/v2.0/fixtures/date/2021-07-18?api_token={{api_token}}&include=
		// get team by team id (data.localteam_id, data.visitorteam_id)
		// -> https://soccer.sportmonks.com/api/v2.0/teams/2447?api_token={{api_token}}&include=
		String todayDate = "2021-07-18";
		String apiToken = "EuWlXXgur6j6aoUZwll7mWFGeU3bQcudww5AcQ9pz7AnUDb4Ed96KtJdUZQa";
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder() 
				.url("https://soccer.sportmonks.com/api/v2.0/fixtures/date/" + todayDate + "?api_token=" + apiToken + "&include=localTeam,visitorTeam")
				.get().build();

		Response response = client.newCall(request).execute();
		String jsonData = response.body().string();
	    JSONObject Jobject = new JSONObject(jsonData);
	    System.out.println(Jobject.get("data"));
	    JSONArray data = Jobject.getJSONArray("data");
	    int dataLength = data.length();
	    for(int i=0; i<dataLength; i++) {
	    	JSONObject actualMatch = data.getJSONObject(i);
	    	Team localTeam = new Team();
	    	localTeam.setId(actualMatch.getInt("localteam_id"));
	    	localTeam.setName(actualMatch.getJSONObject("localTeam").getJSONObject("data").getString("name"));
	    	Team visitorTeam = new Team();
	    	visitorTeam.setId(actualMatch.getInt("visitorteam_id"));
	    	visitorTeam.setName(actualMatch.getJSONObject("visitorTeam").getJSONObject("data").getString("name"));
	    	Match m = new Match();
	    	m.setLocalTeam(localTeam);
	    	m.setVisitorTeam(visitorTeam);
	    	m.setLocalScore(actualMatch.getJSONObject("scores").getInt("localteam_score"));
	    	m.setVisitorScore(actualMatch.getJSONObject("scores").getInt("visitorteam_score"));
	    	result.add(m);
	    }
        return result;
    }
}
