package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {
	
	public static String sourceBucket;
	public static String destinationBucket;
	public static String accessKey;
	public static String secretKey;
	public static String fileName;

	public Props(String propsFilePath) throws IOException {
		if(propsFilePath.isEmpty())
			propsFilePath="dataCleaner.properties";
		
		InputStream input = new FileInputStream(propsFilePath);
		Properties prop = new Properties();
        prop.load(input);
    	sourceBucket = prop.getProperty("sourceBucket");
    	destinationBucket = prop.getProperty("destinationBucket");
    	accessKey = prop.getProperty("accessKey");
    	secretKey = prop.getProperty("secretKey");
    	fileName = prop.getProperty("outputFileName");
    	input.close();
    	System.out.println("Properties Loaded");
	}

}