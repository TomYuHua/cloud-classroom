package com.singFly.cloud_examination_commonDfs.pool;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.singFly.cloud_examination_commonDfs.common.MyException;
import com.singFly.cloud_examination_commonDfs.fastdfs.ClientGlobal;
import com.singFly.cloud_examination_commonDfs.fastdfs.ProtoCommon;
import com.singFly.cloud_examination_commonDfs.fastdfs.StorageServer;
import com.singFly.cloud_examination_commonDfs.fastdfs.TrackerClient;
import com.singFly.cloud_examination_commonDfs.fastdfs.TrackerServer;

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


	public void activateObject(PooledObject<FdfsClient> p) throws Exception
	{

	}

	public void passivateObject(PooledObject<FdfsClient> p) throws Exception
	{

	}

}