package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Friend;
import pojo.NoFriend;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userId, String friendid) {
        // 向判断userid到friendid是否存在数据，有就是重复添加好友了，如果则直接添加，返回0
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendid);
        if (friend != null) {
            return 0;
        }
        // 直接添加好友，让好友表中userid到friendid方向的type为0
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        // 判断friendid到userid是否存在数据，如果有，把双方的状态都改为1
        Friend friend2 = friendDao.findByUseridAndFriendid(friendid, userId);
        if (null != friend2) {
            // 双方互相喜欢，把双方的islike都改为1
            friendDao.updateIsLike("1", userId, friendid);
            friendDao.updateIsLike("1", friendid, userId);
        }
        return 1;
    }

    /**
     * 添加非好友
     * @param userId
     * @param friendid
     * @return
     */
    public int addNoFriend(String userId, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendid);
        if (null != noFriend) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 删除好友
     * @param userId
     * @param friendid
     */
    public void deleteFriend(String userId, String friendid) {
        // 删除好友表中userid到friendid这条数据
        friendDao.deleteFriend(userId, friendid);
        // 更新friendid到userid的islike为0
        friendDao.updateIsLike("0", friendid, userId);
        // 非好友表中添加userid到friendid的数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
