//package me.yusuf.ecommerce_builder.demo.engine.repository;
//
//import jakarta.persistence.*;
//import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
//import me.yusuf.utils.StringUtils;
//import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//import java.io.Closeable;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//public class RepositoryProxy implements InvocationHandler, Closeable {
//	private final EntityManager entityManager;
//	private final Class<?> entityClass;
//	private final SimpleJpaRepository<?,?> jpaRepository;
//	public RepositoryProxy(EntityManager manager, SimpleJpaRepository<?,?> jpaRepository, Class<?> entityClass){
//		this.entityManager = manager;
//		this.entityClass = entityClass;
//		this.jpaRepository = jpaRepository;
//	}
//	@Override
//	public Object invoke(Object obj, Method method, Object[] args) {
//		if (method.getName().startsWith("save")){ //save or saveAll
//			var tx = entityManager.getTransaction();
//			tx.begin();
//			try {
//				for (var arg: args)
//					try {
//						entityManager.persist(arg);
//					} catch (EntityExistsException e){
//						entityManager.merge(arg);
//					}
//				tx.commit();
//			} catch (Exception e){tx.rollback(); throw e;}
//			if (method.getName().endsWith("All"))
//				return args;
//			else return args[0];
//		} else if (method.getName().matches("^(remove|delete)(All)?$") ){
//			var tx = entityManager.getTransaction();
//			tx.begin();
//			try {
//				for (var arg : args)
//					entityManager.remove(arg);
//				tx.commit();
//			} catch (Exception e){ tx.rollback(); throw e;}
//			return null;
//		}
//		jakarta.persistence.Query query;
//		JpqlStatementBuilder.Type type;
//		if ( method.isAnnotationPresent(Query.class)){
//			type = method.isAnnotationPresent(Modifying.class)? JpqlStatementBuilder.Type.UPDATE: JpqlStatementBuilder.Type.SELECT;
//			query = entityManager.createQuery( method.getAnnotation(Query.class).value(), method.getReturnType());
//		} else{
//			var b =createStatementBuilderFromFields(method, args);
//			query = b.build(entityManager);
//			type = b.getType();
//		}
//		if (type!= JpqlStatementBuilder.Type.SELECT){
//			var tx = entityManager.getTransaction();
//			try {
//				tx.begin();
//				var count = query.executeUpdate();
//				tx.commit();
//				return count;
//			} catch (Exception e){tx.rollback(); throw new RuntimeException(e);}
//		} else return executeSelect(query,method.getReturnType(),type);
//	}
//	private static <T> T toProxy(T e){
//		var enhancer= new Enhancer();
//		enhancer.setSuperclass(e.getClass());
//		enhancer.setCallback(new DynamicEntityProxy(e));
//		return (T) enhancer.create();
//	}
//	private Object executeSelect(jakarta.persistence.Query q, Class<?> methodReturnType, JpqlStatementBuilder.Type type){
//		if (Iterable.class.isAssignableFrom(methodReturnType)){
//			var list=q.getResultList();
//			list.replaceAll(RepositoryProxy::toProxy);
//			if (Page.class.isAssignableFrom(methodReturnType)){
//				return new PageImpl(list);
//			}else if (List.class.isAssignableFrom(methodReturnType)){
//				return new ArrayList<>(list);
//			} else if (Set.class.isAssignableFrom(methodReturnType)){
//				return new HashSet<>(list);
//			}
//			else throw new RuntimeException("Unsupported return type: " + methodReturnType);
//		} else{
//			Object ret;
//			try{
//				ret = q.getSingleResult();
//			} catch (NoResultException e){ret = null;}
//			if (Optional.class.isAssignableFrom(methodReturnType))
//				return Optional.ofNullable(ret);
//			else return ret;
//		}
//	}
//	private JpqlStatementBuilder<?> createStatementBuilderFromFields(Method method, Object[] args){
//		JpqlStatementBuilder<?> builder;
//		if (method.getName().startsWith("get") | method.getName().startsWith("find")) {
//			builder =  QueryBuilder.buildSelect(entityClass,method.getName(), args);
//		} else if (method.getName().matches("^update\\w+By\\w+$")){
//			builder = QueryBuilder.buildUpdateBy(entityClass, method.getName(),args);
//		} else if (method.getName().startsWith("deleteBy")) {
//			builder = QueryBuilder.buildDeleteBy(entityClass,method.getName(), args);
//		}else throw new UnsupportedOperationException("Query type for handle name cannot be determined: " + method.getName());
//		return builder;
//	}
//	private interface QueryBuilder{
//		 static JpqlStatementBuilder<?> buildSelect(Class<?> eType, String methodName, Object[] args ){
//			var builder = JpqlStatementBuilder.select(eType, eType.getSimpleName());
//			List<String> joinedEntityNames = extractJoinedEntities(methodName);
//			List<String> queriedFields = extractQueriedFields(methodName, JpqlStatementBuilder.Type.SELECT);
//			List<Join> joins = getJoins(eType, joinedEntityNames,queriedFields);
//			List<Tuple2<String, Object>> params = createParams(queriedFields, args);
//			for (Join join : joins)
//				builder.join( "JOIN"+(join.type()== Join.Type.FETCH?" FETCH":""), join.path(), join.entity().getSimpleName());
//			 buildWhereClause(params, joins, builder);
//			 return builder;
//		}
////		static JpqlStatementBuilder<?> buildInsert(Class<?> eType, Object extendedClass){
////			 var builder = JpqlStatementBuilder.insert(eType);
////			Arrays.stream(eType.getDeclaredFields()).filter(QueryBuilder::isReadonly).forEach(field->{
////                try {
////                    builder.column(field.getName()).value(ReflectionUtils.findGetter(field, eType).invoke(extendedClass));
////                } catch (IllegalAccessException | InvocationTargetException e) {
////                    throw new RuntimeException(e);
////                }
////            });
////			return builder;
////		}
//		private static boolean isReadonly(Field f){
//			 return !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(GeneratedValue.class)&&!f.isAnnotationPresent(OneToMany.class);
//		}
//		static JpqlStatementBuilder<?> buildUpdateBy(Class<?> eType, String methodName, Object[] args){
//			 var splt = methodName.split("By");
//		 	 var updatedFields = splt[0].replaceFirst("[uU]pdate","").split("And");
//			 var queriedFields = List.of(splt[1].split("And"));
//			 var ij = getInnerJoins(List.of(),null,eType,new ArrayList<>(queriedFields));
//			 var params = createParams(queriedFields, Arrays.copyOfRange(args,updatedFields.length,args.length));
//			 var builder = JpqlStatementBuilder.update(eType, eType.getSimpleName());
//			 buildWhereClause(params,ij,builder);
//			 for (int i  =0; i < updatedFields.length; i++)
//				builder.set(updatedFields[i], args[i]);
//			return builder;
//		 }
//		 static JpqlStatementBuilder<?> buildDeleteBy(Class<?> eType, String methodName, Object[] args){
//			 var builder = JpqlStatementBuilder.delete(eType, eType.getSimpleName());
//			 var fieldNames = extractQueriedFields(methodName, JpqlStatementBuilder.Type.DELETE);
//			 var params =  createParams(fieldNames,args);
//			 buildWhereClause(params,List.of(),builder);
//			 return builder;
//		 }
//
////		private static void addWhereClauses(Class<?> eType, Object extendedClass, Field[] idFields, JpqlStatementBuilder<?> builder) {
////			Arrays.stream(idFields).forEach(field->{
////				try {
////					builder.where(eType.getSimpleName() + '.' + field.getName() + "=?",
////							ReflectionUtils.findGetter(field, eType).invoke(extendedClass));
////				} catch (IllegalAccessException | InvocationTargetException e) {
////					throw new RuntimeException(e);
////				}
////			});
////		}
//
//		private static void buildWhereClause(List<Tuple2<String, Object>> params, List<Join> joins, JpqlStatementBuilder<?> builder) {
//			for (var param : params){
//				String left;
//				var p = Pattern.compile("^([A-Z][a-z]+)([A-Z][a-z]+)$");
//				var m = p.matcher(param._1());
//				if (m.matches() && (left = joins.stream().filter(j->
//						j.type()==Join.Type.INNER &&
//								Arrays.asList(j.path().split("\\.")).getLast().equals(param._2()))
//						.findAny().map(j->j.entity().getSimpleName()).orElse(null))!=null
//				) {
//					left += "." + StringUtils.firstLetterToLowerCase(m.group(2));
//				} else{
//					left = builder.getAlias() + '.' +  StringUtils.firstLetterToLowerCase(param._1());
//				}
//				builder.where( left + "=?", param._2());
//			}
//		}
//
//		private static List<Tuple2<String, Object>> createParams(List<String> fieldNames, Object[] args){
//			List<Tuple2<String, Object>> ar = new ArrayList<>();
//			for (int i =0; i < fieldNames.size(); i++){
//				ar.add( new Tuple2<>(fieldNames.get(i), args[i]));
//			}
//			return ar;
//		}
//		private static List<Join> getJoins(Class<?>entityType,List<String>joinedEntityNames,List<String> parameterNames){
//
//			var joins = getFetchJoins(null,entityType,joinedEntityNames);
//			joins.addAll(getInnerJoins(joins,null, entityType,parameterNames));
//			return joins;
//		}
//		private static List<Join> getFetchJoins(final String initialPath, Class<?>entityType,List<String>joinedEntityNames/*mutates the list*/){
//			if (joinedEntityNames.isEmpty()) return new ArrayList<>();
//			var fJoin =  Arrays.stream(entityType.getDeclaredFields()).filter(f->joinedEntityNames.contains(StringUtils.firstLetterToUpperCase(f.getName())))
//					.peek(f->joinedEntityNames.remove(StringUtils.firstLetterToUpperCase(f.getName())))
//					.map(f-> new Join(Join.Type.FETCH, (initialPath!=null? initialPath+ '.':"") + entityType.getSimpleName() + '.' + f.getName(), f.getType()))
//					.collect(Collectors.toList());
//			fJoin.addAll(fJoin.stream().map(j->getFetchJoins(j.path(), j.entity(),joinedEntityNames)).flatMap(List::stream).toList());
//			return fJoin;
//		}
//		private static List<Join> getInnerJoins(List<Join> fetchJoins, final String initialPath,Class<?> entityType, List<String> parameterNames){
//			if (parameterNames.isEmpty()) return List.of();
//			var parameterNamesCopy = new ArrayList<>(parameterNames);
//			var ret = Arrays.stream(entityType.getDeclaredFields()).filter(f->parameterNames.contains(StringUtils.firstLetterToUpperCase(f.getName())))
//					.filter(f->
//							fetchJoins.stream().noneMatch(j->Arrays.asList(j.path.split("\\.")).getLast().equals(f.getName())) &&
//									parameterNamesCopy.stream().filter(p->p.matches(StringUtils.firstLetterToUpperCase(f.getName()) + "\\w+" + '|' + "\\w+" +StringUtils.firstLetterToUpperCase( f.getName()) + "\\w+"))
//											.peek(s->parameterNames.remove(StringUtils.firstLetterToUpperCase(s))).findAny().isPresent()
//					).map(f->new Join(Join.Type.INNER,(initialPath!=null? initialPath+ '.':"") + entityType.getSimpleName() + '.' + f.getName(),f.getType()))
//					.collect(Collectors.toList());
//			if (fetchJoins.isEmpty()) return ret;
//			ret.addAll(fetchJoins.stream().map(j->getInnerJoins(List.of(),j.path(),j.entity(),parameterNames)).flatMap(List::stream).toList());
//			return ret;
//		}
//
//		private static List<String> extractJoinedEntities(String methodName){
//			var array = methodName.split("With");
//			 return new ArrayList<>(List.of(array.length > 1? array[1].split("By")[0].split("And"):new String[0]));
//		}
//		record Join(Type type,String path, Class<?> entity){
//			enum Type{
//				FETCH,INNER, OUTER
//			}
//		}
//		private static List<String> extractQueriedFields(String methodName, JpqlStatementBuilder.Type type){
//			return switch (type){
//				case SELECT -> {
//					var s = methodName.split("By");
//					yield  new ArrayList<>(List.of(Arrays.stream(s).skip(1).collect(Collectors.joining()).split("And")));
//				}
//				default -> throw new UnsupportedOperationException("Extracting fields from query types other than select is not implemented.");
//			};
//		}
////		private  Class<?> getEntityType(Method handle, JpqlStatementBuilder.Type type){
//			 //			switch (type){
////				case SELECT -> {
////					if (handle.getGenericReturnType() instanceof ParameterizedType pt) {
////						var tp = pt.getActualTypeArguments()[0];
////						if (handle)
////					} else return handle.getReturnType();
////				} case UPDATE,DELETE -> {
////					if (handle.getGenericParameterTypes()[0] instanceof ParameterizedType pt) {
////						return (Class<?>) pt.getActualTypeArguments()[0];
////					} else return handle.getParameterTypes()[0];
////				} case null ->throw new IllegalArgumentException("Query type is null.");
////			}
////		}
//	}
//
//	@Override
//	public void close() {
//        System.out.println("Close called on RepositoryProxy.");
//		entityManager.close();
//	}
//}
