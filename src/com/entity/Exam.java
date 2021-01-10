package com.entity;

import org.w3c.dom.Text;

public class Exam
{
    private String qid;
    private String qTxt;
    private String A;
    private String B;
    private String C;
    private String D;
    private char choiceAns;
    private String testAns;

    public Exam(){ }
    public String getQid()
    {
        return qid;
    }
    public void setQid(String qid)
    {
        this.qid = qid;
    }
    public String getqTxt()
    {
        return qTxt;
    }
    public void setqTxt(String qAns)
    {
        this.qTxt = qAns;
    }
    public String getA()
    {
        return A;
    }
    public void setA(String A)
    {
        this.A = A;
    }
    public String getB()
    {
        return B;
    }
    public void setB(String B)
    {
        this.B = B;
    }
    public String getC()
    {
        return C;
    }
    public void setC(String C)
    {
        this.C = C;
    }
    public String getD()
    {
        return D;
    }
    public void setD(String D)
    {
        this.D = D;
    }
    public char getChoiceAns()
    {
        return choiceAns;
    }
    public void setChoiceAns(char choiceAns)
    {
        this.choiceAns = choiceAns;
    }
    public String getTestAns()
    {
        return testAns;
    }
    public void setTestAns(String testAns)
    {
        this.testAns = testAns;
    }
}
