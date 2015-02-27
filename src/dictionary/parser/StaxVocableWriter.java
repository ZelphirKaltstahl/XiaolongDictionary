package dictionary.parser;

import dictionary.model.Vocable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



public class StaxVocableWriter {

	private static final String VOCABLE = "vocable";

	private static final String FIRST_LANGUAGE_TRANSLATIONS = "firstLanguageTranslations";
	private static final String FIRST_LANGUAGE_PHONETIC_SCRIPTS = "firstLanguagePhoneticScripts";

	private static final String SECOND_LANGUAGE_TRANSLATIONS = "secondLanguageTranslations";
	private static final String SECOND_LANGUAGE_PHONETIC_SCRIPTS = "secondLanguagePhoneticScripts";

	private static final String CHAPTERS = "chapters";
	private static final String TOPICS = "topics";

	private static final String DESCRIPTION = "description";

	private static final String LEARN_LEVEL = "learnLevel";
	private static final String RELEVANCE_LEVEL = "relevanceLevel";
	
	public void saveVocablesToXML(ObservableList<Vocable> vocableList, File vocableFile) {
		
		try {
			// create an XMLOutputFactory
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			
			// create XMLEventWriter
			XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(vocableFile));
			
			// create an EventFactory
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent newLine = eventFactory.createCharacters("\n");
			XMLEvent tab = eventFactory.createCharacters("\t");
			
			// create and write start tag
			StartDocument startDocument = eventFactory.createStartDocument();
			eventWriter.add(startDocument);
			eventWriter.add(newLine);
			
			// create list open tag
			StartElement configStartElement = eventFactory.createStartElement("", "", "list");
			eventWriter.add(configStartElement);
			eventWriter.add(newLine);
			
			
			// write the different nodes
			vocableList.forEach((Vocable vocable) -> {
				try {
					
					StartElement vocableStartElement = eventFactory.createStartElement("", "", "vocable");
					eventWriter.add(tab);
					eventWriter.add(vocableStartElement);
					eventWriter.add(newLine);
					
					
					createNode(eventWriter, FIRST_LANGUAGE_TRANSLATIONS, vocable.getFirstLanguageTranslationsAsString());
					createNode(eventWriter, FIRST_LANGUAGE_PHONETIC_SCRIPTS, vocable.getFirstLanguagePhoneticScriptsAsString());
					
					createNode(eventWriter, SECOND_LANGUAGE_TRANSLATIONS, vocable.getSecondLanguageTranslationsAsString());
					createNode(eventWriter, SECOND_LANGUAGE_PHONETIC_SCRIPTS, vocable.getSecondLanguagePhoneticScriptsAsString());
					
					createNode(eventWriter, TOPICS, vocable.getTopicsAsString());
					createNode(eventWriter, CHAPTERS, vocable.getChaptersAsString());
					
					createNode(eventWriter, DESCRIPTION, vocable.getDescription());
					
					createNode(eventWriter, LEARN_LEVEL, vocable.getLearnLevel());
					createNode(eventWriter, RELEVANCE_LEVEL, vocable.getRelevanceLevel());
					
					EndElement vocableEndEvent = eventFactory.createEndElement("", "", "vocable");
					eventWriter.add(tab);
					eventWriter.add(vocableEndEvent);
					eventWriter.add(newLine);
					
				} catch (XMLStreamException ex) {
					Logger.getLogger(StaxVocableWriter.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
			
			// end of list
			eventWriter.add(eventFactory.createEndElement("", "", "list"));
			eventWriter.add(newLine);
			
			// end of document
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.close();
			
		} catch (XMLStreamException ex) {
			Logger.getLogger(StaxVocableWriter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(StaxVocableWriter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createCharacters("\n");
		XMLEvent tab = eventFactory.createCharacters("\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);

	}

}
