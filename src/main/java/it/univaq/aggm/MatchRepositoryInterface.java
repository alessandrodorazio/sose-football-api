package it.univaq.aggm;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebService
public interface MatchRepositoryInterface {
	@WebMethod
    public ArrayList<Match> getMatches() throws IOException, JSONException;
	
	@WebMethod
	@WebResult(name="matches",targetNamespace = "")
	@RequestWrapper(localName="getMatchesAsyncRequest", className = "it.univaq.aggm.MatchRepositoryRequestWrapper", targetNamespace = "http://matchesRepository.univaq.it/")
	@ResponseWrapper(localName = "getMatchesAsyncResponse", className="it.univaq.aggm.MatchRepositoryResponseWrapper", targetNamespace = "http://matchesRepository.univaq.it/")
    public ArrayList<Match> getMatchesAsync() throws IOException, JSONException;
}