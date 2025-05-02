package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.demo.utils.Utils;
import me.yusuf.ecommerce_builder.shared.utils.SharedUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Component
public class RepositoryFacadeFactory implements BeanPostProcessor {
    private final RepositoryProxyFactory repositoryProxyFactory;
    private static final Class<? extends Repository<?,?>>[] repositoryInterfaces;
    public RepositoryFacadeFactory(RepositoryProxyFactory repositoryProxyFactory) throws IOException {
        this.repositoryProxyFactory = repositoryProxyFactory;
    }

    @Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof Repository<?, ?> r) {
            var entityClass = Arrays.stream(repositoryInterfaces).filter(rr->rr.isAssignableFrom(r.getClass()))
                    .map(Utils::extractEntityClassFromRepository).findAny()
                    .orElse(null);
            if (entityClass == null) {return bean;}
            var handler = new RepositoryFacade(getRepositoryIface(bean.getClass()), entityClass,r, repositoryProxyFactory);
            return Proxy.newProxyInstance(CartRepository.class.getClassLoader(),
                    Arrays.stream(bean.getClass().getInterfaces()).filter(Repository.class::isAssignableFrom).toArray(Class[]::new),
                    handler);
        }
        return bean;
	}
	private static Class<? extends Repository<?,?>> getRepositoryIface(Class<?> cls){
		return (Class<? extends Repository<?, ?>>) Arrays.stream(cls.getInterfaces())
				.filter(c->Repository.class .isAssignableFrom(c) && c.getPackageName().contains("me.yusuf.ecommerce_builder")).findAny().orElse(null);
	}
    static {
        try {
            var classes = SharedUtils.getPatternMatchingClasses("classpath*:me.yusuf.ecommerce_builder.demo.domain.repository.*.class");
            repositoryInterfaces = Arrays.stream(classes).map(Class::getGenericInterfaces).flatMap(Arrays::stream).filter(i ->
                    i instanceof ParameterizedType pt && pt.getRawType() instanceof Class<?> c &&
                            c.getPackageName().contains("me.yusuf.ecommerce_builder") && Repository.class.isAssignableFrom(c)).map(i -> (Class<?>) i).toArray(Class[]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
