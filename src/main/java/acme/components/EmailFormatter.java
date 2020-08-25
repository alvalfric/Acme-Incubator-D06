
package acme.components;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.format.Formatter;

import acme.datatypes.Email;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.StringHelper;

public class EmailFormatter implements Formatter<Email> {

	@Override
	public String print(final Email object, final Locale locale) {
		assert object != null;
		assert locale != null;

		String result;
		String displayName, user, domain;

		displayName = String.format("%s", object.getDisplayName());
		user = String.format("%s", object.getUser());
		domain = String.format("%s", object.getDomain());

		if (object.getDisplayName() != null && !object.getDisplayName().isEmpty()) {
			result = String.format("%s <%s@%s>", displayName, user, domain);
		} else {
			result = String.format("%s@%s", user, domain);
		}

		return result;
	}

	@Override
	public Email parse(final String text, final Locale locale) throws ParseException {
		assert !StringHelper.isBlank(text);
		assert locale != null;

		Email result;
		String displayNameRegex, userRegex, domainRegex, emailRegex;
		Pattern pattern;
		Matcher matcher;
		String errorMessage;
		String displayName, user, domain;

		displayNameRegex = "([\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@#$€%ˆ&*(){}|~<>;:\\[\\]]*\\s)?";
		userRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")";
		domainRegex = "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])|(?:[a-z0-9-]*[a-z0-9])";
		emailRegex = String.format("^\\s*(?<DN>%1$s)(<)?(?<U>%2$s)@(?<D>%3$s)(>)?\\s*$", displayNameRegex, userRegex, domainRegex);

		pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		matcher = pattern.matcher(text);

		if (!matcher.find()) {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(errorMessage, 0);
		} else {
			displayName = matcher.group("DN").trim();
			user = matcher.group("U").toLowerCase();
			domain = matcher.group("D").toLowerCase();

			result = new Email();
			result.setDisplayName(displayName);
			result.setUser(user);
			result.setDomain(domain);
		}

		return result;
	}
}
