/**
 * 
 */
package com.zoo.youshang.entity;

/**
 * @author sunpeng
 * 
 */
public interface TaskStatus {

	public static final Byte PENDING = 0; // 悬赏中

	public static final Byte EXECUTING = 3; // 待完成

	public static final Byte CONFIRMING = 5; // 待确定

	public static final Byte UNFINISHED = 7; // 未完成
	
	public static final Byte FINISHED = 10; // 已完成
}
