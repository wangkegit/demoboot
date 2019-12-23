package com.demoboot.study;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

public class Guava1 {

    @Test
    public  void test1(){
        String str ="qwe,ewq,eqw,e,wqe,wqe,sa,da,f,ad,zc,gjf";
       // Splitter.on(",").split(str).forEach(System.out::println);
       // Splitter.on(",").splitToList(str).stream().filter(x->x.length()>0).map(x->x.toUpperCase()).collect()

    }
}
