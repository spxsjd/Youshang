/**
 * 
 */
package com.zoo.youshang.config;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoo.youshang.util.ThreadLocalCleanUtil;

/**
 * @author sunpeng.peng
 * 
 */
public abstract class SystemHookerRegistry {
	private static final Logger logger = LoggerFactory.getLogger(SystemHookerRegistry.class);

	private static final ArrayList<ShutdownHooker> shutdownHookers = new ArrayList<ShutdownHooker>();

	static {
		/**
		 * 销毁一些特殊的线程
		 */
		SystemHookerRegistry.register(new ShutdownHooker() {
			@SuppressWarnings("deprecation")
			@Override
			public void onShutdown() {
				Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
				Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
				for (Thread t : threadArray) {
					if (t.getName().contains("Abandoned connection cleanup thread")) {
						logger.info("stop the Abandoned connection cleanup thread: " + t.getName());
						synchronized (t) {
							t.stop(); // don't complain, it works
						}
					}
				}
			}

		});

		/**
		 * 如果数据故驱动是通过应用服务器(tomcat etc...)中配置的<公用>连接池,这里不需要 否则必须卸载Driver
		 * 
		 * 原因: DriverManager是System classloader加载的, Driver是webappclassloader加载的, driver保存在DriverManager中,在reload过程中,由于system classloader不会销毁,driverManager就一直保持着对driver的引用, driver无法卸载,与driver关联的其他类 ,例如DataSource,jdbcTemplate,dao,service....都无法卸载
		 */
		SystemHookerRegistry.register(new ShutdownHooker() {
			@Override
			public void onShutdown() {
				try {
					Enumeration<?> e = DriverManager.getDrivers();
					if (e != null) {

						for (; e.hasMoreElements();) {
							Driver driver = (Driver) e.nextElement();
							if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
								logger.info("clean jdbc Driver: " + driver.getClass());
								DriverManager.deregisterDriver(driver);
							}
						}
					}
				} catch (Exception e) {
					logger.error("Exception cleaning up java.sql.DriverManager's driver: "
					                + e.getMessage());
				}
			}

		});

		/**
		 * 对于使用mysql JDBC驱动的:mysql JDBC驱动会启动一个Timer Thread,这个线程在reload的时候也是无法自动销毁. 因此,需要强制结束这个timer
		 */
		SystemHookerRegistry.register(new ShutdownHooker() {
			@Override
			public void onShutdown() {
				try {
					Class<?> ConnectionImplClass = Thread
					        .currentThread()
					        .getContextClassLoader()
					        .loadClass("com.mysql.jdbc.ConnectionImpl");
					if (ConnectionImplClass != null
					        && ConnectionImplClass.getClassLoader() == getClass()
					                .getClassLoader()) {
						logger.info("clean mysql timer......");
						Field f = ConnectionImplClass.getDeclaredField("cancelTimer");
						f.setAccessible(true);
						Timer timer = (Timer) f.get(null);
						timer.cancel();
					}
				} catch (java.lang.ClassNotFoundException e1) {
				} catch (Exception e) {
					logger.info("Exception cleaning up MySQL cancellation timer: "
					                + e.getMessage());
				}
			}

		});

		/**
		 * 清理所有的线程变量
		 */
		SystemHookerRegistry.register(new ShutdownHooker() {
			@Override
			public void onShutdown() {
				ThreadLocalCleanUtil.clearThreadLocals();
			}
		});
	}

	public static void register(ShutdownHooker shutdownHooker) {
		shutdownHookers.add(0, shutdownHooker);
	}

	public static List<ShutdownHooker> getShutdownHookers() {
		return shutdownHookers;
	}

}
