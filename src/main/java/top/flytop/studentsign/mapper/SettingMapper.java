package top.flytop.studentsign.mapper;

import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.OpenTime;

/**
 * @ClassName SettingMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/3/24 11:00
 * @Version 1.0
 */
@Repository
public interface SettingMapper {
    int saveOpenTime(OpenTime openTime);

    OpenTime getOpenTime();
}
