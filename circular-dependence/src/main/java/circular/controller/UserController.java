package circular.controller;


import circular.pojo.User;
import circular.service.IUserService;
import myspring.ioc.annocation.MyAutowired;
import myspring.ioc.annocation.MyController;
import myspring.ioc.annocation.MyRequestMapping;
import myspring.ioc.annocation.RequestMethod;
import myspring.mvc.bean.Data;
import myspring.mvc.bean.Param;
import myspring.mvc.bean.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Myauthor litianxiang
 */
@MyController
public class UserController {
    @MyAutowired
    public IUserService userService;

    /**
     * 用户列表
     *
     * @Myreturn
     */
    @MyRequestMapping(value = "/userList", method = RequestMethod.GET)
    public View getUserList() {
        List<User> userList = userService.getAllUser();
        return new View("index.jsp").addModel("userList", userList);
    }

    /**
     * 用户详情
     *
     * @Myparam param
     * @Myreturn
     */
    @MyRequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public Data getUserInfo(Param param) {
        String id = (String) param.getParamMap().get("id");
        User user = userService.GetUserInfoById(Integer.parseInt(id));

        return new Data(user);
    }

    @MyRequestMapping(value = "/userEdit", method = RequestMethod.GET)
    public Data editUser(Param param) {
        String id = (String) param.getParamMap().get("id");
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("age", 911);
        userService.updateUser(Integer.parseInt(id), fieldMap);

        return new Data("Success.");
    }

}
