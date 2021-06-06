package ccdr4gon.utils;


import okhttp3.*;

public class HttpUtil {
    public static void post (String url,String cookie,String bodystr) throws Exception{
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(bodystr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //System.out.println(Objects.requireNonNull(response.body()).string());
        }
    }

    public static byte[] get (String url, String cookie) throws Exception{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().bytes();
        }
    }
}
