package org.ddukki.game.util.name;

import org.ddukki.game.util.RNGen;
import org.ddukki.utilities.xml.XMLElement;

/**
 * A language definition for how words are constructed
 * 
 */
public class LanguageRule {
	public static final String RULE = "rule";
	public static final String RULE_NAME = "name";
	private static final String PART = "part";

	private String name;
	private String[][] parts;

	public LanguageRule() {
		name = Integer.toString(hashCode());
		parts = new String[0][0];
	}

	public LanguageRule(XMLElement xel) {
		XMLElement rName = xel.getChildElements(RULE_NAME)[0];
		name = rName.getData();

		// Read off the parts for this particular language
		XMLElement[] xParts = xel.getChildElements(PART);
		parts = new String[xParts.length][];
		for (int i = 0; i < xParts.length; i++) {
			XMLElement iPart = xParts[i];
			String iString = iPart.getData();
			parts[i] = iString.split(";");
		}
	}

	public String generateWord() {
		if (parts.length == 0) {
			return "";
		}

		int n = parts.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			String[] pieces = parts[i];
			int l = pieces.length;
			int p = RNGen.generate(0, l);
			sb.append(pieces[p].trim());
		}

		return sb.toString();
	}

	public String getName() {
		return name;
	}
}