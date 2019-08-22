package com.tensquare.article.service;

import com.tensquare.article.dao.ColumnDao;
import com.tensquare.article.pojo.Column;
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
public class ColumnService {
    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private IdWorker idWorker;

    public List<Column> findAll() {
        return columnDao.findAll();
    }

    public Column findById(String columnId) {
        return columnDao.findById(columnId).get();
    }

    public void add (Column column) {
        column.setId(idWorker.nextId() + "");
        columnDao.save(column);
    }

    public void update (Column column) {
        columnDao.save(column);
    }

    public void deleteById (String columnId) {
        columnDao.deleteById(columnId);
    }

    public List<Column> search (Map map) {
        Specification<Column> spec = createSpec(map);
        return columnDao.findAll(spec);
    }

    public Page pageQuery(Map map, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Column> spec = createSpec(map);
        return columnDao.findAll(spec, pageable);
    }

    private Specification<Column> createSpec(Map searchMap) {
        return new Specification<Column>() {
            @Override
            public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 专栏简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
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
