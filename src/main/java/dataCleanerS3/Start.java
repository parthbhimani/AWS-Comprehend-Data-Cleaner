package dataCleanerS3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import dataCleanerS3.parser.LineParser;
import dataCleanerS3.parser.TarParser;
import properties.Props;

public class Start {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length == 0)
			new Props("dataCleaner.properties");
//			new Props("src/main/resources/dataCleaner.properties");
		else
			new Props(args[0]);

		AWSCredentials credentials = new BasicAWSCredentials(Props.accessKey, Props.secretKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
		System.out.println("Connected to AWS");

		StringBuilder sb = new StringBuilder(LineParser.getHeaderLine());

		System.out.println("Searcing for tarballs in " + Props.sourceBucket);
		ObjectListing objectListing = s3client.listObjects(Props.sourceBucket);
		for (S3ObjectSummary os : objectListing.getObjectSummaries()) {

			String filename = os.getKey();
			if (filename.endsWith(".tar.gz")) {
				System.out.println("Reading tarball " + filename);
				S3Object tarObject = s3client.getObject(Props.sourceBucket, filename);
				TarParser.parseArchive(tarObject, sb);
			}
		}

		s3client.putObject(Props.destinationBucket, Props.fileName, sb.toString());
		System.out.println(String.format("Run Complete, File %s created in %s", Props.fileName,Props.destinationBucket));
	}

}
