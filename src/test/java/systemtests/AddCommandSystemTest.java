package systemtests;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.MEANING_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.learnvocabulary.testutil.TypicalWords.ALICE;
import static seedu.learnvocabulary.testutil.TypicalWords.AMY;
import static seedu.learnvocabulary.testutil.TypicalWords.BOB;
import static seedu.learnvocabulary.testutil.TypicalWords.CARL;
import static seedu.learnvocabulary.testutil.TypicalWords.HOON;
import static seedu.learnvocabulary.testutil.TypicalWords.IDA;
import static seedu.learnvocabulary.testutil.TypicalWords.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.logic.commands.RedoCommand;
import seedu.learnvocabulary.logic.commands.UndoCommand;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;
import seedu.learnvocabulary.testutil.WordUtil;

public class AddCommandSystemTest extends LearnVocabularySystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a word without tags to a non-empty learnvocabulary, command with leading spaces and trailing spaces
         * -> added
         */
        Word toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + " " + NAME_DESC_AMY + "  " + MEANING_DESC + " " + TAG_DESC_FRIEND + " ";

        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addWord(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a word with all fields same as another word in the learnvocabulary book except name -> added */
        toAdd = new WordBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + MEANING_DESC + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a word with all fields same as another word in the learnvocabulary book except phone and email
         * -> added
         */
        toAdd = new WordBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        command = WordUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty learnvocabulary book -> added */
        deleteAllWords();
        assertCommandSuccess(ALICE);

        /* Case: add a word with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + MEANING_DESC
                + NAME_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a word, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the word list before adding -> added */
        showWordsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a word card is selected --------------------------- */

        /* Case: selects first card in the word list, add a word -> added, card selection remains unchanged */
        selectWord(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate word -> rejected */
        command = WordUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORD);

        /* Case: add a duplicate word except with different phone -> rejected */
        toAdd = new WordBuilder(HOON).build();
        command = WordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORD);

        /* Case: add a duplicate word except with different email -> rejected */
        toAdd = new WordBuilder(HOON).build();
        command = WordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORD);

        /* Case: add a duplicate word except with different learnvocabulary -> rejected */
        toAdd = new WordBuilder(HOON).build();
        command = WordUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORD);

        /* Case: add a duplicate word except with different tags -> rejected */
        command = WordUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORD);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + MEANING_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MEANING_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing learnvocabulary -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MEANING_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + WordUtil.getWordDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + MEANING_DESC;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MEANING_DESC + PHONE_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code WordListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Word toAdd) {
        assertCommandSuccess(WordUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Word)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Word)
     */
    private void assertCommandSuccess(String command, Word toAdd) {
        Model expectedModel = getModel();
        expectedModel.addWord(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Word)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code WordListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Word)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code WordListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see LearnVocabularySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
