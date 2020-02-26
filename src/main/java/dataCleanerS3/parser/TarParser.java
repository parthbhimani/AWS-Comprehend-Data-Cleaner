package dataCleanerS3.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import com.amazonaws.services.s3.model.S3Object;

public class TarParser {
	
	public static void parseArchive(S3Object tarObject, StringBuilder sb) throws IOException, FileNotFoundException {
		TarArchiveInputStream tarInput = new TarArchiveInputStream(
				new GZIPInputStream(tarObject.getObjectContent()));
		TarArchiveEntry currentEntry = tarInput.getNextTarEntry();
		while (currentEntry != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(tarInput));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(LineParser.parseLine(line));
			}
			currentEntry = tarInput.getNextTarEntry();
		}
		tarInput.close();
	}

}
