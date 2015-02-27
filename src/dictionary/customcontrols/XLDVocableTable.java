/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customcontrols;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author XiaoLong
 * @param <T> the contained type
 */
public class XLDVocableTable<T> extends TableView {

	XLDBigCharacterBox xldBigCharacterBox;

	//private ObservableList<Vocable> initialVocables;
	public XLDVocableTable() {
		//empty
	}

	public XLDVocableTable(ObservableList<Vocable> data) {
		setItems(data);
	}

	public void initializeVocableTable() {

		TableColumn numberColumn = new TableColumn("#");
		numberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vocable, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Vocable, String> vocableCellDataFeature) {
				return new ReadOnlyObjectWrapper(getItems().indexOf(vocableCellDataFeature.getValue()) + "");
			}
		});
		numberColumn.maxWidthProperty().bind(this.widthProperty().multiply(.15));
		numberColumn.minWidthProperty().bind(numberColumn.maxWidthProperty());
		numberColumn.setResizable(false);
		//numberColumn.setMaxWidth(numberColumn.getMaxWidth()-20);

		try {
			TableColumn<Vocable, String> firstLanguageColumn = new TableColumn<>(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME));
			firstLanguageColumn.setCellValueFactory(new PropertyValueFactory<>("firstLanguageTranslationsAsString"));
			firstLanguageColumn.setMinWidth(100);

			TableColumn<Vocable, String> firstLanguagePhoneticScriptColumn = new TableColumn<>(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
			firstLanguagePhoneticScriptColumn.setCellValueFactory(new PropertyValueFactory<>("firstLanguagePhoneticScriptsAsString"));
			firstLanguagePhoneticScriptColumn.setMinWidth(100);

			TableColumn<Vocable, String> secondLanguagePhoneticScriptColumn = new TableColumn<>(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
			secondLanguagePhoneticScriptColumn.setCellValueFactory(new PropertyValueFactory<>("secondLanguagePhoneticScriptsAsString"));
			secondLanguagePhoneticScriptColumn.setMinWidth(100);

			TableColumn<Vocable, String> secondLanguageColumn = new TableColumn<>(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME));
			secondLanguageColumn.setCellValueFactory(new PropertyValueFactory<>("secondLanguageTranslationsAsString"));
			secondLanguageColumn.setMinWidth(100);

			getColumns().add(numberColumn);
			getColumns().add(firstLanguageColumn);
			getColumns().add(firstLanguagePhoneticScriptColumn);
			getColumns().add(secondLanguagePhoneticScriptColumn);
			getColumns().add(secondLanguageColumn);

		} catch (SettingNotFoundException ex) {
			Logger.getLogger(XLDVocableTable.class.getName()).log(Level.SEVERE, null, ex);
		}

		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		registerListChangeListeners();
		addListeners();
	}

	private void addListeners() {
		/*getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vocable>() {

			@Override
			public void changed(ObservableValue<? extends Vocable> observable, Vocable oldValue, Vocable newValue) {

				if (getSelectionModel().getSelectedItem() != null) {
					TableView.TableViewSelectionModel<Vocable> selectionModel = getSelectionModel();
					ObservableList<Vocable> selectedItems = selectionModel.getSelectedItems();

					if (selectedItems.size() == 1) {
						xldBigCharacterBox.setCharacters(selectedItems.get(0).getSecondLanguageTranslationsAsString());
					}

//					selectedItems.stream().forEach((vocable) -> {
//						System.out.println(
//								vocable.getFirstLanguageTranslations() + "|" + 
//								vocable.getFirstLanguagePhoneticScripts() + "|" + 
//								vocable.getSecondLanguagePhoneticScripts() + "|" + 
//								vocable.getSecondLanguageTranslations()
//						);
//					});
					//selectedItems.get(selectedItems.size()-1)
				}
			}
		});*/
		
		getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (getSelectionModel().getSelectedItem() != null) {
				TableView.TableViewSelectionModel<Vocable> selectionModel = getSelectionModel();
				ObservableList<Vocable> selectedItems = selectionModel.getSelectedItems();

				if (selectedItems.size() == 1) {
					xldBigCharacterBox.setCharacters(selectedItems.get(0).getSecondLanguageTranslationsAsString());
				}
			}
		});
	}

	private void registerListChangeListeners() {
		/*
		ManagerInstanceManager.getVocableManagerInstance().getVocableList().addListener((Change<? extends Vocable> change) -> {
			while (change.next()) {
				if (change.wasPermutated()) {
					//for (int i = change.getFrom(); i < change.getTo(); ++i) {
					//}
					//System.out.println("Notified of: Vocable List Change (Permutation)");
					System.out.println("The list of vocables of the vocable table (items) was permutated and no one does something about it!");

				} else if (change.wasUpdated()) {
					setItems(ManagerInstanceManager.getVocableManagerInstance().getSearchResultList());
					System.out.println("Notified of: Vocable List Change (Update)");

				} else if (change.wasReplaced()) {
					System.out.println("Notified of: Vocable List Change (Replace)");
					setItems(ManagerInstanceManager.getVocableManagerInstance().getSearchResultList());

				} else if (change.wasAdded()) {
					System.out.println("KUCKUCK Added!");
					change.getAddedSubList().stream().forEach((addedItem) -> {
						getItems().add(addedItem);
					});

				} else if (change.wasRemoved()) {
					System.out.println("KUCKUCK Removed!");
					change.getRemoved().stream().forEach((removedItem) -> {
						getItems().remove(removedItem);
					});

				} else {
					System.out.println("Something happened to the list of vocables of the vocable table (items) and no one does something about it!");
				} 
			}
			
		});
		*/
		
		ManagerInstanceManager.getVocableManagerInstance().addSearchResultVocableListChangeListener(
			(Change<? extends Vocable> change) -> {
				while (change.next()) {
					if (change.wasPermutated()) {
						/*for (int i = change.getFrom(); i < change.getTo(); ++i) {
						}
						System.out.println("Notified of: Vocable List Change (Permutation)");*/
						System.out.println("The list of vocables of the vocable table (items) was permutated and no one does something about it!");

					} else if (change.wasUpdated()) {
						setItems(ManagerInstanceManager.getVocableManagerInstance().getSearchResultList());
						System.out.println("Notified of: Vocable List Change (Update)");

					} else if (change.wasReplaced()) {
						System.out.println("Notified of: Vocable List Change (Replace)");
						setItems(ManagerInstanceManager.getVocableManagerInstance().getSearchResultList());

					} else if (change.wasAdded()) {
						System.out.println("KUCKUCK Added!");
						change.getAddedSubList().stream().forEach((addedItem) -> {
							getItems().add(addedItem);
						});

					} else if (change.wasRemoved()) {
						System.out.println("KUCKUCK Removed!");
						change.getRemoved().stream().forEach((removedItem) -> {
							getItems().remove(removedItem);
						});

					} else {
						System.out.println("Something happened to the list of vocables of the vocable table (items) and no one does something about it!");
					} 
				}
			}
		);
	}
	
	public void bindXLDBigCharacterBox(XLDBigCharacterBox xldBigCharacterBox) {
		this.xldBigCharacterBox = xldBigCharacterBox;
	}
	
	private void updateXLDBigCharacterBoxCharacter(String characters) {
		if(xldBigCharacterBox != null) {
			xldBigCharacterBox.setCharacters(characters);
		}
	}
}
