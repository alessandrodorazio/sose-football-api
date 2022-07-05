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

	@GET
	@Path("today")
	/*public Future<?> getMatchesAsync(AsyncHandler<MatchResponse> asyncHandler){
		
		System.out.println(formatter.format(new Date())
				+ " - executing Future<?> MatchAsync with AsyncHandler *asynchronously*");

		final ServerAsyncResponse<MatchResponse> asyncResponse = new ServerAsyncResponse<MatchResponse>();

		new Thread() {
			public void run() {
				
				try {
					Thread.sleep(1000); // 1s
				
					MatchResponse response = new MatchResponse();
					
					try {
						response.setRes(getMatches());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					asyncResponse.set(response);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println(formatter.format(new Date()) + " - match-date");
				asyncHandler.handleResponse(asyncResponse);
			}
		}.start();
		
		return asyncResponse;
	}
	*/public ArrayList<Match> getMatches() throws IOException, JSONException {
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
	    System.out.println("Got matches");
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
	    System.out.println(url);
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
