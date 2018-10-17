package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;


public class GroupaddCommand extends Command {

    public static final String COMMAND_WORD = "groupadd";
    public static final String MESSAGE_SUCCESS = "Group %1$s has been added";

    private final Tag toAdd;

    /**
     * Creates an GroupaddCommand to delete the specified {@code Word}
     */
    public GroupaddCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addGroup(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
