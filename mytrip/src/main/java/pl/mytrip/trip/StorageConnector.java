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

    public CloudBlobContainer getStorageContainer(String name) {
        String storageConnectionString =
                "DefaultEndpointsProtocol=https;" +
                        "AccountName=tripsbackendstorage;" +
                        "AccountKey=pugJGg9FMiZ8YRbn/UBrCAS7G5DAeJZVulEyeiV3f+MwfKBKg+Sobk+gEuYxDY9C+afRWeAsDEPcPRuQhaSV5g==";

        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            return blobClient.getContainerReference(name);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
