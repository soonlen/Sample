package com.wzf.com.sample.dagger;

/**
 * Created by soonlen on 2017/1/20.
 */

//@Module
public class MainModule {

//    @Provides
//    @Singleton
    Person providerPerson() {
        return new Person();
    }
}
