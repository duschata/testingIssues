package com.foo.bar.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.foo.bar.dao.MyDao;
import com.foo.bar.entity.TestEntity;

public class MyValidator implements ConstraintValidator<MyValidation, TestEntity> {

    @Inject
    private MyDao myDao;

    @Override
    public void initialize(MyValidation constraintAnnotation) {
        System.out.println("init...");
    }

    @Override
    public boolean isValid(TestEntity testEntity, ConstraintValidatorContext constraintValidatorContext) {
        return myDao.isValid();
    }

    public MyDao getMyDao() {
        return myDao;
    }
}
