package riderIssue.validation.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import riderIssue.entity.TestEntityWithValidation;
import riderIssue.validation.dao.MyDao;

public class MyValidator implements ConstraintValidator<MyValidation, TestEntityWithValidation> {

    @Inject
    private MyDao myDao;

    @Override
    public void initialize(MyValidation constraintAnnotation) {
        System.out.println("init...");
    }

    @Override
    public boolean isValid(TestEntityWithValidation testEntityWithValidation,
            ConstraintValidatorContext constraintValidatorContext) {
        return myDao.isValid();
    }

    public MyDao getMyDao() {
        return myDao;
    }
}
