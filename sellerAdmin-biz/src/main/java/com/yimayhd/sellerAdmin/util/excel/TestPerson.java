package com.yimayhd.sellerAdmin.util.excel;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/11/26.
 */
public class TestPerson {
    @ExportField
    @FieldDesc("编号")
    private long id;
    @ExportField
    @FieldDesc("姓名")
    private String name;
    @ExportField
    @FieldDesc("年龄")
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Field[] fds = TestPerson.class.getDeclaredFields();
        for (Field fd :fds){
            System.out.println(fd.getName());
        }
    }
}
