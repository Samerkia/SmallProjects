/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author GauvinLuke
 *
 */
class BlocksTest {

	Blocks defaultBlock;
	Blocks block0;
	Blocks block1;
	Blocks block2;
	Blocks block3;
	Blocks block4;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	
	}

	/**
	 * Test method for {@link application.Blocks#Blocks()}.
	 */
	@Test
	void testBlocks() {
		defaultBlock = new Blocks();
		assertEquals(defaultBlock.getBlockLevel(), 4);
	}

	/**
	 * Test method for {@link application.Blocks#Blocks(int)}.
	 */
	@Test
	void testBlocksInt() {
		block0 = new Blocks(0);
		block1 = new Blocks(1);
		block2 = new Blocks(2);
		block3 = new Blocks(3);
		block4 = new Blocks(4);
		assertEquals(block0.getBlockLevel(), 0);
		assertEquals(block1.getBlockLevel(), 1);
		assertEquals(block2.getBlockLevel(), 2);
		assertEquals(block3.getBlockLevel(), 3);
		assertEquals(block4.getBlockLevel(), 4);
		
		Blocks testBlock;
		
		for(int i = 5; i < 500; i++)
		{
			testBlock = new Blocks(i);
			assertEquals(testBlock.getBlockLevel(), 4);
		}
		for(int i = -1; i > -500; i--)
		{
			testBlock = new Blocks(i);
			assertEquals(testBlock.getBlockLevel(), 0);
		}
		
	}

	/**
	 * Test method for {@link application.Blocks#destroyBlock()}.
	 */
	@Test
	void testDestroyBlock() {
		block0 = new Blocks(0);
		block1 = new Blocks(1);
		block2 = new Blocks(2);
		block3 = new Blocks(3);
		block4 = new Blocks(4);
		block4.destroyBlock();
		block3.destroyBlock();
		block2.destroyBlock();
		block1.destroyBlock();
		block0.destroyBlock();
		assertEquals(block4.getBlockLevel(), 3);
		assertEquals(block3.getBlockLevel(), 2);
		assertEquals(block2.getBlockLevel(), 1);
		assertEquals(block1.getBlockLevel(), 0);
		assertEquals(block0.getBlockLevel(), -1);
		
	}

	/**
	 * Test method for {@link application.Blocks#getRectangle()}.
	 */
	@Test
	void testGetRectangle() {
		block0 = new Blocks(0);
		
		Rectangle testRectangle = block0.getRectangle();
		assertEquals(testRectangle.getFill(), Color.BLUE);
		assertEquals(testRectangle.getWidth(), 145);
		assertEquals(testRectangle.getHeight(), 45);
		
	}

	/**
	 * Test method for {@link application.Blocks#getBlockLevel()}.
	 */
	@Test
	void testGetBlockLevel() {
		block0 = new Blocks(0);
		block1 = new Blocks(1);
		block2 = new Blocks(2);
		block3 = new Blocks(3);
		block4 = new Blocks(4);
		
		assertEquals(block0.getBlockLevel(), 0);
		assertEquals(block1.getBlockLevel(), 1);
		assertEquals(block2.getBlockLevel(), 2);
		assertEquals(block3.getBlockLevel(), 3);
		assertEquals(block4.getBlockLevel(), 4);
	}

}
