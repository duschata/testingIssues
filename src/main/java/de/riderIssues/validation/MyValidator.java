package de.riderIssues.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.riderIssues.dao.MyDao;
import de.riderIssues.entity.TestEntity;

public class MyValidator implements ConstraintValidator<MyValidation, TestEntity> {

    @Inject
    private MyDao myDao;

    @Override
    public void initialize(MyValidation constraintAnnotation) {
        System.out.println("init...");
    }

    @Override
    public boolean isValid(TestEntity testEntity, ConstraintValidatorContext constraintValidatorContext) {
        myDao.doFoo();
        return true;
    }

    public MyDao getMyDao() {
        return myDao;
    }
}
