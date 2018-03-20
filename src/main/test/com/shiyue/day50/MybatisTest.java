package com.shiyue.day50;

import com.shiyue.day50.dao.Department;
import com.shiyue.day50.dao.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
//        新建流读取配置文件
        InputStream inputStream=null;
//       读取配置文件
        try {
            inputStream = Resources.getResourceAsStream("mybatis.cfg.xml");
//             inputStream = Resources.getResourceAsStream("mybatis.cfg.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void test1(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> objects = sqlSession.selectList("com.shiyue.day50.dto.userMapper.queryUser");
        for (User object : objects) {
            System.out.println(object.getUserName());
        }
        sqlSession.close();
    }
    @Test
    public void test2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user= sqlSession.selectOne("com.shiyue.day50.dto.userMapper.quereyUserById", 1);
        System.out.println(user.getUserName());
    }

    @Test
    public void test3(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUserName("greece");
        user.setEmail("444");
        user.setPassword("444");
        user.setSex(0);
        sqlSession.selectOne("com.shiyue.day50.dto.userMapper.saveUser",user);
//        这里没有提交事务所以需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }
@Test
    public void test4(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Department department = new Department();
        department.setDeptName("工信部");
        sqlSession.insert("com.shiyue.day50.dto.departmentMapper",department);
        sqlSession.commit();
        sqlSession.close();
    }
}
