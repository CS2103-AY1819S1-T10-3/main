package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.Word;
import seedu.address.model.tag.Tag;




/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the word list with {@code words}.
     * {@code words} must not contain duplicate words.
     */
    public void setPersons(List<Word> words) {
        this.persons.setPersons(words);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// word-level operations

    /**
     * Returns true if a word with the same identity as {@code word} exists in the address book.
     */
    public boolean hasPerson(Word word) {
        requireNonNull(word);
        return persons.contains(word);
    }

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        for (Word person:persons) {
            if (person.getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Adds a word to the address book.
     * The word must not already exist in the address book.
     */
    public void addPerson(Word p) {
        persons.add(p);
    }

    /**
     * Replaces the given word {@code target} in the list with {@code editedWord}.
     * {@code target} must exist in the address book.
     * The word identity of {@code editedWord} must not be the same as another existing word in the address book.
     */
    public void updatePerson(Word target, Word editedWord) {
        requireNonNull(editedWord);

        persons.setPerson(target, editedWord);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Word key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Word> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }


}
