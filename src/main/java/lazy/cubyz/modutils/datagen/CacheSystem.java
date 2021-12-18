package lazy.cubyz.modutils.datagen;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Properties;

/**
 * The MD5 checksum was based on this https://stackoverflow.com/a/304275
 */
public class CacheSystem {

    private final Properties cache;

    public CacheSystem(String modId) {
        this.cache = this.loadCacheFile(Paths.get("assets/".concat(modId).concat("_cache.txt")));
        this.cleanCachedDeletedFiles(modId);
    }

    public boolean isDataObjectCached(DataObject fileData) {
        String md5Checksum = this.getMD5Checksum(fileData.buildObject().toString());
        String fileName = fileData.getLocation().getID().concat(".json");
        boolean fileWasCached = this.cache.containsKey(fileName);
        boolean cachedFileIsEqual = fileWasCached && this.cache.get(fileName).equals(md5Checksum);
        if (!fileWasCached || !cachedFileIsEqual) {
            this.cache.put(fileName, md5Checksum);
            return false;
        } else {
            return true;
        }
    }

    public Properties getCache() {
        return cache;
    }

    private Properties loadCacheFile(Path path) {
        Properties props = new Properties();
        if (Files.exists(path)) {
            try {
                FileReader reader = new FileReader(path.toFile());
                props.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return props;
    }

    private void cleanCachedDeletedFiles(String modID) {
        //noinspection SuspiciousToArrayCall
        for (String s : this.cache.keySet().toArray(new String[0])) {
            String filePath = "assets/".concat(modID).concat("/").concat(s);
            if (!Files.exists(Paths.get(filePath))) {
                this.cache.remove(s);
            }
        }
    }

    private byte[] createChecksum(String fileContent) throws Exception {
        InputStream fis = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    private String getMD5Checksum(String fileContent) {
        byte[] b = new byte[0];
        try {
            b = createChecksum(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();

        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
