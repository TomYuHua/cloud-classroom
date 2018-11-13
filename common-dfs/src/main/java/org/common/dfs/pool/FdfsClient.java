package org.common.dfs.pool;

import org.common.dfs.fastdfs.StorageClient1;
import org.common.dfs.fastdfs.StorageServer;
import org.common.dfs.fastdfs.TrackerServer;

/**
 * FdfsClient对象
 * 
 * @author t
 * 
 *
 */
public class FdfsClient extends StorageClient1
{

	public TrackerServer trackerServer;
	public StorageServer storageServer;

	public FdfsClient()
	{
	}

	public FdfsClient(TrackerServer trackerServer, StorageServer storageServer)
	{
		super(trackerServer, storageServer);
		this.trackerServer = trackerServer;
		this.storageServer = storageServer;
	}

	public TrackerServer getTrackerServer()
	{
		return this.trackerServer;
	}

	public StorageServer getStorageServer()
	{
		return this.storageServer;
	}

}