import okhttp3.*;

import java.io.IOException;

public class PostRequestOkHttp {

    public static final MediaType JSON = MediaType.parse("application/json");
    public static final OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(/*json*/"{\n    \"id\": 1,\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"Player.Stop\",\n    \"params\": {\n        \"playerid\": 0\n    }\n}",JSON);
        System.out.println(body);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                //.post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.toString();
    }

}
