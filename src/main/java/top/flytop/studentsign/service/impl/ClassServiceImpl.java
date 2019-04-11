package top.flytop.studentsign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.flytop.studentsign.dto.BaseResult;
import top.flytop.studentsign.mapper.ClassMapper;
import top.flytop.studentsign.pojo.SClass;
import top.flytop.studentsign.service.ClassService;

/**
 * @ClassName ClassServiceImpl
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 11/04/2019 16:18
 * @Version 1.0
 */
@Service
public class ClassServiceImpl implements ClassService {
    private ClassMapper classMapper;

    @Autowired
    void classMapper(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    /**
     * @param
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO
     * @Date 11/04/2019 16:37
     */
    @Override
    public BaseResult getClassList() {
        try {
            return BaseResult.success(classMapper.getClassList());
        } catch (Exception e) {
            return BaseResult.fail(1, "班级列表获取失败！");
        }
    }

    /**
     * @param sClass
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 添加班级
     * @Date 11/04/2019 16:38
     */
    @Override
    public BaseResult addClass(SClass sClass) {
        return classMapper.addClass(sClass) == 1
                ? BaseResult.success("添加成功！")
                : BaseResult.fail(1, "添加失败！");
    }

    /**
     * @param cNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 删除班级
     * @Date 11/04/2019 16:38
     */
    @Override
    public BaseResult removeClass(String cNo) {
        return classMapper.removeClassByCNo(cNo) == 1
                ? BaseResult.success("删除成功！")
                : BaseResult.fail(1, "删除失败！");
    }

    /**
     * @param cNo
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 获取单个班级信息
     * @Date 11/04/2019 16:38
     */
    @Override
    public BaseResult getClass(String cNo) {
        try {
            return BaseResult.success(classMapper.getClassByCNo(cNo));
        } catch (Exception e) {
            return BaseResult.fail(1, "班级信息获取失败！");
        }
    }

    /**
     * @param sClass
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 更新班级信息
     * @Date 11/04/2019 16:38
     */
    @Override
    public BaseResult updateClassInfo(SClass sClass) {
        return classMapper.updateClassByCNo(sClass) == 1
                ? BaseResult.success("修改成功！")
                : BaseResult.fail(1, "修改失败！");
    }
}
