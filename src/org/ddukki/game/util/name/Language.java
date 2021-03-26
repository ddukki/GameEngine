package org.ddukki.game.util.name;

import java.util.ArrayList;

import org.ddukki.utilities.xml.XMLElement;

/**
 * A class containing rules for word generation for a given language. These
 * rules should specify what words they are generating and what the purpose of
 * these rules are; for example, some rules may generate names for characters
 */
public class Language {
	ArrayList<LanguageRule> lRules = new ArrayList<>();
	private String name;

	public Language(XMLElement xel) {
		// Get the name of this language
		name = xel.getName();

		// Retrieve all the rules for this language
		XMLElement[] xRules = xel.getChildElements(LanguageRule.RULE);
		for (int i = 0; i < xRules.length; i++) {
			lRules.add(new LanguageRule(xRules[i]));
		}
	}

	public String getName() {
		return name;
	}

	public LanguageRule getRule(String rName) {
		for (LanguageRule lr : lRules) {
			if (lr.getName().compareToIgnoreCase(rName) == 0) {
				return lr;
			}
		}

		return null;
	}
}
