package pl.mytrip.trip;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StorageConnector {

    public CloudBlobContainer getStorageContainer() {
        String storageConnectionString =
                "DefaultEndpointsProtocol=https;" +
                        "AccountName=kmdapistorage;" +
                        "AccountKey=7h2rlrAZpCXgWoma6OaUtL5PzXAhyAh/SxCN72ROE7tUn1AqrOgD3oMy+Ye0xtc1eXWahhQV8YgFo3lWKNuFdQ==";

        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            return blobClient.getContainerReference("mytrip");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
