package com.nwu.recommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.client.BaseClient;
import com.nwu.recommend.client.CourseClient;
import com.nwu.recommend.client.RecordClient;
import com.nwu.recommend.entity.Recommend;
import com.nwu.recommend.entity.SimilarityItem;
import com.nwu.recommend.entity.SimilarityUser;
import com.nwu.recommend.mapper.RecommendMapper;
import com.nwu.recommend.service.RecommendService;
import com.nwu.recommend.service.SimilarityItemService;
import com.nwu.recommend.service.SimilarityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-12-23
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private RecordClient recordClient;
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SimilarityUserService similarityUserService;
    @Autowired
    private SimilarityItemService similarityItemService;

    @Override
    public CommonResult getListByUserId(Long user_id) {
        QueryWrapper<Recommend> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        Recommend recommend = baseMapper.selectOne(wrapper);
        return courseClient.getCourseListByIds(recommend.getUserRecommend(),recommend.getItemRecommend());
    }

    @Override
    public void getSimilarity(){
        CommonResult result = recordClient.getAllUserRecord();
        CommonResult re = baseClient.getUserDepartment();
        getUserSimilarity((Map<Long, Set<Long>>) result.getData().get("userInfo"),
                (Map<Long, Integer>) re.getData().get("deptInfo"));
        getItemSimilarity((Map<Long, Set<Long>>) result.getData().get("itemInfo"));
    }

    @Override
    public void getUserSimilarity(Map<Long, Set<Long>> userInfo,Map<Long, Integer> departmentInfo) {
        List<Long> list= new ArrayList<>(userInfo.keySet());
        for (int i=0;i<list.size();i++){
            for (int j=i+1;j<list.size();j++){
                Set<Long> set1 = new HashSet<>(userInfo.get(list.get(i)));
                Set<Long> set2 = new HashSet<>(userInfo.get(list.get(j)));
                Set<Long> set3 = new HashSet<>();
                set3.addAll(set1);set3.addAll(set2);
                set1.retainAll(set2);
                SimilarityUser user = new SimilarityUser();
                user.setUserOne(list.get(i));
                user.setUserTwo(list.get(j));
                BigDecimal value = BigDecimal.valueOf((double)set1.size()/set3.size());
                if (departmentInfo.get(list.get(i))==departmentInfo.get(list.get(j))){
                    value = value.multiply(new BigDecimal("1.2"));
                }
                user.setValue(value.setScale(2, RoundingMode.HALF_UP));
                similarityUserService.save(user);
            }
        }
    }

    @Override
    public void getItemSimilarity(Map<Long, Set<Long>> itemInfo) {
        List<Long> list= new ArrayList<>(itemInfo.keySet());
        for (int i=0;i<list.size();i++){
            for (int j=i+1;j<list.size();j++){
                Set<Long> set1 = new HashSet<>(itemInfo.get(list.get(i)));
                Set<Long> set2 = new HashSet<>(itemInfo.get(list.get(j)));
                Set<Long> set3 = new HashSet<>();
                set3.addAll(set1);set3.addAll(set2);
                set1.retainAll(set2);
                SimilarityItem item = new SimilarityItem();
                item.setItemOne(list.get(i));
                item.setItemTwo(list.get(j));
                BigDecimal value = BigDecimal.valueOf((double)set1.size()/set3.size());
                item.setValue(value.setScale(2, RoundingMode.HALF_UP));
                similarityItemService.save(item);
            }
        }
    }

    @Override
    public void getUserResult() {
        CommonResult result = recordClient.getAllUserRecord();
        Map<Long, Set<Long>> userInfo=(Map<Long, Set<Long>>) result.getData().get("userInfo");
        Map<Long, Set<Long>> itemInfo=(Map<Long, Set<Long>>) result.getData().get("itemInfo");
        for (Long user_id : userInfo.keySet()) {
            //找出相似度最高的k个用户
            QueryWrapper<SimilarityUser> wrapper=new QueryWrapper<>();
            wrapper.eq("user_one", user_id).or().eq("user_two", user_id);
            List<SimilarityUser> list = similarityUserService.list(wrapper);
            list.sort((o1, o2) -> {
                BigDecimal decimal = o1.getValue().subtract(o2.getValue());
                if (decimal.doubleValue() > 0) return 1;
                if (decimal.doubleValue() == 0) return 0;
                return -1;
            });
            StringBuilder sameUser = new StringBuilder();
            for (int i=0;i<list.size() && i<5;i++){
                if (list.get(i).getUserOne()==user_id){
                    sameUser.append(list.get(i).getUserTwo());
                } else {
                    sameUser.append(list.get(i).getUserOne());
                }
                sameUser.append(":");
            }
            //找出这个k个用户选过的课程
            CommonResult res = courseClient.getCourseIdsByUids(sameUser.substring(0,sameUser.length()-1));
            List<Long> ids = (List<Long>) res.getData().get("ids");
            StringBuilder stringBuilder=new StringBuilder();

            for (int i=0;i<ids.size() && i<4;i++){
                stringBuilder.append(ids.get(i));
                stringBuilder.append(":");
            }
            QueryWrapper<Recommend> wrapper1=new QueryWrapper<>();
            wrapper1.eq("user_id",user_id);
            Recommend recommend = baseMapper.selectOne(wrapper1);
            if (recommend!=null){
                recommend.setUserRecommend(stringBuilder.substring(0,stringBuilder.length()-1));
                baseMapper.updateById(recommend);
            }else {
                recommend=new Recommend();
                recommend.setUserRecommend(stringBuilder.substring(0,stringBuilder.length()-1));
                baseMapper.insert(recommend);
            }
        }
    }

    @Override
    public void getItemResult() {
        CommonResult result = recordClient.getAllUserRecord();
        Map<Long, Set<Long>> userInfo=(Map<Long, Set<Long>>) result.getData().get("userInfo");
        Map<Long, Set<Long>> itemInfo=(Map<Long, Set<Long>>) result.getData().get("itemInfo");
        for (Long user_id : userInfo.keySet()) {
            QueryWrapper<SimilarityUser> wrapper=new QueryWrapper<>();
            wrapper.eq("user_one", user_id).or().eq("user_two", user_id);
            List<SimilarityUser> list = similarityUserService.list(wrapper);
            list.sort((o1, o2) -> {
                BigDecimal decimal = o1.getValue().subtract(o2.getValue());
                if (decimal.doubleValue() > 0) return 1;
                if (decimal.doubleValue() == 0) return 0;
                return -1;
            });
            StringBuilder sameUser = new StringBuilder();
            for (int i=0;i<list.size() && i<5;i++){
                if (list.get(i).getUserOne()==user_id){
                    sameUser.append(list.get(i).getUserTwo());
                } else {
                    sameUser.append(list.get(i).getUserOne());
                }
                sameUser.append(":");
            }
            CommonResult res = courseClient.getCourseIdsByUids(sameUser.substring(0,sameUser.length()-1));
            List<Long> ids = (List<Long>) res.getData().get("ids");
            StringBuilder stringBuilder=new StringBuilder();

            for (int i=ids.size()-1;i>=0 && i>ids.size()-5;i++){
                stringBuilder.append(ids.get(i));
                stringBuilder.append(":");
            }
            QueryWrapper<Recommend> wrapper1=new QueryWrapper<>();
            wrapper1.eq("user_id",user_id);
            Recommend recommend = baseMapper.selectOne(wrapper1);
            recommend.setItemRecommend(stringBuilder.substring(0,stringBuilder.length()-1));
            baseMapper.updateById(recommend);
        }
    }
}
