package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllPhonebills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhonebills();
  }

  @Test
  public void nonExsistingPhoneBillThrowsException() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    try {
      client.getPhoneBill("sree");
      Assert.fail("should have thrown a phonebillRestException");
    } catch (PhoneBillRestClient.PhoneBillRestException e) {
      assertThat(e.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND) );
    }
  }

  /*@Test
  public void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
   // Map<String, String> dictionary = client.getAllPhonebills();
    assertThat(dictionary.size(), equalTo(0));
  }*/


  @Test
  public void test2DefineOneWord() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String testWord = "TEST WORD";
    String testDefinition = "TEST DEFINITION";
    client.addDictionaryEntry(testWord, testDefinition);

    String definition = client.getPhoneBill(testWord);
    assertThat(definition, equalTo(testDefinition));
  }

  @Test
  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("word")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

}
