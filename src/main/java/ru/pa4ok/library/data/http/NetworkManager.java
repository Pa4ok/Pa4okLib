package ru.pa4ok.library.data.http;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.pa4ok.library.util.GsonUtil;

import java.io.IOException;

//https://coderlessons.com/tutorials/java-tekhnologii/izuchite-apache-http-client/apache-httpclient-kratkoe-rukovodstvo

public class NetworkManager
{

    private CloseableHttpClient httpClient;

    public NetworkManager()
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

    public HttpPost createJsonPost(String url, String json)
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, "UTF-8"));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept-Encoding", "UTF-8");
        return post;
    }

    public <T> T doJsonPost(String url, String json, Class<T> responseClass) throws Exception
    {
        CloseableHttpResponse response = httpClient.execute(createJsonPost(url, json));
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
        String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
        return GsonUtil.gson.fromJson(jsonResponse, responseClass);
    }
}
