package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//表示章节数据
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
