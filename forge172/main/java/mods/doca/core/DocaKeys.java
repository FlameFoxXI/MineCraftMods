package mods.doca.core;

public class DocaKeys
{
	private String username;
	private int timer;
	private boolean[] keys = new boolean[DocaTools.KEY_MAX];

	public DocaKeys(String name)
	{
		this.username = name;
		this.timer = 0;
		for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
		{
			this.keys[i] = false;
		}
	}

	public String getUserName()
	{
		return this.username;
	}

	public void setUserName(String name)
	{
		this.username = name;
	}

	public int getTimer()
	{
		return this.timer;
	}

	public void setTimer(int time)
	{
		this.timer = time;
	}

	public boolean getKeys(int no)
	{
		return this.keys[no];
	}

	public void setKeys(int no, boolean type)
	{
		this.keys[no] = type;
	}

	public void clearAll()
	{
		for (int i = DocaTools.KEY_MIN; i < DocaTools.KEY_MAX; i++)
		{
			this.keys[i] = false;
		}
	}
}
