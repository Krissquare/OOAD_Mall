package cn.edu.xmu.oomall.core.util.bo;

import lombok.Data;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/12
 */
@Data
public class TestBo {
    private Integer outId;
    private String outName;
    private int year;
    private int month;

    private TestSubBo sub;
}
