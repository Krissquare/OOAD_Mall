package cn.edu.xmu.oomall.coupon.model.bo.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author xincong yao
 * @date 2020-11-18
 */
public interface JsonSerializable {

	String toJsonString() throws JsonProcessingException;
}
