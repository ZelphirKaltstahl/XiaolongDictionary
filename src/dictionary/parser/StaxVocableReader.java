/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.parser;

import dictionary.model.Vocable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author xiaolong
 */
public class StaxVocableReader {

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

	public List<Vocable> readVocablesFromXMLFile(File vocableFile) {

		List<Vocable> vocables = new ArrayList<>();

		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			// Setup a new eventReader
			InputStream in = new FileInputStream(vocableFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			// read the XML document
			Vocable vocable = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {// if an opening tag

					StartElement startElement = event.asStartElement();
					
					if (startElement.getName().getLocalPart().equals(VOCABLE)) {// If we have an item element, we create a new item
						//System.out.println("STARTING NEW VOCABLE");
						vocable = new Vocable();
						
						// We read the attributes from this tag and add the date
						// attribute to our object
						/*
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(DATE)) {
								item.setDate(attribute.getValue());
							}
						}
						*/
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(FIRST_LANGUAGE_TRANSLATIONS)) {
							//System.out.println("STARTING FLT");
							event = eventReader.nextEvent();
							vocable.setFirstLanguageTranslationsAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(FIRST_LANGUAGE_PHONETIC_SCRIPTS)) {
							//System.out.println("STARTING FLPS");
							event = eventReader.nextEvent();
							vocable.setFirstLanguagePhoneticScriptsAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(SECOND_LANGUAGE_TRANSLATIONS)) {
							//System.out.println("STARTING SLT");
							event = eventReader.nextEvent();
							vocable.setSecondLanguageTranslationsAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(SECOND_LANGUAGE_PHONETIC_SCRIPTS)) {
							//System.out.println("STARTING SLPS");
							event = eventReader.nextEvent();
							vocable.setSecondLanguagePhoneticScriptsAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(TOPICS)) {
							//System.out.println("STARTING TOPIC");
							event = eventReader.nextEvent();
							vocable.setTopicsAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(CHAPTERS)) {
							//System.out.println("STARTING CHAPTER");
							event = eventReader.nextEvent();
							vocable.setChaptersAsString(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(DESCRIPTION)) {
							//System.out.println("STARTING DESC");
							event = eventReader.nextEvent();
							vocable.setDescription(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(LEARN_LEVEL)) {
							//System.out.println("STARTING LEARN");
							event = eventReader.nextEvent();
							vocable.setLearnLevel(event.asCharacters().getData());
							continue;
						}
					}
					
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals(RELEVANCE_LEVEL)) {
							//System.out.println("STARTING RELE");
							event = eventReader.nextEvent();
							vocable.setRelevanceLevel(event.asCharacters().getData());
							continue;
						}
					}
				}
				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals(VOCABLE)) {
						vocables.add(vocable);
					}
				}

			}
		} catch (FileNotFoundException e) {
			Logger.getLogger(StaxVocableReader.class.getName()).log(Level.SEVERE, null, e);
		} catch (XMLStreamException ex) {
			Logger.getLogger(StaxVocableReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return vocables;
	}
}
