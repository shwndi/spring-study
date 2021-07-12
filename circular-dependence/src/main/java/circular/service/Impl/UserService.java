package circular.service.Impl;

import circular.pojo.User;
import circular.service.IUserService;
import myspring.ioc.annocation.MyService;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author litianxiang
 */
@MyService
public class UserService implements IUserService {
    /**
     * 获取所有用户
     */
    @Override
    public List<User> getAllUser() {
        String sql = "SELECT * FROM user";
//        return DatabaseHelper.queryEntityList(User.class, sql);
        return Arrays.asList( new User(3,"萧炎",20)
                , new User(3,"萧炎",20));
    }

    /**
     * 根据id获取用户信息
     */
    @Override
    public User GetUserInfoById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
//        return DatabaseHelper.queryEntity(User.class, sql, id);
        return new User(3,"萧炎",20);
    }

    /**
     * 修改用户信息
     */
    //@Transactional
    @Override
    public boolean updateUser(int id, Map<String, Object> fieldMap) {
        //return DatabaseHelper.updateEntity(User.class, id, fieldMap);
        return true;
    }
}
