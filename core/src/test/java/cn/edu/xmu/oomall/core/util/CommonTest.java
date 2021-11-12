package cn.edu.xmu.oomall.core.util;


import cn.edu.xmu.oomall.core.util.bo.ComplexBo;
import cn.edu.xmu.oomall.core.util.vo.ComplexVo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//@SpringBootTest(classes = CoreApplication.class)

class CommonTest {

    /**
     * @author cangbai xu
     * @date 2021/11/13
     */
    @Test
    void CloneVo1() {
        List<Integer> integerList = new ArrayList<>(3);
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        LocalDateTime localDateTime= LocalDateTime.now();
        ComplexBo complexBo = new ComplexBo(1,"a", (short) 1,1.1,1.1F,'a',true,localDateTime,integerList, 3, 4);
        ComplexVo complexVo = (ComplexVo) Common.cloneVo(complexBo, ComplexVo.class);

        assertNotEquals(complexVo.getIntegerList().get(0),1);
        assertNotEquals(complexVo.getIntegerList().get(1),2);
        assertNotEquals(complexVo.getIntegerList().get(2),3);
        assertEquals(complexVo.getInteger(),1);
        assertEquals(complexVo.getString(),"a");
        assertEquals(complexVo.getAShort(),(short) 1);
        assertEquals(complexVo.getADouble(),1.1);
        assertEquals(complexVo.getAFloat(),1.1F);
        assertEquals(complexVo.getCharacter(),'a');
        assertEquals(complexVo.getBool(),true);
        assertEquals(complexVo.getLocalDateTime(),localDateTime);
        assertEquals(complexVo.getBaseInt1(),3);
    }
}