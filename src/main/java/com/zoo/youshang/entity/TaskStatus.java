/**
 * 
 */
package com.zoo.youshang.entity;

/**
 * @author sunpeng
 * 
 */
public interface TaskStatus {

	public static final Byte PENDING = 0; // 初始状态

	public static final Byte EXECUTING = 5; // 执行中
	
	public static final Byte FINISH = 10; // 完成
}
