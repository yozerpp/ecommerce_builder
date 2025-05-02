package me.yusuf.ecommerce_builder.editor.helper;

import me.yusuf.ecommerce_builder.editor.EditorApplication;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class DemoClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String baseURL = EditorApplication.isKUBE_DEPLOYMENT()? "http://demo-service.default.svc.cluster.local:8080/" : "http://localhost:8080/";
    private final String basePath;
    private boolean demoStarted = false;
    public DemoClient(String basePath){
        this.basePath = basePath;
    }
    public DemoClient(){basePath = "";}
    private void waitTillDemoStarted(){
        if (demoStarted) return;
        int i =0;
        while (true){
            try {
                HttpResponse<InputStream> res = client.send(HttpRequest.newBuilder(URI.create(baseURL+ "health/startup"))
                                .method("GET", HttpRequest.BodyPublishers.noBody()).build(),
                        HttpResponse.BodyHandlers.ofInputStream());
                if (res.statusCode() == 200) {
                    demoStarted = true; break;
                };
            } catch (InterruptedException | ConnectException _) {}
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException _) {}
        }
    }
    public <T> HttpResponse<T> send(String path, String method, HttpRequest.BodyPublisher body, HttpResponse.BodyHandler<T> bodyHandler, Tuple2<String,String>[] headers) throws IOException, InterruptedException {
    waitTillDemoStarted();
    var req = HttpRequest.newBuilder(URI.create(baseURL + basePath + path)).method(method, body);
    for (var header: headers)
        req.header(header._1(), header._2());
    req.header("Content-Type", "application/json");
     return client.send(req.build(),bodyHandler);
    }
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(String path, String method, HttpRequest.BodyPublisher body, HttpResponse.BodyHandler<T> bodyHandler, Tuple2<String,String>[] headers) {
        waitTillDemoStarted();
        var req = HttpRequest.newBuilder(URI.create(baseURL + basePath + path)).method(method, body);
        for (var header: headers)
            req.header(header._1(), header._2());
        req.header("Content-Type", "application/json");
        return client.sendAsync(req.build(), bodyHandler);
    }
    public <T> HttpResponse<T> send(String path, String method, HttpRequest.BodyPublisher body, HttpResponse.BodyHandler<T> bodyHandler) throws IOException, InterruptedException {
        return this.send(path,method,body, bodyHandler, new Tuple2[0]);
    }
}
