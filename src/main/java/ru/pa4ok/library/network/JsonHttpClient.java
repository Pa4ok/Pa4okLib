package ru.pa4ok.library.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
public class JsonHttpClient implements Closeable
{
    private static final Logger LOGGER = LogManager.getLogger(JsonHttpClient.class);

    public static final ResponseConverter DEFAULT_RESPONSE_CONVERTER = new DefaultResponseConverter();

    protected final CloseableHttpClient client;
    protected final Map<String, String> defaultHeaders;

    public JsonHttpClient(CloseableHttpClient client)
    {
        this.client = client;
        this.defaultHeaders = new HashMap<>();

        defaultHeaders.put("Accept-Encoding", "UTF-8");
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("Content-type", "application/json");
    }

    public JsonHttpClient()
    {
        this(HttpClients.createDefault());
    }

    public <T> T get(String url, TypeToken<?> responseType) throws HttpException {
        return execute(newGet(url), responseType, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> T get(String url, Class<T> responseType) throws HttpException {
        return execute(newGet(url), TypeToken.get(responseType), DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createGet(String url, TypeToken<?> responseType) {
        return new RequestBuilder<>(this, newGet(url), responseType, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createGet(String url, Class<T> responseType) {
        return new RequestBuilder<>(this, newGet(url), TypeToken.get(responseType), DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> T post(String url, Object body, TypeToken<?> responseType) throws HttpException {
        return execute(newPost(url, body), responseType, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> T post(String url, Object body, Class<T> responseType) throws HttpException {
        return execute(newPost(url, body), TypeToken.get(responseType), DEFAULT_RESPONSE_CONVERTER);
    }

    public void post(String url, Object body) throws HttpException {
        execute(newPost(url, body), null, DEFAULT_RESPONSE_CONVERTER);
    }

    public void post(String url) throws HttpException {
        execute(newPost(url, null), null, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createPost(String url, Object body, TypeToken<?> responseType) {
        return new RequestBuilder<>(this, newPost(url, body), responseType, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createPost(String url, Object body, Class<T> responseType) {
        return new RequestBuilder<>(this, newPost(url, body), TypeToken.get(responseType), DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createPost(String url, Object body) {
        return new RequestBuilder<>(this, newPost(url, body), null, DEFAULT_RESPONSE_CONVERTER);
    }

    public <T> RequestBuilder<T> createPost(String url) {
        return new RequestBuilder<>(this, newPost(url, null), null, DEFAULT_RESPONSE_CONVERTER);
    }

    protected HttpGet newGet(String url)
    {
        HttpGet get = new HttpGet(url);
        defaultHeaders.forEach(get::setHeader);
        return get;
    }

    protected HttpPost newPost(String url, Object body)
    {
        HttpPost post = new HttpPost(url);
        defaultHeaders.forEach(post::setHeader);
        if(body != null) {
            Gson gson = GsonUtil.BUILDER.create();
            post.setEntity(new StringEntity(gson.toJson(body), StandardCharsets.UTF_8));
        }
        return post;
    }

    protected <T> T execute(HttpRequestBase request, TypeToken<?> responseType, ResponseConverter responseConverter) throws HttpException
    {
        LOGGER.debug("Send request " + request.getClass().getSimpleName() + ": url='" + request.getURI() + "'");
        long mills = System.currentTimeMillis();

        try(CloseableHttpResponse response = client.execute(request))
        {
            mills = System.currentTimeMillis() - mills;
            LOGGER.debug("Get post response: url='" + request.getURI() + "' code='" + response.getStatusLine().getStatusCode() + "' time='" + mills + "ms'");

            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpException("bad request code: " + response.getStatusLine().getStatusCode());
            }
            if(responseType == null) {
                return null;
            }

            return responseConverter.convert(response.getEntity(), responseType);

        } catch (Exception e) {
            throw new HttpException("request error", e);
        }
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    public void addDefaultHeader(String name, String value)
    {
        this.defaultHeaders.put(name, value);
    }

    public void removeDefaultHeader(String name)
    {
        this.defaultHeaders.remove(name);
    }

    @Getter
    @AllArgsConstructor
    public static class RequestBuilder<T>
    {
        private final JsonHttpClient client;
        private final HttpRequestBase request;
        private TypeToken<?> responseType;
        private ResponseConverter responseConverter;

        public T execute() throws HttpException
        {
            return client.execute(this.request, this.responseType, DEFAULT_RESPONSE_CONVERTER);
        }

        public RequestBuilder<T> addHeader(String name, String value) {
            this.request.setHeader(name, value);
            return this;
        }

        public RequestBuilder<T> removeHeader(String name) {
            this.request.removeHeaders(name);
            return this;
        }

        public RequestBuilder<T> setResponseType(TypeToken<?> responseType) {
            this.responseType = responseType;
            return this;
        }

        public RequestBuilder<T> setResponseConverter(ResponseConverter responseConverter) {
            this.responseConverter = responseConverter;
            return this;
        }
    }

    public interface ResponseConverter
    {
        <T> T convert(HttpEntity entity, TypeToken<?> responseType) throws IOException;
    }

    public interface ParseResponseConverter extends ResponseConverter
    {
        @Override
        default <T> T convert(HttpEntity entity, TypeToken<?> responseType) throws IOException {
            return convert(JsonParser.parseString(EntityUtils.toString(entity, StandardCharsets.UTF_8)), responseType);
        }

        <T> T convert(JsonElement topElement, TypeToken<?> responseType) throws IOException;
    }

    @SuppressWarnings("unchecked")
    public static class DefaultResponseConverter implements ResponseConverter
    {
        private final Gson gson = GsonUtil.BUILDER.create();

        @Override
        public <T> T convert(HttpEntity entity, TypeToken<?> responseType) throws IOException {
            return (T) gson.fromJson(EntityUtils.toString(entity, StandardCharsets.UTF_8), responseType);
        }
    }
}

