package me.yusuf.ecommerce_builder.demo.engine.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.demo.engine.EntityRegistry;
import me.yusuf.ecommerce_builder.shared.components.EntityManagerFactoryFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.*;

@Component
@Scope("singleton")
public class RepositoryProxyFactory {
	private final EntityManagerFactoryFactory entityManagerFactoryFactory;
	private final Map<Integer, Map<Class<?>, Repository<?,?>>> cache = new HashMap<>();
	private final EntityRegistry entityRegistry;
	private final Map<Integer, EntityManagerFactory> factoryCache = new HashMap<>();
	public RepositoryProxyFactory(EntityManagerFactoryFactory entityManagerFactoryFactory, EntityRegistry entityRegistry){
		this.entityManagerFactoryFactory = entityManagerFactoryFactory;
		this.entityRegistry = entityRegistry;
	}

	/**
	 * @param iface Generic interface of the repository, as present in the source files.
	 * @param entityClass Generic repostiroy class, as in the type parameter of the {@code  iface}.
	 * @return a JPA-like repostiory proxy.
	 * @param <T> generic interface of the repostiroy, (see {@code iface})
	 */
	public <T extends Repository<?,?>> T create(int editorId, Class<T> iface, Class<?> entityClass){
		Map<Class<?>, Repository<?,?>> cac;
		if ( (cac = cache.get(editorId)) !=null)
			return (T) cac.get(entityClass);//return the cached repo if the editor never updated an entity since.
		Class<?> actualEntityClass;
		if((actualEntityClass = entityRegistry.get(editorId, entityClass))==null) //get the updated entity class if exits.
			actualEntityClass = entityClass;
		var emf = factoryCache.computeIfAbsent(editorId, this::createEntityManagerFactory);
		factoryCache.put(editorId,emf);
		return createProxy(emf.createEntityManager(),iface, actualEntityClass);
	}
	/**
	 * call this when registering another entity
     */
	public void invalidateCache(int editorId) {
		var f =  factoryCache.remove(editorId);
		if (f != null) f.close();
		cache.remove(editorId);
	}
	private static <T extends Repository<?,?>> T createProxy(EntityManager entityManager,Class<T> iface, Class<?> entityClass) {
		var ih = new RepositoryProxy(entityManager, new SimpleJpaRepository<>(entityClass, entityManager), entityClass);
		return (T) Proxy.newProxyInstance(CartRepository.class.getClassLoader(), new Class[]{iface}, ih);
	}
	private EntityManagerFactory createEntityManagerFactory(int editorId) {
		return entityManagerFactoryFactory.create(editorId, entityRegistry.getAll(editorId).stream().map(Class::getName).toList());
	}
}