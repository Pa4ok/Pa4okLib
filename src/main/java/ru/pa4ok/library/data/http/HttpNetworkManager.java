package ru.pa4ok.library.data.http;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.pa4ok.library.data.NetworkException;
import ru.pa4ok.library.data.message.JsonClientMessage;
import ru.pa4ok.library.data.message.JsonServerMessage;
import ru.pa4ok.library.util.GsonUtil;
import ru.pa4ok.library.util.Tuple;

import java.io.IOException;
import java.util.List;

//https://coderlessons.com/tutorials/java-tekhnologii/izuchite-apache-http-client/apache-httpclient-kratkoe-rukovodstvo

public class HttpNetworkManager implements AutoCloseable
{
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
        if(headers != null && !(headers.isEmpty())) {
            for(Tuple<String, String> t : headers) {
                post.setHeader(t.getFirst(), t.getSecond());
            }
        }
        return post;
    }

    private <T extends JsonServerMessage> T checkServerError(T response) throws NetworkException {
        if(response == null) {
            if(response.getError() != null) {
                throw new NetworkException(response.getError());
            }
            throw new NetworkException();
        }
        return response;
    }

    public <V extends JsonClientMessage, T extends JsonServerMessage> T doJsonPost(String url, List<Tuple<String, String>> headers, V clientMessage, Class<T> responseClass) throws IOException, NetworkException, HttpException {
        CloseableHttpResponse response = httpClient.execute(createJsonPost(url, headers, clientMessage.toString()));
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
        String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
        return checkServerError(GsonUtil.gson.fromJson(jsonResponse, responseClass));
    }

    public <V extends JsonClientMessage, T extends JsonServerMessage> T doJsonPost(String url, V clientMessage, Class<T> responseClass) throws IOException, HttpException, NetworkException {
        return doJsonPost(url, null, clientMessage, responseClass);
    }
}
