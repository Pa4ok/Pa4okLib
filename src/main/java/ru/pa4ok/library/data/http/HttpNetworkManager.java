package ru.pa4ok.library.data.http;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.pa4ok.library.data.message.JsonServerMessage;
import ru.pa4ok.library.util.GsonUtil;
import ru.pa4ok.library.util.Tuple;

import java.io.IOException;
import java.util.List;

//https://coderlessons.com/tutorials/java-tekhnologii/izuchite-apache-http-client/apache-httpclient-kratkoe-rukovodstvo

public class HttpNetworkManager
{

    private CloseableHttpClient httpClient;

    public HttpNetworkManager()
    {
        this.httpClient = HttpClients.createDefault();
    }

    public void onDisable()
    {
        try {
            this.httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpPost createJsonPost(String url, List<Tuple<String, String>> headers, String json)
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, "UTF-8"));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept-Encoding", "UTF-8");
        if(headers != null && !(headers.isEmpty())) {
            for(Tuple<String, String> t : headers) {
                post.setHeader(t.getFirst(), t.getSecond());
            }
        }
        return post;
    }

    public <T extends JsonServerMessage> T doJsonPost(String url, List<Tuple<String, String>> headers, String json, Class<T> responseClass) throws Exception
    {
        CloseableHttpResponse response = httpClient.execute(createJsonPost(url, headers, json));
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
        String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");

        JsonServerMessage serverMessage = GsonUtil.gson.fromJson(jsonResponse, JsonServerMessage.class);
        if(serverMessage.error != null) {
            throw   new HttpException("Server error: " + serverMessage.error);
        }

        return GsonUtil.gson.fromJson(jsonResponse, responseClass);
    }

    public <T extends JsonServerMessage> T doJsonPost(String url, String json, Class<T> responseClass) throws Exception
    {
        return doJsonPost(url, null, json, responseClass);
    }
}
