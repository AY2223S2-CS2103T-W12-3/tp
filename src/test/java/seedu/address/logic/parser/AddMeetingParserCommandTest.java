package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.model.person.Meeting;

public class AddMeetingParserCommandTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();
    private final String nonEmptyMeeting = " 09-11-2001 11:30 12:30";

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // have MEETING
        String userInput = " 1 " + PREFIX_MEETING + NonEmptyMeeting;

        Meeting testMeeting = new Meeting(
            LocalDateTime.of(2001, 11, 9, 11, 30),
            LocalDateTime.of(2001, 11, 9, 12, 30)
        );

        AddMeetingCommand expectedCommand = new AddMeetingCommand(INDEX_FIRST_PERSON, testMeeting);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + " " + NonEmptyMeeting, expectedMessage);
    }
}
