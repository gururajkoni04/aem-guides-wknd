package com.adobe.aem.guides.wknd.core.models;

/**
 * Represents one earn tile of the Desktop V1 component.
 **/
public interface DesktopV1Item {

	/***
	 * @return the earn rate including the percent sign (for example 4%), or null when not authored.
	 */
	String getTitle();

	/***
	 * @return the supporting description, or null when not authored.
	 */
	String getDescription();
}
