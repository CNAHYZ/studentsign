package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.SignIn;

import java.util.List;

/**
 * @ClassName SignInMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/12 16:08
 * @Version 1.0
 */
@Repository
public interface SignInMapper {
    /**
     * @param dayNum
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 查询所有用户的签到记录
     * @date 2019/1/20 17:11
     */
    List<SignIn> signRecord(String dayNum);

    /**
     * @param startTime
     * @param endTime
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 查询所有用户指定时间段内的签到记录
     * @date 2019/1/20 17:55
     */
    List<SignIn> signRecordTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @param sNo
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 查询个人用户当天的签到记录
     * @date 2019/1/20 17:39
     */
    List<SignIn> personalSignRecord(String sNo);

    /**
     * @param sNo
     * @param startTime
     * @param endTime
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 查询个人用户指定时间段内及所有时间的签到记录
     * @date 2019/1/20 17:54
     */
    List<SignIn> personalSignRecordTime(@Param("sNo") String sNo, @Param("startTime")
            String startTime, @Param("endTime") String endTime);

    /**
     * @param keyword
     * @return java.util.List<top.flytop.studentsign.pojo.SignIn>
     * @Description TODO 通过关键字查询签到记录
     * @date 2019/1/26 16:42
     */
    List<SignIn> findSignRecordByKeyword(@Param("keyword") String keyword);

    /**
     * @param signIn
     * @return java.lang.Boolean
     * @Description TODO 学生签到方法
     * @date 2019/1/26 16:40
     */
    Boolean addSignRecord(SignIn signIn);


}
