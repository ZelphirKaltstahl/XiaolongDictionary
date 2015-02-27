package dictionary.manager;

import dictionary.listeners.VocableListLoadedListener;
import dictionary.model.Vocable;
import dictionary.parser.StaxVocableReader;
import dictionary.parser.StaxVocableWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author XiaoLong
 */
public class VocableFileManager {
	
	private List<Vocable> vocableListFromFile;
	
	private final List<VocableListLoadedListener> vocableListLoadedListeners;
	/*
	public static void main(String[] args) {
		ArrayList<Vocable> oldVocables = loadOldVocables();
		oldVocables.stream().forEach((vocable) -> {
			System.out.println(" :: "+vocable.getFirstLanguageTranslations());
		});
		ManagerInstanceManager.getVocableFileManagerInstance().saveToXMLFile(oldVocables, new File("vocables.xml"));
	}*/
	
	public VocableFileManager() {
		this.vocableListLoadedListeners = new ArrayList<>();
	}
	
	public List<Vocable> loadVocablesFromFile(File vocableFile) {
		return loadFromXMLFile(vocableFile);
	}

	private void printVocable(Vocable vocable) {
		System.out.println("First Language Translations:" + vocable.getFirstLanguageTranslations());
		System.out.println("First Language Phonetic Scripts:" + vocable.getSecondLanguagePhoneticScripts());
		System.out.println("Second Language Translations:" + vocable.getSecondLanguageTranslations());
		System.out.println("Second Language Phonetic Scripts:" + vocable.getSecondLanguagePhoneticScripts());
		System.out.println("Chapters:" + vocable.getChapters());
		System.out.println("Topics:" + vocable.getTopics());
		System.out.println("Learn Level:" + vocable.getLearnLevel());
		System.out.println("Relevance:" + vocable.getRelevanceLevel());
		System.out.println("Description:" + vocable.getDescription());
	}

	public void saveToXMLFile(List<Vocable> vocableList, File file) {
		StaxVocableWriter staxVocableWriter = new StaxVocableWriter();
		staxVocableWriter.saveVocablesToXML(vocableList, file);
		
//		//vocableList.forEach((Vocable vocable) -> (System.out.println(vocable.getFirstLanguageTranslationsAsString())));
//		//Vocable[] vocableList = (Vocable[]) objectList;
//		
//		XStream xstream = new XStream();
//		//xstream.registerConverter(new ListVocableConverter());
//		//xstream.processAnnotations(dictionary.model.Vocable.class);
//		xstream.alias("list", List.class);
//		xstream.alias("vocable", Vocable.class);
//		//xstream.addImplicitCollection(VocableList.class, "list");
//		
//		//xstream.alias("vocable_list", Object.class);
//		xstream.setMode(XStream.NO_REFERENCES);
//
//		OutputStream outputStream = null;
//		Writer writer = null;
//
//		try {
//			outputStream = new FileOutputStream(file);
//			writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
//			
//			xstream.toXML(vocableList);
//			/*
//			for(Object vocable : vocableList) {
//				xstream.toXML((Vocable) vocable, writer);
//			}
//			*/
//
//		} catch (FileNotFoundException exp) {
//			System.out.println("Could not save vocable list!");
//			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, exp);
//			exp.printStackTrace(System.out);
//
//		} finally {
//			try {
//				if (writer != null) {
//					writer.close();
//				}
//				if (outputStream != null) {
//					outputStream.close();
//				}
//			} catch (IOException ex) {
//				Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
//			}
//		}
	}

