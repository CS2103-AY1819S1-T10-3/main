package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GroupaddCommand;
import seedu.address.logic.commands.GroupdeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Create a new groupAdd command with given user input
 */
public class GroupAddCommandParser<T extends Command> implements Parser<GroupaddCommand> {
    @Override
    public GroupaddCommand parse(String userInput) throws ParseException {
        Tag tag = new Tag(userInput.trim());
        return new GroupaddCommand(tag);
    }
}
