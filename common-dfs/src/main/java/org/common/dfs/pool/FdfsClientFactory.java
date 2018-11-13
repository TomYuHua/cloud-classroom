package org.common.dfs.pool;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import org.common.dfs.common.MyException;
import org.common.dfs.fastdfs.ClientGlobal;
import org.common.dfs.fastdfs.ProtoCommon;
import org.common.dfs.fastdfs.StorageServer;
import org.common.dfs.fastdfs.TrackerClient;
import org.common.dfs.fastdfs.TrackerServer;
//import org.common.dfs.pool.exception.FdfsConnectionException;

/**
 * FdfsClient工厂
 * 
 * @author t
 * 
 *
 */
public class FdfsClientFactory implements PooledObjectFactory<FdfsClient>
{

	public FdfsClientFactory()
	{
	}

	public FdfsClientFactory(String configFilePath) throws IOException, MyException
	{
		ClientGlobal.init(configFilePath);
	}

	public FdfsClientFactory(final URI configFileUri) throws IOException, MyException
	{
		ClientGlobal.init(configFileUri.toURL().getFile());
	}

	public PooledObject<FdfsClient> makeObject() throws Exception
	{
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		if (trackerServer == null)
		{
			// throw new FdfsConnectionException("connect to server fail");
		}
		StorageServer storageServer = null;
		FdfsClient client = new FdfsClient(trackerServer, storageServer);
		return new DefaultPooledObject<FdfsClient>(client);
	}

	public void destroyObject(PooledObject<FdfsClient> pooledFdfsClient) throws Exception
	{
		final FdfsClient fastfdsClient = pooledFdfsClient.getObject();
		if (fastfdsClient == null)
		{
			return;
		}
		TrackerServer trackerServer = fastfdsClient.getTrackerServer();
		if (trackerServer != null)
		{
			try
			{
				trackerServer.close();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		StorageServer storageServer = fastfdsClient.getStorageServer();
		if (storageServer != null)
		{
			try
			{
				storageServer.close();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public boolean validateObject(PooledObject<FdfsClient> pooledFdfsClient)
	{
		final FdfsClient fastfdsClient = pooledFdfsClient.getObject();
		if (fastfdsClient != null && fastfdsClient.getTrackerServer() != null)
		{
			try
			{
				return ProtoCommon.activeTest(fastfdsClient.trackerServer.getSocket());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void activateObject(PooledObject<FdfsClient> p) throws Exception
	{

	}

	@Override
	public void passivateObject(PooledObject<FdfsClient> p) throws Exception
	{

	}

}