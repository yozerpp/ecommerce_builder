package me.yusuf.ecommerce_builder.demo.engine;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Watch;
import jakarta.annotation.PreDestroy;
import me.yusuf.ecommerce_builder.demo.EcommerceApplication;
import me.yusuf.ecommerce_builder.shared.types.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.util.*;

@Service
public class EngineService implements Closeable {
    private final MethodMetadataRegistry methodMetadataRegistry;
    private final PluginRegistry pluginRegistry;
    private final ApiClient kubernetesClient;
    private final PodWatcher podWatcher;
    public EngineService(ApiClient kubernetesClient,MethodMetadataRegistry methodMetadataRegistry, PluginRegistry pluginRegistry) throws ApiException {
        this.methodMetadataRegistry = methodMetadataRegistry;
        this.kubernetesClient = kubernetesClient;
        this.pluginRegistry = pluginRegistry;
        if (EcommerceApplication.KUBE_DEPLOYMENT)
            this.podWatcher = new PodWatcher();
        else this.podWatcher = null;
    }
    @PreDestroy
    public void close() {
        podWatcher.close();
    }
    public void addPlugin(PluginClassFile plugin){
        pluginRegistry.registerPlugin(plugin);
    }
    public List<Tuple2<String, MethodMetadata>> getMethods(String className){
        try {
            return Arrays.stream(methodMetadataRegistry.getMethods(className)).map(i->
                    new Tuple2<>(i._1().toGenericString(),i._2())).toList();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String, List<Tuple2<String, MethodMetadata>>> getMethods(){
        Map<String, List<Tuple2<String, MethodMetadata>> > ret = new HashMap<>();
         Arrays.stream(methodMetadataRegistry.getMethods()).forEach(e->
             ret.putIfAbsent(e._1().getDeclaringClass().getSimpleName(),new ArrayList<>()).add(
                     new Tuple2<>(e._1().toGenericString(),e._2())
             )
         );
         return ret;
    }
    private class PodWatcher implements Closeable {
        private final Thread watcherThread;
        private final Watch<V1Pod> watch;
        public PodWatcher() throws ApiException {
            var coreV1Api=new CoreV1Api(kubernetesClient);
            watch=Watch.createWatch(kubernetesClient,
                coreV1Api.listNamespacedPod("default").labelSelector("app=ecommerce").buildCall(null),
                V1Pod.class);
            watcherThread = new Thread(()->this.watcher(new AppsV1Api(kubernetesClient)));
        }
        private void watcher(final AppsV1Api appsV1Api){
            watch.forEachRemaining(res->{ //does that actually block?
                int readyReplicas;
                try {
                    var statefulSet=appsV1Api.readNamespacedStatefulSet("ecommerce","default").execute();
                    readyReplicas=statefulSet.getStatus().getReadyReplicas();
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                switch (res.type){
                    case "ADDED": //assumes that only new replicas are added, not replacements for existing ones.
                    case "DELETED": //assumes only ever the last set in the
                        pluginRegistry.partition(readyReplicas);
                        break;
                    case "MODIFIED":
                    default:
                        break;
                }
            });
        }
        @Override
        public void close() {
            try {
                watcherThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
