package circular.service;

import circular.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author litianxiang
 */
public interface IUserService {
    List<User> getAllUser();

    User GetUserInfoById(Integer id);

    boolean updateUser(int id, Map<String, Object> fieldMap);
}
