/**
 * 
 */
package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author GauvinLuke
 *
 */
class SoundCreatorTest {

	SoundCreator sound;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link application.SoundCreator#SoundCreator()}.
	 */
	@Test
	void testSoundCreator() {
		sound = new SoundCreator();
		assertEquals(sound.gamePlaySound.getName(),  "gamebackground.wav");
		assertEquals(sound.ballBounceSound.getName(),  "ballbounce.wav");
		assertEquals(sound.blockBreakSound.getName(), "pop.wav");
	}

	/**
	 * Test method for {@link application.SoundCreator#playBlockBreakSound()}.
	 */
	@Test
	void testPlayBlockBreakSound() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link application.SoundCreator#playBallBounceSound()}.
	 */
	@Test
	void testPlayBallBounceSound() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link application.SoundCreator#playGamePlaySound()}.
	 */
	@Test
	void testPlayGamePlaySound() {
		fail("Not yet implemented"); // TODO
	}

}
