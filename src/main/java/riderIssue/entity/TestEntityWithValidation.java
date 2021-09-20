package riderIssue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import riderIssue.validation.validation.MyValidation;

@Table(name = "testentityWithValidation")
@Entity(
        name = "testentityWithValidation"
)
@MyValidation
public class TestEntityWithValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    @Column(name = "integerfield")
    private Integer integerField;
    @Column(name = "doublefield")
    private Double doubleField;
    @Column(name = "stringfield")
    private String stringField;
    @Column(name = "datefield")
    @Temporal(TemporalType.DATE)
    private Date dateField;
    @Column(name = "floatfield")
    private Float floatField;
    @Column(name = "shortfield")
    private Short shortField;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(Integer integerField) {
        this.integerField = integerField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Float getFloatField() {
        return floatField;
    }

    public void setFloatField(Float floatField) {
        this.floatField = floatField;
    }

    public Short getShortField() {
        return shortField;
    }

    public void setShortField(Short shortField) {
        this.shortField = shortField;
    }
}