	public List<Vocable> loadFromXMLFile(File file) {
		StaxVocableReader staxVocableReader = new StaxVocableReader();
		return staxVocableReader.readVocablesFromXMLFile(file);
		
		
		
		
//		List<Vocable> result = new ArrayList<>();
//		BufferedReader br;
//		
//		try {
//			br = new BufferedReader(new FileReader(file));
//			if (br.readLine() != null) {
//				InputStreamReader inputStreamReader = null;
//
//				try {
//					inputStreamReader = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
//				} catch (FileNotFoundException ex) {
//					Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
//				}
//
//				XStream xstream = new XStream();
//				xstream.processAnnotations(Vocable.class);
//				xstream.registerConverter(new ListVocableConverter());
//				xstream.alias("vocableList", java.util.List.class);
//				xstream.alias("vocable", dictionary.model.Vocable.class);
//				/*
//				xstream.alias("first__language__translation", String.class);
//				xstream.alias("first__language__phonetic__scripts", String.class);
//				xstream.alias("second__language__translation", String.class);
//				xstream.alias("second__language__phonetic__scripts", String.class);
//				xstream.alias("topics", String.class);
//				xstream.alias("chapters", String.class);
//				xstream.alias("description", String.class);
//				xstream.alias("learn__level", String.class);
//				xstream.alias("relevance__level", String.class);
//				*/
//				//xstream.alias("value", String.class);
//				
//				//System.out.println(((Vocable) xstream.fromXML(inputStreamReader)).getFirstLanguageTranslationsAsString());
//				
//				result = (List<Vocable>) xstream.fromXML(inputStreamReader);
//			}
//		} catch (FileNotFoundException ex) {
//			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (IOException ex) {
//			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		
//		System.out.println("!!! List Size:|"+result.size()+"|");
//		XStream xstream = new XStream();
//		xstream.alias("list", List.class);
//		xstream.alias("vocable", Vocable.class);
//		//xstream.addImplicitCollection(VocableList.class, "list");
//		
//		((List<Vocable>) xstream.fromXML(file)).forEach((Vocable vocable) -> System.out.println("FL:|"+vocable.getFirstLanguageTranslationsAsString()+"|"));
//		
//		return (List<Vocable>) xstream.fromXML(file);
	}
	
	
	private static ArrayList<Vocable> loadOldVocables() {
		BufferedReader in = null;
		String newVocableFilePath = "oldvocables";
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(newVocableFilePath)), "UTF-8"));
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedEncodingException ex2) {
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex2);
		}
		
		ArrayList<Vocable> loadedVocableList = new ArrayList<>();
		
		try {
			//Begin to read
			String line;
			if(in != null) {
				while ((line = in.readLine()) != null) {
					System.out.println("Line: " + line);
					StringTokenizer tokenizer = new StringTokenizer(line, ":");
					for (int i = 0; i < tokenizer.countTokens(); i++) {
						ArrayList<String> topics = new ArrayList<>(Arrays.asList(tokenizer.nextToken().split("/", -1)));
						ArrayList<String> chapters = new ArrayList<>(Arrays.asList(tokenizer.nextToken().split("/", -1)));
						ArrayList<String> firstLanguage = new ArrayList<>(Arrays.asList(tokenizer.nextToken().split("/", -1)));
						ArrayList<String> firstLanguagePhoneticScript = new ArrayList<>(Arrays.asList("---"));
						ArrayList<String> secondLanguage = new ArrayList<>(Arrays.asList(tokenizer.nextToken().split("/", -1)));
						ArrayList<String> secondLanguagePhoneticScript = new ArrayList<>(Arrays.asList(tokenizer.nextToken().split("/", -1)));
						String learnLevel = tokenizer.nextToken();
						String relevanceLevel = tokenizer.nextToken();
						String description = tokenizer.nextToken();

						loadedVocableList.add(new Vocable(firstLanguage, firstLanguagePhoneticScript, secondLanguage, secondLanguagePhoneticScript, topics, chapters, learnLevel, relevanceLevel, description));
					}
				}
			}
		} catch (IOException e3) {
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, e3);
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e4) {
				System.out.println("Error while closing file stream for vocable file.");
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, e4);
			}
		}
		
		return loadedVocableList;
	}
	
	public void registerVocableListLoadedListener(VocableListLoadedListener vocableListLoadedListener) {
		vocableListLoadedListeners.add(vocableListLoadedListener);
	}
	
	private void notifyVocableListLoadedListeners() {
		vocableListLoadedListeners.stream().forEach((vocableListLoadedListener) -> {
			vocableListLoadedListener.reactOnLoadedVocableList();
		});
	}


	public List<Vocable> getVocableList() {
		return vocableListFromFile;
	}
}
