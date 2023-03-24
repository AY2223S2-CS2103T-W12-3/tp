package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;
import seedu.address.model.person.Region.Regions;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String meeting} into a {@code Meeting}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if start time given is after the end time given
     */
    public static Meeting parseMeeting(String desc, String start, String end) {
        requireNonNull(desc);
        requireNonNull(start);
        requireNonNull(end);
        String trimmedDesc = desc.trim();

        LocalDateTime parsedStart = parseStart(start.trim());
        LocalDateTime parsedEnd = parseEnd(start.trim(), end.trim());

        return new Meeting(trimmedDesc, parsedStart, parsedEnd);
    }

    /**
     * Parses {@code String dateTime} into a pair of
     * {@code LocalDateTime} objects
     */
    public static LocalDateTime[] parseDateTime(String dateTime) {
        String[] dateTimeStrings = dateTime.trim().split(" ");

        return new LocalDateTime[]{
            parseStart(dateTimeStrings[0] + " " + dateTimeStrings[1]),
            parseEnd(dateTimeStrings[0] + " " + dateTimeStrings[1],
                dateTimeStrings[2] + " " + dateTimeStrings[3])
        };
        /*
        String[] dateString = dateTimeStrings[0].split("-");
        String[] startTimeString = dateTimeStrings[1].split(":");
        String[] endTimeString = dateTimeStrings[2].split(":");

        int day = Integer.parseInt(dateString[0]);
        int month = Integer.parseInt(dateString[1]);
        int year = Integer.parseInt(dateString[2]);
        int startHour = Integer.parseInt(startTimeString[0]);
        int startMinute = Integer.parseInt(startTimeString[1]);
        int endHour = Integer.parseInt(endTimeString[0]);
        int endMinute = Integer.parseInt(endTimeString[1]);

        return new LocalDateTime[] {
            LocalDateTime.of(year, month, day, startHour, startMinute),
            LocalDateTime.of(year, month, day, endHour, endMinute)
        };
         */
    }

    /**
     * Parses a {@code String region} into the correct {@code Regions} enum
     */
    public static Regions parseRegion(String region) throws ParseException {
        requireNonNull(region); // Do we need to throw a ParserException here?
        String processedInputRegion = region.trim().toUpperCase();
        Regions[] allRegions = Regions.values();
        for (Regions r : allRegions) {
            if (r.toString().equals(processedInputRegion)) {
                return r;
            }
        }
        throw new ParseException(Region.MESSAGE_CONSTRAINTS);
    }

    public static String parseMeetingDescription(String desc) {
        return desc.trim();
    }

    /**
     * Parses a {@code String} into a {@code LocalDateTime}
     * @param start String of meeting start
     * @return meeting start parsed to LocalDateTime
     */
    public static LocalDateTime parseStart(String start) {
        String[] dateTime = start.split(" ");
        String[] date = dateTime[0].split("-");
        String[] time = dateTime[1].split(":");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int startHour = Integer.parseInt(time[0]);
        int startMinute = Integer.parseInt(time[1]);
        return LocalDateTime.of(year, month, day, startHour, startMinute);
    }

    /**
     * Parses a {@code String} into a {@code LocalDateTime}
     * @param start String of meeting start
     * @param end String of meeting end
     * @return meeting end parsed to LocalDateTime
     */
    public static LocalDateTime parseEnd(String start, String end) {
        LocalDateTime startTime = parseStart(start);
        String[] endTimes = end.split(":");
        int endHour = Integer.parseInt(endTimes[0]);
        int endMin = Integer.parseInt(endTimes[1]);
        if (endHour - startTime.getHour() < 0) {
            LocalDateTime endTime = startTime.plusDays(1).withHour(endHour).withMinute(endMin);
            return endTime;
        }
        LocalDateTime endTime = startTime.withHour(endHour).withMinute(endMin);
        return endTime;
    }
}
