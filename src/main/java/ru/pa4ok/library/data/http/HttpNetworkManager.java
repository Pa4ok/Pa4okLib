package ru.pa4ok.library.data.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.util.GsonUtil;
import ru.pa4ok.library.util.Tuple;

import java.io.IOException;
import java.util.List;

//https://coderlessons.com/tutorials/java-tekhnologii/izuchite-apache-http-client/apache-httpclient-kratkoe-rukovodstvo

public class HttpNetworkManager implements AutoCloseable
{
    private static final Logger logger = LogManager.getLogger(HttpNetworkManager.class);

    private final Gson gson = GsonUtil.gson;
    private CloseableHttpClient httpClient;

    public HttpNetworkManager()
    {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public void close() throws Exception {
        this.httpClient.close();
    }

    private HttpPost createJsonPost(String url, List<Tuple<String, String>> headers, String json)
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, "UTF-8"));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept-Encoding", "UTF-8");
        if(headers != null) {
            headers.forEach(h -> post.setHeader(h.getFirst(), h.getSecond()));
        }
        return post;
    }

    public <V, T> T doJsonPost(String url, List<Tuple<String, String>> headers, V clientMessage, Class<T> responseClass) throws IOException, HttpException
    {
        logger.debug("Send post request: url='" + url + "'" + ", headers='" + headers.toString() + "'" + ", body='" + clientMessage.toString() + "'");
        CloseableHttpResponse response = httpClient.execute(createJsonPost(url, headers, clientMessage.toString()));
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
        String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
        logger.debug("Get post response: " + jsonResponse);

        return gson.fromJson(jsonResponse, responseClass);
    }

    public <V, T> T doJsonPost(String url, V clientMessage, Class<T> responseClass) throws IOException, HttpException {
        return doJsonPost(url, null, clientMessage, responseClass);
    }

    public <V, T> List<T> doJsonPostForList(String url, List<Tuple<String, String>> headers, V clientMessage, Class<T> responseClass) throws IOException, HttpException
    {
        logger.debug("Send post request: url='" + url + "'" + ", headers='" + headers.toString() + "'" + ", body='" + clientMessage.toString() + "'");
        CloseableHttpResponse response = httpClient.execute(createJsonPost(url, headers, clientMessage.toString()));
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
        String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
        logger.debug("Get post response: " + jsonResponse);

        return gson.fromJson(jsonResponse, TypeToken.getParameterized(List.class, responseClass).getType());
    }

    public <V, T> List<T> doJsonPostForList(String url, V clientMessage, Class<T> responseClass) throws IOException, HttpException {
        return doJsonPostForList(url, null, clientMessage, responseClass);
    }
}
