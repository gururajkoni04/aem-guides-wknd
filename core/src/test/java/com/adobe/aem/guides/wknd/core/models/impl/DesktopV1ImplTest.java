package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.guides.wknd.core.models.DesktopV1;
import com.adobe.aem.guides.wknd.core.models.DesktopV1Item;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Teaser;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class DesktopV1ImplTest {

	private final AemContext ctx = new AemContext();

	@Mock
	private Teaser teaser;

	@Mock
	private ListItem action;

	@Mock
	private Resource imageResource;

	@BeforeEach
	void setUp() throws Exception {
		ctx.addModelsForClasses(DesktopV1Impl.class, DesktopV1ItemImpl.class);
		ctx.create().resource("/apps/wknd/components/desktopv1",
				"jcr:primaryType", "cq:Component",
				"sling:resourceSuperType", "core/wcm/components/teaser/v2/teaser");
		ctx.load().json("/com/adobe/aem/guides/wknd/core/models/impl/DesktopV1ImplTest.json", "/content");
	}

	private DesktopV1 adapt(String path) {
		ctx.currentResource(path);
		DesktopV1 desktopV1 = ctx.request().adaptTo(DesktopV1.class);
		assertNotNull(desktopV1);
		return desktopV1;
	}

	/**
	 * Replaces the delegated Core Teaser with a deterministic test double, so the tests do not depend on
	 * whether the Core Components models can be instantiated inside AEM Mocks.
	 */
	private DesktopV1 adaptWithDelegate(String path, Teaser delegate) throws IllegalAccessException {
		DesktopV1 desktopV1 = adapt(path);
		FieldUtils.writeField(desktopV1, "delegate", delegate, true);
		return desktopV1;
	}

	@Test
	void testGetEarnTiles() {
		List<DesktopV1Item> tiles = adapt("/content/desktopv1").getEarnTiles();

		assertEquals(4, tiles.size());
		assertEquals("4%", tiles.get(0).getTitle());
		assertEquals("on DoorDash and Caviar orders", tiles.get(0).getDescription());
		assertEquals("3%", tiles.get(1).getTitle());
		assertEquals("on dining directly from a restaurant", tiles.get(1).getDescription());
		assertEquals("2%", tiles.get(2).getTitle());
		assertEquals("on grocery stores, online or in person", tiles.get(2).getDescription());
		assertEquals("1%", tiles.get(3).getTitle());
		assertEquals("on all other purchases", tiles.get(3).getDescription());
	}

	@Test
	void testGetEarnTiles_IsUnmodifiable() {
		List<DesktopV1Item> tiles = adapt("/content/desktopv1").getEarnTiles();

		assertThrows(UnsupportedOperationException.class, tiles::clear);
	}

	@Test
	void testGetEarnTiles_WithoutTiles() {
		List<DesktopV1Item> tiles = adapt("/content/without-tiles").getEarnTiles();

		assertNotNull(tiles);
		assertTrue(tiles.isEmpty());
	}

	@Test
	void testGetEarnTiles_WithEmptyTilesNode() {
		List<DesktopV1Item> tiles = adapt("/content/empty-tiles").getEarnTiles();

		assertNotNull(tiles);
		assertTrue(tiles.isEmpty());
	}

	@Test
	void testGetEarnTiles_SkipsTilesWithoutTitleAndTrimsValues() {
		List<DesktopV1Item> tiles = adapt("/content/invalid-tiles").getEarnTiles();

		assertEquals(1, tiles.size());
		assertEquals("2%", tiles.get(0).getTitle());
		assertNull(tiles.get(0).getDescription());
	}

	@Test
	void testGetExportedType() {
		assertEquals("wknd/components/desktopv1", adapt("/content/desktopv1").getExportedType());
	}

	@Test
	void testDelegatesToTeaser() throws Exception {
		List<ListItem> actions = Collections.singletonList(action);
		when(teaser.getTitle()).thenReturn("Welcome to the DoorDash Rewards Mastercard");
		when(teaser.getPretitle()).thenReturn("Make every purchase more rewarding");
		when(teaser.getDescription()).thenReturn("<p>Earn cash back on everyday spending</p>");
		when(teaser.getTitleType()).thenReturn("h1");
		when(teaser.getImageResource()).thenReturn(imageResource);
		when(teaser.isActionsEnabled()).thenReturn(true);
		when(teaser.getActions()).thenReturn(actions);
		when(teaser.getId()).thenReturn("desktopv1-1234567890");

		DesktopV1 desktopV1 = adaptWithDelegate("/content/desktopv1", teaser);

		assertEquals("Welcome to the DoorDash Rewards Mastercard", desktopV1.getTitle());
		assertEquals("Make every purchase more rewarding", desktopV1.getPretitle());
		assertEquals("<p>Earn cash back on everyday spending</p>", desktopV1.getDescription());
		assertEquals("h1", desktopV1.getTitleType());
		assertSame(imageResource, desktopV1.getImageResource());
		assertTrue(desktopV1.isActionsEnabled());
		assertEquals(actions, desktopV1.getActions());
		assertEquals("desktopv1-1234567890", desktopV1.getId());
		assertFalse(desktopV1.isEmpty());
	}

	@Test
	void testIsEmpty_WithoutTitle() throws Exception {
		when(teaser.getTitle()).thenReturn(null);

		DesktopV1 desktopV1 = adaptWithDelegate("/content/empty", teaser);

		assertTrue(desktopV1.isEmpty());
	}

	@Test
	void testIsEmpty_WithBlankTitle() throws Exception {
		when(teaser.getTitle()).thenReturn("   ");

		DesktopV1 desktopV1 = adaptWithDelegate("/content/desktopv1", teaser);

		assertTrue(desktopV1.isEmpty());
	}

	@Test
	void testGetActions_WhenDelegateReturnsNull() throws Exception {
		when(teaser.getActions()).thenReturn(null);

		DesktopV1 desktopV1 = adaptWithDelegate("/content/desktopv1", teaser);

		assertNotNull(desktopV1.getActions());
		assertTrue(desktopV1.getActions().isEmpty());
	}

	@Test
	void testWithoutDelegate() throws Exception {
		DesktopV1 desktopV1 = adaptWithDelegate("/content/desktopv1", null);

		assertNull(desktopV1.getTitle());
		assertNull(desktopV1.getPretitle());
		assertNull(desktopV1.getDescription());
		assertNull(desktopV1.getTitleType());
		assertNull(desktopV1.getImageResource());
		assertNull(desktopV1.getLink());
		assertNull(desktopV1.getId());
		assertNull(desktopV1.getData());
		assertFalse(desktopV1.isActionsEnabled());
		assertFalse(desktopV1.isTitleLinkHidden());
		assertFalse(desktopV1.isImageLinkHidden());
		assertTrue(desktopV1.getActions().isEmpty());
		assertTrue(desktopV1.isEmpty());
		assertEquals(4, desktopV1.getEarnTiles().size());
	}
}
