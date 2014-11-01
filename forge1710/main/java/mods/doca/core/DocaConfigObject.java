package mods.doca.core;

public class DocaConfigObject
{
	public final static String STRING = "String";
	public final static String INTEGER = "Integer";
	public final static String BOOLEAN = "Boolean";
	public final static String DOUBLE = "Double";

	private String name;
	private String value;
	private String comment;
	private String type;

	private static String[] values = {STRING, INTEGER, BOOLEAN, DOUBLE};

	public DocaConfigObject()
	{
		this.type = null;
	}

	public DocaConfigObject(String name, String value, String type)
	{
		this.name = name;
		this.value = value;
		this.type  = type;
	}

	public boolean isValue(String type)
	{
		try
		{
			if (type.equalsIgnoreCase(STRING))
			{
				return true;
			}
			else if (type.equalsIgnoreCase(INTEGER))
			{
				Integer.parseInt(this.value);
				return true;
			}
			else if (type.equalsIgnoreCase(BOOLEAN))
			{
				return ("true".equals(this.value.toLowerCase()) || "false".equals(this.value.toLowerCase()));
			}
			else if (type.equalsIgnoreCase(DOUBLE))
			{
				Double.parseDouble(this.value);
				return true;

			}
			return false;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	public String getString()
	{
		return this.value;
	}

	public String getValue()
	{
		return this.value;
	}
	public int getInt()
	{
		return getInt(-1);
	}

	public int getInt(int _default)
	{
		try
		{
			return Integer.parseInt(this.value);
		}
		catch (NumberFormatException e)
		{
			return _default;
		}
	}

	public boolean getBoolean(boolean _default)
	{
		if (isValue(BOOLEAN))
		{
			return Boolean.parseBoolean(this.value);
		}
		else
		{
			return _default;
		}
	}

	public double getDouble(double _default)
	{
		try
		{
			return Double.parseDouble(this.value);
		}
		catch (NumberFormatException e)
		{
			return _default;
		}
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setValue(int value)
	{
		this.value = Integer.toString(value);
	}

	public void setValue(boolean value)
	{
		this.value = Boolean.toString(value);
	}

	public void setValue(double value)
	{
		this.value = Double.toString(value);
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return this.type;
	}

	public void setComment(String com)
	{
		this.comment = com;
	}

	public String getComment()
	{
		return this.comment;
	}

	public void setType(String id)
	{
		for (String tmpString: values)
		{
			if (id.equalsIgnoreCase(tmpString))
			{
				this.type = tmpString;
				return;
			}
		}
		this.type = STRING;
	}

	public static String getTypeString(char id)
	{
		for (String tmpString: values)
		{
			if (tmpString.charAt(0) == id)
			{
				return tmpString;
			}
		}
		return STRING;
	}

	public char getTypeChar()
	{
		return this.type.charAt(0);
	}
}

