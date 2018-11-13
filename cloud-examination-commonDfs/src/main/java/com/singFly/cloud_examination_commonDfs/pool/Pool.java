package com.singFly.cloud_examination_commonDfs.pool;



import java.io.Closeable;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.singFly.cloud_examination_commonDfs.fastdfs.ClientGlobal;




/**
 * 连接池
 * 
 * @author t 
 *  
 *
 * @param <T>
 */
public abstract class Pool<T> implements Closeable
{

	/**
	 * 连接池
	 */
	protected GenericObjectPool<T> internalPool;

	public Pool()
	{
	}

	/**
	 * 
	 * @param poolConfig
	 * @param factory
	 */
	public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
	{
		initPool(poolConfig, factory);
	}

	/**
	 * 初始化连接池
	 * 
	 * @param poolConfig
	 * @param factory
	 */
	public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
	{
		if (this.internalPool != null)
		{
			try
			{
				closeInternalPool();
			} catch (Exception e)
			{
			}
		}
		this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
		this.addObjects(ClientGlobal.g_tracker_group.tracker_servers.length);
	}

	/**
	 * 获取资源对象
	 * 
	 * @return
	 */
	public T getResource()
	{
		try
		{
			return this.internalPool.borrowObject();
		} catch (Exception e)
		{
			 return null;
			// throw new FdfsConnectionException("Could not get a resource from the pool", e);
		}
	}

	/**
	 * 回收资源对象
	 * 
	 * @param resource
	 */
	public void returnResource(final T resource)
	{
		if (resource != null)
		{
			try
			{
				this.internalPool.returnObject(resource);
			} catch (Exception e)
			{
				// throw new FdfsException("Could not return the resource to the
				// pool", e);
			}
		}
	}

	/**
	 * 失效对象
	 * 
	 * @param resource
	 */
	public void returnBrokenResource(final T resource)
	{
		if (resource != null)
		{
			try
			{
				this.internalPool.invalidateObject(resource);
			} catch (Exception e)
			{
				// throw new FdfsException("Could not return the resource to the
				// pool", e);
			}
		}
	}

	/**
	 * 销毁对象
	 */
	public void destroy()
	{
		closeInternalPool();
	}

	public void close()
	{
		destroy();
	}

	/**
	 * 关闭对象
	 * 
	 * @return
	 */
	public boolean isClosed()
	{
		return this.internalPool.isClosed();
	}

	/**
	 * 关闭对象
	 */
	protected void closeInternalPool()
	{
		try
		{
			internalPool.close();
		} catch (Exception e)
		{
			// throw new FdfsException("Could not destroy the pool", e);
		}
	}

	public int getNumActive()
	{
		if (poolInactive())
		{
			return -1;
		}

		return this.internalPool.getNumActive();
	}

	public int getNumIdle()
	{
		if (poolInactive())
		{
			return -1;
		}

		return this.internalPool.getNumIdle();
	}

	public int getNumWaiters()
	{
		if (poolInactive())
		{
			return -1;
		}

		return this.internalPool.getNumWaiters();
	}

	public long getMeanBorrowWaitTimeMillis()
	{
		if (poolInactive())
		{
			return -1;
		}

		return this.internalPool.getMeanBorrowWaitTimeMillis();
	}

	public long getMaxBorrowWaitTimeMillis()
	{
		if (poolInactive())
		{
			return -1;
		}

		return this.internalPool.getMaxBorrowWaitTimeMillis();
	}

	private boolean poolInactive()
	{
		return this.internalPool == null || this.internalPool.isClosed();
	}

	public void addObjects(int count)
	{
		try
		{
			for (int i = 0; i < count; i++)
			{
				this.internalPool.addObject();
			}
		} catch (Exception e)
		{
			// throw new FdfsException("Error trying to add idle objects", e);
		}
	}
}