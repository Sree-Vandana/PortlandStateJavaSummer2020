package edu.pdx.cs410J.sreev2;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.CALLEE_NUMBER_PARAMETER;
import static edu.pdx.cs410J.sreev2.PhoneBillURLParameters.START_TIME_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MessagesTest {

  @Test
  public void malformedWordAndDefinitionReturnsNull() {
    assertThat(Messages.parseDictionaryEntry("blah"), nullValue());
  }

  @Test
  public void canParseFormattedDictionaryEntryPair() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));
  }

  @Test
  public void canParseFormattedDictionaryEntryWithoutLeadingSpaces() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    String trimmed = formatted.trim();
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(trimmed);
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));

  }

  @Test
  public void nullDefinitionIsParsedAsNull() {
    String word = "testWord";
    String definition = null;
    String formatted = Messages.formatDictionaryEntry(word, definition);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));
  }

  @Test
  public void canParseFormattedDictionary() {
    Map<String, String> dictionary = new HashMap<>();

    for (int i = 0; i < 5; i++) {
      String word = String.valueOf(i);
      String definition = "QQ" + word;
      dictionary.put(word, definition);
    }

    StringWriter sw = new StringWriter();
    Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);

    String formatted = sw.toString();

    Map<String, String> actual = Messages.parseDictionary(formatted);
    assertThat(actual, equalTo(dictionary));
  }

  @Test
  public void testingNoPhoneBillCustomerStatement(){
    assertThat(Messages.noPhoneBillForCustomer("xyz"), containsString("No phone bill for customer"));
  }

  @Test
  public void testingStartTimeBeforeEndTimeStatement(){
    assertThat(Messages.startTimeBeforEndTime(), containsString("Start date and time can not be equals or"));
  }

  @Test
  public void malformattedDateOrTimeMsg(){
    assertThat(Messages.malformattedDateOrTime(START_TIME_PARAMETER, "01/15/20/2 10:30 pm"), containsString("is not in correct format. please follow the format"));
  }

  @Test
  public void malformattedPhoneNumber(){
    assertThat(Messages.malformattedPhoneCall(CALLEE_NUMBER_PARAMETER), containsString("is not in correct format. please follow the format"));
  }
}