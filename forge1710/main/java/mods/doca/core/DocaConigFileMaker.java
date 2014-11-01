package mods.doca.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DocaConigFileMaker
{
	private File configFile;
	private String configEncode = "UTF-8";
	private static final String MATCH_CHARS = "._-";

	private Map<String, Map<String, DocaConfigObject>> DocaConfigObjects = new TreeMap<String, Map<String, DocaConfigObject>>();

	public DocaConigFileMaker(File file)
	{
		this.configFile = file;
		load();
	}

	public DocaConfigObject get(String category, String key, int value, String comment)
	{
		DocaConfigObject tmpConfigObject = get(category, key, Integer.toString(value), comment, DocaConfigObject.INTEGER);
		if (!tmpConfigObject.isValue(DocaConfigObject.INTEGER))
		{
			tmpConfigObject.setValue(value);
		}
		return tmpConfigObject;
	}

	public DocaConfigObject get(String category, String key, boolean value, String comment)
	{
		DocaConfigObject tmpConfigObject = get(category, key, Boolean.toString(value), comment, DocaConfigObject.BOOLEAN);
		if (!tmpConfigObject.isValue(DocaConfigObject.BOOLEAN))
		{
			tmpConfigObject.setValue(value);
		}
		return tmpConfigObject;
	}

	public DocaConfigObject get(String category, String key, double value, String comment)
	{
		DocaConfigObject tmpConfigObject = get(category, key, Double.toString(value), comment, DocaConfigObject.DOUBLE);
		if (!tmpConfigObject.isValue(DocaConfigObject.DOUBLE))
		{
			tmpConfigObject.setValue(value);
		}
		return tmpConfigObject;
	}

	public DocaConfigObject get(String category, String key, String value, String comment)
	{
		return get(category, key, value, comment, DocaConfigObject.STRING);
	}

	private DocaConfigObject get(String category, String key, String value, String comment, String type)
	{
		category = category.toLowerCase();
		Map<String, DocaConfigObject> tmpDocaConfigObjects = DocaConfigObjects.get(category);

		if(tmpDocaConfigObjects == null)
		{
			tmpDocaConfigObjects = new TreeMap<String, DocaConfigObject>();
			DocaConfigObjects.put(category, tmpDocaConfigObjects);
		}

		if (tmpDocaConfigObjects.containsKey(key))
		{
			DocaConfigObject tmpConfigObject = tmpDocaConfigObjects.get(key);

			if (tmpConfigObject.getType() == null)
			{
				tmpConfigObject = new DocaConfigObject(tmpConfigObject.getName(), tmpConfigObject.getString(), type);
				tmpConfigObject.setComment(comment);
				tmpDocaConfigObjects.put(key, tmpConfigObject);
			}
			else
			{
				tmpConfigObject.setType(type);
				tmpConfigObject.setComment(comment);
				//				tmpDocaConfigObjects.put(key, tmpConfigObject);
			}
			return tmpConfigObject;
		}
		else if (value != null)
		{
			DocaConfigObject tmpConfigObject = new DocaConfigObject(key, value, type);
			tmpConfigObject.setComment(comment);
			tmpDocaConfigObjects.put(key, tmpConfigObject);
			return tmpConfigObject;
		}
		else
		{
			return null;
		}
	}

	public void load()
	{
		BufferedReader tmpBufferedReader = null;
		try
		{
			if (configFile.getParentFile() != null)
			{
				configFile.getParentFile().mkdirs();
			}

			if (!configFile.exists() && !configFile.createNewFile())
			{
				return;
			}

			if (configFile.canRead())
			{
				FileInputStream fileinputstream = new FileInputStream(configFile);
				tmpBufferedReader = new BufferedReader(new InputStreamReader(fileinputstream, configEncode));

				String tmpType = null;
				String tmpScopeName = null;
				String tmpReadLine = null;
				Map<String, DocaConfigObject> tmpDocaConfigObjects = null;

				while (true)
				{
					tmpReadLine = tmpBufferedReader.readLine();

					if (tmpReadLine == null)
					{
						break;
					}

					int nameStart = -1, nameEnd = -1;
					boolean skip = false;

					for (int i = 0; i < tmpReadLine.length() && !skip; ++i)
					{
						if (Character.isLetterOrDigit(tmpReadLine.charAt(i)) || MATCH_CHARS.indexOf(tmpReadLine.charAt(i)) != -1)
						{
							if (nameStart == -1)
							{
								nameStart = i;
							}
							nameEnd = i;
						}
						else if (Character.isWhitespace(tmpReadLine.charAt(i)))
						{
						}
						else
						{
							switch (tmpReadLine.charAt(i))
							{
							case '#':
								skip = true;
								continue;

							case '{':
								String scopeName = tmpReadLine.substring(nameStart, nameEnd + 1);

								tmpDocaConfigObjects = DocaConfigObjects.get(scopeName);
								if (tmpDocaConfigObjects == null)
								{
									tmpDocaConfigObjects = new TreeMap<String, DocaConfigObject>();
									DocaConfigObjects.put(scopeName, tmpDocaConfigObjects);   
									tmpScopeName = scopeName;
								}
								break;

							case '}':
								tmpDocaConfigObjects = null;
								tmpScopeName = null;
								break;

							case '=':
								String propertyName = tmpReadLine.substring(nameStart, nameEnd + 1);

								if (tmpDocaConfigObjects != null && tmpScopeName != null && tmpType != null)
								{
									DocaConfigObject tempDocaConfigObject = new DocaConfigObject();
									tempDocaConfigObject.setName(propertyName);
									tempDocaConfigObject.setValue(tmpReadLine.substring(i + 1));
									tempDocaConfigObject.setType(tmpType);
									tempDocaConfigObject.setComment("***************");
									i = tmpReadLine.length();

									tmpDocaConfigObjects.put(propertyName, tempDocaConfigObject);	
									DocaConfigObjects.put(tmpScopeName, tmpDocaConfigObjects);    
								}
								break;

							case ':':
								tmpType = DocaConfigObject.getTypeString(tmpReadLine.substring(nameStart, nameEnd + 1).charAt(0));
								nameStart = nameEnd = -1;
								break;

							default:
								break;
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (tmpBufferedReader != null)
			{
				try 
				{
					tmpBufferedReader.close();
				} catch (IOException e){}
			}
		}
	}

	public void save()
	{
		try
		{
			if (configFile.getParentFile() != null)
			{
				configFile.getParentFile().mkdirs();
			}

			if (!configFile.exists() && !configFile.createNewFile())
			{
				return;
			}

			if (configFile.canWrite())
			{
				FileOutputStream tmpFileOutputStream = new FileOutputStream(configFile);
				BufferedWriter tmpBufferedWriter = new BufferedWriter(new OutputStreamWriter(tmpFileOutputStream, configEncode));

				tmpBufferedWriter.write("# Configuration file (UTF-8)\r\n");
				tmpBufferedWriter.write("# update : " + DateFormat.getInstance().format(new Date()) + "\r\n");
				tmpBufferedWriter.write("\r\n");

				for(Entry<String, Map<String, DocaConfigObject>> tmpDocaConfigObjects : DocaConfigObjects.entrySet())
				{
					tmpBufferedWriter.write("####################\r\n");
					tmpBufferedWriter.write("# " + tmpDocaConfigObjects.getKey() + " \r\n");
					tmpBufferedWriter.write("####################\r\n\r\n");

					tmpBufferedWriter.write(tmpDocaConfigObjects.getKey() + " {\r\n");

					for (DocaConfigObject property : tmpDocaConfigObjects.getValue().values())
					{
						if (property.getComment() != null)
						{
							tmpBufferedWriter.write("   # " + property.getComment() + "\r\n");
						}

						tmpBufferedWriter.write("   " + property.getTypeChar() + ":" + property.getName() + "=" + property.getValue());
						tmpBufferedWriter.write("\r\n\r\n");
					}
					tmpBufferedWriter.write("}\r\n\r\n");
				}

				tmpBufferedWriter.close();
				tmpFileOutputStream.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
