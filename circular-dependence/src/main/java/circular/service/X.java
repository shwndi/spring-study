package circular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author czy
 * @date 2021/7/9
 */
@Component
public class X {
    @Autowired
    private Y y;
    public X(){
        System.out.println("X creator`");
    }

}
