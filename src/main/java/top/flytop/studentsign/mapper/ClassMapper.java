package top.flytop.studentsign.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.flytop.studentsign.pojo.SClass;

import java.util.List;

/**
 * @ClassName ClassMapper
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 11/04/2019 16:18
 * @Version 1.0
 */
@Repository
public interface ClassMapper {
    List<SClass> getClassList();

    int addClass(SClass sClass);

    int removeClassByCNo(@Param("cNo") String CNo);

    SClass getClassByCNo(String cNo);

    int updateClassByCNo(SClass sClass);
}
