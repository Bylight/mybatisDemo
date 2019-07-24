package mybatis;

import entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis demo
 *
 * @author 何家伟
 */
public class MyBatisDemo {
    public static void main(String[] args) throws IOException {
        /**
         * 第一步：要先获取 SqlSessionFactory， MyBatis 的每一个操作都以 SqlSessionFactory 为核心
         *
         * SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。
         * 而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。
         */

        // 比较推荐的是我们代码中使用的第一种方法；即使用 xml 来构建，如下文使用的代码
        // 设置 mybatis 的 xml 配置文件路径
        String xmlConfigResource = "conf.xml";
        // 使用MyBatis 的 Resources 的工具类，从指定 classpath 或其他位置加载资源文件更加容易
        // 在这里使用 Resources 从指定 classpath 加载了 xml 配置文件
        InputStream resourceAsStream = MyBatisDemo.class.getClassLoader().getResourceAsStream(xmlConfigResource);
        // 构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(xmlConfigResource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "userMapper.getUser";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        User user = session.selectOne(statement, 2);
        System.out.println(user);
        resourceAsStream.close();
    }
}

