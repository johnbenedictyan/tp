package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Gender;
import seedu.address.model.tag.Tag;

/**
 * Parses user input for the list command.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses user input for the list command.
     *
     * @param args user input, for filtering the list of displayed users
     * @return Filtered list, or list of all users if no filters were specified.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        if (args.length() == 0) {
            return new ListCommand(Optional.empty(), Optional.empty(), Optional.empty(), new HashSet<Tag>());
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_CATEGORY, PREFIX_GENDER,
                PREFIX_TAG);

        Optional<Address> address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
        Optional<Gender> gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER));
        Optional<Category> category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        return new ListCommand(address, category, gender, tagList);
    }

}
