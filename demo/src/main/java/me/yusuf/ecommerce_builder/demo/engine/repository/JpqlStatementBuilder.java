package me.yusuf.ecommerce_builder.demo.engine.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import lombok.Getter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
/**
 * A simplified JPQL statement builder supporting SELECT, UPDATE, DELETE, and INSERT.
 * Usage:
 *   // SELECT
 *   List<MyEntity> list = JpqlStatementBuilder
 *       .select(MyEntity.class, "e")
 *       .where("e.name = ?", "John")
 *       .orderBy("e.createdAt", false)
 *       .build(em)
 *       .getResultList();
 *
 *   // UPDATE
 *   int updated = JpqlStatementBuilder
 *       .update(MyEntity.class, "e")
 *       .set("e.status = ?", Status.ACTIVE)
 *       .where("e.id = ?", 42)
 *       .build(em)
 *       .executeUpdate();
 *
 *   // DELETE
 *   int deleted = JpqlStatementBuilder
 *       .delete(MyEntity.class, "e")
 *       .where("e.active = ?", false)
 *       .build(em)
 *       .executeUpdate();
 *
 *   // INSERT
 *   int inserted = JpqlStatementBuilder
 *       .insert(MyEntity.class)
 *       .column("name").value("Alice")
 *       .column("status").value(Status.ACTIVE)
 *       .build(em)
 *       .executeUpdate();
 */
public class JpqlStatementBuilder<T> {

    public enum Type { SELECT, UPDATE, DELETE, INSERT }
    @Getter
    private final Type type;
    private final Class<T> entityClass;
    @Getter
    private final String alias;
    private final StringBuilder selectClause = new StringBuilder();
    private final StringBuilder fromClause = new StringBuilder();
    private final StringJoiner joinClauses = new StringJoiner(" ", " ", "");
    private final StringJoiner setClause = new StringJoiner(", ", " SET ", "");
    private final StringJoiner whereClause = new StringJoiner(" AND ", " WHERE ", "");
    private final StringJoiner groupByClause = new StringJoiner(", ", " GROUP BY ", "");
    private final StringJoiner havingClause = new StringJoiner(" AND ", " HAVING ", "");
    private final StringJoiner orderByClause = new StringJoiner(", ", " ORDER BY ", "");
    // for INSERT support:
    private final StringJoiner insertColumns = new StringJoiner(", ", "(", ")");
    private final StringJoiner valuesClause  = new StringJoiner(", ", "VALUES(", ")");
    private final Map<String, Object> parameters = new LinkedHashMap<>();
    private boolean groupByUsed = false;
    private boolean havingUsed = false;
    private boolean orderByUsed = false;
    private int paramCount = 0;

    // pagination
    private Integer maxResults;
    private Integer firstResult;
    private boolean distinct;

    private JpqlStatementBuilder(Type type, Class<T> entityClass, String alias) {
        this.type = type;
        this.entityClass = entityClass;
        this.alias = alias;
        if (type == Type.SELECT) {
            selectClause.append("SELECT ");
        } else if (type == Type.INSERT) {
            // we will build a native INSERT later; clear the default FROM
            fromClause.setLength(0);
        }
        fromClause.append("FROM ").append(entityClass.getSimpleName()).append(" ").append(alias);
    }

    public static <T> JpqlStatementBuilder<T> select(Class<T> entityClass, String alias) {
        return new JpqlStatementBuilder<>(Type.SELECT, entityClass, alias);
    }

    public static <T> JpqlStatementBuilder<T> update(Class<T> entityClass, String alias) {
        return new JpqlStatementBuilder<>(Type.UPDATE, entityClass, alias);
    }

    public static <T> JpqlStatementBuilder<T> delete(Class<T> entityClass, String alias) {
        return new JpqlStatementBuilder<>(Type.DELETE, entityClass, alias);
    }

    /** start building a native-ID insert */
    public static <T> JpqlStatementBuilder<T> insert(Class<T> entityClass) {
        return new JpqlStatementBuilder<>(Type.INSERT, entityClass, null);
    }

    /**
     * Mark SELECT DISTINCT
     */
    public JpqlStatementBuilder<T> distinct() {
        requireType(Type.SELECT, "DISTINCT");
        distinct = true;
        return this;
    }

    /**
     * Add custom projection for SELECT
     */
    public JpqlStatementBuilder<T> select(String projection) {
        requireType(Type.SELECT, "SELECT");
        if (selectClause.toString().endsWith("SELECT ")) {
            selectClause.append(projection);
        } else {
            selectClause.append(", ").append(projection);
        }
        return this;
    }

    /**
     * Add a JOIN clause: type can be "JOIN", "LEFT JOIN", "RIGHT JOIN", "FETCH JOIN"
     */
    public JpqlStatementBuilder<T> join(String joinType, String associationPath, String joinAlias) {
        requireType(Type.SELECT, "JOIN");
        joinClauses.add(joinType + " " + associationPath + " " + joinAlias);
        return this;
    }

