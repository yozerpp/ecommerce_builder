package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.ProductRepository;
import me.yusuf.ecommerce_builder.demo.engine.EntityRegistry;
import me.yusuf.ecommerce_builder.demo.utils.EngineUtils;
import me.yusuf.ecommerce_builder.shared.utils.SharedUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Component
public class RepositoryFacadeFactory implements BeanPostProcessor {
    private final RepositoryFactory repositoryFactory;

    private static final Logger logger = Logger.getLogger(RepositoryFacadeFactory.class);
    private final Class<? extends Repository<?,?>>[] repositoryInterfaces;
    private final EntityRegistry entityRegistry;

    public RepositoryFacadeFactory(RepositoryFactory repositoryFactory, EntityRegistry entityRegistry, Class<? extends Repository<?,?>>[] repositoryInterfaces) {
        this.repositoryFactory = repositoryFactory;
        this.entityRegistry = entityRegistry;
        this.repositoryInterfaces = repositoryInterfaces;
    }

    @Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<? extends Repository<?,?>> repoInterface;
        if (bean instanceof Repository<?, ?> r &&  ((repoInterface = getRepositoryIface((Class<? extends Repository<?, ?>>) r.getClass())))!=null) {
            if (repoInterface==null) return bean;
            var handler = new RepositoryFacade( entityRegistry,repoInterface,r, repositoryFactory);
            var ret = Proxy.newProxyInstance(CartRepository.class.getClassLoader(),
                    Arrays.stream(bean.getClass().getInterfaces()).filter(Repository.class::isAssignableFrom).toArray(Class[]::new),
                    handler);
            logger.debug("Created RepositoryFacade for bean: " + repoInterface);
            return ret;
        }
        return bean;
	}
	private static Class<? extends Repository<?,?>> getRepositoryIface(Class<? extends Repository<?,?>> cls){
		return (Class<? extends Repository<?, ?>>) Arrays.stream(cls.getInterfaces())
				.filter(c->Repository.class .isAssignableFrom(c) && c.getPackageName().equals(CartRepository.class.getPackageName())).findAny().orElse(null);
	}
}
