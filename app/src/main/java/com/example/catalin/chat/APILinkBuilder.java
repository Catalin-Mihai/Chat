package com.example.catalin.chat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class APILinkBuilder
{
    //public static final String API_LINK = "http://catalinmihai.000webhostapp.com/rest/";
    public static final String API_LINK = "http://10.0.2.2:80/rest/";

    public static String Build(String api_file, String ... params) throws UnsupportedEncodingException {
        StringBuilder result;
        result = new StringBuilder(API_LINK + api_file + "?");
        for(int i = 0; i < params.length - 1; i++)//2 by 2
        {
            result.append(URLEncoder.encode(params[i], "UTF-8")).append("=").append(URLEncoder.encode(params[i+1], "UTF-8"));
            if(i < params.length-1) result.append("&");
            i++;
        }
        return result.toString();
    }
}
