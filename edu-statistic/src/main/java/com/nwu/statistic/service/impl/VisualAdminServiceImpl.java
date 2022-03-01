package com.nwu.statistic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.statistic.client.CourseClient;
import com.nwu.statistic.client.ExtraClient;
import com.nwu.statistic.entity.VisualAdmin;
import com.nwu.statistic.mapper.VisualAdminMapper;
import com.nwu.statistic.service.VisualAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
@Service
public class VisualAdminServiceImpl extends ServiceImpl<VisualAdminMapper, VisualAdmin> implements VisualAdminService {

    @Autowired
    private ExtraClient extraClient;
    @Autowired
    private CourseClient courseClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static String[] types=new String[]{"登陆人数",
            "注册人数","新增课程数","新增笔记数","新增问题数","讨论热度","异常数"};
    @Override
    public Map<String, Object> showFigureOfDay(String day) {
        QueryWrapper<VisualAdmin> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculate",day);
        VisualAdmin visualAdmin = baseMapper.selectOne(dayQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<String> typeList = Arrays.asList(types);
        List<Object> dataList = Arrays.asList(new Integer[]{
                visualAdmin.getLoginNum(),
                visualAdmin.getRegisterNum(),
                visualAdmin.getCourseNum(),
                visualAdmin.getNoteNum(),
                visualAdmin.getQuestionNum(),
                visualAdmin.getAnswerNum(),
                visualAdmin.getErrorNum()
        });
        map.put("typeList",typeList);
        map.put("dataList",dataList);
        return map;
    }

    @Override
    public Map<String, Object> showFigureOfMonth(String month) {
        QueryWrapper<VisualAdmin> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.likeRight("date_calculate",  month);
        List<VisualAdmin> visualAdmins = baseMapper.selectList(dayQueryWrapper);
        Map<String,Object> map=new HashMap<>();
        int registerNum = 0;
        int loginNum = 0;
        int questionNum = 0;
        int answerNum = 0;
        int courseNum = 0;
        int noteNum = 0;
        int errorNum = 0;
        for (VisualAdmin visualAdmin : visualAdmins) {
            registerNum += visualAdmin.getRegisterNum();
            loginNum+=visualAdmin.getLoginNum();
            questionNum+=visualAdmin.getQuestionNum();
            answerNum+=visualAdmin.getAnswerNum();
            courseNum+=visualAdmin.getCourseNum();
            noteNum+=visualAdmin.getNoteNum();
            errorNum+=visualAdmin.getErrorNum();
        }
        List<String> typeList = Arrays.asList(types);
        List<Object> dataList = Arrays.asList(new Integer[]{
                loginNum,registerNum,courseNum,noteNum,questionNum,answerNum,errorNum
        });
        map.put("typeList",typeList);
        map.put("dataList",dataList);
        return map;
    }

    @Override
    public Map<String, Object> showTrendOfDay(String begin, String end, String type) {
        QueryWrapper<VisualAdmin> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.between("date_calculate", begin, end);
        List<VisualAdmin> dayList = baseMapper.selectList(dayQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        for (VisualAdmin visualAdmin : dayList) {
            dateList.add(visualAdmin.getDateCalculate());
            switch (type) {
                case "register_num":
                    dataList.add(visualAdmin.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(visualAdmin.getLoginNum());
                    break;
                case "question_num":
                    dataList.add(visualAdmin.getQuestionNum());
                    break;
                case "answer_num":
                    dataList.add(visualAdmin.getAnswerNum());
                    break;
                case "course_num":
                    dataList.add(visualAdmin.getCourseNum());
                    break;
                case "note_num":
                    dataList.add(visualAdmin.getNoteNum());
                    break;
                case "error_num":
                    dataList.add(visualAdmin.getErrorNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> showTrendOfMonth(String begin, String end, String type) {
        end = end.substring(0,end.length()-2)+ (Integer.parseInt(end.substring(end.length() - 2)) + 1);
        QueryWrapper<VisualAdmin> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.between("date_calculate", begin, end);
        List<VisualAdmin> dayList = baseMapper.selectList(dayQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        Map<Integer,Integer> monthMap=new HashMap<>();
        for (VisualAdmin visualAdmin : dayList) {
            int month=Integer.parseInt(visualAdmin.getDateCalculate().split("-")[1]);
            switch (type) {
                case "register_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getRegisterNum());
                    break;
                case "login_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getLoginNum());
                    break;
                case "question_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getQuestionNum());
                    break;
                case "answer_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getAnswerNum());
                    break;
                case "course_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getCourseNum());
                    break;
                case "note_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getNoteNum());
                    break;
                case "error_num":
                    monthMap.put(month,monthMap.getOrDefault(month,0)+visualAdmin.getErrorNum());
                    break;
                default:
                    break;
            }
        }
        List<Integer> temp = new ArrayList<>(monthMap.keySet());
        Collections.sort(temp);
        for (Integer integer : temp) {
            dateList.add(integer+"月");
            dataList.add(monthMap.get(integer));
        }
        return map;
    }

    @Override
    public void createData(String data) {
        String registerNum = redisTemplate.opsForValue().get(data + ":registerNum:");
        String loginNum = redisTemplate.opsForValue().get(data + ":loginNum:");
        String errorNum = redisTemplate.opsForValue().get(data + ":errorNum:");
        int questionNum = extraClient.getQuestionNumByDate(data);
        int answerNum = extraClient.getAnswerNumByDate(data);
        int courseNum = courseClient.getCourseNumByDate(data);
        int noteNum = extraClient.getArticleNumByDate(data);
        VisualAdmin visualAdmin = new VisualAdmin(
                Integer.parseInt(registerNum),Integer.parseInt(loginNum),
                questionNum,answerNum,courseNum,noteNum,Integer.parseInt(errorNum),data);
        baseMapper.insert(visualAdmin);
    }
}
