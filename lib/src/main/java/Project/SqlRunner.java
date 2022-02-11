package Project;

import java.util.List;

public interface SqlRunner {
    Object selectOne(String queryId, Object queryParam, Class resultType);
    List<?> selectMany(String queryId, Object queryParam, Class resultItemType);
    int update(String queryId, Object queryParam);
    int insert(String queryId, Object queryParam);
    int delete(String queryId, Object queryParam);
}
