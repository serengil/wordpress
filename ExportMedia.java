package com.blog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ExportMedia {
	
	public static String source = "C:\\Users\\IS96273\\Desktop\\sefikilkinserengil.wordpress.2018-03-08.001.xml";
	public static String targetPath = "C:\\Users\\IS96273\\Desktop\\wp";
	public static String prefix = "https://serengil.files.wordpress.com/";
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(source));
		
		String line = br.readLine();
		
		int lineNum = -1, linkNum = 0;
		
		while (line != null) {
			
			line = br.readLine();
			lineNum++;
			
			if(true){ //finally switch this to always true
				
				if(line != null && line.contains(prefix)){
					
					for(int i=0;i<line.length();i++){
												
						if(i+prefix.length() < line.length() && line.substring(i, i+prefix.length()).equals(prefix)){
							
							for(int j=i;j<line.length();j++){
								
								if(line.substring(j, j+1).equals("\"") || line.substring(j, j+1).equals("<")){
									
									String link = line.substring(i, j).split("\\?")[0];
									
									//System.out.println(link);
									
									String filename = link.substring(link.lastIndexOf("/")+1, link.length());
									
									//System.out.println(filename);
									
									String baseurl = link.substring("https://".length(), link.length());
									String path = baseurl.substring(baseurl.indexOf("/"), baseurl.length() - filename.length());
									//System.out.println(path);

									if(!link.substring(link.length()-1, link.length()).equals(">")){
										
										System.out.print(linkNum+"\t");
										saveFile(link, path, filename);
										
									}
									
									linkNum++;
									
									break;
									
								}
								
							}
							
							i = i + prefix.length();
							
						}
						
					}
					
				}
				
			}
			
		}
		
		br.close();
		
	}
	
	public static void saveFile(String link, String path, String filename){
		
		try{
			
			path = path.replace("/", "\\");
			
			String targetFolder = targetPath+path;
			
			Path targetFilePath = new File(targetFolder+filename).toPath();
			
			if (!Paths.get(targetFolder).toFile().isDirectory()){
							
				new File(targetFolder).mkdirs();
				
				//System.out.println(targetFolder+" created");
				
			}
			
			//-----------------------------------------------------------
			
			URL url = new URL(link);
			
			try (InputStream in = url.openStream()) {
				
			    Files.copy(in, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
			}
			
			System.out.println(link+" saved to "+targetFolder);
			
		}
		catch(Exception ex){
			
			System.out.println("exception while processing "+link);
			
		}
		
	}

}
