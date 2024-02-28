package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.FrameUser;

import java.util.List;

/**
 * 1.映射文件的namespace需要和接口全类名保持一致
 * 2.映射文件中的id需要和接口中方法名称保持一致
 */
@Mapper
public interface FrameUserMapper {
    List<FrameUser> getAllUser();
}
