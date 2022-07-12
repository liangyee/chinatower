package com.vastio.mapper;

import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:45 2018/7/3
 */

@Repository
public interface StandBookMapper {
    List<StandBook> findStandBook(@Param("param") RequestCondition param);

    List<StandBook> findToPayStandBook();

    List<StandBook> findOverTimePayStandBook();

    List<StandBook> findRenewContractStandBook();

    void deleteStandBook();

    void addStandBook(@Param("standBook") StandBook standBook);

    void addAllStandBooks(List<StandBook> standBooks);

    void deleteStandBookById(@Param("id") int id);

    void updateStandBook(@Param("standBook") StandBook standBook);

    List<Map> findOrderByYear();

    long findOverTime();

    long findToPay();

    long findRenew();
}
