package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor
            , EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection
                , entitySQLMetaData.getSelectByIdSql()
                , List.of(id)
                , this::createInstance);
//        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Connection connection) {
        var res = dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectAllSql()
                , List.of()
                , this::createInstances);

        return res.orElse(List.of());
//        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        // список значений которые надо предать в поля
        var clientListValues = getObjectFieldsValues(client
                , entityClassMetaData.getFieldsWithoutId());

        return dbExecutor.executeStatement(connection
                , entitySQLMetaData.getInsertSql(), clientListValues);
    }

    @Override
    public void update(Connection connection, T client) {
        // список значений которые надо предать в поля
        var clientListValues = getObjectFieldsValues(client, entityClassMetaData.getFieldsWithoutId());
        // for Update I added value in ID_field in common List of values
        clientListValues.add(getObjectFieldValue(client
                        , entityClassMetaData.getIdField()));

        dbExecutor.executeStatement(connection
                , entitySQLMetaData.getUpdateSql(), clientListValues);
    }

    //получить список значений из экземпляра класса
    private List<Object> getObjectFieldsValues(Object object, List<Field> fields) {
        return fields
                .stream()
                .map(field -> getObjectFieldValue(object, field)
                )
                .collect(Collectors.toList());
    }
// получить значение поля
    private Object getObjectFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
//    создаем класс на основе полученных данных из БД
    private T createInstance(ResultSet rs) {
        try {
            if (rs.next()) {
                var rsValues = getResultsSetFieldsValues(rs, entityClassMetaData.getAllFields());
                return (T) entityClassMetaData.getConstructor().newInstance(rsValues);
            }
            return null;
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DataTemplateException(e);
        }
    }
    //    создаем список классов на основе результатов из БД
    private List<T> createInstances(ResultSet rs) {
        final List<T> result = new ArrayList<>();
        try {
            while (rs.next()) {
                var rsValues = getResultsSetFieldsValues(rs, entityClassMetaData.getAllFields());
                result.add((T) entityClassMetaData.getConstructor().newInstance(rsValues));
            }
            return result;
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DataTemplateException(e);
        }
    }

    private Object[] getResultsSetFieldsValues(ResultSet rs, Collection<Field> fields) {
        return fields.stream()
                .map(field -> {
                    try {
                        return rs.getObject(field.getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();
    }
}
