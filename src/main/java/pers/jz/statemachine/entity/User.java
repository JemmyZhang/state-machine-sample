package pers.jz.statemachine.entity;

import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.enums.MasterStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author jemmyzhang on 2018/3/29.
 */
@Entity
public class User implements Serializable{

    private static final long serialVersionUID = -2374084100691226776L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private MasterStatus master;

    @Column
    private CheckStatus passed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MasterStatus getMaster() {
        return master;
    }

    public void setMaster(MasterStatus master) {
        this.master = master;
    }

    public CheckStatus getPassed() {
        return passed;
    }

    public void setPassed(CheckStatus passed) {
        this.passed = passed;
    }
}
