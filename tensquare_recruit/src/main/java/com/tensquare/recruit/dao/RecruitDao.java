package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitDao extends JpaRepository<Recruit,String>, JpaSpecificationExecutor<Recruit>{
    // 推荐职位列表
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    // 查询最新的12条职位
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
