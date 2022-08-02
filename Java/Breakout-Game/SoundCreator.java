/**
 * 
 */
package application;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import javafx.animation.Timeline;


/**
 * @author Nicholas Raffel
 * Class that creates the sounds that play during game play
 */
public class SoundCreator {

	protected File blockBreakSound;
	protected File ballBounceSound;
	protected File gamePlaySound;
	protected AudioInputStream audioInStream;
	protected AudioFormat audioForm;
	protected Clip clip;
	protected DataLine.Info DataLineInfo;
	
	/**
	 * @author Nicholas Raffel
	 * Sound creation constructor creates the sounds the way they need to be 
	 */
	SoundCreator()
	{
		
		this.blockBreakSound = new File("pop.wav");
		this.ballBounceSound = new File("ballbounce.wav");
		this.gamePlaySound = new File("gamebackground.wav");
	}
	
	/**
	 * @author Nicholas Raffel
	 * this method creates the way for the sound to played for when the blocks are destroyed
	 */
	public void playBlockBreakSound()
	{
		//This is the try statement for playing the sound for when the block breaks
		try
		{
			this.blockBreakSound = new File("pop.wav");
			audioInStream = AudioSystem.getAudioInputStream(blockBreakSound);
			audioForm = audioInStream.getFormat();
			DataLineInfo = new DataLine.Info(Clip.class, audioForm);
			clip = (Clip) AudioSystem.getLine(DataLineInfo);
			clip.open(audioInStream);
			clip.start();
		}
		catch(Exception e)
		{
			System.out.println("Couldn't play pop.wav");
		}
		
	}
	
	/**
	 * @author Nicholas Raffel
	 * this method creates the way for the sound to played for when the blocks are destroyed
	 */
	public void playBallBounceSound()
	{
		//This is the try statement for playing the sound for when the ball bounces off a wall or the bar
		try
		{
			audioInStream = AudioSystem.getAudioInputStream(ballBounceSound);
			audioForm = audioInStream.getFormat();
			DataLineInfo = new DataLine.Info(Clip.class, audioForm);
			clip = (Clip) AudioSystem.getLine(DataLineInfo);
			clip.open(audioInStream);
			clip.start();
		}
		catch(Exception e)
		{
			System.out.println("Couldn't play pop.wav");
		}
	}
	
	/**
	 * @author Nicholas Raffel
	 * this method creates the way for the sound to played for when you're playing the game
	 */
	public void playGamePlaySound()
	{
		//This is the try statement for playing the game sound for when you're playing the game
		try
		{
			audioInStream = AudioSystem.getAudioInputStream(gamePlaySound);
			audioForm = audioInStream.getFormat();
			DataLineInfo = new DataLine.Info(Clip.class, audioForm);
			clip = (Clip) AudioSystem.getLine(DataLineInfo);
			clip.open(audioInStream);
			
			//Lowers the max volume for the game sound that it isn't overwhelming
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10.0f);
			
			clip.start();
			clip.loop(Timeline.INDEFINITE);
		}
		catch(Exception e)
		{
			System.out.println("Couldn't play pop.wav");
		}
	}
	
}
