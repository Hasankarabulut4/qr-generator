package org.api;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// Just static methods
public class QrGenerator {

    public static final String getQrcode(String text, Integer size) {
        var urlText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        return "https://quickchart.io/qr?text=" + urlText + "&size=" + size;
    }
}
