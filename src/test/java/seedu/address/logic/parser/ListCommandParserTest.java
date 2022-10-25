package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_JURONG;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JURONG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.category.Category;
import seedu.address.model.person.Address;
import seedu.address.model.person.Gender;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noFiltersApplied_listAll() {
        assertParseSuccess(
                parser,
                PREAMBLE_WHITESPACE,
                new ListCommand(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Set.of()));
    }

    @Test
    public void parse_addressFilterApplied_listAddress() {
        assertParseSuccess(
                parser,
                ADDRESS_DESC_JURONG,
                new ListCommand(
                        Optional.of(new Address(VALID_ADDRESS_JURONG)),
                        Optional.empty(),
                        Optional.empty(),
                        Set.of()));
    }

    @Test
    public void parse_categoryFilterApplied_listCategory() {
        assertParseSuccess(
                parser,
                CATEGORY_DESC_AMY,
                new ListCommand(
                        Optional.empty(),
                        Optional.of(new Category(Category.PATIENT_SYMBOL)),
                        Optional.empty(),
                        Set.of()));
    }

    @Test
    public void parse_genderFilterApplied_listGender() {
        assertParseSuccess(
                parser,
                GENDER_DESC_AMY,
                new ListCommand(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(new Gender(Gender.FEMALE_SYMBOL)),
                        Set.of()));
    }

    @Test
    public void parse_tagFilterApplied_listTag() {
        assertParseSuccess(
                parser,
                TAG_DESC_FRIENDS,
                new ListCommand(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Set.of(new Tag(VALID_TAG_FRIENDS))));
    }

    @Test
    public void parse_allFiltersApplied_listAllFilters() {
        assertParseSuccess(
                parser,
                ADDRESS_DESC_JURONG + CATEGORY_DESC_AMY + GENDER_DESC_AMY + TAG_DESC_FRIENDS,
                new ListCommand(
                        Optional.of(new Address(VALID_ADDRESS_JURONG)),
                        Optional.of(new Category(Category.PATIENT_SYMBOL)),
                        Optional.of(new Gender(Gender.FEMALE_SYMBOL)),
                        Set.of(new Tag(VALID_TAG_FRIENDS))));
    }
}
