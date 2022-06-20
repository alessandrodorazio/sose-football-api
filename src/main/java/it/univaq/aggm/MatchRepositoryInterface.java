package it.univaq.aggm;
import javax.jws.WebMethod;
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

@WebService
public interface MatchRepositoryInterface {
	@WebMethod
    public ArrayList<Match> getCourse() throws IOException, JSONException;
}
