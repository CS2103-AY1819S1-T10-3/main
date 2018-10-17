package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Word;
import seedu.address.model.tag.Tag;



/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Word> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a word with the same identity as {@code word} exists in the address book.
     */
    boolean hasPerson(Word word);

    /**
     * Deletes the given word.
     * The word must exist in the address book.
     */
    void deletePerson(Word target);

    /**
     * Adds the given word.
     * {@code word} must not already exist in the address book.
     */
    void addPerson(Word word);

    /**
     * Replaces the given word {@code target} with {@code editedWord}.
     * {@code target} must exist in the address book.
     * The word identity of {@code editedWord} must not be the same as another existing word in the address book.
     */
    void updatePerson(Word target, Word editedWord);

    /** Returns an unmodifiable view of the filtered word list */
    ObservableList<Word> getFilteredPersonList();

    /**
     * Updates the filter of the filtered word list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Word> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     *
     * @param tags set of tags that is entered by the user
     * @return whether the tag has existed in the list
     */
    boolean hasTag(Set<Tag> tags);

    /**
     *
     * @param tag set of tags that is entered by the user
     * @return whether the tag has existed in the list
     */
    boolean hasTag(Tag tag);


    /**
     * @param toDelete tag to delete as a word group
     */
    void deleteGroup(Tag toDelete);

    /**
     * @param toAdd tag to delete as a word group
     */
    void addGroup(Tag toAdd);
}
