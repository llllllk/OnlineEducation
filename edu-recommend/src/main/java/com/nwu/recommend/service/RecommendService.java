package com.nwu.recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.entity.Recommend;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-12-23
 */
public interface RecommendService extends IService<Recommend> {
    CommonResult getListByUserId(Long user_id);

    void getSimilarity();

    void getUserSimilarity(Map<Long, Set<Long>> userInfo,Map<Long, Integer> departmentInfo);

    void getItemSimilarity(Map<Long, Set<Long>> itemInfo);

    void getUserResult();

    void getItemResult();
}
