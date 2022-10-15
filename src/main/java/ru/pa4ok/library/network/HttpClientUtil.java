package ru.pa4ok.library.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.util.GsonUtil;
import ru.pa4ok.library.common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * some tips
 * https://coderlessons.com/tutorials/java-tekhnologii/izuchite-apache-http-client/apache-httpclient-kratkoe-rukovodstvo
 */
public class HttpClientUtil
{
    private static final Logger logger = LogManager.getLogger(HttpClientUtil.class);

    private static final Gson GSON = GsonUtil.GSON;
    private static final CloseableHttpClient DEFAULT_HTTP_CLIENT = HttpClients.createDefault();

    public static HttpPost createPost(String url, List<Tuple<String, String>> headers)
    {
        HttpPost request = new HttpPost(url);

        request.setHeader("Accept-Encoding", "UTF-8");
        if(headers != null) {
            headers.forEach(h -> request.setHeader(h.getFirst(), h.getSecond()));
        }

        return request;
    }

    public static HttpPost createPost(String url, Tuple<String, String>... headers)
    {
        return createPost(url, headers != null ? Arrays.asList(headers) : null);
    }

    public static HttpGet createGet(String url, List<Tuple<String, String>> headers)
    {
        HttpGet request = new HttpGet(url);

        request.setHeader("Accept-Encoding", "UTF-8");
        if(headers != null) {
            headers.forEach(h -> request.setHeader(h.getFirst(), h.getSecond()));
        }

        return request;
    }

    public static HttpGet createGet(String url, Tuple<String, String>... headers)
    {
        return createGet(url, headers != null ? Arrays.asList(headers) : null);
    }

    public static void applyHeaders(HttpRequestBase request, Tuple<String, String>... headers)
    {
        if(headers != null && headers.length > 0) {
            Arrays.asList(headers).forEach(h -> request.setHeader(h.getFirst(), h.getSecond()));
        }
    }

    public static void jsonPost(HttpPost request, Object body)
    {
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        if (body != null) {
            request.setEntity(new StringEntity(GSON.toJson(body), "UTF-8"));
        }
    }

    public static <T> T executeJsonPost(CloseableHttpClient httpClient, HttpPost request, Class<T> responseClass) throws HttpException
    {
        try {
            logger.debug("Send post request: url='" + request.getURI() + "' body='" + checkPostBody(request) + "'");
            long mills = System.currentTimeMillis();
            CloseableHttpResponse response = (httpClient != null ? httpClient : DEFAULT_HTTP_CLIENT).execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpException(response.getStatusLine().getStatusCode() + (response.getEntity() == null ? "" : " " + EntityUtils.toString(response.getEntity(), "UTF-8")));
            }
            mills = System.currentTimeMillis() - mills;
            if (responseClass == null || response.getEntity() == null) {
                logger.debug("Get post response: url='" + request.getURI() + "' time='" + mills + "ms' body='null'");
                return null;
            }
            String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.debug("Get post response: url='" + request.getURI() + "' time='" + mills + "ms' body='" + checkStringBody(jsonResponse) + "'");

            return GSON.fromJson(jsonResponse, responseClass);
        } catch (IOException e) {
            throw new HttpException("IO error in request", e);
        }
    }

    public static <T> T executeJsonPost(HttpPost request, Class<T> responseClass) throws HttpException
    {
        return executeJsonPost(null, request, responseClass);
    }

    public static <T> List<T> executeJsonPostForList(CloseableHttpClient httpClient, HttpPost request, Class<T> responseClass) throws HttpException
    {
        try {
            logger.debug("Send post request: url='" + request.getURI() + "' body='" + checkPostBody(request) + "'");
            long mills = System.currentTimeMillis();
            CloseableHttpResponse response = (httpClient != null ? httpClient : DEFAULT_HTTP_CLIENT).execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpException(response.getStatusLine().getStatusCode() + (response.getEntity() == null ? "" : " " + EntityUtils.toString(response.getEntity(), "UTF-8")));
            }
            mills = System.currentTimeMillis() - mills;
            if (responseClass == null || response.getEntity() == null) {
                logger.debug("Get post response: url='" + request.getURI() + "' time='" + mills + "ms' body='null'");
                return null;
            }
            String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.debug("Get post response: url='" + request.getURI() + "' time='" + mills + "ms' body='" + checkStringBody(jsonResponse) + "'");

            return GSON.fromJson(jsonResponse, TypeToken.getParameterized(List.class, responseClass).getType());
        } catch (IOException e) {
            throw new HttpException("IO error in request", e);
        }
    }

    public static <T> List<T> executeJsonPostForList(HttpPost request, Class<T> responseClass) throws HttpException
    {
        return executeJsonPostForList(null, request, responseClass);
    }

    public static <T> T doJsonPost(CloseableHttpClient httpClient, String url, List<Tuple<String, String>> headers, Object body, Class<T> responseClass) throws HttpException
    {
        HttpPost request = createPost(url, headers);
        jsonPost(request, body);
        return executeJsonPost(httpClient, request, responseClass);
    }

    public static <T> T doJsonPost(String url, List<Tuple<String, String>> headers, Object body, Class<T> responseClass) throws HttpException
    {
        return doJsonPost(null, url, headers, body, responseClass);
    }

    public static <T> List<T> doJsonPostForList(CloseableHttpClient httpClient, String url, List<Tuple<String, String>> headers, Object body, Class<T> responseClass) throws HttpException
    {
        HttpPost request = createPost(url, headers);
        jsonPost(request, body);
        return executeJsonPostForList(httpClient, request, responseClass);
    }

    public static <T> List<T> doJsonPostForList(String url, List<Tuple<String, String>> headers, Object body, Class<T> responseClass) throws HttpException
    {
        return doJsonPostForList(null, url, headers, body, responseClass);
    }

    public static byte[] doFileGet(String url) throws HttpException
    {
        try {
            return IOUtils.toByteArray((new URL(url)).openStream());
        } catch (Exception e) {
            throw new HttpException("Error while file downloading", e);
        }
    }

    private static String checkPostBody(HttpPost request)
    {
        HttpEntity entity = request.getEntity();
        String s;

        if(entity != null) {
            try {
                String body = EntityUtils.toString(entity);
                if(body != null && body.length() > 512) {
                    s =  "[LONG_BODY_HIDDEN]";
                } else {
                    s = body;
                }
            } catch (IOException e) {
                e.printStackTrace();
                s = e.getMessage();
            }
        } else {
            s = "[NULL]";
        }

        return s;
    }

    private static String checkStringBody(String body) {
        if(body != null && body.length() > 512) {
            return "[LONG_BODY_HIDDEN]";
        }
        return body;
    }
}
