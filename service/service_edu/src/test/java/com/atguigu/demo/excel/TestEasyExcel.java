package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    //实现excel写的操作
    public static void main(String[] args) {
        //1、设置写入文件夹地址和excel文件名称
        String fileName = "E:\\write.xlsx";
        //2、调用EasyExcel中的方法实现写操作,方法中两个参数：1、文件路径名称，2、参数实体类class
        EasyExcel.write(fileName,ExcelDemo.class).sheet("学生列表").doWrite(getData());
    }

    private static List<ExcelDemo> getData(){
        List<ExcelDemo> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            ExcelDemo demo = new ExcelDemo();
            demo.setSno(i);
            demo.setSname("lucy"+i);
            list.add(demo);
        }
        return list;
    }

    @Test
    public void testRead(){
        String fileName = "E:\\write.xlsx";

        EasyExcel.read(fileName,ExcelDemo.class,new ExcelListener()).sheet().doRead();
    }

}
