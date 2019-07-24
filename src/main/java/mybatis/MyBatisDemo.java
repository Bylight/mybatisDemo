package mybatis;

import entity.User;
import mapper.UserMapper;
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
    public static void main(String[] args) {
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
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        /**
         * 有了 session 工厂后就可以创建 session 实例，然后使用 session 实例来使用映射器(map)执行 sql 语句了
         * 同样有两种方式，但我们强烈推荐后面的方法
         * 使用正确描述每个语句的参数和返回值的接口（比如 BlogMapper.class）
         * 不仅可以执行更清晰和类型安全的代码，而且还不用担心易错的字符串字面值以及强制类型转换。
         */
        // 不推荐的方法
        /*
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 映射sql的标识字符串
            String statement = "UserMapper.queryUserById";
            // 执行查询返回一个唯一user对象的sql
            User user = session.selectOne(statement, 2);
            System.out.println(user);
        }
        */

        // 第二种方法有很多优势
        // 首先它不依赖于字符串字面值，会更安全一点；
        // 其次，如果你的 IDE 有代码补全功能，那么代码补全可以帮你快速选择已映射的 SQL 语句。
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.queryUserById(2);
            System.out.println(user);
        }

    }
}

