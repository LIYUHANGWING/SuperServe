package com.backofli.mapper;

import com.backofli.pojo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

    @Insert("INSERT INTO messages (user_id, content, timestamp) VALUES (#{userId}, #{content}, #{timestamp})")
    void insertMessage(Message message);
}
