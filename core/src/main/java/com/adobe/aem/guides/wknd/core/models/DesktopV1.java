package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

import com.adobe.cq.wcm.core.components.models.Teaser;

/**
 * Represents the Desktop V1 AEM Component for the WKND Site project: a Core Teaser v2 extension
 * with an additional list of earn-rate tiles.
 **/
public interface DesktopV1 extends Teaser {

	/***
	 * @return the authored earn tiles in authoring order; never null.
	 */
	List<DesktopV1Item> getEarnTiles();

	/***
	 * @return a boolean if the component has no content to display (no title resolved).
	 */
	boolean isEmpty();
}
