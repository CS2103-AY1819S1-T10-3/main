package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.MEANING_DESC;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_MEANING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.AMY;
import static seedu.learnvocabulary.testutil.TypicalWords.BOB;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Address;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Phone;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Word expectedWord = new WordBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWord));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWord));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWord));

        // multiple addresses - last learnvocabulary accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedWord));

        // multiple tags - all accepted
        Word expectedWordMultipleTags = new WordBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedWordMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Word expectedWord = new WordBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + MEANING_DESC + PHONE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedWord));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + VALID_MEANING + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_MEANING + VALID_PHONE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_MEANING + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing learnvocabulary prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_MEANING + PHONE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_MEANING + VALID_PHONE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MEANING_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + MEANING_DESC + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid learnvocabulary
        assertParseFailure(parser, NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + MEANING_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + MEANING_DESC + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
