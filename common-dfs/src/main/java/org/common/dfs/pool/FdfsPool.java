package org.common.dfs.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


/**
 * Fdfs连接池
 * 
 * @author t
 * 
 * 
 */
public class FdfsPool extends Pool<FdfsClient>
{

	public FdfsPool()
	{
	}

	/**
	 * 创建连接池
	 * 
	 * @param poolConfig
	 * @param factory
	 */
	public FdfsPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<FdfsClient> factory)
	{
		super(poolConfig, factory);
	}

	/**
	 * 回收资源对象
	 */
	@Override
	public void returnResource(final FdfsClient resource)
	{
		if (resource != null)
		{
			try
			{
				super.returnResource(resource);
			} catch (Exception e)
			{
				returnBrokenResource(resource);
				// throw new FdfsException("Could not return the resource to the
				// pool", e);
			}
		}
	}

}