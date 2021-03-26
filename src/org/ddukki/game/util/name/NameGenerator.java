package org.ddukki.game.util.name;

import java.util.ArrayList;

import org.ddukki.utilities.xml.XMLDoc;
import org.ddukki.utilities.xml.XMLElement;

public class NameGenerator {
	private static final String GEN_FILE = "D:\\git\\GameEngine\\namegen.xml";

	private ArrayList<Language> languages = new ArrayList<>();

	public NameGenerator() {
		XMLDoc doc = new XMLDoc(GEN_FILE);
		XMLElement[] xchildren = doc.getRootElement().getChildElements();
		for (XMLElement xel : xchildren) {
			Language lang = new Language(xel);
			languages.add(lang);
		}
	}

	public static void main(String[] args) {
		NameGenerator ng = new NameGenerator();
		LanguageRule lang = ng.languages.get(0).getRule("generational");
		System.out.println(ng.genName(lang));
		System.out.println(ng.genName(lang));
		System.out.println(ng.genName(lang));
		System.out.println(ng.genName(lang));
		System.out.println(ng.genName(lang));
		System.out.println(ng.genName(lang));
	}

	public String genName(LanguageRule l) {
		return l.generateWord();
	}

}
