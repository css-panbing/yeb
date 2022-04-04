package com.cssnj.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.cssnj.server.common.page.PageParams;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.common.response.RespPageData;
import com.cssnj.server.pojo.*;
import com.cssnj.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@Api(tags = "员工管理")
@RequestMapping("/employee/basic")
public class EmployeeController {

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation("分页查询所有员工信息")
    @GetMapping("/")
    public RespPageData getEmployee(PageParams pageParams, Employee employee, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(pageParams, employee, beginDateScope);
    }

    @ApiOperation("通过ID获取员工信息")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @ApiOperation("获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getPoliticsStatus(){
        return politicsStatusService.getPoliticsStatus();
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getNations(){
        return nationService.getNations();
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getJoblevels(){
        return joblevelService.getJoblevels();
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public List<Position> getPositions(){
        return positionService.getPositions();
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/departments")
    public List<Department> getDepartments(){
        return departmentService.getAllDepartments(-1);
    }

    @ApiOperation("自动生成工号")
    @GetMapping("/generateWorkId")
    public RespData generateWorkId(){
        return employeeService.generateWorkId();
    }

    @ApiOperation("添加员工信息")
    @PostMapping("/")
    public RespData addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("更新员工信息")
    @PutMapping("/")
    public RespData updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @ApiOperation("删除员工信息")
    @DeleteMapping("/{id}")
    public RespData deleteEmployee(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }

    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployeeData(HttpServletResponse response){
        List<Employee> list = employeeService.getAllEmployee();
        ExportParams exportParams = new ExportParams("员工表","员工表", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employee.class, list);
        ServletOutputStream outputStream = null;
        try{
            //流的形式
            response.setHeader("content-type", "application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        }catch (Exception e){
            logger.error("输出流异常", e);
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭流异常", e);
                }
            }
        }
    }

    @ApiOperation("导入员工数据")
    @PostMapping("/import")
    public RespData importEmployee(MultipartFile file){
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//去除标题行
        List<Nation> nations = nationService.list();
        List<PoliticsStatus> politicsStatus = politicsStatusService.list();
        List<Department> departments = departmentService.list();
        List<Position> positions = positionService.list();
        List<Joblevel> joblevels = joblevelService.list();
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            list.forEach(employee -> {
                employee.setNationId(nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatus.get(politicsStatus.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
                employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
            });
            if(employeeService.saveBatch(list)){
                return RespData.success("导入成功！");
            }
        } catch (Exception e) {
            logger.error("文件流转换异常：", e);
        }
        return RespData.error("导入失败！");
    }

}
