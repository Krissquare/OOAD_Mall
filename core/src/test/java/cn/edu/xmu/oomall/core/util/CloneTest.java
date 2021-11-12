package cn.edu.xmu.oomall.core.util;


import cn.edu.xmu.oomall.core.util.bo.TestBo;
import cn.edu.xmu.oomall.core.util.bo.TestSubBo;
import cn.edu.xmu.oomall.core.util.vo.TestVo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.edu.xmu.oomall.core.util.Common.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Common Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11月 12, 2021</pre>
 */
public class CloneTest {


    @Test
    public void testCLoneVo() throws Exception{
        TestSubBo testSubBo=new TestSubBo();
        testSubBo.setInId(200);
        testSubBo.setInName("YuJieIn");
        testSubBo.setYear(1949);

        TestBo testBo=new TestBo();
        testBo.setOutId(1000);
        testBo.setOutName("YuJieOut");
        testBo.setYear(2020);
        testBo.setMonth(12);
        testBo.setSub(testSubBo);

        TestVo vo=null;
        vo= (TestVo) cloneVo(testBo,TestVo.class);

        assertEquals(1000,vo.getOutId());
        assertEquals("YuJieOut",vo.getOutName());
        assertEquals(2020,vo.getYear());
        assertEquals(12,vo.getMonth());
        assertEquals(200,vo.getSub().getInId());
        assertEquals("YuJieIn",vo.getSub().getInName());
        assertEquals(1949,vo.getSub().getYear());

    }



} 
