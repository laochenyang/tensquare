package com.tensquare.gathering.service;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class GatheringService {
    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;

    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }

    @Cacheable(value = "gathering", key = "#id") // 将查询结果放入到缓存中
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    public void add(Gathering gathering) {
        gathering.setId(idWorker.nextId() + "");
        gatheringDao.save(gathering);
    }

    @CacheEvict(value = "gathering", key = "#gathering.id") // 当活动修改后,需要将缓存的数据删除
    public void update (Gathering gathering) {
        gatheringDao.save(gathering);
    }

    @CacheEvict(value = "gathering", key = "#id") // 当活动删除后删除当前缓存的活动信息
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }

    public List<Gathering> search(Map map) {
       Specification<Gathering> spec = createSpec(map);
       return gatheringDao.findAll(spec);
    }

    public Page pageQuery(Map map, int page, int size) {
        Specification<Gathering> spec = createSpec(map);
        Pageable pageable = PageRequest.of(page - 1, size);
        return gatheringDao.findAll(spec, pageable);
    }

    private Specification<Gathering> createSpec(Map searchMap) {
        return new Specification<Gathering>() {
            @Override
            public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 编号
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 活动名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 大会简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 详细说明
                if (searchMap.get("detail")!=null && !"".equals(searchMap.get("detail"))) {
                    predicateList.add(cb.like(root.get("detail").as(String.class), "%"+(String)searchMap.get("detail")+"%"));
                }
                // 主办方
                if (searchMap.get("sponsor")!=null && !"".equals(searchMap.get("sponsor"))) {
                    predicateList.add(cb.like(root.get("sponsor").as(String.class), "%"+(String)searchMap.get("sponsor")+"%"));
                }
                // 活动图片
                if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
                }
                // 举办地点
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                    predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 是否可见
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 城市
                if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
                    predicateList.add(cb.like(root.get("city").as(String.class), "%"+(String)searchMap.get("city")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
