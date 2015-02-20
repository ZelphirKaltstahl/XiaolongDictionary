package dictionary.manager;

import com.thoughtworks.xstream.XStream;
import dictionary.listeners.VocableListLoadedListener;
import dictionary.model.Vocable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author XiaoLong
 */
public class VocableFileManager {
	
	private ArrayList<Vocable> vocableListFromFile;
	
	private ArrayList<VocableListLoadedListener> vocableListLoadedListeners;
	
	public static void main(String[] args) {
		ArrayList<Vocable> oldVocables = loadOldVocables();
		oldVocables.stream().forEach((vocable) -> {
			System.out.println(" :: "+vocable.getFirstLanguageTranslations());
		});
		ManagerInstanceManager.getVocableFileManagerInstance().saveToXMLFile(oldVocables, new File("vocables.xml"));
	}
	
	public VocableFileManager() {
		this.vocableListLoadedListeners = new ArrayList<>();
	}
	
	public void loadVocablesFromFile(File vocableFile) {
		vocableListFromFile = loadFromXMLFile(vocableFile);
		notifyVocableListLoadedListeners();
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

	private void saveToXMLFile(ArrayList<Vocable> vocableList, File file) {

		XStream xstream = new XStream();
		xstream.alias("vocable", Vocable.class);
		xstream.alias("value", String.class);
		xstream.setMode(XStream.NO_REFERENCES);
		
		OutputStream outputStream = null;
		Writer writer = null;

		try {
			outputStream = new FileOutputStream(file);
			writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
			xstream.toXML(vocableList, writer);

		} catch (FileNotFoundException exp) {
			System.out.println("Could not save vocable list!");
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, exp);
			exp.printStackTrace(System.out);

		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private ArrayList<Vocable> loadFromXMLFile(File file) {
		InputStreamReader inputStreamReader = null;

		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VocableFileManager.class.getName()).log(Level.SEVERE, null, ex);
		}

		XStream xstream = new XStream();
		xstream.alias("vocable", Vocable.class);
		xstream.alias("value", String.class);
		return (ArrayList<Vocable>) xstream.fromXML(inputStreamReader);
	}
	
	private static ArrayList<Vocable> loadOldVocables() {
		BufferedReader in = null;
		String newVocableFilePath = "oldvocables";
		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(newVocableFilePath)), "UTF-8"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex2) {
			ex2.printStackTrace();
		}
		
		ArrayList<Vocable> loadedVocableList = new ArrayList<>();
		
		try {
			//Begin to read
			String line;
			
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
		} catch (IOException e3) {
			e3.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e4) {
				System.out.println("Error while closing file stream for vocable file.");
				e4.printStackTrace();
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


	public ArrayList<Vocable> getVocableListFromFile() {
		return vocableListFromFile;
	}
}
