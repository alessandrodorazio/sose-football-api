package it.univaq.aggm;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;
import org.codehaus.jettison.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;



@WebService
public interface MatchRepositoryInterface {
	
    /*public ArrayList<Match> getMatchesAsync() throws IOException, JSONException;
     * // return the list of matches*/ 
	@WebMethod
	@ResponseWrapper(localName = "matchResponse", className="it.univaq.aggm.MatchResponse")
    public ArrayList<Match> getMatches() throws IOException, JSONException;
}
