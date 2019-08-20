package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
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

/**
 * 标签业务逻辑累
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签数据
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据标签ID查询标签数据
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 新增标签
     *
     * @param label
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 根据查询条件进行查询
     *
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        Specification<Label> specification = createSpecification(label);
        return labelDao.findAll(specification);
    }

    /**
     * 分页条件查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page pageQuery(Label label, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Label> specification = createSpecification(label);
        return labelDao.findAll(specification, pageable);
    }

    /**
     * 拼接查询条件
     * @param label
     * @return
     */
    private Specification<Label> createSpecification(Label label) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>(); // 用来存放查询条件
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                if (label.getId() != null && !"".equals(label.getId())) {
                    Predicate predicate = cb.equal(root.get("id").as(String.class), label.getId());
                    list.add(predicate);
                }
                if (label.getCount() != null && !"".equals(label.getCount())) {
                    Predicate predicate = cb.equal(root.get("count").as(Long.class), label.getCount());
                    list.add(predicate);
                }
                if (label.getRecommend() != null && !"".equals(label.getRecommend())) {
                    Predicate predicate = cb.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(predicate);
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }

}
