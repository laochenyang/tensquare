package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
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

@Service
public class RecruitService {
    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有招聘信息
     * @return
     */
    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }

    /**
     * 根据id查询招聘信息
     * @param id
     * @return
     */
    public Recruit findById(String id) {
        return recruitDao.findById(id).get();
    }

    /**
     * 根据id删除招聘信息
     * @param id
     */
    public void deleteById (String id) {
        recruitDao.deleteById(id);
    }

    /**
     * 保存招聘信息
     * @param recruit
     */
    public void save(Recruit recruit) {
        recruit.setId(idWorker.nextId() + "");
        recruitDao.save(recruit);
    }

    /**
     * 修改招聘信息
     * @param recruit
     */
    public void update(Recruit recruit) {
        recruitDao.save(recruit);
    }

    /**
     * 根据条件进行查询
     * @param recruit
     * @return
     */
    public List<Recruit> search(Recruit recruit) {
        Specification<Recruit> specification = createSpecification(recruit);
        return recruitDao.findAll(specification);
    }


    /**
     * 分页条件查询
     * @param recruit
     * @param page
     * @param size
     * @return
     */
    public Page pageQuery(Recruit recruit, int page, int size) {
        Specification specification = createSpecification(recruit);
        Pageable pageable = PageRequest.of(page - 1, size);
        return recruitDao.findAll(specification, pageable);
    }

    /**
     * 最新的4条推荐职位
     * @param state
     * @return
     */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state) {
        return recruitDao.findTop4ByStateOrderByCreatetimeDesc(state);
    }

    /**
     * 查询最新的12条职位
     * @param state
     * @return
     */
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state) {
        return recruitDao.findTop12ByStateNotOrderByCreatetimeDesc(state);
    }
    /**
     * 拼接查询条件
     * @param recruit
     * @return
     */
    private Specification<Recruit> createSpecification(Recruit recruit) {
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (recruit.getId()!=null && !"".equals(recruit.getId())) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)recruit.getId()+"%"));
                }
                // 职位名称
                if (recruit.getJobname()!=null && !"".equals(recruit.getJobname())) {
                    predicateList.add(cb.like(root.get("jobname").as(String.class), "%"+(String)recruit.getJobname()+"%"));
                }
                // 薪资范围
                if (recruit.getSalary()!=null && !"".equals(recruit.getSalary())) {
                    predicateList.add(cb.like(root.get("salary").as(String.class), "%"+(String)recruit.getSalary()+"%"));
                }
                // 经验要求
                if (recruit.getCondition()!=null && !"".equals(recruit.getCondition())) {
                    predicateList.add(cb.like(root.get("condition").as(String.class), "%"+(String)recruit.getCondition()+"%"));
                }
                // 学历要求
                if (recruit.getEducation()!=null && !"".equals(recruit.getEducation())) {
                    predicateList.add(cb.like(root.get("education").as(String.class), "%"+(String)recruit.getEducation()+"%"));
                }
                // 任职方式
                if (recruit.getType()!=null && !"".equals(recruit.getType())) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)recruit.getType()+"%"));
                }
                // 办公地址
                if (recruit.getAddress()!=null && !"".equals(recruit.getAddress())) {
                    predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)recruit.getAddress()+"%"));
                }
                // 企业ID
                if (recruit.getEid()!=null && !"".equals(recruit.getEid())) {
                    predicateList.add(cb.like(root.get("eid").as(String.class), "%"+(String)recruit.getEid()+"%"));
                }
                // 状态
                if (recruit.getState()!=null && !"".equals(recruit.getState())) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)recruit.getState()+"%"));
                }
                // 网址
                if (recruit.getUrl()!=null && !"".equals(recruit.getUrl())) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)recruit.getUrl()+"%"));
                }
                // 标签
                if (recruit.getLabel()!=null && !"".equals(recruit.getLabel())) {
                    predicateList.add(cb.like(root.get("label").as(String.class), "%"+(String)recruit.getLabel()+"%"));
                }
                // 职位描述
                if (recruit.getContent1()!=null && !"".equals(recruit.getContent1())) {
                    predicateList.add(cb.like(root.get("content1").as(String.class), "%"+(String)recruit.getContent1()+"%"));
                }
                // 职位要求
                if (recruit.getContent2()!=null && !"".equals(recruit.getContent2())) {
                    predicateList.add(cb.like(root.get("content2").as(String.class), "%"+(String)recruit.getContent2()+"%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
