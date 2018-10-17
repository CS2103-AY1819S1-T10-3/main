package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;


public class GroupdeleteCommand extends Command {

    public static final String COMMAND_WORD = "groupdelete";
    public static final String MESSAGE_NO_GROUP = "The group typed does not exist.";
    public static final String MESSAGE_SUCCESS = "Group %1$s has been deleted";

    private final Tag toDelete;

    /**
     * Creates an GroupdeleteCommand to delete the specified {@code Word}
     */
    public GroupdeleteCommand(Tag tag) {
        requireNonNull(tag);
        toDelete = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(toDelete)) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }

        model.deleteGroup(toDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }
}
