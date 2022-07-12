package com.vastio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vastio.bean.base.ResponseResult;
import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.RenewContract;
import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import com.vastio.controller.base.BaseController;
import com.vastio.service.ContractService;
import com.vastio.service.ScheduledService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:43 2018/7/3
 */

@RestController
@RequestMapping("/api")
public class StandBookController extends BaseController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ScheduledService scheduledService;

    @GetMapping("/standBook")
    public ResponseResult<StandBook> getStandBook(RequestCondition param) throws ParseException {
        PageHelper.startPage(param.getCurPage(), param.getPageSize());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNoneBlank(param.getStartTime())) {
            param.setStartTime(String.valueOf(formatter.parse(param.getStartTime()).getTime()));
        }
        if (StringUtils.isNoneBlank(param.getEndTime())) {
            param.setEndTime(String.valueOf(formatter.parse(param.getEndTime()).getTime()));
        }
        List<StandBook> standBookList = contractService.getStandBook(param);
        PageInfo<StandBook> pageInfo = new PageInfo<>(standBookList);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @PostMapping("/standBook")
    public ResponseResult<String> addStandBook(@RequestBody StandBook standBook) {
        contractService.addStandBook(standBook);
        return success("success");
    }

    @DeleteMapping("/standBook/{id}")
    public ResponseResult<String> deleteStandBook(@PathVariable("id") int id) {
        contractService.deleteStandBookById(id);
        return success("success");
    }

    @PutMapping("/standBook")
    public ResponseResult<String> updateStandBook(@RequestBody StandBook standBook) {
        contractService.updateStandBook(standBook);
        return success("success");
    }

    @GetMapping("/overTimeContract")
    public ResponseResult<OverTimeContract> getOverTimeContract(@RequestParam(name = "siteName", required = false) String siteName,
                                                                @RequestParam(name = "flag") Integer flag,
                                                                @RequestParam("curPage") int curPage,
                                                                @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<OverTimeContract> overTimeContract = contractService.getOverTimeContract(siteName, flag);
        PageInfo<OverTimeContract> pageInfo = new PageInfo<>(overTimeContract);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping("/toPayContract")
    public ResponseResult<RenewContract> getContractInfo(@RequestParam(name = "siteName", required = false) String siteName,
                                                         @RequestParam("curPage") int curPage,
                                                         @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<RenewContract> contractInfos = contractService.getToPayContract(siteName);
        PageInfo<RenewContract> pageInfo = new PageInfo<>(contractInfos);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping("/renewContract")
    public ResponseResult<RenewContract> getRenewContract(@RequestParam(name = "siteName", required = false) String siteName,
                                                          @RequestParam("curPage") int curPage,
                                                          @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<RenewContract> renewContracts = contractService.getRenewContract(siteName);
        PageInfo<RenewContract> pageInfo = new PageInfo<>(renewContracts);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @PostMapping("/standbook/import")
    public ResponseResult<String> addUser(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        contractService.batchImportNew(fileName, file);
        return success("success");
    }

    @PutMapping("/task")
    public ResponseResult<String> addUser() {
        scheduledService.timer();
        return success("success");
    }

    @GetMapping("/statistic")
    public ResponseResult<Map<String, Object>> findOrderByYear() {
        List<Map> result = contractService.findOrderByYear();
        Map<String, Object> map = new HashMap<>();
        Set<String> stringSet = new LinkedHashSet<>();
        Set<String> regionSet = new LinkedHashSet<>();
        result.forEach(e -> {
            stringSet.add(e.get("year").toString());
            regionSet.add(e.get("region").toString());
        });
        map.put("year", stringSet.toArray());
        map.put("region", regionSet.toArray());
        Map<String, Object> avgMap = new HashMap<>();
        Map<String, Object> taxMap = new HashMap<>();
        List<Map> innerRentMap = new ArrayList<>();
        List<Map> outRentMap = new ArrayList<>();
        List<Map> innerCountMap = new ArrayList<>();
        List<Map> outCountMap = new ArrayList<>();
        regionSet.forEach(t -> {
            List<Double> avg = new ArrayList<>();
            List<Double> tax = new ArrayList<>();
            double rent = 0;
            long count = 0;
            Map<String, Object> map2 = new HashMap<>();
            Map<String, Object> map3 = new HashMap<>();
            for (Map e : result) {
                Map<String, Object> map1 = new HashMap<>();
                Map<String, Object> map4 = new HashMap<>();
                if (e.get("region").toString().equals(t)) {
                    avg.add(transfor((double) e.get("avg_rent")));
                    tax.add((double) e.get("tax"));
                    rent += (double) e.get("rent");
                    count += (long) e.get("count");
                    map1.put("name", t + "-" + e.get("year").toString());
                    map1.put("value", e.get("rent"));
                    map4.put("name", t + "-" + e.get("year").toString());
                    map4.put("value", e.get("count"));
                    outRentMap.add(map1);
                    outCountMap.add(map4);
                }
            }
            avgMap.put(t, avg);
            taxMap.put(t, tax);
            map2.put("name", t);
            map3.put("name", t);
            map2.put("value", rent);
            map3.put("value", count);
            innerRentMap.add(map2);
            innerCountMap.add(map3);
        });
        map.put("avg", avgMap);
        map.put("tax", taxMap);
        map.put("innerRent", innerRentMap);
        map.put("outRent", outRentMap);
        List<String> rentName = new ArrayList<>();
        innerRentMap.forEach(e -> rentName.add(e.get("name").toString()));
        outRentMap.forEach(e -> rentName.add(e.get("name").toString()));
        map.put("rentName", rentName);
        map.put("innerCount", innerCountMap);
        map.put("outCount", outCountMap);
        List<String> countName = new ArrayList<>();
        innerCountMap.forEach(e -> countName.add(e.get("name").toString()));
        outCountMap.forEach(e -> countName.add(e.get("name").toString()));
        map.put("countName", countName);
        map.putAll(contractService.getCount());
        return successResult(map, "统计数据");
    }

    private double transfor(double a) {
        BigDecimal b = new BigDecimal(a);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    @GetMapping("/download")
    public void downloadTemplate(HttpServletResponse res) {
        String fileName = "template.xlsx";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, RequestCondition param) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNoneBlank(param.getStartTime())) {
            param.setStartTime(String.valueOf(formatter.parse(param.getStartTime()).getTime()));
        }
        if (StringUtils.isNoneBlank(param.getEndTime())) {
            param.setEndTime(String.valueOf(formatter.parse(param.getEndTime()).getTime()));
        }
        List<StandBook> list = contractService.getStandBook(param);

        // excel模板路径
        String excel = "standbook.xlsx";
        File fi = new File(excel);
        // 读取excel模板
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(fi));
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("yyy/MM/dd"));
            // 读取了模板内所有sheet内容
            XSSFSheet sheet = wb.getSheetAt(0);
            // 在相应的单元格进行赋值
            int rowIndex = 2;
            int j = 1;
            for (StandBook standBook : list) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (null == row) {
                    row = sheet.createRow(rowIndex);
                }
                XSSFCell cell0 = row.getCell(0);
                if (null == cell0) {
                    cell0 = row.createCell(0);
                }
                cell0.setCellValue(j);// 标识

                XSSFCell cell1 = row.getCell(1);
                if (null == cell1) {
                    cell1 = row.createCell(1);
                }
                cell1.setCellValue(standBook.getRegion());// 用户名

                XSSFCell cell2 = row.getCell(2);
                if (null == cell2) {
                    cell2 = row.createCell(2);
                }
                cell2.setCellValue(standBook.getCode());// 头像

                XSSFCell cell3 = row.getCell(3);
                if (null == cell3) {
                    cell3 = row.createCell(3);
                }
                cell3.setCellValue(standBook.getAttribute());// 性别

                XSSFCell cell5 = row.getCell(4);
                if (null == cell5) {
                    cell5 = row.createCell(4);
                }
                cell5.setCellValue(standBook.getSiteName());// 手机

                XSSFCell cell6 = row.getCell(5);
                if (null == cell6) {
                    cell6 = row.createCell(5);
                }
                cell6.setCellValue(standBook.getContractName());

                XSSFCell cell7 = row.getCell(6);
                if (null == cell7) {
                    cell7 = row.createCell(6);
                }
                cell7.setCellValue(standBook.getOwnerName());

                XSSFCell cell9 = row.getCell(7);
                if (null == cell9) {
                    cell9 = row.createCell(7);
                }
                cell9.setCellValue(standBook.getPhoneNumber());

                XSSFCell cell14 = row.getCell(8);
                if (null == cell14) {
                    cell14 = row.createCell(8);
                }
                cell14.setCellStyle(cellStyle);
                cell14.setCellValue(standBook.getContractStart());

                XSSFCell cell15 = row.getCell(9);
                if (null == cell15) {
                    cell15 = row.createCell(9);
                }
                cell15.setCellStyle(cellStyle);
                cell15.setCellValue(standBook.getContractEnd());

                XSSFCell cell16 = row.getCell(10);
                if (null == cell16) {
                    cell16 = row.createCell(10);
                }
                cell16.setCellStyle(cellStyle);
                cell16.setCellValue(standBook.getPayTime());

                XSSFCell cell17 = row.getCell(11);
                if (null == cell17) {
                    cell17 = row.createCell(11);
                }
                cell17.setCellStyle(cellStyle);
                cell17.setCellValue(standBook.getStart());

                XSSFCell cell18 = row.getCell(12);
                if (null == cell18) {
                    cell18 = row.createCell(12);
                }
                cell18.setCellStyle(cellStyle);
                cell18.setCellValue(standBook.getEnd());

                XSSFCell cell19 = row.getCell(13);
                if (null == cell19) {
                    cell19 = row.createCell(13);
                }
                cell19.setCellValue(standBook.getRent());

                XSSFCell cell20 = row.getCell(14);
                if (null == cell20) {
                    cell20 = row.createCell(14);
                }
                cell20.setCellValue(standBook.getTax());
                j++;
                rowIndex++;
            }

            String fileName = "台账信息";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(sout);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

