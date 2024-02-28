import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.FrameUserMapper;
import org.example.pojo.FrameUser;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    public static void main(String[] args) throws IOException {
        //读取mybatis-config.xml核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //sqlSessionFactory核心工厂
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(resourceAsStream);
        //openSession中参数为true，代表事务自动提交
        SqlSession sqlSession = build.openSession(true);
        FrameUserMapper mapper = sqlSession.getMapper(FrameUserMapper.class);
        List<FrameUser> allUser = mapper.getAllUser();
        //sqlSession.commit();
    }


    @Test
    public void Test(){
        String s = "hello";
        String s2 = s + "hello";
        System.out.println(s==s2);
    }
}
