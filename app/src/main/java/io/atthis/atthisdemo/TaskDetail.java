package io.atthis.atthisdemo;

import junit.framework.Test;

/**
 * Created by WMPCXPY on 2017/5/31.
 */

public class TaskDetail {
    private String title;
    private String Tester;
    private String Tester2;
    public String getTitle(){
        return this.title;
    }
    public String getTester(){
        return this.Tester;
    }
    public String getTester2(){
        return this.Tester2;
    }
    public TaskDetail(String title, String Tester, String Tester2){
        this.title = title;
        this.Tester = Tester;
        this.Tester2 = Tester2;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setTester(String tester){
        this.Tester = tester;
    }

}
