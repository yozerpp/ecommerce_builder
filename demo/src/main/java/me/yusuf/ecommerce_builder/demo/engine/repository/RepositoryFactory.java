package me.yusuf.ecommerce_builder.demo.engine.repository;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.demo.engine.EntityRegistry;
import me.yusuf.ecommerce_builder.demo.engine.InMemoryClassLoader;
import me.yusuf.ecommerce_builder.demo.utils.Cached;
import me.yusuf.ecommerce_builder.demo.utils.EngineUtils;
import me.yusuf.utils.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFragment;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to generate proxies for dynamic entity classes.
 */
@Component
@Scope("singleton")
public class RepositoryFactory implements Cached<Integer> {
    private final EntityManagerFactory entityManagerFactory;
	private final Map<Integer, CacheElement> cache = new ConcurrentHashMap<>();
	private final EntityRegistry entityRegistry;
	private final InMemoryClassLoader classLoader;
	public RepositoryFactory(InMemoryClassLoader classLoader, EntityManagerFactory entityManagerFactory, EntityRegistry entityRegistry){
		this.entityManagerFactory = entityManagerFactory;
		this.entityRegistry = entityRegistry;
		this.classLoader = classLoader;
	}

	/**
	 * @param iface the interface class of the repository.
	 * @return a JPA repostiory proxy.
	 * @param <T> generic interface of the repository.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Repository<?,?>> T create(int editorId, Class<T> iface){
		var factories = cache.computeIfAbsent(editorId,id->{
			var em = getEntityManager(id);
			var repoFactory = new JpaRepositoryFactory(em);
			repoFactory.setBeanClassLoader(classLoader);
			repoFactory.setRepositoryBaseClass(SimpleJpaRepository.class);
			return new CacheElement(repoFactory);
		});
		return (T) factories.repositoryCache.computeIfAbsent(iface, repoInterface->{
			Class<?> baseEntityClass = EngineUtils.extractEntityClassFromRepository(repoInterface).orElseThrow(()-> new IllegalArgumentException("Entity class cannot be determined for repository: " + repoInterface) );
			Class<?> actualEntityClass = entityRegistry.get(editorId, baseEntityClass);
			if (actualEntityClass==null) actualEntityClass = baseEntityClass;
			var idType = ReflectionUtils.findIdFields(baseEntityClass)[0].getType();
			return factories.repositoryFactory.getRepository(iface, new RepositoryMetadataImpl(idType,actualEntityClass,repoInterface));
		});
	}
	/**
	 * Needs to be called after {@link EntityManagerFactory#invalidateCache(Integer)}
     */
	@Override
	public void invalidateCache(Integer editorId) {
		var c = cache.remove(editorId);
		if (c!=null){
			c.repositoryCache.clear();
		}
	}
//	private static <T extends Repository<?,?>> T createProxy(EntityManager entityManager,Class<T> iface, Class<?> entityClass) {
//		var ih = new RepositoryProxy(entityManager, new SimpleJpaRepository<>(entityClass, entityManager), entityClass);
//		return (T) Proxy.newProxyInstance(CartRepository.class.getClassLoader(), new Class[]{iface}, ih);
//	}
	private EntityManager getEntityManager(int editorId) {
		return entityManagerFactory.create(editorId, entityRegistry.getAll(editorId).stream().map(Class::getName).toList());
	}
	private record CacheElement(
			JpaRepositoryFactory repositoryFactory,
			Map<Class<?>, Repository<?,?>> repositoryCache
	) {
		public CacheElement( JpaRepositoryFactory repositoryFactory) {
			this( repositoryFactory, new ConcurrentHashMap<>());
		}
	}
	private static class RepositoryMetadataImpl extends AbstractRepositoryMetadata {
		private final Class<?> idType;
		private final Class<?> entityType;
		public RepositoryMetadataImpl(Class<?> idType, Class<?> entityType, Class<?> repositoryInterface) {
			super(repositoryInterface);
			this.idType = idType;
			this.entityType = entityType;
		}
		@NotNull
		@Override
		public Class<?> getIdType() {
			return idType;
		}

		@NotNull
		@Override
		public Class<?> getDomainType() {
			return entityType;
		}

		@NotNull
		@Override
		public TypeInformation<?> getIdTypeInformation() {
			return TypeInformation.of(idType);
		}

		@NotNull
		@Override
		public TypeInformation<?> getDomainTypeInformation() {
			return TypeInformation.of(entityType);
		}

		@NotNull
		@Override
		public Set<RepositoryFragment<?>> getFragments() {
			return Set.of();
		}
	}
}