package ccdr4gon.utils;


import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class HttpUtil {
    static class HeaderInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .removeHeader("User-Agent")
                    .removeHeader("Connection")
                    .removeHeader("Accept-Encoding")
                    .build();
            return chain.proceed(request);
        }
    }
    /* https://www.stubbornjava.com/posts/okhttpclient-trust-all-ssl-certificates */
    /*
     * This is very bad practice and should NOT be used in production.
     */
    /* 那咋办嘛 摊手手.jpg */
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;
    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
    private static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }
    public static void post (String url,String cookie,String bodystr) throws Exception{
        OkHttpClient client = trustAllSslClient(new OkHttpClient());
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
        OkHttpClient client = trustAllSslClient(new OkHttpClient())
                .newBuilder()
                .addNetworkInterceptor(new HeaderInterceptor())
                .build();
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
