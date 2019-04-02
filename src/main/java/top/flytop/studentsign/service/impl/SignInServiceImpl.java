package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.SignInMapper;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.SignIn;
import top.flytop.studentsign.service.SignInService;
import top.flytop.studentsign.utils.FaceUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SignInServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 16:06
 * @Version 1.0
 */
@Service
public class SignInServiceImpl implements SignInService {
    private SignInMapper signInMapper;
    private FaceUtil faceUtil;
    private UserMapper userMapper;

    @Autowired
    private void signInMapper(SignInMapper signInMapper) {
        this.signInMapper = signInMapper;
    }

    @Autowired
    private void faceUtil(FaceUtil faceUtil) {
        this.faceUtil = faceUtil;
    }

    @Autowired
    private void userMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * @param dayNum
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/12 16:17
     */
    @Override
    public List<SignIn> getSignRecord(String dayNum) {
        List<SignIn> signList = signInMapper.signRecord(dayNum);
        System.out.println(signList);
        return signList;
    }

    /**
     * @param sNo
     * @param startTime
     * @param endTime
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 查询某个人的签到记录
     * @Date 2019/2/17 21:28
     */
    @Override
    public List<SignIn> getPersonalSignRecord(String sNo, String startTime, String endTime) {
        return signInMapper.getSignBySNo(sNo, startTime, endTime);
    }

    /**
     * @param startTime
     * @param endTime
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据自定义时间筛选签到记录
     * @date 2019/1/26 16:44
     */
    @Override
    public List<SignIn> getSignRecordFilter(String startTime, String endTime) {
        List<SignIn> signList = signInMapper.signRecordTime(startTime, endTime);
        System.out.println(signList);
        return signList;
    }

    /**
     * @param keyword
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 根据关键字查询签到记录
     * @date 2019/1/26 16:47
     */
    public List<SignIn> getSignRecordByKeyword(String keyword) {
        List<SignIn> signList = signInMapper.findSignRecordByKeyword(keyword);
        System.out.println(signList);
        return signList;
    }

    /**
     * @param dayNum
     * @return java.util.List<java.util.Map>
     * @Description TODO 获取签到数
     * @Date 2019/3/19 15:17
     */
    @Override
    public List<Map> getSignCount(int dayNum) {
        int totalCount = userMapper.getTotalCount();
        List<Map> signList = signInMapper.getSignCount(dayNum);
        List<Map> newList = new ArrayList<>();

        for (Map item : signList) {
            item.put("notCount", totalCount - (long) item.get("count"));
            newList.add(item);
        }
        System.out.println(newList);
        return newList;
    }

    /**
     * @param faceImage
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @date 2019/1/20 16:36
     */
    @Override
    public BaseResult saveStudentSign(String faceImage) {
        BaseResult<Map> searResult = faceUtil.faceSearch(faceImage, null);
        if (!searResult.isSuccess()) {
            //异常，直接返回至Controller
            return searResult;
        }
        Map data = searResult.getData();
        if (data.get("score") != null && (Double) data.get("score") >= 75) {
            String sno = (String) data.get("sNo");
            if (!signInMapper.personalSignRecord(sno).isEmpty()) {
                return BaseResult.fail(1, sno + " 今天已签到，请勿重复签到");
            }
            // 记录签到数据
            SignIn signIn = new SignIn();
            signIn.setsNo(sno);
            signIn.setSignStatus(0);
            signIn.setSignTime(new Timestamp(System.currentTimeMillis()));
            try {
                if (signInMapper.addSignRecord(signIn))
                    return new BaseResult<>(true, sno + " 签到成功！");
            } catch (Exception e) {
                e.printStackTrace();
                return new BaseResult(false, 1, "系统错误，请重试或联系管理员！");
            }
        }
        return new BaseResult(false, 1, "未匹配到您的信息，请联系管理员!");
    }

}
