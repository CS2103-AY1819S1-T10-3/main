package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GroupdeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Create a new groupDelete command with given user input
 */
public class GroupDeleteCommandParser<T extends Command> implements Parser<GroupdeleteCommand> {
    @Override
    public GroupdeleteCommand parse(String userInput) throws ParseException {
        Tag tag = new Tag(userInput.trim());
        return new GroupdeleteCommand(tag);
    }
}
