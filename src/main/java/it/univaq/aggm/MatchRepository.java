package it.univaq.aggm;
import javax.jws.WebService;
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
@WebService(endpointInterface = "it.univaq.aggm.MatchRepositoryInterface", serviceName = "RegisterOfficeWS", portName = "RegisterOfficeWSPort", 
targetNamespace = "http://matchRepository.univaq.it")
public class MatchRepository {	
	@GET
    @Path("today")
    public ArrayList<Match> getMatches() throws IOException, JSONException {
		ArrayList<Match> result = new ArrayList<Match>();
		JSONArray data = getMatchesData();
	    int dataLength = data.length();
	    for(int i=0; i<dataLength; i++) {
	    	JSONObject fetchedMatch = data.getJSONObject(i);
	    	
	    	Team localTeam = createTeam(fetchedMatch, "local");
	    	Team visitorTeam = createTeam(fetchedMatch, "visitor");
	    	
	    	Match m = new Match();
	    	m.setLocalTeam(localTeam);
	    	m.setVisitorTeam(visitorTeam);
	    	
	    	int localTeamScore = fetchedMatch.getJSONObject("scores").getInt("localteam_score");
	    	int visitorTeamScore = fetchedMatch.getJSONObject("scores").getInt("visitorteam_score");
	    	
	    	m.setLocalScore(localTeamScore);
	    	m.setVisitorScore(visitorTeamScore);
	    	m.setCoordinates(getCoordinates(fetchedMatch));
	    	
	    	result.add(m);
	    }
        return result;
    }
	
	private JSONArray getMatchesData() throws IOException, JSONException {
		String todayDate = "2021-07-18";
		String apiToken = "EuWlXXgur6j6aoUZwll7mWFGeU3bQcudww5AcQ9pz7AnUDb4Ed96KtJdUZQa";
		String url = "https://soccer.sportmonks.com/api/v2.0/fixtures/date/" + todayDate + "?api_token=" + apiToken + "&include=localTeam,visitorTeam";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder() 
				.url(url)
				.get().build();

		Response response = client.newCall(request).execute();
		String jsonData = response.body().string();
	    JSONObject Jobject = new JSONObject(jsonData);
	    System.out.println(Jobject.get("data"));
	    JSONArray data = Jobject.getJSONArray("data");
	    return data;
	}
	
	private Team createTeam(JSONObject actualMatch, String type) throws JSONException {
		Team team = new Team();
    	int id = actualMatch.getInt(type + "team_id");
    	String name = actualMatch.getJSONObject(type + "Team").getJSONObject("data").getString("name");
    	team.setId(id);
    	team.setName(name);
    	return team;
	}
	
	private String getCoordinates(JSONObject actualMatch) throws JSONException {
		return actualMatch.getJSONObject("weather_report").getJSONObject("coordinates").getString("lat") + ',' + actualMatch.getJSONObject("weather_report").getJSONObject("coordinates").getString("lon");
	}
}
