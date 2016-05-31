package com.androidmads.actvieandroidexample;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Mushtaq on 27-05-2016.
 */
@Table(name = "Details")
public class Details extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Age")
    public String age;

}
