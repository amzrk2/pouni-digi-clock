import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// 资料来源：
// https://m.jb51.net/article/48678.htm

/**
 * 铃声播放类。用一个新的线程来播放铃声。<br/>
 * 每次从头开始播放，可以随时关闭 。<br/>
 * 关闭后需要构造一个新的线程类来播放。
 * 
 * @author 8f235831
 * @version 0.1.0
 * @see Thread
 */
public class AlarmMusic extends Thread
{
	private File musicFile;
	private SourceDataLine sourceDataLine = null;
	private AudioInputStream audioInputStream = null;

	/**
	 * 构造方法。<br/>
	 * <strong><big>不会对指定的文件进行格式检查！且文件错误不会抛出异常，也不会播放任何声音！</big></strong><br/>
	 * 修改音频文件时，应当测试文件是否能够播放。
	 * 
	 * @param musicFile 指定的音乐文件。
	 */
	public AlarmMusic(File musicFile)
	{
		this.musicFile = musicFile;
	}

	/**
	 * 线程方法，启动后立即开始播放。<br/>
	 * 通过这个类的<code>close</code>
	 */
	@Override
	public void run()
	{
		int count;
		byte tempBuffer[] = new byte[1024];
		AudioFormat audioFormat;

		try
		{
			// 初始化播放器。
			this.audioInputStream = AudioSystem
				.getAudioInputStream(this.musicFile);
			audioFormat = this.audioInputStream.getFormat();
			this.sourceDataLine = (SourceDataLine) AudioSystem
				.getLine(new DataLine.Info(SourceDataLine.class, audioFormat,
					AudioSystem.NOT_SPECIFIED));
			this.sourceDataLine.open(audioFormat);
			this.sourceDataLine.start();

			while (true)
			{
				try
				{
					// 尝试读取文件，异常代表希望终止播放，结束线程。
					count = this.audioInputStream.read(tempBuffer, 0,
						tempBuffer.length);
				}
				catch (Exception e)
				{
					// 终止播放，结束线程。
					this.close();
					return;
				}

				if (count == -1)
				{
					// 文件播放完毕，重置输入流，循环播放。
					this.audioInputStream = AudioSystem
						.getAudioInputStream(this.musicFile);
				}
				else if (count > 0)
				{
					// 继续读取，播放。
					this.sourceDataLine.write(tempBuffer, 0, count);
				}
			}
		}
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// 找不到文件抛出的异常。
			e.printStackTrace();
		}
		finally
		{
			this.close();
		}
	}

	/**
	 * 终止音乐播放，结束线程。对于一个实例，只能关闭一次。但反复调用这个方法也不会出现错误。
	 */
	public synchronized void close()
	{
		if (this.sourceDataLine != null)
		{
			if (this.audioInputStream != null)
			{
				try
				{
					this.audioInputStream.close();
					this.audioInputStream = null;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			this.sourceDataLine.drain();
			this.sourceDataLine.close();
			this.sourceDataLine = null;
		}
	}

	/**
	 * @return <code>true</code>代表正在播放，<code>false</code>代表播放停止。
	 */
	public boolean isPlaying()
	{
		return this.sourceDataLine != null;
	}
}
