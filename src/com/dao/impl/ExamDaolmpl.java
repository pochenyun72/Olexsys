package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.MyExamination;
import com.dao.ExamDao;
import com.entity.Exam;
import com.utils.DBUtils;

public class ExamDaolmpl
        extends DBUtils
        implements ExamDao
{
    @Override
    public int updateAns(String[] choicesAns, String[] testAns, String paperId, String stuId)
    {
        //给占位符赋予的值
        Object params [] =
                {
                        choicesAns[0],
                        choicesAns[1],
                        choicesAns[2],
                        choicesAns[3],
                        choicesAns[4],
                        choicesAns[5],
                        choicesAns[6],
                        choicesAns[7],
                        testAns[0],
                        testAns[1],
                        paperId,
                        stuId
                };
        //要执行的sql语句
        //String sql = "insert into paperdone(ans1, ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10) values(?,?,?,?,?,?,?,?,?,?) where paperid = ? and stuid = ?";
        String sql = "update paperdone set " +
                "ans1 = ?, " +
                "ans2 = ?, " +
                "ans3 = ?, " +
                "ans4 = ?, " +
                "ans5 = ?, " +
                "ans6 = ?, " +
                "ans7 = ?, " +
                "ans8 = ?, " +
                "ans9 = ?, " +
                "ans10 = ? where paperid = ? and stuid = ?";
        //执行sql语句
        int i = doUpdate(sql, params);

        //释放资源
        getClose();
        return  i;
    }

    @Override
    public List<Exam> selectChoice()
    {
        // 给占位符赋予的值
        //Object params[] = null;
        // 要执行的sql语句
        String sql = "select * from multiplechoice_db";
        // 执行sql语句
        ResultSet rs = doQueryChoice(sql, null);
        //存放学生的list集合
        List<Exam> list = null;

        try
        {
            rs.beforeFirst(); //初始化
            if (rs.next())
            {//判断是否至少存在一条数据记录
                rs.beforeFirst();//将光标移动到第一行数据之前
                list = new ArrayList<Exam>();

                //将学生存放在list集合中
                while (rs.next())
                {
                    //将每一个题目记录保存在list集合中
                    Exam exam = new Exam ();
                    exam.setQid(rs.getString("qid"));
                    exam.setqTxt(rs.getString("qtxt"));
                    exam.setA(rs.getString("A"));
                    exam.setB(rs.getString("B"));
                    exam.setC(rs.getString("C"));
                    exam.setD(rs.getString("D"));

                    list.add(exam);
                }
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 释放资源
        getClose();
        return list;
    }

    @Override
    public List<Exam> selectTest()
    {
        // 给占位符赋予的值
        //Object params[] = null;
        // 要执行的sql语句
        String sql = "select * from shortanswer_db";
        // 执行sql语句
        ResultSet rs = doQueryTest(sql, null);
        //存放学生的list集合
        List<Exam> list = null;

        try
        {
            rs.beforeFirst(); //初始化
            if (rs.next())
            {//判断是否至少存在一条数据记录
                rs.beforeFirst();//将光标移动到第一行数据之前
                list = new ArrayList<Exam>();

                //将学生存放在list集合中
                while (rs.next())
                {
                    //将每一个学生记录保存在list集合中
                    Exam exam = new Exam ();
                    exam.setQid(rs.getString(1));
                    exam.setqTxt(rs.getString(2));

                    list.add(exam);
                }
            }

        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 释放资源
        getClose();
        return list;
    }
}
