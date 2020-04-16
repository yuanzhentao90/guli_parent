package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelDemo> {
    //读取excel中的内容（不含表头中的内容）
    @Override
    public void invoke(ExcelDemo excelDemo, AnalysisContext analysisContext) {
        System.out.println("*********"+excelDemo);
    }

    //读取表头的内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //读取完成之后要做的事情
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
