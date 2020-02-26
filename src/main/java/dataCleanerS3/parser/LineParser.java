package dataCleanerS3.parser;

public class LineParser {

	public static String parseLine(String line){
		StringBuilder parsedLine = new StringBuilder();
		
		int indexOfFileName = 10;
		String fileName = line.substring(indexOfFileName, line.indexOf("\"",indexOfFileName)-4);
		String[] fileNameSplit = fileName.split("_");
		parsedLine.append(fileNameSplit[1]);
		parsedLine.append(","+fileNameSplit[0]);
		parsedLine.append(","+fileNameSplit[2]);
		parsedLine.append(","+fileNameSplit[3].replace('-',':'));
		
		int indexOfSentiment = line.indexOf("Sentiment\": ")+13;
		parsedLine.append(","+line.substring(indexOfSentiment, line.indexOf("\"",indexOfSentiment)));
		
		
		parsedLine.append(System.lineSeparator());
		return parsedLine.toString();
		
	}
	
	public static String getHeaderLine(){
		StringBuilder header = new StringBuilder("tweetId");
		header.append(", CandidateId");
		header.append(", Date");
		header.append(", Time");
		header.append(", Sentiment");
		header.append(System.lineSeparator());
		return header.toString();
	}
}
