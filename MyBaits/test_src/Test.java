import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yihaomen.inter.IUserOperation;
import com.yihaomen.mybatis.model.Article;
import com.yihaomen.mybatis.model.User;

public class Test {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	/**
	 * 接口方式查询用户集合
	 * */
	public void getUserList(String userName) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation userOperation = session
					.getMapper(IUserOperation.class);
			List<User> users = userOperation.selectUsers(userName);
			for (User user : users) {
				System.out.println(user.getId() + ":" + user.getUserName()
						+ ":" + user.getUserAddress());
			}

		} finally {
			session.close();
		}
	}

	/**
	 * 两种方式查询用户
	 **/
	public void getUserById(int id) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			/**
			 * User user = (User) session.selectOne(
			 * "com.yihaomen.mybatis.models.UserMapper.selectUserByID", 1);
			 **/
			IUserOperation iUserOperation = session
					.getMapper(IUserOperation.class);
			User user = iUserOperation.selectUserByID(1);
			System.out.println(user.getUserAddress());
			System.out.println(user.getUserName());
		} finally {
			session.close();
		}
	}
	/**
	 * 在增加，更改，删除的时候要调用session.commit()，这样才会真正对数据库进行操作，否则是没有提交的。 
	 * */
	public void addUser() {
		User user = new User();
		user.setUserAddress("USA");
		user.setUserAge(150);
		user.setUserName("tom");
		SqlSession session = sqlSessionFactory.openSession();
		try {

			IUserOperation iUserOperation = session
					.getMapper(IUserOperation.class);
			iUserOperation.addUser(user);
			session.commit();
			System.out.println("添加用户成功");
		} finally {
			session.close();
		}
	}

	public void updateUser() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation iUserOperation = session
					.getMapper(IUserOperation.class);
			User u = iUserOperation.selectUserByID(2);
			u.setUserAge(1);
			iUserOperation.updateUser(u);
			session.commit();
			System.out.println("更新成功");
		} finally {
			session.close();
		}

	}

	public void deleteUser() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation iUserOperation = session
					.getMapper(IUserOperation.class);
			iUserOperation.deleteUser(2);

			session.commit(); // 删除数据一定要commit
			System.out.println("删除成功");
		} finally {
			session.close();
		}
	}

	public List<Article> getUserArticle(int id) {
		List list = new ArrayList<Article>();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation iUserOperation = session
					.getMapper(IUserOperation.class);
			list = iUserOperation.getUserArticles(id);
		} finally {
			session.close();
		}
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.addUser();
		// test.getUserList("%");*/
		// test.updateUser();
		// test.deleteUser();
		int size = test.getUserArticle(1).size();
		System.out.println(size);

	}
}