    /**
     * Add SET clause for UPDATE
     */
    public JpqlStatementBuilder<T> set(String fieldExpr, Object value) {
        requireType(Type.UPDATE, "SET");
        String param = nextParam();
        setClause.add(fieldExpr.replaceFirst("\\?", ":" + param));
        parameters.put(param, value);
        return this;
    }

    /**
     * Add WHERE clause
     */
    public JpqlStatementBuilder<T> where(String expression, Object value) {
        String param = nextParam();
        whereClause.add(expression.replaceFirst("\\?", ":" + param));
        parameters.put(param, value);
        return this;
    }

    /**
     * Add GROUP BY
     */
    public JpqlStatementBuilder<T> groupBy(String expression) {
        groupByUsed = true;
        requireType(Type.SELECT, "GROUP BY");
        groupByClause.add(expression);
        return this;
    }

    /**
     * Add HAVING
     */
    public JpqlStatementBuilder<T> having(String expression, Object value) {
        havingUsed = true;
        requireType(Type.SELECT, "HAVING");
        String param = nextParam();
        havingClause.add(expression.replaceFirst("\\?", ":" + param));
        parameters.put(param, value);
        return this;
    }

    /**
     * Add ORDER BY
     */
    public JpqlStatementBuilder<T> orderBy(String expression, boolean desc) {
        orderByUsed = true;
        requireType(Type.SELECT, "ORDER BY");
        orderByClause.add(expression + (desc ? " DESC" : " ASC"));
        return this;
    }

    /**
     * Set max results (LIMIT)
     */
    public JpqlStatementBuilder<T> limit(int max) {
        requireType(Type.SELECT, "LIMIT");
        this.maxResults = max;
        return this;
    }

    /**
     * Set first result (OFFSET)
     */
    public JpqlStatementBuilder<T> offset(int first) {
        requireType(Type.SELECT, "OFFSET");
        this.firstResult = first;
        return this;
    }

    /**
     * Register a column name for INSERT
     */
    public JpqlStatementBuilder<T> column(String columnName) {
        requireType(Type.INSERT, "COLUMN");
        insertColumns.add(columnName);
        return this;
    }

    /**
     * Register a value for INSERT; will generate a named parameter.
     */
    public JpqlStatementBuilder<T> value(Object val) {
        requireType(Type.INSERT, "VALUE");
        String param = nextParam();
        valuesClause.add(":" + param);
        parameters.put(param, val);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Query build(EntityManager em) {
        StringBuilder jpql = new StringBuilder();
        switch (type) {
            case SELECT:
                jpql.append(selectClause)
                        .append(distinct ? " DISTINCT" : "")
                        .append(selectClause.toString().endsWith("SELECT ") ? alias : "")
                        .append(" ")
                        .append(fromClause)
                        .append(joinClauses)
                        .append(whereClause);
                if (groupByUsed)
                        jpql.append(groupByClause);
                if (havingUsed)
                        jpql.append(havingClause);
                if (orderByUsed)
                    jpql.append(orderByClause);
                TypedQuery<T> tq = em.createQuery(jpql.toString(), entityClass);
                parameters.forEach(tq::setParameter);
                if (firstResult != null) tq.setFirstResult(firstResult);
                if (maxResults != null) tq.setMaxResults(maxResults);
                return tq;

            case UPDATE:
                jpql.append("UPDATE ")
                        .append(entityClass.getSimpleName()).append(" ").append(alias)
                        .append(setClause)
                        .append(whereClause);
                Query uq = em.createQuery(jpql.toString());
                parameters.forEach(uq::setParameter);
                return uq;

            case DELETE:
                jpql.append("DELETE ")
                        .append(alias).append(" FROM ")
                        .append(entityClass.getSimpleName()).append(" ").append(alias)
                        .append(whereClause);
                Query dq = em.createQuery(jpql.toString());
                parameters.forEach(dq::setParameter);
                return dq;

            case INSERT:
                // build a native INSERT INTO table(column1, column2, ...)
                //   VALUES(:p1, :p2, ...)
                String tableName = entityClass.isAnnotationPresent(Table.class)?entityClass.getAnnotation(Table.class).name():entityClass.getSimpleName();
                StringBuilder sql = new StringBuilder()
                    .append("INSERT INTO ").append(tableName).append(' ')
                    .append(insertColumns).append(' ')
                    .append(valuesClause);
                Query iq = em.createNativeQuery(sql.toString());
                parameters.forEach(iq::setParameter);
                return iq;

            default:
                throw new IllegalStateException("Unsupported query type: " + type);
        }
    }

    private void requireType(Type expected, String clause) {
        if (type != expected && type != Type.SELECT) {
            throw new IllegalStateException(clause + " is only valid for " + expected + " queries");
        }
    }

    private String nextParam() {
        return "p" + (++paramCount);
    }
}
