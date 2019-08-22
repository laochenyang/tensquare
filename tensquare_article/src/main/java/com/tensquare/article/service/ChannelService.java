package com.tensquare.article.service;

import com.tensquare.article.dao.ChannelDao;
import com.tensquare.article.pojo.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChannelService {
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Channel> findAll() {
        return channelDao.findAll();
    }

    public Channel findById(String channelId) {
        return channelDao.findById(channelId).get();
    }

    public void add(Channel channel) {
        channel.setId(idWorker.nextId() + "");
        channelDao.save(channel);
    }

    public void update(Channel channel) {
        channelDao.save(channel);
    }

    public void deleteById (String channelId) {
        channelDao.deleteById(channelId);
    }

    public List<Channel> search (Map map) {
        Specification spec = createSpec(map);
        return channelDao.findAll(spec);
    }

    public Page pageQuery(Map map, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification spec = createSpec(map);
        return channelDao.findAll(spec, pageable);
    }

    private Specification<Channel> createSpec(Map searchMap) {
        return new Specification<Channel>() {
            @Override
            public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 频道名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
