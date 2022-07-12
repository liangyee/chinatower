package com.vastio.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.vastio.VastioException;
import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.RenewContract;
import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import com.vastio.mapper.ContractMapper;
import com.vastio.mapper.StandBookMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 14:10 2018/7/5
 */

@Service
public class ContractService {
    @Autowired
    private StandBookMapper standBookMapper;

    @Autowired
    private ContractMapper contractMapper;

    public List<StandBook> getStandBook(RequestCondition param) {
        return standBookMapper.findStandBook(param);
    }

    public List<OverTimeContract> getOverTimeContract(String siteName, Integer flag) {
        return contractMapper.getOverPayContract(siteName, flag);
    }

    public List<RenewContract> getToPayContract(String siteName) {
        return contractMapper.getToPayContract(siteName);
    }

    public List<RenewContract> getRenewContract(String siteName) {
        return contractMapper.getRenewContract(siteName);
    }

    public void deleteStandBookById(int id) {
        standBookMapper.deleteStandBookById(id);
    }

    public void addStandBook(StandBook standBook) {
        standBookMapper.addStandBook(standBook);
    }

    public void updateStandBook(StandBook standBook) {
        standBookMapper.updateStandBook(standBook);
    }

    public List<Map> findOrderByYear() {
        return standBookMapper.findOrderByYear();
    }

    public void batchImport(String fileName, MultipartFile file) {
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new VastioException("上传文件格式不正确");
        }
        if (file == null) {
            throw new VastioException("文件内容为空");
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setReadRows(100);
        List<StandBook> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), StandBook.class, params);
        } catch (NoSuchElementException e) {
            throw new VastioException("excel文件不能为空");
        } catch (Exception e) {
            throw new VastioException(e.getMessage());
        }
        standBookMapper.addAllStandBooks(list);
    }

    @Transactional
    public boolean batchImportNew(String fileName, MultipartFile file) throws IOException {
        boolean notNull = false;
        List<StandBook> standBookList = new ArrayList<StandBook>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new VastioException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        System.out.println(sheet.getLastRowNum());
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            StandBook standBook = new StandBook();
            try {
                String region = row.getCell(1).getStringCellValue();
                String code = row.getCell(2).getStringCellValue();
                String attr = row.getCell(3).getStringCellValue();
                String siteName = row.getCell(5).getStringCellValue();
                String contractName = row.getCell(6).getStringCellValue();
                String owner = row.getCell(7).getStringCellValue();
                String phoneNum = row.getCell(9).getStringCellValue();
                Date contractStart = row.getCell(14).getDateCellValue();
                Date contractEnd = row.getCell(15).getDateCellValue();
                Date pay = row.getCell(16).getDateCellValue();
                Date start = row.getCell(17).getDateCellValue();
                Date end = row.getCell(18).getDateCellValue();
                Double rent = row.getCell(19).getNumericCellValue();
                Double tax = row.getCell(20).getNumericCellValue();
                standBook.setRegion(region);
                standBook.setCode(code);
                standBook.setAttribute(attr);
                standBook.setSiteName(siteName);
                standBook.setContractName(contractName);
                standBook.setOwnerName(owner);
                standBook.setPhoneNumber(phoneNum);
                standBook.setContractStart(contractStart);
                standBook.setContractEnd(contractEnd);
                standBook.setPayTime(pay);
                standBook.setStart(start);
                standBook.setEnd(end);
                standBook.setRent(rent);
                standBook.setTax(tax);
            } catch (Exception e) {
                throw new VastioException("第" + (r + 1) + "行数据有错误！！" + e.getMessage());
            }
            standBookList.add(standBook);
        }
        standBookMapper.deleteStandBook();
        standBookMapper.addAllStandBooks(standBookList);
        return notNull;
    }

    public Map getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("toPay", standBookMapper.findToPay());
        map.put("renew", standBookMapper.findRenew());
        map.put("overTime", standBookMapper.findOverTime());
        return map;
    }
}
