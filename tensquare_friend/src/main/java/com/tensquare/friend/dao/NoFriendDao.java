package com.tensquare.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pojo.NoFriend;

public interface NoFriendDao extends JpaRepository<NoFriend, String>{

    public NoFriend findByUseridAndFriendid(String userid, String friendid);

}
