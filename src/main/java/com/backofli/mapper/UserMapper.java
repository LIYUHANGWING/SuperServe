package com.backofli.mapper;

import com.backofli.pojo.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {


    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getUserById(String userId);

    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert("INSERT INTO users(user_id, username, password, email) VALUES(#{userId}, #{username}, #{password}, #{email})")
    void insertUser(User user);

    @Update("UPDATE users SET username=#{username}, password=#{password}, email=#{email} WHERE user_id=#{userId}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE user_id=#{userId}")
    void deleteUser(String userId);

    @Insert("INSERT INTO friends(user_id, friend_id) VALUES(#{userId}, #{friendId})")
    void addFriend(@Param("userId") String userId, @Param("friendId") String friendId);

    @Select("SELECT u.* FROM users u JOIN friends f ON u.user_id = f.friend_id WHERE f.user_id = #{userId}")
    List<User> getFriends(String userId);
}
