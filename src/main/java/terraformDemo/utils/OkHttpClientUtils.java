package terraformDemo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp client
 * 
 * @author charles
 *
 */
public class OkHttpClientUtils {

    public static final MediaType MediaType_JSON = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .build();

    public static enum HttpMethod {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE
    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.chengchao.name";
        System.out.println(get(url, null, null));
    }

    public static String get(String url, Map<String, String> urlParams, Map<String, String> headerMap)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        if (null != urlParams && !urlParams.isEmpty()) {
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                httpUrl.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        String result = excute(httpUrl.build(), HttpMethod.GET, headerMap, null);
        return result;
    }

    public static String post(String url, Map<String, String> headerMap, String body)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        String result = excute(httpUrl.build(), HttpMethod.POST, headerMap, body);
        return result;
    }

    public static String post(String url, Map<String, String> headerMap, Map<String, String> formBody)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        String result = excute(httpUrl.build(), HttpMethod.POST, headerMap, formBody);
        return result;
    }

    public static String put(String url, Map<String, String> headerMap, String jsonBody)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        String result = excute(httpUrl.build(), HttpMethod.PUT, headerMap, jsonBody);
        return result;
    }

    public static String delete(String url, Map<String, String> headerMap)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        String result = excute(httpUrl.build(), HttpMethod.DELETE, headerMap, null);
        return result;
    }

    public static String patch(String url, Map<String, String> headerMap, String body)
        throws Exception {
        HttpUrl.Builder httpUrl = HttpUrl.parse(url).newBuilder();
        String result = excute(httpUrl.build(), HttpMethod.PATCH, headerMap, body);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static String excute(HttpUrl httpUrl, HttpMethod method, Map<String, String> headerMap, Object body)
        throws Exception {
        Headers.Builder headers = new Headers.Builder();
        if (null != headerMap && !headerMap.isEmpty()) {

            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = null;
        if (null != body) {
            if (body instanceof String) {
                String bodyString = (String)body;
                requestBody = RequestBody.create(bodyString, MediaType_JSON);
            } else if (body instanceof HashMap) {
                Map<String, String> bodyMap = (Map<String, String>)body;
                FormBody.Builder formBody = new FormBody.Builder();
                for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
                    formBody.add(entry.getKey(), entry.getValue());
                }
                requestBody = formBody.build();
            }
        }
        Request request = new Request(httpUrl, method.toString(), headers.build(), requestBody, new HashMap<>());
        Call call = client.newCall(request);
        Response response = call.execute();
        int responseCode = response.code();
        String result = response.body().string();
        // 2xx 正常
        if (!(200 <= responseCode && 300 > responseCode)) {
            throw new RuntimeException("[" + responseCode + "] " + result);
        }
        return result;
    }
}
