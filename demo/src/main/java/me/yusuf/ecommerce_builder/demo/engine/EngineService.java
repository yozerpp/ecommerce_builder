package me.yusuf.ecommerce_builder.demo.engine;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import me.yusuf.ecommerce_builder.demo.engine.plugin.PluginRegistry;
import me.yusuf.ecommerce_builder.demo.engine.repository.EntityManagerFactory;
import me.yusuf.ecommerce_builder.demo.engine.repository.RepositoryFactory;
import me.yusuf.ecommerce_builder.demo.engine.repository.SchemaManager;
import me.yusuf.ecommerce_builder.shared.components.MethodMetadataRegistry;
import me.yusuf.ecommerce_builder.shared.types.plugin.*;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.util.*;

@Service
public class EngineService implements Closeable {
    private final MethodMetadataRegistry methodMetadataRegistry;
    private final PluginRegistry pluginRegistry;
    private final EntityRegistry entityRegistry;
    private final SchemaManager schemaManager;
//    private final ApiClient kubernetesClient;
    @Getter
    private final Set<Integer> waitingSchemaUpdates = new HashSet<>();
//    private final PodWatcher podWatcher;
    private final RepositoryFactory repositoryFactory;
    private final EntityManagerFactory entityManagerFactory;

    public EngineService(SchemaManager schemaManager, ApiClient kubernetesClient, EntityRegistry entityRegistry, MethodMetadataRegistry methodMetadataRegistry, PluginRegistry pluginRegistry, RepositoryFactory repositoryFactory, EntityManagerFactory entityManagerFactory) throws ApiException {
        this.methodMetadataRegistry = methodMetadataRegistry;
        this.schemaManager = schemaManager;
//        this.kubernetesClient = kubernetesClient;
        this.entityRegistry = entityRegistry;
        this.pluginRegistry = pluginRegistry;
//        if (EcommerceApplication.KUBE_DEPLOYMENT)
//            this.podWatcher = new PodWatcher();
//        else this.podWatcher = null;
        this.repositoryFactory = repositoryFactory;
        this.entityManagerFactory = entityManagerFactory;
    }
    @PreDestroy
    public void close() {
//    if (podWatcher != null) podWatcher.close();
    }
    public void addPlugin(Plugin plugin){
        pluginRegistry.registerPlugin(plugin);
    }
    public Map<String, MethodMetadata> getMethods(String className){
        return methodMetadataRegistry.getMethods(className);
    }
    public void replaceEntities(EntitySource[] entitySources, PluginDto[] pluginDtos , int editorId){
        addEntities(entitySources, editorId);
        replacePlugins(pluginDtos, editorId);
        Arrays.stream(entitySources).forEach(entityRegistry::unregisterOldVersion);
    }
    private void addEntities(EntitySource[] entitySources, int editorId){
        var clses = entityRegistry.registerAll(Arrays.asList(entitySources));
        schemaManager.update(clses,editorId);
        repositoryFactory.invalidateCache(editorId);
        entityManagerFactory.invalidateCache(editorId);
    }
    private void replacePlugins(PluginDto[] plugins,int editorId){
        Arrays.stream(plugins).map(Plugin::new).forEach(p->{
            pluginRegistry.unregisterPlugin(new IPlugin.Id(editorId,p.getId().getName(),p.getId().getHookedMethod(),p.getId().getVersion()-1));
            pluginRegistry.registerPlugin(p);
        });
    }
    public Map<String, Map<String, MethodMetadata>> getMethods(){
        return methodMetadataRegistry.getClassAndMethodsMap();
    }
//    private class PodWatcher implements Closeable {
//        private final Thread watcherThread;
//        private final Watch<V1Pod> watch;
//        public PodWatcher() throws ApiException {
//            var coreV1Api=new CoreV1Api(kubernetesClient);
//            watch=Watch.createWatch(kubernetesClient,
//                coreV1Api.listNamespacedPod("default").labelSelector("app=ecommerce").buildCall(null),
//                V1Pod.class);
//            watcherThread = new Thread(()->this.watcher(new AppsV1Api(kubernetesClient)));
//        }
//        private void watcher(final AppsV1Api appsV1Api){
//            watch.forEachRemaining(res->{ //does that actually block?
//                int readyReplicas;
//                try {
//                    var statefulSet=appsV1Api.readNamespacedStatefulSet("ecommerce","default").execute();
//                    readyReplicas=statefulSet.getStatus().getReadyReplicas();
//                } catch (ApiException e) {
//                    throw new RuntimeException(e);
//                }
//                switch (res.type){
//                    case "ADDED": //assumes that only new replicas are added, not replacements for existing ones.
//                    case "DELETED": //assumes only ever the last set in the
//                        pluginRegistry.partition(readyReplicas);
//                        break;
//                    case "MODIFIED":
//                    default:
//                        break;
//                }
//            });
//        }
//        @Override
//        public void close() {
//            try {
//                watcherThread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
