
import com.fasterxml.jackson.databind.ObjectMapper;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.io.*;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import sun.misc.IOUtils;

import java.util.HashMap;


public class PostTest{
    public static void main(String[] args) throws IOException {
        JSONObject stopPlayer = new JSONObject();


      URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");

        StopPlayer player = new StopPlayer();
        ObjectMapper mapper =new ObjectMapper();
        StringWriter s= new StringWriter();
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        String str=s.toString();

        HttpPost post = new HttpPost("http://kodi:123@192.168.1.205:8080/jsonrpc");
        //HttpPost post = new HttpPost("http://kodi:123@127.0.0.1:8081/jsonrpc");




        HttpResponse response = httpClient.execute(post);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String strRes = reader.readLine();
        System.out.println(strRes);

        //headers.put("Content-type","json");

        //JsonRpcHttpClient client = new JsonRpcHttpClient(url);
        //client.setContentType("application/json");
        //System.out.println(client.getHeaders());

    }

}
