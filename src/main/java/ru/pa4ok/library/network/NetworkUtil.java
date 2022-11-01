package ru.pa4ok.library.network;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;

import java.net.URL;

public class NetworkUtil
{
    public static byte[] doFileGet(String url) throws HttpException
    {
        try {
            return IOUtils.toByteArray((new URL(url)).openStream());
        } catch (Exception e) {
            throw new HttpException("Error while file downloading", e);
        }
    }
}
