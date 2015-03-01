/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customcontrols;

import dictionary.customdatastructures.DoubleLinkedList;
import dictionary.listeners.SettingsPropertyChangeListener;
import dictionary.model.Action;
import dictionary.model.Settings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * This is a custom {@link  Control} which displays a character of a given String and two buttons for
 * switching which of the Characters of the set text is displayed. Optionally a {@link Label} can be
 * displayed. To provide this functionality the {@link XLDBigCharacterBox} uses a
 * {@link DoubleLinkedList}.
 *
 * @author xiaolong
 */
public class XLDBigCharacterBox extends VBox implements SettingsPropertyChangeListener {

	private final DoubleLinkedList<Character> characters;
	private Text charactersText;
	//private int currentCharacterPosition;

	private Label charactersHeadingLabel;
	private String labelText;

	private TextFlow charactersTextFlow;
	private HBox buttonHBox;
	private Button nextButton;
	private Button previousButton;
	
	private String ignoredCharacters;
	
	private final HashMap<String, Action<String>> actionsForObservedSettingsChanges = new HashMap<>();

	/**
	 * This constructor creates a {@link XLDCharacterBox} with a label containing the String, which
	 * is specified in labelText.
	 *
	 * @param initialCharacters the initially set characters, if empty, a " " will be set.
	 * @param labelText the text which is shown on the label
	 */
	public XLDBigCharacterBox(String initialCharacters, String labelText) {
		this.characters = new DoubleLinkedList<>();
		this.labelText = labelText;
		List<Character> listOfCharacters = new ArrayList<>();
		for (Character character : initialCharacters.toCharArray()) {
			listOfCharacters.add(character);
		}
		this.characters.setValues(listOfCharacters);
	}

	/**
	 * This constructor creates a {@link XLDBigCharacterBox} without a label.
	 *
	 * @param initialCharacters the initially set characters, if empty, a " " will be set.
	 */
	public XLDBigCharacterBox(String initialCharacters) {
		this.characters = new DoubleLinkedList<>();
		List<Character> listOfCharacters = new ArrayList<>();
		for (Character character : initialCharacters.toCharArray()) {
			listOfCharacters.add(character);
		}
		this.characters.setValues(listOfCharacters);
	}

	/**
	 * This method initializes the {@link XLDBigCharacterBox} control. Controls which make the
	 * {@link  XLDBigCharacterBox} are instanciated and added. ActionListeners are registered
	 * afterwards.
	 */
	public void init() {
		initializeUIControls();
		addControls();
		addActionListeners();
		registerAsListener();
		setActionsForNotifications();
	}

	/**
	 * This method instanciates Controls, which make the {@link  XLDBigCharacterBox}.
	 */
	private void initializeUIControls() {
		setPadding(new Insets(10));

		setAlignment(Pos.TOP_CENTER);

		if (labelText != null) {
			charactersHeadingLabel = new Label(labelText);
			charactersHeadingLabel.setTextAlignment(TextAlignment.CENTER);
			charactersHeadingLabel.setAlignment(Pos.TOP_CENTER);
			charactersHeadingLabel.setMaxWidth(Double.MAX_VALUE);
		}

		charactersTextFlow = new TextFlow();
		charactersTextFlow.setTextAlignment(TextAlignment.CENTER);
		charactersText = new Text(String.valueOf(characters.getCurrentValue()));
		
		//Predicate<Font> fontPred = (font) -> font.getFamily().contains("WenQuanYi");
		if(Font.getFamilies().stream().anyMatch(fontFamily -> fontFamily.contains("WenQuanYi Zen Hei"))) {
			charactersText.setFont(new Font("WenQuanYi Zen Hei", 50));
		} else {
			charactersText.setFont(new Font("Dialog", 50));
		}

		buttonHBox = new HBox();
		buttonHBox.setAlignment(Pos.CENTER);
		previousButton = new Button("<--");
		nextButton = new Button("-->");
	}

	/**
	 * Controls, which make the {@link  XLDBigCharacterBox} are added to the
	 * {@link XLDBigCharacterBox}.
	 */
	private void addControls() {
		if (labelText != null) {
			getChildren().add(charactersHeadingLabel);
		}

		charactersTextFlow.getChildren().addAll(charactersText);
		getChildren().add(charactersTextFlow);

		buttonHBox.getChildren().addAll(previousButton, nextButton);
		getChildren().addAll(buttonHBox);
	}

	/**
	 * This method adds the action listeners to the buttons.
	 */
	private void addActionListeners() {
		nextButton.setOnAction((ActionEvent event) -> {
			showCharacter(characters.getNextValue());
		});

		previousButton.setOnAction((ActionEvent event) -> {
			showCharacter(characters.getPreviousValue());
		});
	}

	/**
	 * This method sets characters, which the {@link XLDBigCharacterBox} will be displaying.
	 *
	 * @param newCharacters characters, which the {@link XLDBigCharacterBox} will be displaying
	 */
	public void setCharacters(String newCharacters) {
		List<Character> listOfCharacters = new ArrayList<>();
		for (Character character : newCharacters.toCharArray()) {
			if(!ignoredCharacters.contains(character.toString())) {
				listOfCharacters.add(character);
			}
		}
		if(!listOfCharacters.isEmpty()) {
			characters.setValues(listOfCharacters);
			showCharacter(characters.getCurrentValue());
		}
	}

	/**
	 * This method returns the characters, which the {@link XLDBigCharacterBox} is displaying.
	 *
	 * @return the characters, which the {@link XLDBigCharacterBox} is displaying
	 */
	public String getCharacters() {
		String charactersAsString = "";
		characters.getValues().stream().forEach((character) -> {
			String concat = charactersAsString.concat(String.valueOf(character));
		});
		return charactersAsString;
	}

	/**
	 * Sets the character which is currently displayed.
	 *
	 * @param character the character which is currently displayed
	 */
	private void showCharacter(char character) {
		charactersText.setText(String.valueOf(character));
	}
	
	/**
	 * This method shows the previously hidden characters.
	 */
	public void showCharacters() {
		//showCharacter(characters.getCurrentValue());
		charactersText.setVisible(true);
	}
	
	/**
	 * This method hides the characters.
	 */
	public void hideCharacters() {
		charactersText.setVisible(false);
		//showCharacter(' ');
	}
	
	public void setIgnoredCharacters(String ignoredCharacters) {
		this.ignoredCharacters = ignoredCharacters;
	}
	
	private void registerAsListener() {
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().BIG_CHARACTER_BOX_LOOP_SETTING_NAME, this);
	}
	
	@Override
	public void update(String settingName, String settingValue) {
		actionsForObservedSettingsChanges.get(settingName).execute(settingValue);
	}
	
	private void setActionsForNotifications() {
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME,
			(Action<String>) (String value) -> {
				charactersText.setFont(new Font(value, charactersText.getFont().getSize()));
			}
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME,
			(Action<String>) (String value) -> {
				double fontSize = Double.parseDouble(value);
				charactersText.setFont(new Font(charactersText.getFont().getName(), fontSize));
			}
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().BIG_CHARACTER_BOX_LOOP_SETTING_NAME,
			(Action<String>) (String value) -> {
				System.out.println("Not yet implemented!");
			}
		);
	}
}
