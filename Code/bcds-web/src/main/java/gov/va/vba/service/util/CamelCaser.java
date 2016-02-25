package gov.va.vba.service.util;

import org.springframework.stereotype.Service;


@Service
public class CamelCaser {

	public String toCamelCase(String textWithSpace) {
		if (textWithSpace.trim().isEmpty()) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder(textWithSpace.trim().replaceAll("\\s+", "").length());
			String[] tempArray = textWithSpace.trim().split(" ");

			for (String word : tempArray) {
				if (!word.trim().isEmpty()) {
					if (!word.equals(tempArray[0])) {
						sb.append(word.substring(0, 1).toUpperCase());
						sb.append(word.substring(1).toLowerCase());
					} else {
						sb.append(word.toLowerCase());
					}

				}
			}
			return sb.toString().replaceAll("\\s+", "");
		}
	}
}
