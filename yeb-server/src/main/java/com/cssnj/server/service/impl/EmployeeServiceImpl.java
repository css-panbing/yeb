package com.cssnj.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cssnj.server.common.page.PageParams;
import com.cssnj.server.common.pojo.MailConstants;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.common.response.RespPageData;
import com.cssnj.server.mapper.MailLogMapper;
import com.cssnj.server.pojo.Employee;
import com.cssnj.server.mapper.EmployeeMapper;
import com.cssnj.server.pojo.MailLog;
import com.cssnj.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * 员工 实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;

    /**
     * 分页查询员工信息
     * @param pageParams 分页参数
     * @param employee 员工信息
     * @param beginDateScope 入职日期区间
     * @return
     */
    @Override
    public RespPageData getEmployeeByPage(PageParams pageParams, Employee employee, LocalDate[] beginDateScope) {
        int currentPage = pageParams.getCurrentPage() == 0 ? 1 : pageParams.getCurrentPage();
        int pageSize = pageParams.getPageSize() == 0 ? 10 : pageParams.getPageSize();
        //开启分页
        Page<Employee> page = new Page<>(currentPage, pageSize);
        IPage<Employee> result = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageData respPageData = new RespPageData();
        respPageData.setData(result.getRecords());
        respPageData.setTotal(result.getTotal());
        return respPageData;
    }

    /**
     * 自动生成工号
     * @return
     */
    @Override
    public RespData generateWorkId() {
        //获取最大工号
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId) as result"));
        String result = "";
        if(maps != null && maps.size() > 0){
            result = String.format("%08d", Integer.parseInt(maps.get(0).get("result").toString()) + 1);
        }
        if(!"".equals(result)){
            return RespData.success(null, result);
        }
        return RespData.error("自动生成工号失败！", null);
    }

    /**
     * 添加员工信息
     * @param employee
     * @return
     */
    @Override
    public RespData addEmployee(Employee employee) {
        employee.setWorkState("在职");
        employee.setContractTerm(getContractTerm(employee));
        if(employeeMapper.insert(employee) == 1){
            //数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusSeconds(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            //发送邮件信息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME,
                    employee, new CorrelationData(msgId));
            return RespData.success("添加成功");
        }
        return RespData.error("添加失败");
    }

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @Override
    public RespData updateEmployee(Employee employee) {
        employee.setContractTerm(getContractTerm(employee));
        if(employeeMapper.updateById(employee) == 1){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

    /**
     * 查询所有员工
     * @return
     */
    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.getAllEmployee();
    }

    /**
     * 通过合同开始和结束时间计算合同期限
     * @param employee
     * @return
     */
    private Double getContractTerm(Employee employee){
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        //计算合同到期天数
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        //转换成年
        String year = decimalFormat.format(days / 365.00);
        return Double.parseDouble(year);
    }


}
