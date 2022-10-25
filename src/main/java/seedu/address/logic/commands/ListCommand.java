package seedu.address.logic.commands;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "ADDRESS: %s, CATEGORY: %s, GENDER: %s, TAG: %s";

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons with specifications:\n" + MESSAGE_ARGUMENTS;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all enrolled users who fit the specified criteria, "
            + "or all enrolled users if no criteria were specified.\n"
            + "Parameters: \n"
            + "<optional> c/ [CATEGORY]\n"
            + "<optional> g/ [GENDER]\n"
            + "<optional> a/ [ADDRESS]\n"
            + "<optional> t/ [TAG]\n"
            + "Example: " + COMMAND_WORD + "  "
            + "c/ nurse";

    private final Optional<Address> address;
    private final Optional<Category> category;
    private final Optional<Gender> gender;
    private final Set<Tag> tags;

    /**
     * @param a address to be filtered
     * @param c category (nurse/patient) to be filtered
     * @param g gender to be filtered
     * @param t tag to be filtered
     */
    public ListCommand(Optional<Address> a, Optional<Category> c, Optional<Gender> g, Set<Tag> t) {
        address = a;
        category = c;
        gender = g;
        tags = t;
    }

    @Override
    public CommandResult execute(Model model) {
        Predicate<Person> addressPredicate = testPerson -> address
                .map(address -> testPerson.getAddress().isSimilarTo(address)).orElse(true);
        Predicate<Person> categoryPredicate = testPerson -> category
                .map(category -> testPerson.getCategory().equals(category)).orElse(true);
        Predicate<Person> genderPredicate = testPerson -> gender.map(gender -> testPerson.getGender().equals(gender))
                .orElse(true);
        Predicate<Person> tagsPredicate = testPerson -> tags.isEmpty()
                ? true
                : testPerson.getTags().isEmpty()
                        ? false
                        : tags.stream().anyMatch(targetTag -> testPerson.getTags().contains(targetTag));
        Predicate<Person> predicate = addressPredicate.and(categoryPredicate).and(genderPredicate).and(tagsPredicate);

        model.updateFilteredPersonList(predicate);

        String tagString = tags.isEmpty()
                ? "NIL"
                : tags.stream().map(tag -> tag.tagName).collect(Collectors.joining(","));
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, address.map(addressObj -> addressObj.toString()).orElse("NIL"),
                        category.map(categoryObj -> categoryObj.toString()).orElse("NIL"),
                        gender.map(genderObj -> genderObj.toString()).orElse("NIL"), tagString));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // state check
        ListCommand e = (ListCommand) other;
        return address.equals(e.address)
                && category.equals(e.category)
                && gender.equals(e.gender)
                && tags.equals(e.tags);
    }
}
