package edu.pdx.cs410J.sreev2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class PhoneBillTextParser implements PhoneBillParser<PhoneBill> {
    private final Reader reader;

    public PhoneBillTextParser(Reader reader) {
        this.reader = reader;
    }

    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
        try {
            String customer = br.readLine();

            PhoneBill bill = new PhoneBill(customer);

            while (br.ready()) {
                String caller = br.readLine();
                if (caller == null) {
                    break;
                }
                bill.addPhoneCall(new PhoneCall(caller));
            }

            return bill;

        } catch (IOException e) {
            throw new ParserException("While parsing", e);
        }
    }
}
