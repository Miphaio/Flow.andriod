package io.atthis.atthisdemo;

import junit.framework.Test;

/**
 * Created by WMPCXPY on 2017/5/31.
 */

public class TaskDetail {
    private String title;
    private int Tester;
    public String getTitle(){
        return this.title;
    }
    public String getTester(){
        return this.Tester+"";
    }
    public TaskDetail(String title, int Tester){
        this.title = title;
        this.Tester = Tester;

    }

}
