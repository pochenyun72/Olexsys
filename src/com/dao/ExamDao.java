package com.dao;

import java.awt.*;
import java.util.List;

import com.entity.Exam;

public interface ExamDao
{
    /**
     * 添加
     */
    public int updateAns(String[] choicesAns, String[] testAns, String paperId, String stuid);
    /**
     * 查询
     */
    public List<Exam> selectChoice();
    public List<Exam> selectTest();
}
