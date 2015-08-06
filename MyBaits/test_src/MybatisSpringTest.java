import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yihaomen.inter.IUserOperation;
import com.yihaomen.mybatis.model.User;


public class MybatisSpringTest {
	private static ApplicationContext ctx;
	static{
		ctx = new ClassPathXmlApplicationContext("config/applicationContext.xml");
	}
	public static void main(String[] args) {
		IUserOperation mapper = (IUserOperation) ctx.getBean("userMapper");
		User u = mapper.selectUserByID(1);
		System.out.println(u.getUserName());
	}

}
